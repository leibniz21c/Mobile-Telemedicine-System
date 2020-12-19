package com.knu.medifree.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.knu.medifree.R;

import java.util.ArrayList;

public class DoctorAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Doctor> sample;

    public DoctorAdapter(Context context, ArrayList<Doctor> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public Object getItem(int position) {
        return sample.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_doctor_item, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.listview_doctor_item_pic);
        TextView major = (TextView)view.findViewById(R.id.listview_doctor_item_major);
        TextView name = (TextView)view.findViewById(R.id.listview_doctor_item_name);

        //사진 이슈 있음
        name.setText(sample.get(position).getName());
        major.setText(sample.get(position).getMajor());

        return view;
    }

}
