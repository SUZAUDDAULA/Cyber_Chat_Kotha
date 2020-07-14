package com.opus_bd.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.opus_bd.myapplication.APIClient.RetrofitClientInstance;
import com.opus_bd.myapplication.APIClient.RetrofitService;
import com.opus_bd.myapplication.Adapter.ContactAdapter;
import com.opus_bd.myapplication.Adapter.MemberListAdapter;
import com.opus_bd.myapplication.Model.User.UserListModel;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.Constants;
import com.opus_bd.myapplication.Utils.SharedPrefManager;
import com.opus_bd.myapplication.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity {
    @BindView(R.id.rvUserList)
    RecyclerView rvUserList;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.bt_clear)
    ImageView bt_clear;
    ContactAdapter contactAdapter;
    ArrayList<UserListModel> UserListModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        initToolbar();
        intRecyclerView();
        getAllUser();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.app_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Tools.setSystemBarColor(this);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence c, int i, int i1, int i2) {
            if (c.toString().trim().length() == 0) {
                bt_clear.setVisibility(View.GONE);
            } else {
                getSearchData(c.toString());
                bt_clear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    public void intRecyclerView() {
        contactAdapter = new ContactAdapter(UserListModel, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvUserList.setLayoutManager(layoutManager);
        rvUserList.setAdapter(contactAdapter);
        et_search.addTextChangedListener(textWatcher);
    }

    private void getAllUser() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        int id = Integer.parseInt(SharedPrefManager.getInstance(this).getUserID());

        Call<List<UserListModel>> registrationRequest = retrofitService.GetEmployeeInfoExceptMe(id);
        registrationRequest.enqueue(new Callback<List<UserListModel>>() {
            @Override
            public void onResponse(Call<List<UserListModel>> call, @NonNull Response<List<UserListModel>> response) {
                try {
                    if (response.body() != null) {
                        UserListModel.addAll(response.body());
                        Utilities.showLogcatMessage("dataforcontact= "+response.body().size());
                    }

                    contactAdapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<UserListModel>> call, Throwable t) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }


    @OnClick(R.id.bt_clear)
    public void getData() {
        et_search.setText(" ");
        intRecyclerView();
        getAllUser();
    }

    private void getSearchData(String text) {
        ArrayList<UserListModel> tempItems = new ArrayList<>();
        contactAdapter = new ContactAdapter(tempItems, getApplicationContext());
        rvUserList.setAdapter(contactAdapter);

        for (int i = 0; i < UserListModel.size(); i++) {
            UserListModel tempModel = UserListModel.get(i);
            if (tempModel.getUserName().toLowerCase().contains(text.toLowerCase()) || tempModel.getEmpName().toLowerCase().contains(text.toLowerCase())) {
                tempItems.add(tempModel);
            }
        }

        if (tempItems.isEmpty()) {
            contactAdapter = new ContactAdapter(UserListModel, getApplicationContext());
        }
        contactAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}