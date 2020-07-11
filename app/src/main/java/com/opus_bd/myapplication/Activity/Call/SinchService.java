package com.opus_bd.myapplication.Activity.Call;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.opus_bd.myapplication.Utils.Voicecall;
import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.video.VideoController;


public class SinchService extends Service {


    public static final int MESSAGE_PERMISSIONS_NEEDED = 1;
    public static final String REQUIRED_PERMISSION = "REQUIRED_PESMISSION";
    public static final String MESSENGER = "MESSENGER";
    public static final String CALL_ID = "CALL_ID";
    static final String TAG = SinchService.class.getSimpleName();
    private static final String APP_KEY = Voicecall.KEY;
    private static final String APP_SECRET = Voicecall.SECERET;
    private static final String ENVIRONMENT = Voicecall.HOSTNAME;
    private Messenger messenger;
    private SinchServiceInterface mSinchServiceInterface = new SinchServiceInterface();
    private SinchClient mSinchClient;
    private String mUserId;

    private StartFailedListener mListener;
    private PersistedSettings mSettings;

    @Override
    public void onCreate() {
        super.onCreate();
        mSettings = new PersistedSettings(getApplicationContext());

        // sessionManager = new SessionManager(this);

        attemptAutoStart();
    }

    private void attemptAutoStart() {
        String userName = mSettings.getUsername();
        if (!userName.isEmpty() && messenger != null) {
            start(userName);
        }
    }

    @Override
    public void onDestroy() {
        if (mSinchClient != null && mSinchClient.isStarted()) {
            mSinchClient.terminate();
        }
        super.onDestroy();
    }

    private void start(String userName) {
        boolean permissionsGranted = true;
        if (mSinchClient == null) {
            mSettings.setUsername(userName);
            mUserId = userName;
            createClient(userName);
        }
        try {
            //mandatory checks
            mSinchClient.checkManifest();
            //auxiliary check
            if (getApplicationContext().checkCallingOrSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                throw new MissingPermissionException(Manifest.permission.CAMERA);
            }
        } catch (MissingPermissionException e) {
            permissionsGranted = false;
            if (messenger != null) {
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString(REQUIRED_PERMISSION, e.getRequiredPermission());
                message.setData(bundle);
                message.what = MESSAGE_PERMISSIONS_NEEDED;
                try {
                    messenger.send(message);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (permissionsGranted) {
            Log.d(TAG, "Starting SinchClient");
            mSinchClient.start();
            Toast.makeText(this, "Starting SinchClient", Toast.LENGTH_SHORT).show();
        }
    }

    private void createClient(String userName) {
        mSinchClient = Sinch.getSinchClientBuilder().context(getApplicationContext()).userId(userName)
                .applicationKey(APP_KEY)
                .applicationSecret(APP_SECRET)
                .environmentHost(ENVIRONMENT).build();

        mSinchClient.setSupportCalling(true);
        mSinchClient.startListeningOnActiveConnection();

        mSinchClient.addSinchClientListener(new MySinchClientListener());
        mSinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
    }

    private void stop() {
        if (mSinchClient != null) {
            mSinchClient.terminate();
            mSinchClient = null;
        }
    }

    private boolean isStarted() {
        return (mSinchClient != null && mSinchClient.isStarted());
    }

    @Override
    public IBinder onBind(Intent intent) {
        messenger = intent.getParcelableExtra(MESSENGER);
        return mSinchServiceInterface;
    }

    public interface StartFailedListener {

        void onStartFailed(SinchError error);

        void onStarted();
    }

    public class SinchServiceInterface extends Binder {

        public Call callUserVideo(String userId) {
            return mSinchClient.getCallClient().callUserVideo(userId);
        }

        public String getUserName() {
            return mUserId;
        }

        public void retryStartAfterPermissionGranted() {
            SinchService.this.attemptAutoStart();
        }

        public boolean isStarted() {
            return SinchService.this.isStarted();
        }

        public void startClient(String userName) {
            start(userName);
        }

        public void stopClient() {
            stop();
        }

        public void setStartListener(StartFailedListener listener) {
            mListener = listener;
        }

        public Call getCall(String callId) {
            return mSinchClient.getCallClient().getCall(callId);
        }

        public VideoController getVideoController() {
            if (!isStarted()) {
                return null;
            }
            return mSinchClient.getVideoController();
        }

        public AudioController getAudioController() {
            if (!isStarted()) {
                return null;
            }
            return mSinchClient.getAudioController();
        }

        public Call callPhoneNumber(String phoneNumber) {
            return mSinchClient.getCallClient().callPhoneNumber(phoneNumber);
        }

        public Call callUser(String userId) {
            if (mSinchClient == null) {
                return null;
            }
            return mSinchClient.getCallClient().callUser(userId);
        }




    }

    private class MySinchClientListener implements SinchClientListener {

        @Override
        public void onClientFailed(SinchClient client, SinchError error) {
            if (mListener != null) {
                mListener.onStartFailed(error);
            }
            mSinchClient.terminate();
            mSinchClient = null;
        }

        @Override
        public void onClientStarted(SinchClient client) {
            Log.d(TAG, "SinchClient started");
            if (mListener != null) {
                mListener.onStarted();
            }
        }

        @Override
        public void onClientStopped(SinchClient client) {
            Log.d(TAG, "SinchClient stopped");
        }

        @Override
        public void onLogMessage(int level, String area, String message) {
            switch (level) {
                case Log.DEBUG:
                    Log.d(area, message);
                    break;
                case Log.ERROR:
                    Log.e(area, message);
                    break;
                case Log.INFO:
                    Log.i(area, message);
                    break;
                case Log.VERBOSE:
                    Log.v(area, message);
                    break;
                case Log.WARN:
                    Log.w(area, message);
                    break;
            }
        }

        @Override
        public void onRegistrationCredentialsRequired(SinchClient client,
                                                      ClientRegistration clientRegistration) {
        }


    }

    private class SinchCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, Call call) {

            if (call.getDetails().isVideoOffered()) {
                Intent intent = new Intent(SinchService.this, IncomingCallScreenActivity.class);
                intent.putExtra(CALL_ID, call.getCallId());
           /* intent.putExtra("USER_NAME", list.getProfile().getName());
            intent.putExtra("USER_PHOTO", list.getProfile().getPhoto());*/


                intent.putExtra("USER_NAME", "User!");
                intent.putExtra("USER_PHOTO", "pHOTO");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SinchService.this.startActivity(intent);
            } else {
                Intent intent = new Intent(SinchService.this, com.opus_bd.myapplication.Activity.VoiceCall.IncomingCallScreenActivity.class);
                intent.putExtra(CALL_ID, call.getCallId());
           /* intent.putExtra("USER_NAME", list.getProfile().getName());
            intent.putExtra("USER_PHOTO", list.getProfile().getPhoto());*/


             /* intent.putExtra("USER_NAME", "User!");
              intent.putExtra("USER_PHOTO", "pHOTO");*/
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SinchService.this.startActivity(intent);
            }
        }

           /* Api.getInstance().get_user_info(call.getRemoteUserId(), new ApiListener.profileFetchListener() {
                @Override
                public void onprofileFetchSuccess(FetchProfileResponse list) {
                    if (list != null && list.getProfile() != null) {
                        Log.d(TAG, "Incoming call");

                        if (sessionManager.getUserType().equals("d") & sessionManager.get_isCallEnabled()) {
                            DOCTOR_ID = sessionManager.getUserId();
                            DOCTOR_PHOTO = sessionManager.get_userPhoto();
                            DOCTOR_NAME = sessionManager.getUserName();

                            PATIENT_ID=""+list.getProfile().getId();
                            PATIENT_PHOTO = list.getProfile().getPhoto();
                            PATIENT_NAME = list.getProfile().getName();
                        } else {
                            DOCTOR_ID = ""+list.getProfile().getId();
                            DOCTOR_PHOTO = list.getProfile().getPhoto();
                            DOCTOR_NAME = list.getProfile().getName();
                        }
                        boolean shouldWeStartCall = true;
                        if (sessionManager.getUserType().equals("d") & !sessionManager.get_isCallEnabled()) {
                            shouldWeStartCall = false;
                        }
                        if (shouldWeStartCall == true) {
                            Intent intent = new Intent(SinchService.this, IncomingCallScreenActivity.class);
                            intent.putExtra(CALL_ID, call.getCallId());
                            intent.putExtra("USER_NAME", list.getProfile().getName());
                            intent.putExtra("USER_PHOTO", list.getProfile().getPhoto());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SinchService.this.startActivity(intent);
                        }


                    }

                }

                @Override
                public void onprofileFetchFailed(String msg) {

                }
            });


        }*/
    }


    private class PersistedSettings {

        private static final String PREF_KEY = "Sinch";
        private SharedPreferences mStore;

        public PersistedSettings(Context context) {
            mStore = context.getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        }

        public String getUsername() {
            return mStore.getString("Username", "");
        }

        public void setUsername(String username) {
            SharedPreferences.Editor editor = mStore.edit();
            editor.putString("Username", username);
            editor.commit();
        }
    }

}

