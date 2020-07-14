package com.opus_bd.myapplication.Activity.Fargment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opus_bd.myapplication.APIClient.RetrofitClientInstance;
import com.opus_bd.myapplication.APIClient.RetrofitService;
import com.opus_bd.myapplication.Activity.ChatActivity;
import com.opus_bd.myapplication.Activity.ContactActivity;
import com.opus_bd.myapplication.Activity.HomeActivity;
import com.opus_bd.myapplication.Activity.LoginActivity;
import com.opus_bd.myapplication.Adapter.MemberListAdapter;
import com.opus_bd.myapplication.Model.User.ContactConnectModel;
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

public class ChatFragment extends Fragment {
    @BindView(R.id.rvUserList)
    RecyclerView rvUserList;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.bt_clear)
    ImageView bt_clear;
    MemberListAdapter memberListAdapter;
    ArrayList<ContactConnectModel> connectModelArrayList = new ArrayList<>();
    private SearchView searchView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, v);
        Constants.CONTACT_TYPE="chat";
        setHasOptionsMenu(true);
        intRecyclerView();
        getAllUser();
        return v;
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
        memberListAdapter = new MemberListAdapter(connectModelArrayList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvUserList.setLayoutManager(layoutManager);
        rvUserList.setAdapter(memberListAdapter);
        et_search.addTextChangedListener(textWatcher);
    }


    private void getAllUser() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        int id = Integer.parseInt(SharedPrefManager.getInstance(getContext()).getUserID());

        Call<List<ContactConnectModel>> registrationRequest = retrofitService.GetContactConnectionLog(id,"chat","connected");
        registrationRequest.enqueue(new Callback<List<ContactConnectModel>>() {
            @Override
            public void onResponse(Call<List<ContactConnectModel>> call, @NonNull Response<List<ContactConnectModel>> response) {
                try {
                    if (response.body() != null) {
                        connectModelArrayList.addAll(response.body());
                    }

                    memberListAdapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<ContactConnectModel>> call, Throwable t) {
            }
        });

    }

    @OnClick(R.id.fabStatus)
    public void loadAllContact() {
        Constants.CONTACT_TYPE="chat";
        Intent intent = new Intent(getContext(), ContactActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.bt_clear)
    public void getData() {
        et_search.setText(" ");
        intRecyclerView();
        getAllUser();
    }

    private void getSearchData(String text) {
        ArrayList<ContactConnectModel> tempItems = new ArrayList<>();
        memberListAdapter = new MemberListAdapter(tempItems, getContext());
        rvUserList.setAdapter(memberListAdapter);

        for (int i = 0; i < connectModelArrayList.size(); i++) {
            ContactConnectModel tempModel = connectModelArrayList.get(i);
            if (tempModel.getEmpCode().toLowerCase().contains(text.toLowerCase()) || tempModel.getEmpName().toLowerCase().contains(text.toLowerCase())|| tempModel.getDesignationName().toLowerCase().contains(text.toLowerCase())) {
                tempItems.add(tempModel);
            }
        }

        if (tempItems.isEmpty()) {
            memberListAdapter = new MemberListAdapter(connectModelArrayList, getContext());
        }
        memberListAdapter.notifyDataSetChanged();
    }

}
