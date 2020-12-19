package com.knu.medifree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.knu.medifree.model.Hospital;
import com.knu.medifree.model.User;
import com.knu.medifree.util.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignupDoctor2Activity<database> extends AppCompatActivity {
    ImageButton btn_reg;
    private FirebaseAuth mAuth;
    private Spinner hospitalNameSpinner,majorSpinner;
    private ArrayAdapter<String> arrayAdapter;
    public static final String EXTRA_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_doctor_next);


        mAuth = FirebaseAuth.getInstance();

        // Hospital Name Spinner
        Spinner hospitalNameSpinner=(Spinner)findViewById(R.id.hospital_Name);
        arrayAdapter = new ArrayAdapter<String>(
                this
                , android.R.layout.simple_spinner_item
                , (String[]) getResources().getStringArray(R.array.spinner_hospital)
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospitalNameSpinner.setAdapter(arrayAdapter);
        hospitalNameSpinner.setSelection(0);

        // Major Name Spinner
        Spinner majorSpinner = (Spinner) findViewById(R.id.major);
        arrayAdapter = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item
                , (String[]) getResources().getStringArray(R.array.spinner_major)
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(arrayAdapter);
        majorSpinner.setSelection(0);


        // 병원_메이저 보내주는 버튼 객체 할당
        btn_reg = (ImageButton) findViewById(R.id.d_reg_btn_reg);

        // 클릭 리스너 할당
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원 가입 버튼을 눌렀을 때
                // 현재 상황 : DHomeActivity로 이동
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();
                insert_user_Information(uid);
                Intent intent = getIntent();
                finish();
                DBManager.initDBManager(intent.getStringExtra("user_id"), User.TYPE_DOCTOR);
                startActivity(new Intent(getApplicationContext(), DHomeActivity.class));
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    //알림을 출력하는 method
    private void startToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }

    //생성된 uid 및 나머지 정보들 firestore에 넣는 작업.
    private void insert_user_Information(final String uid) {
        String hospital_Name = ((Spinner)findViewById((R.id.hospital_Name))).getSelectedItem().toString();
        String major = ((Spinner)findViewById(R.id.major)).getSelectedItem().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String phone=intent.getStringExtra("phone");

        Map<String, Object> user = new HashMap<>();
        user.put("userType","Doctor");
        user.put("name",name);
        user.put("phoneNum",phone);
        user.put("Hospital_Name",hospital_Name);
        user.put("Major",major);

        //실제 firestore에 추가하는 작업, add=> 자동으로 문서id(문서이름)를 만들어줌

        // Add a new document with a generated ID
        db.collection("Profile").document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        //uid정보를 hospital에 넣기.
                        insert_doctor_to_hospital(uid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("정보저장에 실패하였습니다1.");

                    }
                });

    }

    private void insert_doctor_to_hospital(String uid) {
        String hospital_Name = ((Spinner)findViewById((R.id.hospital_Name))).getSelectedItem().toString();
        String major = ((Spinner)findViewById(R.id.major)).getSelectedItem().toString();
        String hospital_id = DBManager.getHospitalId(hospital_Name);

        if (hospital_id == null) {
            // There is not corresponding hospital in DB.
            DBManager.createHospital(new Hospital(hospital_Name, Hospital.getBitmaskByMajorTag(major)));
        } else {
            ArrayList<Hospital> hospitals = DBManager.getHospitals();
            for (int i = 0 ;i < hospitals.size(); i ++) {
                if (hospital_id.equals(hospitals.get(i).getHospitalId())) {
                    int major_bit_mask = hospitals.get(i).getBitmask();

                    if ((major_bit_mask & Hospital.getBitmaskByMajorTag(major)) != 0) return;
                    else {
                        DBManager.deleteHospital(hospital_id);
                        major_bit_mask += Hospital.getBitmaskByMajorTag(major);
                        DBManager.createHospital(new Hospital(hospital_Name, major_bit_mask));
                    }
                }
            }
        }
    }

}
