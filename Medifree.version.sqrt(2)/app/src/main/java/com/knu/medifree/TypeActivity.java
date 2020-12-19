package com.knu.medifree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TypeActivity extends AppCompatActivity {
    ImageButton btn_patient;
    ImageButton btn_doctor;
    ImageButton btn_pharmacist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        // 객체 할당
        btn_patient = (ImageButton) findViewById(R.id.type_btn_patient);
        btn_doctor = (ImageButton) findViewById(R.id.type_btn_doctor);
        btn_pharmacist = (ImageButton) findViewById(R.id.type_btn_ph);

        // 클릭 리스너 할당
        btn_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupPatientActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupDoctorActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_pharmacist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ph. 버튼을 눌렀을 때
                // 현재 상황 : 일단 대기
                // TODO : PH.
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}