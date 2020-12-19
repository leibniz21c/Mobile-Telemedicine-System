package com.knu.medifree.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.knu.medifree.R;

import java.util.ArrayList;

public class PatientAdapter2 extends ArrayAdapter {
    public PatientAdapter2(Context context, ArrayList users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_appointment_patientname, parent, false);
        }

        // Get the data item for this position
        Patient patient = (Patient) getItem(position);
        // Lookup view for data population

        TextView listview_request_item_name = (TextView) convertView.findViewById(R.id.patient_app_name);
        // Populate the data into the template view using the data object
        listview_request_item_name.setText(patient.getName());
        // Return the completed view to render on screen
        return convertView;
    }

}
