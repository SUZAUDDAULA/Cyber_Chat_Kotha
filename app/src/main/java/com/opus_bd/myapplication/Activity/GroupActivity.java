package com.opus_bd.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.opus_bd.myapplication.APIClient.RetrofitClientInstance;
import com.opus_bd.myapplication.APIClient.RetrofitService;
import com.opus_bd.myapplication.Model.Group.GroupPost;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.SharedPrefManager;
import com.opus_bd.myapplication.Utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupActivity extends AppCompatActivity {
    @BindView(R.id.etGroupName)
    EditText etGroupName;
    @BindView(R.id.etGroupTagline)
    EditText etGroupTagline;
    @BindView(R.id.rvGroup)
    RecyclerView rvGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.btnEntry)
    public void login() {
        if (!validatedForm())
            return;
        submitToServer();
    }


    private void submitToServer() {
        GroupPost groupPost = new GroupPost();
        groupPost.setName(etGroupName.getText().toString());
        groupPost.setTagline(etGroupTagline.getText().toString());
        SharedPrefManager.getInstance(this).saveUserName(etGroupName.getText().toString());
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<Integer> registrationRequest = retrofitService.GroupCreate(groupPost);
        registrationRequest.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Utilities.showLogcatMessage("  model 1" + response.body());
                try {
                    if (response.body() != null) {
                        Utilities.showLogcatMessage("  model " + response.body());

                        Toast.makeText(GroupActivity.this, "Successfully Logged in!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(GroupActivity.this, MainActivity.class));

                    } else {
                        Toast.makeText(GroupActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(GroupActivity.this, "Something went Wrong! Please try again later", Toast.LENGTH_SHORT).show();
                }
                //  showProgressBar(false);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {


                //  showProgressBar(false);
                Toast.makeText(GroupActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();
                Utilities.showLogcatMessage("Fail to connect " + t.toString());
            }
        });
    }

    private boolean validatedForm() {
        if (TextUtils.isEmpty(etGroupName.getText().toString())) {
            etGroupName.setError(getResources().getString(R.string.field_null_error));
            Toast.makeText(this, "Contact field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etGroupTagline.getText().toString())) {
            etGroupTagline.setError(getResources().getString(R.string.field_null_error));
            Toast.makeText(this, "Password field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
