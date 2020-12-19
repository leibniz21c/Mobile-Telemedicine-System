package com.knu.medifree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.knu.medifree.util.DBManager;

public class DHomeActivity extends AppCompatActivity {
    ImageButton btn_app;
    ImageButton btn_office;
    ImageButton btn_request;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_home);

        intent = getIntent();
        Log.i("HEESUNG_DHOME", "Welcome" + intent.getStringExtra("user_id"));

        // 객체 할당
        btn_app = (ImageButton) findViewById(R.id.d_app);
        btn_office = (ImageButton) findViewById(R.id.d_office);
        btn_request = (ImageButton) findViewById(R.id.d_request);
        // 클릭 리스너 할당
        btn_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 예약하기 버튼을 눌렀을 때
                // 현재 상황 : DAppActivity로 이동
                Intent intent = new Intent(getApplicationContext(), DAppActivity.class);
                startActivity(intent);
                //finish(); 일단 뒤로 버튼을 눌러서 의사 홈으로 돌아올 수 있게 해둠.
            }
        });
        btn_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 진료실 버튼을 눌렀을 때
                // 현재 상황 :
                Intent intent = new Intent(getApplicationContext(), DOfficeActivity.class);
                DBManager.startActivityWithReservationReading(DHomeActivity.this, intent);
                // TODO :
            }
        });
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로고침 버튼을 눌렀을 때
                // 현재 상황 :
                Intent intent = new Intent(getApplicationContext(), ResCheckActivity.class);
                DBManager.startActivityWithReservationReading(DHomeActivity.this, intent);
                // TODO :

            }
        });
    }
}