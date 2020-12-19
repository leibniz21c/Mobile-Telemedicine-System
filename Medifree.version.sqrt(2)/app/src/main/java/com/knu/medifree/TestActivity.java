package com.knu.medifree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.knu.medifree.model.User;
import com.knu.medifree.util.DBManager;

import java.text.ParseException;

public class TestActivity extends AppCompatActivity {
    Button btn;
    TextView tv;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//
//        // 할당
//        btn = (Button) findViewById(R.id.btn);
//        tv = (TextView) findViewById(R.id.tv);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // Init DBManager
//                DBManager.initDBManager("oYnAfFJe8XXih2klptUueBnwtic2", User.TYPE_PATIENT);
//
//                DBManager.startActivityWithMajorReading("OXIRMiC9OS675mdikFZV",TestActivity.this, new Intent(getApplicationContext(), Test2Activity.class));
//
//            }
//        });
//
//
//
//
}