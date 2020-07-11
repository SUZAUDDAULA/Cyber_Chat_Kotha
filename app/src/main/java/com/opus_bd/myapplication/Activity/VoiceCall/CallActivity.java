package com.opus_bd.myapplication.Activity.VoiceCall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.opus_bd.myapplication.Activity.Call.SinchService;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.Constants;
import com.opus_bd.myapplication.Utils.Utilities;
import com.sinch.android.rtc.SinchError;

public class CallActivity extends BaseActivity implements SinchService.StartFailedListener {
    String userName;
    private ProgressDialog mSpinner;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        userName = Constants.callerId;
        // loginClicked();
        // mLoginName = (EditText) findViewById(R.id.loginName);

        mLoginButton = findViewById(R.id.button);
        mLoginButton.setEnabled(false);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicked();
            }
        });
    }

    @Override
    protected void onServiceConnected() {
        mLoginButton.setEnabled(true);
        getSinchServiceInterface().setStartListener(this);
    }

    @Override
    protected void onPause() {
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
        super.onPause();
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
    }

    @Override
    public void onStarted() {
        openPlaceCallActivity();
    }

    public void loginClicked() {
        // openPlaceCallActivity();

        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            if (!userName.equals(getSinchServiceInterface().getUserName())) {
                getSinchServiceInterface().stopClient();
            }
            if ((getSinchServiceInterface() == null) || (!getSinchServiceInterface().isStarted())) {
                getSinchServiceInterface().startClient(userName);
                showSpinner();
            } else {
                openPlaceCallActivity();
            }
        } catch (Exception e) {
            Utilities.showLogcatMessage(" " + e.toString());
        }
    }

    private void openPlaceCallActivity() {
        Intent mainActivity = new Intent(this, PlaceCallActivity.class);
        startActivity(mainActivity);
    }

    private void showSpinner() {
        mSpinner = new ProgressDialog(this);
        mSpinner.setTitle("Logging in");
        mSpinner.setMessage("Please wait...");
        mSpinner.show();
    }
}
