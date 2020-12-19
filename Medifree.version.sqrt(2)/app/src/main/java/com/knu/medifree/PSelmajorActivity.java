package com.knu.medifree;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.knu.medifree.model.Hospital;
import com.knu.medifree.model.MajorAdapter;
import com.knu.medifree.model.PatientAdapter2;
import com.knu.medifree.util.DBManager;
import com.knu.medifree.model.Doctor;
import com.knu.medifree.model.DoctorAdapter;

import java.util.ArrayList;

public class PSelmajorActivity extends Activity {
    private ArrayList<String> list_majors = new ArrayList<>();
    private String uid;
    private FirebaseAuth mAuth;
    private String hospital_name;
    private String major_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_sel_doc);

        /* PSelmajorActivity 설명
         *       - activity_p_sel_doc.xml 참조.
         *       - PSelhospaActivity에서 hospital name을 선택해서 희성이가 구현한 startmajor~~~로 major Arraylist를 가져왔음.
         *       - PSelMajorActivity는 activity_p_sel_doc.xml layout을 사용
         *       목적 : 넘겨받은 major를 listview로 구현해서 "선택"하면 major id를 PSeldocActivity로 넘김.
         */

        Intent intent = getIntent();
        hospital_name = intent.getExtras().getString("hospital_name");
        TextView textView = (TextView)findViewById(R.id.p_sel_doc_name);
        textView.setText(hospital_name+" is selected");

        //Debug
        Log.e("hos name :", hospital_name);

        // 현재 uid 가져오기.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String cur_uid = user.getUid();

        // Major_list
        //16:48 Major_list 어떻게 할건지 모르겠음 일단 넘어감.
        ArrayList<Hospital> hospital_list = DBManager.getHospitals();
        for (int i= 0 ;i < hospital_list.size() ;i ++) {
            if (hospital_list.get(i).getHospitalName().equals(hospital_name)) {
                list_majors = hospital_list.get(i).getMajors();
                Log.d("TAG", "onCreate:  list_majors"+ list_majors.size());
                break;
            }
        }
        ArrayList<String> tmp = new ArrayList<>();
        //  log.e로 major 이름들 확인.
        for (int i = 0 ;i < list_majors.size(); i ++) {
            Log.e("List of Hospital", list_majors.get(i));
            tmp.add(list_majors.get(i));
        }
        // dunp majors 만들어서 사용하는 부분임.


//        tmp.add("123");
//        tmp.add("123");tmp.add("123");tmp.add("123");tmp.add("123");tmp.add("123");
        // tmp부분 지우고 넣으면 됨

        MajorAdapter adapter = new MajorAdapter(this, tmp);//tmp에 list_majors넣으면 됨
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listview_major);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            major_name = ((adapter.getItem(position))).toString();
            Log.d("TAG", "onCreate: majorname "+major_name);
            Intent intent2 = new Intent(getApplicationContext(), PSeldocActivity.class);
            intent2.putExtra("hospital_name",hospital_name);
            DBManager.startActivityWithDoctorReading(hospital_name,major_name,PSelmajorActivity.this,intent2);
        });

        /*
         *   PSelhospAcitivity와 같이 여기서 부터 Listview를 구성
         *       -Major을 클릭하면, 그 major이름을 통해서 DBManager에 구현해놓은 startwithDoctor메소드로 Major관련 Doctor 가져옴.
         *       - Doctor로 가기위해서는 hospital name과 major name이 필요.
         *       - 선택된 major을 major_name변수에 집어넣음.
         *       - 현재는 서울대병원, 피부과 가정.
         *       - hospital name은 intent를 통해 넘어옴.
         */


    }
}
//        Intent intent = getIntent();//병원 이름 받아옵니다.
//        TextView textView = (TextView)findViewById(R.id.p_sel_doc_name);
//        textView.setText(intent.getStringExtra("hopitalname")+" is selected");
//
////
////        DoctorAdapter doctorAdapter = new DoctorAdapter(this, DBManager.getDoctors_list());
////        ListView listView2 = (ListView)findViewById(R.id.listview_doctorlist);
////        listView2.setAdapter(doctorAdapter);
////        listView2.setOnItemClickListener((parent, view, position, id) -> {
////            //String hospitalname = ((Hospital)hospitalAdapter.getItem(position)).getHospitalName();
////            String doctorName = ((Doctor)doctorAdapter.getItem(position)).getName();
////            Intent intent2 = new Intent(PSeldocActivity.this, PSeltimeActivity.class);
////            intent2.putExtra("doctorName",doctorName);
////            startActivity(intent2);
////        });



//doctor1 = (LinearLayout) findViewById(R.id.doctor1);
// doctor1.setOnClickListener(onClickListener);


