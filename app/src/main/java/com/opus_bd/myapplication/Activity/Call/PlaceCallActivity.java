package com.opus_bd.myapplication.Activity.Call;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.opus_bd.myapplication.R;
import com.sinch.android.rtc.calling.Call;

import java.util.List;

public class PlaceCallActivity extends VoiceCallBaseActivity {

    public static String TARGET_CALL_ID;
    public static String DOCTOR_ID;
    public static String DOCTOR_NAME;
    public static String DOCTOR_PHOTO;
    public static String PATIENT_ID;
    public static String PATIENT_NAME;
    public static String PATIENT_PHOTO;
    public static String CALLER_ID;
    public static String CALLER_TYPE;
    public static String TYPE_DOCTOR = "d";
    public static String TYPE_PATIENT = "p";
    String targerUser;
    Context context = this;
    Intent iin;
    Bundle b;
    private Button mCallButton;
    private EditText mCallName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_call);

        iin = getIntent();
        b = iin.getExtras();

        if (b != null) {
            targerUser = (String) b.get("targerUser");

        }
    }

    @Override
    protected void onServiceConnected() {
        askPermission();


    }

    private void askPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            // Call call = getSinchServiceInterface().callUserVideo("" + NOW_SHOWING_ONLINE_DOC.getId());
                            Call call = getSinchServiceInterface().callUserVideo("" + TARGET_CALL_ID);
                            String callId = call.getCallId();

                            Intent callScreen = new Intent(context, CallScreenActivity.class);
                            callScreen.putExtra(SinchService.CALL_ID, callId);

                            //  callScreen.putExtra("DOCTOR_NAME", (String) b.get("DOCTOR_NAME"));
                            // callScreen.putExtra("DOCTOR_NAME", (String) b.get("DOCTOR_NAME"));
                            // callScreen.putExtra("PATIENT_NAME", (String) b.get("PATIENT_NAME"));


                            startActivity(callScreen);
                        } else {
                            Toast.makeText(context, "permission not givel all", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }


    private void stopButtonClicked() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().stopClient();
        }
        finish();
    }

    private void callButtonClicked() {
        String userName = mCallName.getText().toString();
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = getSinchServiceInterface().callUserVideo(userName);
        String callId = call.getCallId();

        Intent callScreen = new Intent(this, CallScreenActivity.class);
        callScreen.putExtra(SinchService.CALL_ID, callId);
        startActivity(callScreen);
    }


}

