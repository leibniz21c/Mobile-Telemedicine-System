package com.knu.medifree;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.knu.medifree.model.Patient;
import com.knu.medifree.model.PatientAdapter2;
import com.knu.medifree.model.PatientAdapter3;
import com.knu.medifree.model.Reservation;
import com.knu.medifree.util.DBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DOfficeActivity extends AppCompatActivity {

    public Button office_btn;
    private ArrayList<Reservation> list_reservations;
    private static String reservation_id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_office);
        list_reservations = DBManager.getReservations();


        ArrayList arrayOfUsers = Patient.getPatientForDiagnosis();
        // Create the adapter to convert the array to views
        PatientAdapter3 adapter = new PatientAdapter3(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listview_office_patient);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String patient_name = ((Patient)adapter.getItem(position)).getName();
            String patient_time = ((Patient)adapter.getItem(position)).getTime();

            for (int i =0;i<list_reservations.size();i++){

                if (  (list_reservations.get(i).getPatient_name().equals(patient_name))   &&
                        (list_reservations.get(i).getDate().equals(patient_time))  ){
                    reservation_id = list_reservations.get(i).getId();
                    Log.e("asdf",reservation_id);
                    break;
                }
            }

        });
        office_btn=(Button)findViewById(R.id.d_office_btn);
        office_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(reservation_id==null) {
                    startToast("진료를 클릭해주십시오.");
                }
                String today = getToday();
                Log.e("today",today);

                for(int i =0;i<list_reservations.size();i++){
                    if (list_reservations.get(i).getId() == reservation_id) {
                        Log.e("Here",reservation_id);
                        if(list_reservations.get(i).getDate().substring(0,13).equals(today)) {
                            Intent intent = new Intent(getApplicationContext(), DoctorDiagnosisActivity.class);
                            Log.e("Reservation_ID : ", list_reservations.get(i).getId());
                            Log.e("Reservation_ID : ", reservation_id);
                            intent.putExtra("Reservation_ID", list_reservations.get(i).getId());
                            startActivity(intent);
                            finish();
                        }
                        else{
                            startToast("아직 시간이 아닙니다.");
                        }
                    }
                }
            }
        });
    }


    private String getToday(){
        SimpleDateFormat todaySdf = new SimpleDateFormat("yyyy/MM/dd/HH", Locale.KOREA);
        //한국기준 날짜
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());
        todaySdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String todayDate = todaySdf.format(date);
        return todayDate;
    }
    private void startToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }
}
/*
*
*
* */