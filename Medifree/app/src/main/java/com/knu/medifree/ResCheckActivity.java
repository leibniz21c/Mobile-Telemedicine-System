package com.knu.medifree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.knu.medifree.model.Doctor;
import com.knu.medifree.model.DoctorAdapter;
import com.knu.medifree.model.Hospital;
import com.knu.medifree.model.HospitalAdapter;
import com.knu.medifree.model.Patient;
import com.knu.medifree.model.PatientAdapter;
import com.knu.medifree.model.Reservation;
import com.knu.medifree.util.DBManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.webrtc.ContextUtils.getApplicationContext;


public class ResCheckActivity extends AppCompatActivity {
    public Button first_btn, origin_btn;
    public ImageButton dhome_btn;
    private Button btn_true,btn_false;
    private ArrayList<Reservation> list_reservations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_res_check);

        populatePatientsList();



        first_btn = (Button) findViewById(R.id.d_req_first);
        dhome_btn=(ImageButton)findViewById(R.id.backtodhome);

        btn_true = (Button) findViewById(R.id.btn_true);
        btn_false = (Button) findViewById(R.id.btn_true);

        list_reservations = DBManager.getReservations();



        dhome_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });


//        btn_true.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = ( (TextView) findViewById(R.id.list_item_name) ).toString();
//                String time = ( (TextView) findViewById(R.id.listview_request_item_time) ).toString();
//
//                for (int i =0;i<list_reservations.size(); i++){
//                    if( (list_reservations.get(i).getPatient_name().equals(name) ) && (list_reservations.get(i).getDate().equals(time) )){
//                        Log.e("Update",list_reservations.get(i).getId());
//                        //DBManager.updateReservations( list_reservations.get(i).getId(), true);
//                        Intent intent = new Intent(getApplicationContext(), DHomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//
//            }
//        });
//        btn_false.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = ((TextView) findViewById(R.id.listview_request_item_name)).toString();
//                String time = ((TextView) findViewById(R.id.listview_request_item_time)).toString();
//
//                for (int i =0;i<list_reservations.size(); i++){
//                    if( (list_reservations.get(i).getPatient_name().equals(name) ) && (list_reservations.get(i).getDate().equals(time) )){
//                        Log.e("Delete",list_reservations.get(i).getId());
//                        //DBManager.deleteReservations( list_reservations.get(i).getId());
//                        Intent intent = new Intent(getApplicationContext(), ResCheckActivity.class);
//                        DBManager.startActivityWithReservationReading(ResCheckActivity.this,intent);
//                    }
//                }
//
//            }
//        });

    }

    private void populatePatientsList() {
        ArrayList arrayOfUsers = Patient.getPatient();
        // Create the adapter to convert the array to views
        PatientAdapter adapter = new PatientAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listview_patientrequest);
        listView.setAdapter(adapter);
    }

}
