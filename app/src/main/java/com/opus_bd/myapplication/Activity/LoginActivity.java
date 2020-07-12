package com.opus_bd.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.opus_bd.myapplication.APIClient.RetrofitClientInstance;
import com.opus_bd.myapplication.APIClient.RetrofitService;
import com.opus_bd.myapplication.Activity.LOGREG.RegistrationActivity;
import com.opus_bd.myapplication.Model.User.UserLoginModel;
import com.opus_bd.myapplication.Model.User.UserModel;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.Constants;
import com.opus_bd.myapplication.Utils.SharedPrefManager;
import com.opus_bd.myapplication.Utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.rootLayout)
    LinearLayout rootLayout;
    @BindView(R.id.ivApplogo)
    ImageView ivApplogo;
    @BindView(R.id.ivpassShow)
    ImageView ivpassShow;
    boolean isPassChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.cchat).into(ivApplogo);

    }


    @OnClick(R.id.btnSignIn)
    public void login() {
        if (!validatedForm())
            return;
        submitToServer();
    }


    private void submitToServer() {
        showProgressBar(true);
        final UserLoginModel userModel = new UserLoginModel();
        userModel.setID(etUserName.getText().toString());
        userModel.setPassword(etPassword.getText().toString());
        SharedPrefManager.getInstance(this).saveUserName(etUserName.getText().toString());
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<UserModel> registrationRequest = retrofitService.login(userModel);
        registrationRequest.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Utilities.showLogcatMessage("  model 1" + response.body());
                try {
                    if (response.body() != null) {
                        Utilities.showLogcatMessage("  model 2" + response.body());


                        SharedPrefManager.getInstance(LoginActivity.this).saveUserModel(response.body());
                        SharedPrefManager.getInstance(LoginActivity.this).saveUserId(response.body().getId());

                        SharedPrefManager.getInstance(LoginActivity.this).setIsUserLogin(true);
                        SharedPrefManager.getInstance(LoginActivity.this).setLoggedIn(true);
                        Constants.callerId = SharedPrefManager.getInstance(LoginActivity.this).getUserID();

                        Toast.makeText(LoginActivity.this, "Successfully Logged in!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Something went Wrong! Please try again later", Toast.LENGTH_SHORT).show();
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                showProgressBar(false);
                Toast.makeText(LoginActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();
                Utilities.showLogcatMessage("Fail to connect " + t.toString());
            }
        });
    }

    private boolean validatedForm() {
        if (TextUtils.isEmpty(etUserName.getText().toString())) {
            etUserName.setError(getResources().getString(R.string.field_null_error));
            Toast.makeText(this, "Contact field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(getResources().getString(R.string.field_null_error));
            Toast.makeText(this, "Password field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void showProgressBar(boolean visible) {
        if (visible) {
            progressbar.setVisibility(View.VISIBLE);
            rootLayout.setAlpha(Constants.PROGRESSBAR_ALPHA);
            setLayoutStatus(false);

        } else {
            progressbar.setVisibility(View.GONE);
            rootLayout.setAlpha(Constants.PROGRESSBAR_AFTER_FINISH_ALPHA);
            setLayoutStatus(true);
        }
    }

    private void setLayoutStatus(boolean status) {
        for (int i = 0; i < rootLayout.getChildCount(); i++) {
            View view = rootLayout.getChildAt(i);
            view.setEnabled(status);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.ivpassShow)
    public void Passwordshow() {

        if (isPassChecked) {
            // show password
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            Glide.with(this).load(R.drawable.ic_visibility_off).into(ivpassShow);
            isPassChecked = false;
        } else {
            // hide password
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            Glide.with(this).load(R.drawable.ic_view).into(ivpassShow);
            isPassChecked = true;
        }
    }


    @OnClick(R.id.tvSignUp)
    public void tvSignUp() {
        try {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Utilities.showLogcatMessage("Exception " + e.toString());
        }
    }

}