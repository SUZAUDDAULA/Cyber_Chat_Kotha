/*
package com.opus_bd.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.opus_bd.myapplication.Model.Group.GroupPost;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.TransactionViewHolder> {
    private final Context context;
    private List<GroupPost> items;
    Boolean languageStatus;

   */
/* public GroupListAdapter(List<DocumentType> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_others_item_list, parent, false);
        languageStatus = getSharedPrefValue();
        return new TransactionViewHolder(v);
    }
    private boolean getSharedPrefValue() {
        SharedPreferences tprefs = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        return tprefs.getBoolean(KEY_State, true);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        DocumentType item = items.get(position);
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

        @BindView(R.id.tvItemName)
        TextView tvreceiveNo;
        @BindView(R.id.ivItemLogo)
        ImageView ivItemLogo;
        @BindView(R.id.llRoot)
        LinearLayout llRoot;
        @BindView(R.id.cvItem)
        CardView cvItem;


        public TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void set(final DocumentType item) {
            //UI setting code
            if (languageStatus) {
                tvreceiveNo.setText(item.getDocumentTypeNameBn());
            } else {
                tvreceiveNo.setText(item.getDocumentTypeName());
            }

          try{
              Glide.with(context).load("http://103.134.88.13:1022/"+item.getImagePath()).into(ivItemLogo);
          }
          catch (Exception e){}


            final int id = item.getId();

            Utilities.showLogcatMessage("id" + id);
            llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(id==1){
                        Intent intent = new Intent(context, CategoryListActivity.class);
                        context.startActivity(intent);
                    }else if(id==2){
                        Intent intent = new Intent(context, OtherItemDetailsActivity.class);
                        context.startActivity(intent);
                    }else if(id==3){
                        Intent intent = new Intent(context, ElectronicsActivity.class);
                        context.startActivity(intent);
                    }else if(id==4){
                        Intent intent = new Intent(context, MobilePhoneActivity.class);
                        context.startActivity(intent);
                    }else if(id==5){
                        Intent intent = new Intent(context, KeysInformationActivity.class);
                        context.startActivity(intent);
                    }else if(id==6){
                        Intent intent = new Intent(context, CardsActivity.class);
                        context.startActivity(intent);
                    }else if(id==7){
                        Intent intent = new Intent(context, DocumentActivity.class);
                        context.startActivity(intent);
                    }else if(id==8){
                        Intent intent = new Intent(context, BagActivity.class);
                        context.startActivity(intent);
                    }else if(id==9){
                        Intent intent = new Intent(context, MoneyActivity.class);
                        context.startActivity(intent);
                    }else if(id==10){
                        Intent intent = new Intent(context, JewelryActivity.class);
                        context.startActivity(intent);
                    }else if(id==11){
                        Intent intent = new Intent(context, GlassActivity.class);
                        context.startActivity(intent);
                    }else if(id==12){
                        Intent intent = new Intent(context, GarmentsActivity.class);
                        context.startActivity(intent);
                    }else if(id==13){
                        Intent intent = new Intent(context, ShoesActivity.class);
                        context.startActivity(intent);
                    }else if(id==14){
                        Intent intent = new Intent(context, CosmeticsActivity.class);
                        context.startActivity(intent);
                    }else if(id==15){
                        Intent intent = new Intent(context, PetActivity.class);
                        context.startActivity(intent);
                    }else if(id==16){
                        Intent intent = new Intent(context, UmbrellaActivity.class);
                        context.startActivity(intent);
                    }else {
                        Intent intent = new Intent(context, CategoryListActivity.class);
                        context.startActivity(intent);
                    }

                }
            });


        }


    }
*//*


}
*/
