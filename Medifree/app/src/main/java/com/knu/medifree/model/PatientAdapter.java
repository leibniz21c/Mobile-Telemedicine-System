package com.knu.medifree.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.knu.medifree.DHomeActivity;
import com.knu.medifree.R;
import com.knu.medifree.ResCheckActivity;
import com.knu.medifree.util.DBManager;

import java.util.ArrayList;

import static org.webrtc.ContextUtils.getApplicationContext;

public class PatientAdapter extends ArrayAdapter  {

    public PatientAdapter(Context context, ArrayList users) {
        super(context, 0, users);
    }
    private ArrayList<Reservation> list_reservations;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_request_item, parent, false);
        }

        // Get the data item for this position
        Patient patient = (Patient) getItem(position);
        // Lookup view for data population

        list_reservations = DBManager.getReservations();

        TextView listview_request_item_name = (TextView) convertView.findViewById(R.id.listview_request_item_name);
        TextView listview_request_item_time = (TextView) convertView.findViewById(R.id.listview_request_item_time);
        // Populate the data into the template view using the data object
        listview_request_item_name.setText(patient.getName());
        listview_request_item_time.setText(patient.getTime());
        // Return the completed view to render on screen


        Button btn_true = (Button) convertView.findViewById(R.id.btn_true);
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i =0;i<list_reservations.size(); i++){
                    if( (list_reservations.get(i).getPatient_name().equals(patient.getName()) ) && (list_reservations.get(i).getDate().equals(patient.getTime()) )){
                        Log.e("Update",list_reservations.get(i).getId());
                        list_reservations.get(i).aceept();
                        DBManager.updateReservation( list_reservations.get(i));
                        Intent intent = new Intent(parent.getContext(), DHomeActivity.class);
                        parent.getContext().startActivity(intent);
                    }
                }

            }
        });

        Button btn_false = (Button) convertView.findViewById(R.id.btn_false);
        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i =0;i<list_reservations.size(); i++){
                    if( (list_reservations.get(i).getPatient_name().equals(patient.getName()) ) && (list_reservations.get(i).getDate().equals(patient.getTime()) )){
                        Log.e("Delete",list_reservations.get(i).getId());
                        DBManager.deleteReservation( list_reservations.get(i).getId());
                        Intent intent = new Intent(parent.getContext(),DHomeActivity.class);
                        parent.getContext().startActivity(intent);

                    }
                }

            }
        });

        return convertView;
    }

}
