package com.opus_bd.myapplication.Activity.Call;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.opus_bd.myapplication.Activity.HomeActivity;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.AudioPlayer;
import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallState;
import com.sinch.android.rtc.video.VideoCallListener;
import com.sinch.android.rtc.video.VideoController;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class CallScreenActivity extends VoiceCallBaseActivity {

    static final String TAG = CallScreenActivity.class.getSimpleName();
    static final String ADDED_LISTENER = "addedListener";
    static final String VIEWS_TOGGLED = "viewsToggled";
    boolean mToggleVideoViewPositions = false;
    ImageView partnerPHOTO;
    Context context = this;
    ImageView switcheCamera, loudSpeaker;
    int LOUD_SPEAKER = 0;
    int MINI_SPEAKER = 1;
    int SPEAKER_MODE = 0;
    String CLIEND_ID = "xploreDoc";
    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;
    private String mCallId;
    private boolean mAddedListener = false;
    private boolean mLocalVideoViewAdded = false;
    private boolean mRemoteVideoViewAdded = false;
    private TextView mCallDuration;
    private TextView mCallState;
    private TextView mCallerName;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(ADDED_LISTENER, mAddedListener);
        savedInstanceState.putBoolean(VIEWS_TOGGLED, mToggleVideoViewPositions);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mAddedListener = savedInstanceState.getBoolean(ADDED_LISTENER);
        mToggleVideoViewPositions = savedInstanceState.getBoolean(VIEWS_TOGGLED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_screen);
        //NOW_SHOWING_ONLINE_DOC

        mAudioPlayer = new AudioPlayer(this);
        mCallDuration = findViewById(R.id.callDuration);
        mCallerName = findViewById(R.id.remoteUser);
        mCallState = findViewById(R.id.callState);
        partnerPHOTO = findViewById(R.id.partnerPHOTO);
        loudSpeaker = findViewById(R.id.loudSpeaker);
        switcheCamera = findViewById(R.id.switcheCamera);
        ImageView endCallButton = findViewById(R.id.hangupButton);

        endCallButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endCall();
            }
        });

        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
    }

    @Override
    public void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            if (!mAddedListener) {
                call.addCallListener(new SinchCallListener());
                mAddedListener = true;
            }
        } else {
            Log.e(TAG, "Started with invalid callId, aborting.");
            finish();
        }

        updateUI();
    }

    public String createChatName(Integer myID, Integer partnerID) {
        String ret = "";
        if (myID < partnerID)
            ret = myID + "-" + partnerID;
        else
            ret = partnerID + "-" + myID;

        return ret;
    }

    private void updateUI() {
        if (getSinchServiceInterface() == null) {
            return; // early
        }

        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            // mCallerName.setText(call.getRemoteUserId());

            //Glide.with(context).load(PHOTO_BASE + getIntent().getStringExtra("USER_PHOTO")).into(partnerPHOTO);
            Glide.with(context).load(R.drawable.app_logo).into(partnerPHOTO);


            //this is incomming
            String USER_NAME = getIntent().getStringExtra("USER_NAME");
            String USER_PHOTO = getIntent().getStringExtra("USER_PHOTO");

            String DOCTOR_NAME = getIntent().getStringExtra("DOCTOR_NAME");
            String PATIENT_NAME = getIntent().getStringExtra("PATIENT_NAME");
            mCallerName.setText(getIntent().getStringExtra("PATIENT_NAME"));
            // Glide.with(context).load(PHOTO_BASE + USER_PHOTO).into(partnerPHOTO);


            mCallState.setText(call.getState().toString());
            if (call.getDetails().isVideoOffered()) {
                if (call.getState() == CallState.ESTABLISHED) {
                    setVideoViewsVisibility(true, true);
                } else {
                    setVideoViewsVisibility(true, false);
                }
            }
        } else {
            setVideoViewsVisibility(false, false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mDurationTask.cancel();
        mTimer.cancel();
        removeVideoViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 500);
        updateUI();
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    private void endCall() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }


        startActivity(new Intent(this, HomeActivity.class));

        finishAffinity();


    }

    private String formatTimespan(int totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private void updateCallDuration() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            mCallDuration.setText(formatTimespan(call.getDetails().getDuration()));
        }
    }

    private ViewGroup getVideoView(boolean localView) {
        if (mToggleVideoViewPositions) {
            localView = !localView;
        }
        return (ViewGroup) (localView ? findViewById(R.id.localVideo) : findViewById(R.id.remoteVideo));
    }

    private void addLocalView() {
        if (mLocalVideoViewAdded || getSinchServiceInterface() == null) {
            return; //early
        }
        final VideoController vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ViewGroup localView = getVideoView(true);
                    localView.addView(vc.getLocalView());
                    localView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vc.toggleCaptureDevicePosition();
                        }
                    });
                    mLocalVideoViewAdded = true;
                    vc.setLocalVideoZOrder(!mToggleVideoViewPositions);
                }
            });
        }
    }

    private void addRemoteView() {
        final VideoController vc = getSinchServiceInterface().getVideoController();

        if (mRemoteVideoViewAdded || getSinchServiceInterface() == null) {
            return; //early
        }
        loudSpeaker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPEAKER_MODE == LOUD_SPEAKER) {
                    setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
                    SPEAKER_MODE = MINI_SPEAKER;
                } else {
                    setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
                    AudioController audioController = getSinchServiceInterface().getAudioController();
                    audioController.enableSpeaker();
                    SPEAKER_MODE = LOUD_SPEAKER;
                }

            }
        });
        switcheCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                vc.toggleCaptureDevicePosition();
            }
        });
        if (vc != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ViewGroup remoteView = getVideoView(false);
                    remoteView.addView(vc.getRemoteView());
                    remoteView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeVideoViews();
                            mToggleVideoViewPositions = !mToggleVideoViewPositions;
                            addRemoteView();
                            addLocalView();
                        }
                    });
                    mRemoteVideoViewAdded = true;
                    vc.setLocalVideoZOrder(!mToggleVideoViewPositions);
                }
            });
        }
    }

    private void removeVideoViews() {
        if (getSinchServiceInterface() == null) {
            return; // early
        }

        final VideoController vc = getSinchServiceInterface().getVideoController();
        if (vc != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((ViewGroup) (vc.getRemoteView().getParent())).removeView(vc.getRemoteView());
                    ((ViewGroup) (vc.getLocalView().getParent())).removeView(vc.getLocalView());
                    mLocalVideoViewAdded = false;
                    mRemoteVideoViewAdded = false;
                }
            });
        }
    }

    private void setVideoViewsVisibility(final boolean localVideoVisibile, final boolean remoteVideoVisible) {
        if (getSinchServiceInterface() == null)
            return;
        if (mRemoteVideoViewAdded == false) {
            addRemoteView();
        }
        if (mLocalVideoViewAdded == false) {
            addLocalView();
        }
        final VideoController vc = getSinchServiceInterface().getVideoController();

        if (vc != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    vc.getLocalView().setVisibility(localVideoVisibile ? View.VISIBLE : View.GONE);
                    vc.getRemoteView().setVisibility(remoteVideoVisible ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    private void addMissedCallNote(String patientId, String doctorId, long currentTimeMillis) {
        String partner_id;
        String own_id;
        String own_name;
        String partner_name;
        String roomName = createChatName(Integer.parseInt(patientId), Integer.parseInt(doctorId));
        // String partner_photo = sessionManager.get_userPhoto();
        String TYPE = "TYPE_MISSED_CALL";
        /*if (sessionManager.getUserType().equals("p")) {
            partner_id = DOCTOR_ID;
            partner_name = DOCTOR_NAME;
            own_id = PATIENT_ID;
            own_name = PATIENT_NAME;
            partner_photo=DOCTOR_PHOTO;

        } else {
            partner_id = PATIENT_ID;
            partner_name = PATIENT_NAME;
            own_id = DOCTOR_ID;
            own_name = DOCTOR_NAME;
            partner_photo=PATIENT_PHOTO;
        }
*/
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        //correncted code  here
        String msg = "Missed Call";

       /* String key = databaseReference.child(CLIEND_ID).child("chatHistory").child(roomName).push().getKey();
        ChatModel model = new ChatModel(String.valueOf(own_id), String.valueOf(partner_id), String.valueOf(msg), TYPE, String.valueOf(System.currentTimeMillis()));
        ChatModelFullBody chatModelFullBody = new ChatModelFullBody(String.valueOf(own_id), String.valueOf(partner_id), String.valueOf(msg), TYPE, String.valueOf(System.currentTimeMillis()), sessionManager.getUserName(), sessionManager.get_userPhoto(), partner_name, partner_photo);


        String finalPartner_photo = partner_photo;
        databaseReference.child(CLIEND_ID).child("chatHistory").child(roomName).child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CallScreenActivity.this, "Successfully sent", Toast.LENGTH_SHORT).show();
                databaseReference.child(CLIEND_ID).child("lastChatHistory").child(own_id).child(String.valueOf(partner_id)).setValue(chatModelFullBody);
                databaseReference.child(CLIEND_ID).child("lastChatHistory").child(String.valueOf(partner_id)).child(own_id).setValue(chatModelFullBody);
                Api.getInstance().appNotification(String.valueOf(partner_id), partner_name, msg, "chat", PHOTO_BASE + finalPartner_photo, "not_matter");


            }
        });*/

    }

    private class UpdateCallDurationTask extends TimerTask {

        @Override
        public void run() {
            CallScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                }
            });
        }
    }

    private class SinchCallListener implements VideoCallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended. Reason: " + cause.toString());
            mAudioPlayer.stopProgressTone();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            String endMsg = "Call ended: " + call.getDetails().toString();
            Toast.makeText(CallScreenActivity.this, endMsg, Toast.LENGTH_LONG).show();
            String DOCTOR_NAME = getIntent().getStringExtra("DOCTOR_NAME");
            String PATIENT_NAME = getIntent().getStringExtra("PATIENT_NAME");

            endCall();
          /*  if (call.getDetails().getDuration() == 0) {
                addMissedCallNote(PATIENT_ID, DOCTOR_ID, call.getDetails().getDuration());
            } else {
                //addSuccessCallNote
                addSuccessCallNote(PATIENT_ID, DOCTOR_ID, call.getDetails().getDuration());
            }

            Api.getInstance().postCallRecord(TOKEN, PATIENT_ID, DOCTOR_ID, "" + System.currentTimeMillis(), "" + call.getDetails().getDuration(), CALLER_ID, NOW_HITTING_SERVICE, DOCTOR_NAME, new ApiListener.VideoCallPostListener() {
                @Override
                public void onVideoCallPostSuccess(StatusMessage response) {

                    endCall();
                }

                @Override
                public void onVideoCallPostFailed(String msg) {
                    Toast.makeText(context, "API FAILED", Toast.LENGTH_SHORT).show();

                }
            });

            if (false) {
                if (SESSION_MANAGER.getUserType().equals("p")) {
                    Calendar calendar = Calendar.getInstance();
                    // PATIENT_ID = partner_id;
                    // DOCTOR_ID = sessionManager.getUserId();
                    Api.getInstance().postCallRecord(TOKEN, PATIENT_ID, call.getRemoteUserId(), "" + System.currentTimeMillis(), "" + call.getDetails().getDuration(), CALLER_ID, NOW_HITTING_SERVICE, DOCTOR_NAME, new ApiListener.VideoCallPostListener() {
                        @Override
                        public void onVideoCallPostSuccess(StatusMessage response) {

                            endCall();
                        }

                        @Override
                        public void onVideoCallPostFailed(String msg) {

                        }
                    });

                } else {
                    Calendar calendar = Calendar.getInstance();
                    Api.getInstance().postCallRecord(TOKEN, DOCTOR_ID, call.getRemoteUserId(), "" + System.currentTimeMillis(), "" + call.getDetails().getDuration(), CALLER_ID, NOW_HITTING_SERVICE, DOCTOR_NAME, new ApiListener.VideoCallPostListener() {
                        @Override
                        public void onVideoCallPostSuccess(StatusMessage response) {

                            endCall();
                        }

                        @Override
                        public void onVideoCallPostFailed(String msg) {

                        }
                    });

                }
            } else {
                endCall();
            }*/


        }

        @Override
        public void onCallEstablished(Call call) {


            Log.d(TAG, "Call established");
            mAudioPlayer.stopProgressTone();
            mCallState.setText(call.getState().toString());
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            AudioController audioController = getSinchServiceInterface().getAudioController();
            audioController.enableSpeaker();
            if (call.getDetails().isVideoOffered()) {
                setVideoViewsVisibility(true, true);
            }
            Log.d(TAG, "Call offered video: " + call.getDetails().isVideoOffered());
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

        @Override
        public void onVideoTrackAdded(Call call) {

        }

        @Override
        public void onVideoTrackPaused(Call call) {

        }

        @Override
        public void onVideoTrackResumed(Call call) {

        }
    }

//    private void addSuccessCallNote(String patientId, String doctorId, long currentTimeMillis) {
//        String partner_id;
//        String own_id;
//        String own_name;
//        String partner_name;
//        String roomName = createChatName(Integer.parseInt(PATIENT_ID), Integer.parseInt(DOCTOR_ID));
//        String partner_photo = sessionManager.get_userPhoto();
//        String TYPE = "TYPE_CALL_SUCCESS";
//        if (sessionManager.getUserType().equals("p")) {
//            partner_id = DOCTOR_ID;
//            partner_name = DOCTOR_NAME;
//            own_id = PATIENT_ID;
//            own_name = PATIENT_NAME;
//            partner_photo=DOCTOR_PHOTO;
//
//        } else {
//            partner_id = PATIENT_ID;
//            partner_name = PATIENT_NAME;
//            own_id = DOCTOR_ID;
//            own_name = DOCTOR_NAME;
//            partner_photo=PATIENT_PHOTO;
//        }
//
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//
//
//
//        //correncted code  here
//        String msg =String.valueOf(currentTimeMillis);
//
//        String key = databaseReference.child(CLIEND_ID).child("chatHistory").child(roomName).push().getKey();
//        ChatModel model = new ChatModel(String.valueOf(own_id), String.valueOf(partner_id), String.valueOf(msg), TYPE, String.valueOf(System.currentTimeMillis()));
//        ChatModelFullBody chatModelFullBody = new ChatModelFullBody(String.valueOf(own_id), String.valueOf(partner_id), String.valueOf(msg), TYPE, String.valueOf(System.currentTimeMillis()), sessionManager.getUserName(), sessionManager.get_userPhoto(), partner_name, partner_photo);
//
//
//        String finalPartner_photo = partner_photo;
//        databaseReference.child(CLIEND_ID).child("chatHistory").child(roomName).child(key).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(CallScreenActivity.this, "Successfully sent", Toast.LENGTH_SHORT).show();
//                databaseReference.child(CLIEND_ID).child("lastChatHistory").child(own_id).child(String.valueOf(partner_id)).setValue(chatModelFullBody);
//                databaseReference.child(CLIEND_ID).child("lastChatHistory").child(String.valueOf(partner_id)).child(own_id).setValue(chatModelFullBody);
//                Api.getInstance().appNotification(String.valueOf(partner_id), partner_name, msg, "chat", PHOTO_BASE + finalPartner_photo, "not_matter");
//
//
//            }
//        });
//
//    }
}
