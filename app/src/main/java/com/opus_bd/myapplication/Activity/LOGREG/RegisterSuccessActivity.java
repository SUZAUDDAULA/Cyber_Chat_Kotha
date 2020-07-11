package com.opus_bd.myapplication.Activity.LOGREG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.opus_bd.myapplication.Activity.LoginActivity;
import com.opus_bd.myapplication.R;

public class RegisterSuccessActivity extends AppCompatActivity {
    Button btnThanks;
    public static String code;
    TextView tvCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

        tvCode = (TextView) findViewById(R.id.tvCode);
        tvCode.setText("Note: Your UserID is BP No : "+code);
        btnThanks = (Button) findViewById(R.id.btnThanks);
        btnThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Intent intent = new Intent(RegisterSuccessActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Toast.makeText(dialog, "Please install browser to continue", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}