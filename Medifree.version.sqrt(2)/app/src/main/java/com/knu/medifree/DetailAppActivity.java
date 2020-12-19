package com.knu.medifree;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ListView;
import android.widget.TextView;

import com.knu.medifree.model.Patient;
import com.knu.medifree.model.PatientAdapter;
import com.knu.medifree.model.PatientAdapter2;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailAppActivity extends AppCompatActivity {
    private String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_detailapp);

        Intent intent = getIntent();
        time =intent.getStringExtra("time");


        TextView textView = (TextView)findViewById(R.id.d_dtailapp_time);
        textView.setText(intent.getStringExtra("time")+" is selected");

        populatePatientsList();
}
    private void populatePatientsList() {
        ArrayList arrayOfUsers = Patient.getPatientFromTime(time);
        // Create the adapter to convert the array to views
        PatientAdapter2 adapter = new PatientAdapter2(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listview_patientlist);
        listView.setAdapter(adapter);
    }
}
