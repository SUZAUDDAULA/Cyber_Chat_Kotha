package com.opus_bd.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.opus_bd.myapplication.Activity.ChatActivity;
import com.opus_bd.myapplication.Model.User.UserListModel;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.Constants;
import com.opus_bd.myapplication.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.TransactionViewHolder> implements Filterable {
    private final Context context;
    private List<UserListModel> items;
    private List<UserListModel> userListModelFiltered;
    public MemberListAdapter(List<UserListModel> items, Context context) {
        this.items = items;
        this.context = context;
        this.userListModelFiltered = items;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_list_view, parent, false);

        return new TransactionViewHolder(v);
    }


    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        UserListModel item = items.get(position);
        holder.set(item);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgUser)
        ImageView ivUserImage;
        @BindView(R.id.tvProfileName)
        TextView tvProfileName;
        @BindView(R.id.description)
          TextView description;
        @BindView(R.id.rootLayout)
        LinearLayout rootLayout;


        public TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void set(final UserListModel item) {
            //UI setting code
            description.setText(String.valueOf(item.getCompanyName()));
            tvProfileName.setText(String.valueOf(item.getEmpName()));

            try {
                Glide.with(context)
                        .applyDefaultRequestOptions(new RequestOptions()
                                .placeholder(R.drawable.ic_person)
                                .error(R.drawable.ic_person))
                        .load(Constants.BASE_URL + item.getCompanyName())
                        .into(ivUserImage);
            } catch (Exception e) {
            }


            rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.ReciverNmae = item.getEmpName();
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra(ChatActivity.EXTRA_RECEIVER_ID, item.getEmployeeId());
                    // Utilities.showLogcatMessage(" USER ID" + item.getId());
                    intent.putExtra(ChatActivity.EXTRA_RECEIVER_NAME, item.getEmpName());
                    intent.putExtra(ChatActivity.EXTRA_RECEIVER_PHOTO, item.getDivisionName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);

                }
            });
        }
    }



    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UserListModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(userListModelFiltered);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (UserListModel item : userListModelFiltered) {
                    if (item.getEmpName().toLowerCase().contains(filterPattern) || item.getEmpCode().toLowerCase().contains(filterPattern) || item.getCompanyName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
                userListModelFiltered = filteredList;
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
/*
    public void setFilter(ArrayList<UserListModel> newsArrayList) {
        userListModelFiltered.clear();
        userListModelFiltered.addAll(newsArrayList);
        notifyDataSetChanged();
    }*/

}
