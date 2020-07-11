package com.opus_bd.myapplication.Activity.Fargment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opus_bd.myapplication.APIClient.RetrofitClientInstance;
import com.opus_bd.myapplication.APIClient.RetrofitService;
import com.opus_bd.myapplication.Adapter.MemberListAdapter;
import com.opus_bd.myapplication.Model.User.UserListModel;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.SharedPrefManager;
import com.opus_bd.myapplication.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {
    @BindView(R.id.rvUserList)
    RecyclerView rvUserList;
    MemberListAdapter memberListAdapter;
    ArrayList<UserListModel> userListModelArrayList = new ArrayList<>();
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        intRecyclerView();
        getAllUser();
        return v;
    }


    public void intRecyclerView() {
        memberListAdapter = new MemberListAdapter(userListModelArrayList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvUserList.setLayoutManager(layoutManager);
        rvUserList.setAdapter(memberListAdapter);
    }


    private void getAllUser() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        int id = Integer.parseInt(SharedPrefManager.getInstance(getContext()).getUserID());

        Call<List<UserListModel>> registrationRequest = retrofitService.GetEmployeeInfoExceptMe(id);
        registrationRequest.enqueue(new Callback<List<UserListModel>>() {
            @Override
            public void onResponse(Call<List<UserListModel>> call, @NonNull Response<List<UserListModel>> response) {
                try {
                    if (response.body() != null) {
                        userListModelArrayList.addAll(response.body());
                    }

                    memberListAdapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<UserListModel>> call, Throwable t) {
            }
        });

    }

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
                //memberListAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.trim().isEmpty()) {
                    memberListAdapter.setFilter(userListModelArrayList);
                    return false;
                }
                else {
                    newText = newText.toLowerCase();
                    final ArrayList<UserListModel> filteredNewsList = new ArrayList<>();
                    for (UserListModel model : userListModelArrayList) {
                        final String empName = model.getEmpName().toLowerCase();
                        if ((empName.contains(newText))) {
                            filteredNewsList.add(model);
                        }
                    }
                    memberListAdapter.setFilter(filteredNewsList);
                    return false;
                }

               /* memberListAdapter.getFilter().filter(newText);
                return false;*/
            }

        });
        //return true;
    }



}
