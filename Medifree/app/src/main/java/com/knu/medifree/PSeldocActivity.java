package com.knu.medifree;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.knu.medifree.util.DBManager;
import com.knu.medifree.model.Doctor;
import com.knu.medifree.model.DoctorAdapter;

import java.util.ArrayList;

public class PSeldocActivity extends Activity  {
    //    public LinearLayout doctor1;
//    private Context mContext;
//    private CustomDialogTwo Dialog;
//    public Button selectdoctor;
    private String Doctor_name ;
    private String Doctor_id;
    private FirebaseAuth mAuth;
    private ArrayList<Doctor> list_doctors = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_sel_doc_again);
        /*
           layout : activity_p_sel_doc_again.xml과 연결되어있음.
           목적 :  major을 통해서 해당 전공의를 ListView로 구현하여 전공의를 선택하는 부분을 구현
                   -> 그 후 PSeltimeActivity로 넘어감.
         */

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String cur_uid = user.getUid();
        // doctor를 Array list로 받아옴.
        list_doctors = DBManager.getDoctors();
        //for debug
        for (int i =0 ;i<list_doctors.size();i++){
            Log.e("Doctor list :" , list_doctors.get(i).getName()); // method, model.Doctor 참조.
        }

        DoctorAdapter doctorAdapter = new DoctorAdapter(this, list_doctors);
        ListView listView2 = (ListView)findViewById(R.id.listview_doctorlist);
        listView2.setAdapter(doctorAdapter);
        listView2.setOnItemClickListener((parent, view, position, id) -> {
            Doctor_name  = ((Doctor)doctorAdapter.getItem(position)).getName();
            Doctor_id = ((Doctor)doctorAdapter.getItem(position)).getDocid();

            Intent intent2 = new Intent(getApplicationContext(), PSeltimeActivity.class);
            intent2.putExtra("hospital_name",getIntent().getExtras().getString("hospital_name"));
            intent2.putExtra("name",Doctor_name);
            intent2.putExtra("id",Doctor_id);
            Log.d("TAG", "onCreate: doctorname " + Doctor_name+Doctor_id+getIntent().getExtras().getString("hospital_name"));
            DBManager.startActivityWithPatientReading(cur_uid,PSeldocActivity.this, intent2);

            finish();
        });

        /*
         *           이밑으로 ListView 구현
         *               목표 : listview를 이번에는 클릭하면 이동하는게 아니라,
         *                       "선택"하고 "예약시간설정" 클릭하면 PSeltimeActivity로 이동함.
         *
         *               주의 : doctor_name을 intent로 넘겨야지 다음 날자정보&doctor정보로 예약 요청함.
         *
         *           현재 : default로 doctor_name = "양닥터"로 설정해놓음.
         *
         */

//        Doctor_name = "양닥터";    // get(i).getName()으로 받기가능
//        Doctor_id = "A9H8hbW4hAUa6FC781xjNsZYWmz2"; // get(i).getDocid()로 받기가능.
//
//        Button btn_time = (Button) findViewById(R.id.p_sel_doctor_again_btn_diag);
//
//        btn_time.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent2 = new Intent(getApplicationContext(), PSeltimeActivity.class);
//                intent2.putExtra("name",Doctor_name);
//                intent2.putExtra("id",Doctor_id);
//                startActivity(intent2);
//                finish();
//            }
//        });

    }
}
//        Intent intent = getIntent();//병원 이름 받아옵니다.
//        TextView textView = (TextView)findViewById(R.id.p_sel_doc_again_name);
//        textView.setText(intent.getStringExtra("hopitalname")+" is selected");
//
//        DoctorAdapter doctorAdapter = new DoctorAdapter(this, DBManager.getDoctors_list());
//        ListView listView2 = (ListView)findViewById(R.id.listview_doctorlist);
//        listView2.setAdapter(doctorAdapter);
//        listView2.setOnItemClickListener((parent, view, position, id) -> {
//            //String hospitalname = ((Hospital)hospitalAdapter.getItem(position)).getHospitalName();
//            String doctorName = ((Doctor)doctorAdapter.getItem(position)).getName();
//        });
//        selectdoctor=(Button)findViewById(R.id.p_sel_doctor_again_btn_diag);
//        selectdoctor.setOnClickListener(this);
//
//    }
//
//    public void onClick(View view) {
//
//        if (view.getId() == R.id.p_sel_doctor_again_btn_diag) {
//            Dialog = new CustomDialogTwo(this);
//            WindowManager.LayoutParams params = this.Dialog.getWindow().getAttributes();
//
//            this.Dialog.getWindow().setAttributes(params);
//            Dialog.setCancelable(false);
//            Dialog.getWindow().setGravity(Gravity.BOTTOM);
//            Dialog.show();
//        }




