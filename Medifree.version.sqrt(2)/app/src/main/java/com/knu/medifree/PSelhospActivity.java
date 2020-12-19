package com.knu.medifree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.knu.medifree.model.HospitalAdapter;
import com.knu.medifree.util.DBManager;
import com.knu.medifree.model.Hospital;

import java.util.ArrayList;

//        import com.example.promise_lab.R;
//        import com.example.promise_lab.lib.MyToast;

public class PSelhospActivity extends Activity {

//    private Spinner spinnerCity, spinnerSigungu, spinnerDong;
//    private ArrayAdapter<String> arrayAdapter;
//    public static final String EXTRA_ADDRESS = "address";
//    public LinearLayout samsung_hospital_select;

    // Hospital list담는 객체 생성.(12.16 - 김동희)
    private ArrayList<Hospital> list_Hospital = new ArrayList<>();
    private FirebaseAuth mAuth;
    private String hospital_name;
    private String hospital_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_p_sel_hosp);

        // 현재 uid 가져오기.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String cur_uid = user.getUid();
        // Hospital_list
        list_Hospital = DBManager.getHospitals();
        //  log.e로 확인하는 부분.
        for (int i = 0; i < list_Hospital.size(); i++) {
            Log.e("List of Hospital", list_Hospital.get(i).getHospitalName());
        }
        HospitalAdapter hospitalAdapter = new HospitalAdapter(this, list_Hospital);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(hospitalAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String hospitalname = ((Hospital) hospitalAdapter.getItem(position)).getHospitalName();
            Intent intent = new Intent(getApplicationContext(), PSelmajorActivity.class);
            Log.d("TAG", "onCreate: btn click");
            intent.putExtra("hospital_name", hospitalname);
            startActivity(intent);
        });
    }
}
        /*
         *
         *  이 밑부터 ListView 관련해서 구현해줘 혹시나 몰라서 밑에 혜교가 해놓은 코드 다 주석처리해놓음
         *  아마 주소값은 사용안할듯.
         *   ### intent로 옮길 것 ###
         *   조건 : ListView안에서 병원이름이 "클릭"되었을때 intent를 PSelmajorActivity.class로 옮김.
         *
         *      참고 : -이미 startActivityWithMajor~~~함수로 선택한 hospital_id를 넘겨주면
         *               자동으로 다음 Activity에서  major List를 가져옴.
         *             - Hospital은 각각 getHospitalName과 getHospitalId로 가져오기 가능 Index활용.
         *
         *  구현 :*/
        // 현재는 hospital : "서울대병원" 이라고 설정(id값임). 버튼 하나 누르면 넘어가도록 설정해놓음.


//        hospital_id = "OXIRMiC9OS675mdikFZV";
//        hospital_name = "서울대병원";
//        Button btn_temp = (Button) findViewById(R.id.temp_button);
//
//
//
//        btn_temp.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent2 = new Intent(getApplicationContext(), PSelmajorActivity.class);
//                intent2.putExtra("hospital_name",hospital_name);
//                startActivity(intent2);
//                finish();
//            }
//        });
        /*
         *
         *  참고사항 :
         *      PSeldocAcitivity -> PSelmajorAcitivity로 이름 바꿨음
         *      PSeldocAgainA~~ -> PSeldocAcitivity로 이름 바꿨음.
         *      # 관련해서 Acitivity이름만 바뀜, layout xml은 그대로임.
         *          PSelmajorAcitivity => activity_p_sel_doc.xml씀.
         *          PSeldocActivity => activity_p_sel_doc_again.xml씀.
         *
         */


/* 여기서부터는 참조할껀 참조해서 위에 구현하고 나머지는 삭제 */

//        spinnerCity = (Spinner) findViewById(R.id.spin_city);
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.spinner_region));
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCity.setAdapter(arrayAdapter);
//        spinnerSigungu = (Spinner) findViewById(R.id.spin_sigungu);
//        spinnerDong = (Spinner) findViewById(R.id.spin_dong);
//        initAddressSpinner();
//
//        ImageButton okBtn = findViewById(R.id.p_search_address);
//        okBtn.setOnClickListener(onClickListener);
//
//        //일단은 삼성병원 클릭 경우만 doctor select로 넘어가게 함
//        samsung_hospital_select = (LinearLayout) findViewById(R.id.samsung_hospital_select);
//        samsung_hospital_select.setOnClickListener(onClickListener);
//
////        HospitalAdapter hospitalAdapter = new HospitalAdapter(this, DBManager.getHospitals());
////        ListView listView = (ListView)findViewById(R.id.listView);
////        listView.setAdapter(hospitalAdapter);
////
////
////        listView.setOnItemClickListener((parent, view, position, id) -> {
////        String hospitalname = ((Hospital)hospitalAdapter.getItem(position)).getHospitalName();
////        Intent intent;
////        if(PHomeActivity.getNotResitered()){
////            //재진의 경우임 ->
////             //intent = new Intent(PSelhospActivity.this, PSeldocAgainActivity.class);
////        }else {
////             //intent = new Intent(PSelhospActivity.this, PSeldocActivity.class);
////        }
////        intent.putExtra("hopitalname",hospitalname);
////        DBManager.getDoctor(hospitalname);
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        startActivity(intent);
////        });
////        //주소 검색버튼 정의
//    }
//
//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//         public void onClick(View view) {
//            Log.d("TAG", "onClick: " + view.toString());
//            if (view.getId() == R.id.p_search_address) {
//                if (spinnerCity.getSelectedItemPosition() == 0) {
//                    //Toast.makeText(this, "행정구역 주소 입력", Toast.LENGTH_SHORT).show();
//                    //아무것도 선택안하고 검색 시 행정구역 주소 입력 안내
//                } else {
//                    Intent data = new Intent();
//
//                    if (spinnerDong.getSelectedItem() != null) {
//                        String address = spinnerCity.getSelectedItem().toString() + " " + spinnerSigungu.getSelectedItem().toString() + " " + spinnerDong.getSelectedItem().toString();
//                        data.putExtra(EXTRA_ADDRESS, address);
//                        //서울특벽시 경우 동까지 포함 된 데이터 값 넘김
//                    } else {
//                        String address = spinnerCity.getSelectedItem().toString() + " " + spinnerSigungu.getSelectedItem().toString();
//                        data.putExtra(EXTRA_ADDRESS, address);
//                        //서울특별시 제외 대구같은 경우 동 이전 시/군/구 까지의 데이터만 넘김
//                    }
//
//                    setResult(RESULT_OK, data);
//                    finish();
//                }
//
//            }
//            //삼성병원 클릭 시 삼성병원의 doctor select 액티비티 전환
//            else if (view.getId() == R.id.samsung_hospital_select) {
//                //Intent intent = new Intent(PSelhospActivity.this, PSeldocActivity.class);
//               // startActivity(intent);
//               // finish();
//            } else if (view.getId() == R.id.listView) {
//                Log.d("TAG", "onClick: " + view.toString());
//            }
//        }
//    };
//    private void initAddressSpinner() {
//        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // 시군구, 동의 스피너를 초기화한다.
//                switch (position) {
//                    case 0:
//                        spinnerSigungu.setAdapter(null);
//                        break;
//                    case 1:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_seoul);
//                        break;
//                    case 2:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_busan);
//                        break;
//                    case 3:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_daegu);
//                        break;
//                    case 4:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_incheon);
//                        break;
//                    case 5:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gwangju);
//                        break;
//                    case 6:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_daejeon);
//                        break;
//                    case 7:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_ulsan);
//                        break;
//                    case 8:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_sejong);
//                        break;
//                    case 9:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeonggi);
//                        break;
//                    case 10:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gangwon);
//                        break;
//                    case 11:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_chung_buk);
//                        break;
//                    case 12:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_chung_nam);
//
//                        break;
//                    case 13:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeon_buk);
//                        break;
//                    case 14:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeon_nam);
//                        break;
//                    case 15:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeong_buk);
//                        break;
//                    case 16:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeong_nam);
//                        break;
//                    case 17:
//                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeju);
//                        break;
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spinnerSigungu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // 서울특별시 선택시
//                if (spinnerCity.getSelectedItemPosition() == 1 && spinnerSigungu.getSelectedItemPosition() > -1) {
//                    switch (position) {
//                        //25
//                        case 0:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangnam);
//                            break;
//                        case 1:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangdong);
//                            break;
//                        case 2:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangbuk);
//                            break;
//                        case 3:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gangseo);
//                            break;
//                        case 4:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gwanak);
//                            break;
//                        case 5:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_gwangjin);
//                            break;
//                        case 6:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_guro);
//                            break;
//                        case 7:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_geumcheon);
//                            break;
//                        case 8:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_nowon);
//                            break;
//                        case 9:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_dobong);
//                            break;
//                        case 10:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_dongdaemun);
//                            break;
//                        case 11:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_dongjag);
//                            break;
//                        case 12:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_mapo);
//                            break;
//                        case 13:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seodaemun);
//                            break;
//                        case 14:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seocho);
//                            break;
//                        case 15:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seongdong);
//                            break;
//                        case 16:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_seongbuk);
//                            break;
//                        case 17:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_songpa);
//                            break;
//                        case 18:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_yangcheon);
//                            break;
//                        case 19:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_yeongdeungpo);
//                            break;
//                        case 20:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_yongsan);
//                            break;
//                        case 21:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_eunpyeong);
//                            break;
//                        case 22:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_jongno);
//                            break;
//                        case 23:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_jung);
//                            break;
//                        case 24:
//                            setDongSpinnerAdapterItem(R.array.spinner_region_seoul_jungnanggu);
//                            break;
//                    }
//                } else {
//                    //굳이 적을 필요 없을듯
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                //음..굳이
//            }
//        });
//      }
//
//    private void setSigunguSpinnerAdapterItem(int array_resource) {
//        if (arrayAdapter != null) {
//            spinnerSigungu.setAdapter(null);
//            arrayAdapter = null;
//        }
//
//        if (spinnerCity.getSelectedItemPosition() > 1) {
//            spinnerDong.setAdapter(null);
//        }
//
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(array_resource));
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerSigungu.setAdapter(arrayAdapter);
//    }
//
//    private void setDongSpinnerAdapterItem(int array_resource) {
//        if (arrayAdapter != null) {
//            spinnerDong.setAdapter(null);
//            arrayAdapter = null;
//        }
//
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(array_resource));
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerDong.setAdapter(arrayAdapter);
//    }
//
//}
