package com.opus_bd.myapplication.Activity.LOGREG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.opus_bd.myapplication.APIClient.RetrofitClientInstance;
import com.opus_bd.myapplication.APIClient.RetrofitService;
import com.opus_bd.myapplication.Activity.LoginActivity;
import com.opus_bd.myapplication.Adapter.CustomAdapter;
import com.opus_bd.myapplication.Model.User.RankModel;
import com.opus_bd.myapplication.Model.User.RegisterModel;
import com.opus_bd.myapplication.Model.User.SubUnitsModel;
import com.opus_bd.myapplication.Model.User.UnitModel;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.SharedPrefManager;
import com.opus_bd.myapplication.Utils.Utilities;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kotlin.Unit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    @BindView(R.id.spnUnit)
    Spinner spnUnit;
    @BindView(R.id.spnSubUnit)
    Spinner spnSubUnit;
    @BindView(R.id.spnDesignation)
    Spinner spnDesignation;

    @BindView(R.id.etNAME)
    EditText etNAME;
    @BindView(R.id.etBPNo)
    EditText etBPNo;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPhn)
    EditText etPhn;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassWord)
    EditText etConfirmPassWord;
    @BindView(R.id.ivpassShow)
    ImageView ivpassShow;
    @BindView(R.id.ivProfilePic)
    ImageView ivProfilePic;

    boolean isPassChecked = true;

    ArrayList<UnitModel> unitModelArrayList = new ArrayList<>();
    ArrayList<SubUnitsModel> subUnitsModelArrayList = new ArrayList<>();
    ArrayList<RankModel> rankModelArrayList = new ArrayList<>();

    public Integer SELECTED_UNIT_ID,SELECTED_SUB_UNIT_ID=null;
    String selectOne,SELECTED_DESIGNATION_NAME="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        selectOne = getResources().getString(R.string.select_option);

        getAllSubUnit();
        getAllUnit();
        getAllDesignation();

    }


    public void getAllUnit() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<List<UnitModel>> listCall = retrofitService.GetAllUnits();
        listCall.enqueue(new Callback<List<UnitModel>>() {
            @Override
            public void onResponse(Call<List<UnitModel>> call, Response<List<UnitModel>> response) {

                if (response.body() != null) {

                    unitModelArrayList.clear();
                    unitModelArrayList.addAll(response.body());

                    addAllUnitSpinnerData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UnitModel>> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addAllUnitSpinnerData(final List<UnitModel> body) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(0, "Select Unit");
        for (int i = 0; i < body.size(); i++) {
            arrayList.add(i + 1, body.get(i).getBranchUnitName());
        }

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), arrayList);
        spnUnit.setAdapter(customAdapter);
        spnUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i >= 1) {
                        SELECTED_UNIT_ID = body.get(i - 1).getId();
                    } else {
                        SELECTED_UNIT_ID = null;
                    }
                } catch (Exception e) {
                    Utilities.showLogcatMessage(" " + e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getAllSubUnit() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<List<SubUnitsModel>> listCall = retrofitService.GetAllSubUnits();
        listCall.enqueue(new Callback<List<SubUnitsModel>>() {
            @Override
            public void onResponse(Call<List<SubUnitsModel>> call, Response<List<SubUnitsModel>> response) {

                if (response.body() != null) {

                    subUnitsModelArrayList.clear();
                    subUnitsModelArrayList.addAll(response.body());

                    addAllSubUnitSpinnerData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SubUnitsModel>> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addAllSubUnitSpinnerData(final List<SubUnitsModel> body) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(0, "Select Sub-Unit");
        for (int i = 0; i < body.size(); i++) {
            arrayList.add(i + 1, body.get(i).getName());
        }

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), arrayList);
        spnSubUnit.setAdapter(customAdapter);
        spnSubUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i >= 1) {
                        SELECTED_SUB_UNIT_ID = body.get(i - 1).getId();
                    } else {
                        SELECTED_SUB_UNIT_ID = null;
                    }
                } catch (Exception e) {
                    Utilities.showLogcatMessage(" " + e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getAllDesignation() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<List<RankModel>> listCall = retrofitService.RankListForCChat();
        listCall.enqueue(new Callback<List<RankModel>>() {
            @Override
            public void onResponse(Call<List<RankModel>> call, Response<List<RankModel>> response) {

                if (response.body() != null) {

                    rankModelArrayList.clear();
                    rankModelArrayList.addAll(response.body());

                    addAllDesignationSpinnerData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<RankModel>> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addAllDesignationSpinnerData(final List<RankModel> body) {
        Utilities.showLogcatMessage("RankModel= "+body.size());
        List<String> arrayList = new ArrayList<>();
        arrayList.add(0, "Select Rank");
        for (int i = 0; i < body.size(); i++) {
            arrayList.add(i + 1, body.get(i).getVehicleType());
        }

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), arrayList);
        spnDesignation.setAdapter(customAdapter);
        spnDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i >= 1) {
                        SELECTED_DESIGNATION_NAME = body.get(i - 1).getVehicleType();
                    } else {
                        SELECTED_DESIGNATION_NAME = "";
                    }
                } catch (Exception e) {
                    Utilities.showLogcatMessage(" " + e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.btnNext1)
    public void Register() {
        if (!validatedForm())
            return;
        submitToServer();

        //fileSubmit();
    }


    public void submitToServer() {

        final RegisterModel registrationModel = new RegisterModel();

        registrationModel.setBPNumber(etBPNo.getText().toString());
        registrationModel.setName(etNAME.getText().toString());
        registrationModel.setDesignation(SELECTED_DESIGNATION_NAME);
        registrationModel.setEmail(etEmail.getText().toString());
        registrationModel.setUnitId(SELECTED_UNIT_ID);
        registrationModel.setSubUnitId(SELECTED_SUB_UNIT_ID);
        registrationModel.setMobile(etEmail.getText().toString());
        registrationModel.setPassword(etPassword.getText().toString());
        registrationModel.setConfirmPassword(etConfirmPassWord.getText().toString());
        //Utilities.showLogcatMessage("RegistrationModel " + registrationModel.toString());

        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<String> registrationRequest = retrofitService.CChatRegister(registrationModel);
        registrationRequest.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        try {
                            RegisterSuccessActivity.code = etBPNo.getText().toString();
                            Intent intent = new Intent(RegistrationActivity.this, RegisterSuccessActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            Utilities.showLogcatMessage("Exception 1" + e.toString());
                            Toast.makeText(RegistrationActivity.this, "Something went Wrong! Please try again later", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(RegistrationActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Utilities.showLogcatMessage("Exception 2" + e.toString());
                    Toast.makeText(RegistrationActivity.this, "Something went Wrong! Please try again later", Toast.LENGTH_SHORT).show();
                }
                //            showProgressBar(false);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Utilities.showLogcatMessage("Fail to connect " + t.toString());
                // Utilities.hideProgress(LoginActivity.this);
                Toast.makeText(RegistrationActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validatedForm() {
        if (TextUtils.isEmpty(etNAME.getText().toString())) {
            etNAME.setError(getResources().getString(R.string.field_null_error));
            Toast.makeText(this, "Name field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(etBPNo.getText().toString())) {
            etBPNo.setError(getResources().getString(R.string.field_null_error));
            Toast.makeText(this, "BP No field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (SELECTED_UNIT_ID==null) {
            Toast.makeText(this, "Unit field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (SELECTED_SUB_UNIT_ID==null) {
            Toast.makeText(this, "Sub-Unit field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(SELECTED_DESIGNATION_NAME)) {
            Toast.makeText(this, "Rank field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError(getResources().getString(R.string.field_null_error));
            Toast.makeText(this, "Email field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(etPhn.getText().toString())) {
            etPhn.setError(getResources().getString(R.string.field_null_error));
            Toast.makeText(this, "Phone field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(getResources().getString(R.string.field_null_error));
            Toast.makeText(this, "Password field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(etConfirmPassWord.getText().toString())) {
            etConfirmPassWord.setError(getResources().getString(R.string.field_null_error));
            Toast.makeText(this, "Confirm Password field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public void fileSubmit() {
        //Create a file object using file path
//        File files = new File(uri.getPath());
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), files);
//        MultipartBody.Part file = MultipartBody.Part.createFormData("file", files.getName(), requestFile);

       File files = new File("");
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), files);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", files.getName(), fileReqBody);


        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<String> registrationRequest = retrofitService.CChatRegisterWithImage(file);
        registrationRequest.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        try {
                            Toast.makeText(RegistrationActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Utilities.showLogcatMessage("Exception 1" + e.toString());
                            Toast.makeText(RegistrationActivity.this, "Something went Wrong! Please try again later", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(RegistrationActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Utilities.showLogcatMessage("Exception 2" + e.toString());
                    Toast.makeText(RegistrationActivity.this, "Something went Wrong! Please try again later", Toast.LENGTH_SHORT).show();
                }
                //            showProgressBar(false);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Utilities.showLogcatMessage("Fail to connect " + t.toString());
                // Utilities.hideProgress(LoginActivity.this);
                Toast.makeText(RegistrationActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            Utilities.showLogcatMessage("onActivityResult " + e.toString());
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                //uri=resultUri;
                ivProfilePic.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Utilities.showLogcatMessage("ierror" + result.getError().getMessage());
                Exception error = result.getError();
            }
        }
    }


    @OnClick(R.id.ivProfilePic)
    public void ivFrontHead() {
        CropImage.activity().start(RegistrationActivity.this);
    }


    @OnClick(R.id.ivpassShow)
    public void Passwordshow() {

        if (isPassChecked) {
            // show password
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            etConfirmPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            Glide.with(this).load(R.drawable.ic_visibility_off).into(ivpassShow);
            isPassChecked = false;
        } else {
            // hide password
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            etConfirmPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
            Glide.with(this).load(R.drawable.ic_view).into(ivpassShow);
            isPassChecked = true;
        }
    }


}