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

public class HospitalAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Hospital> sample;

    public HospitalAdapter(Context context, ArrayList<Hospital> data) {
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
        View view = mLayoutInflater.inflate(R.layout.listview_hospital_item, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.list_item_image);
        TextView HospitalName = (TextView)view.findViewById(R.id.list_item_name);
        String resName = "@drawable/";
        String packName = sample.get(position).getHospitalName();

        int resID = mContext.getResources().getIdentifier(resName,"drawable",packName);
        HospitalName.setText(sample.get(position).getHospitalName());

        return view;
    }


}
