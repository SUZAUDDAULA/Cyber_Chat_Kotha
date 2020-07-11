package com.opus_bd.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.opus_bd.myapplication.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
  List<String> Names;
    LayoutInflater inflter;

    public CustomAdapter(Context context, List<String> names) {
        this.context = context;
        this.Names = names;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return Names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);

        names.setText(Names.get(i));
        return view;
    }
}