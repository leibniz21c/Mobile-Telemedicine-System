package com.knu.medifree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.knu.medifree.model.Reservation;
import com.knu.medifree.util.DBManager;

import java.util.ArrayList;


public class PrescriptionActivity extends AppCompatActivity {
    private Dialog dialog;
    private EditText et_content;
    private Button btn_send;
    String reservation_id;
    String patient_id;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        // View Assigning
        btn_send = (Button) findViewById(R.id.d_office_btn);
        et_content = (EditText) findViewById(R.id.d_office_et_content);

        reservation_id = getIntent().getStringExtra("Reservation_ID");

        ArrayList<Reservation> reservations = DBManager.getReservations();
        for (int i = 0 ;i < reservations.size(); i ++) {
            if (reservations.get(i).getId().equals(reservation_id)) {
                patient_id = reservations.get(i).getPatient_id();
                break;
            }
        }

        dialog = new Dialog(PrescriptionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_check);
        ((TextView)(dialog.findViewById(R.id.timecheck))).setText("다음 예약을 하시겠습니까?");


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = et_content.getText().toString();
                DBManager.showDialogWithGetPatientTel(patient_id, content, PrescriptionActivity.this, dialog, reservation_id);
            }
        });



    }
}