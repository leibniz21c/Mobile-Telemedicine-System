package com.knu.medifree;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.knu.medifree.model.Reservation;
import com.knu.medifree.util.DBManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class PSeltimeActivity extends AppCompatActivity implements View.OnClickListener {

    Button p_sel_time_btn_diag;
    private Context mContext;
    private CustomDialog Dialog;
    private FirebaseAuth mAuth;

    private String doctor_id;
    private String cur_uid;
    private String date;
    private String doctor_name;
    private String patient_name;
    private String checkdate,currentdate;
    static int year;
    static int monthOfYear;
    static int dayOfMonth;
    static private boolean check1,check2,check3;
    static private String time;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_sel_time);
        check1 = false;
        check2 = false;

        Intent intent = getIntent();
        TextView textView = (TextView)findViewById(R.id.who_select);
        textView.setText(intent.getStringExtra("hospital_name")+" " +intent.getStringExtra("name")+" is selected");

        //intent를 통해 정보 받아오기
        doctor_name =intent.getExtras().getString("name");
        doctor_id = intent.getExtras().getString("id");
        patient_name = DBManager.getPatientName();

        // Debug
        Log.e("D_name : ",doctor_name);
        Log.d("TAG", "onCreate: boolean "+check1+check2);
        TextView patient_date = (TextView) findViewById(R.id.p_res_date2);
        DatePickerDialog.OnDateSetListener callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear+=1;
                patient_date.setText(year + "/" + (monthOfYear) + "/" + dayOfMonth);
                PSeltimeActivity.year=year;
                PSeltimeActivity.monthOfYear=monthOfYear;
                PSeltimeActivity.dayOfMonth=dayOfMonth;
                PSeltimeActivity.check1 =true;
                date = null;
            }
        };
        patient_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                currentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);
                StringTokenizer st = new StringTokenizer(currentdate,"-");
                Log.d("TAG", "onClick: " + currentdate);
                DatePickerDialog dialog = new DatePickerDialog(PSeltimeActivity.this, callbackMethod, Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken())-1 , Integer.parseInt(st.nextToken()));
                dialog.show();
            }
        });

        //라디오 그룹 부분

        RadioGroup group=(RadioGroup)findViewById(R.id.time_list);
        RadioButton time10 = (RadioButton) findViewById(R.id.ten_oclock);
        RadioButton time11 = (RadioButton) findViewById(R.id.eleven_oclock);
        RadioButton time15 = (RadioButton) findViewById(R.id.fifteen_oclock);
        RadioButton time16 = (RadioButton) findViewById(R.id.sixteen_oclock);
        time10.setOnClickListener(RadioClick);
        time11.setOnClickListener(RadioClick);
        time15.setOnClickListener(RadioClick);
        time16.setOnClickListener(RadioClick);

        group.setOnCheckedChangeListener(checkedChangeListener);
        //시간 골라서 보내는 거임!



        //다이얼로그 코드 부분
        dialog = new Dialog(PSeltimeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_check);


//        Spinner patient_time = (Spinner) findViewById(R.id.p_res_time);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
//                (String[]) getResources().getStringArray(R.array.spinner_time));
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        patient_time.setAdapter(arrayAdapter);
//        patient_time.setSelection(0);


        // 객체 할당
        /* 일단 이 Acitivity 흐름
            1. 시간대를 클릭한다
            2. 그 시간대를 String으로 받아온다.
            3. Calender를 사용하여 현재 년,월,일을 받아온다.
            4. Firestore에 들어갈 날짜 포맷을 만든다.
                예약 날짜 String format => "2020/12/16/10/00" (년/월/일/시/분) 으로 포맷을 만든다.
            5. Reservation 객체 생성자를 통해 생성 -> model.Reservation 클래스 참조.
         */
        // 현재 uid 가져오기.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        cur_uid = user.getUid();

        // Reservation 객체 생성 , date 는 임시로 넣음.
//        date = "2020/12/16/11/00";
//        Reservation res = new Reservation(cur_uid, doctor_id, date,false);

        p_sel_time_btn_diag = (Button) findViewById(R.id.p_sel_time_btn_diag);
        p_sel_time_btn_diag.setOnClickListener(this);



    }
    public void showDialog(){
        dialog.show();
        Button noBtn = dialog.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Reservation res = new Reservation(cur_uid, doctor_id, date, false,patient_name ,doctor_name);
                Log.d("TAG", "onClick: "+res);
                // 예약 메소드 실행
                DBManager.createReservation(res);
                //PHomeActivity로가면서 요청 기다리기
                startToast(res.getDate()+" 예약요청을 성공하였습니다. 요청이 완료되면 예약목록에 추가됩니다.");
                Intent intent2 = new Intent(getApplicationContext(), PHomeActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }
    RadioButton.OnClickListener RadioClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PSeltimeActivity.time = v.getTag().toString();
            check2 = true;
            Log.d("TAG", "onClick: " + PSeltimeActivity.time);
        }
    };
    RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Log.d("TAG", "onCheckedChanged: "+ checkedId);
        }
    };
    // 예약 주고 받는 부분은 어디서 하는지 모르겠어서 일단 킵 합니당...

    @Override
    public void onClick(View v) {
        //예외처리해야함!!!
        /* 밑에 time dialog가 먼지몰라서 일단 주석해놓음*/
        /* 예약 누르면 PHomeActivity로 옮기면서 예약메소드 실행 */
        date = String.valueOf(PSeltimeActivity.year)+"/"+String.valueOf(PSeltimeActivity.monthOfYear)+"/"+
                String.valueOf(PSeltimeActivity.dayOfMonth)+"/"+PSeltimeActivity.time;
        checkdate = String.valueOf(PSeltimeActivity.year)+"-"+String.valueOf(PSeltimeActivity.monthOfYear)+"-"+
                String.valueOf(PSeltimeActivity.dayOfMonth)+"-";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null, date2=null;
        try {
             date1 = sdf.parse(checkdate);
             date2 = sdf.parse(currentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date1.before(date2) || date1.equals(date2))
            startToast("현재시간 보다 미래를 선택해 주세요.");
        else if(!check1 || !check2){
            startToast("시간을 선택해 주세요.");}
        else {
            ((TextView)(dialog.findViewById(R.id.timecheck))).setText(date+"  예약 하시겠습니까?");
            showDialog();
            // Reservation 객체 생성 , date 는 임시로 넣음.

        }

//        switch (v.getId()) {
//            case R.id.p_sel_time_btn_diag:
//                Dialog = new CustomDialog(this);
//                WindowManager.LayoutParams params = this.Dialog.getWindow().getAttributes();
//
//                this.Dialog.getWindow().setAttributes(params);
//                Dialog.setCancelable(false);
//                Dialog.getWindow().setGravity(Gravity.BOTTOM);
//                Dialog.show();
//                break;
//        }
    }

    private void startToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }
}