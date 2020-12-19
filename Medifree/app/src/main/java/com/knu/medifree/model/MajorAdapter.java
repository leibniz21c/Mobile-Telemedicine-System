package com.knu.medifree.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.knu.medifree.R;

import java.net.CookieHandler;
import java.util.ArrayList;

public class MajorAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<String> sample;

    public MajorAdapter(Context context, ArrayList<String> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public  Object getItem(int position) {
        return sample.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_appointment_patientname, null);

        TextView major = (TextView)view.findViewById(R.id.patient_app_name);
        major.setText(sample.get(position).toString());
        return view;
    }
}
