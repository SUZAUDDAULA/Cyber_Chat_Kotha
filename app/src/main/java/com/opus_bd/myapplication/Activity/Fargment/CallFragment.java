package com.opus_bd.myapplication.Activity.Fargment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opus_bd.myapplication.APIClient.RetrofitClientInstance;
import com.opus_bd.myapplication.APIClient.RetrofitService;
import com.opus_bd.myapplication.Activity.ContactActivity;
import com.opus_bd.myapplication.Adapter.MemberCAllListAdapter;
import com.opus_bd.myapplication.Adapter.MemberListAdapter;
import com.opus_bd.myapplication.Model.User.ContactConnectModel;
import com.opus_bd.myapplication.Model.User.UserListModel;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.Constants;
import com.opus_bd.myapplication.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallFragment extends Fragment {

    @BindView(R.id.rvUserList)
    RecyclerView rvUserList;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.bt_clear)
    ImageView bt_clear;
    MemberCAllListAdapter memberListAdapter;
    ArrayList<ContactConnectModel> contactConnectModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_call, container, false);
        ButterKnife.bind(this, v);
        Constants.CONTACT_TYPE="call";
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
        memberListAdapter = new MemberCAllListAdapter(contactConnectModels, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvUserList.setLayoutManager(layoutManager);
        rvUserList.setAdapter(memberListAdapter);
        et_search.addTextChangedListener(textWatcher);
    }


    private void getAllUser() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        int id = Integer.parseInt(SharedPrefManager.getInstance(getContext()).getUserID());

        Call<List<ContactConnectModel>> registrationRequest = retrofitService.GetContactConnectionLog(id,"call","connected");
        registrationRequest.enqueue(new Callback<List<ContactConnectModel>>() {
            @Override
            public void onResponse(Call<List<ContactConnectModel>> call, @NonNull Response<List<ContactConnectModel>> response) {
                try {
                    if (response.body() != null) {
                        contactConnectModels.addAll(response.body());
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
/*

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //MenuInflater inflater = getMenuInflater();
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.home_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                memberListAdapter.getFilter().filter(newText);
                return true;
            }


        });
        //return true;
    }*/
@OnClick(R.id.fabcal)
public void loadAllContact() {
    Constants.CONTACT_TYPE="call";
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
        memberListAdapter = new MemberCAllListAdapter(tempItems, getContext());
        rvUserList.setAdapter(memberListAdapter);

        for (int i = 0; i < contactConnectModels.size(); i++) {
            ContactConnectModel tempModel = contactConnectModels.get(i);
            if (tempModel.getEmpCode().toLowerCase().contains(text.toLowerCase()) || tempModel.getEmpName().toLowerCase().contains(text.toLowerCase())) {
                tempItems.add(tempModel);
            }
        }

        if (tempItems.isEmpty()) {
            memberListAdapter = new MemberCAllListAdapter(contactConnectModels, getContext());
        }
        memberListAdapter.notifyDataSetChanged();
    }

}
