package com.knu.medifree;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.knu.medifree.model.Reservation;
import com.knu.medifree.util.DBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class DResNextActivity extends AppCompatActivity {

    private TextView patient_date;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Spinner patient_time;
    private ArrayAdapter<String> arrayAdapter;
    private Context mContext;
    private CustomDialogThree Dialog;
    private Reservation tempRes;

    public Button res_yes, res_no;

    // Extra
    private String reservation_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_next_app);

        // Get Intent and Extra
        reservation_id = getIntent().getStringExtra("Reservation_ID");
        ArrayList<Reservation> reservations = DBManager.getReservations();

        for (int i = 0 ;i < reservations.size() ;i ++) {
            if (reservations.get(i).getId().equals(reservation_id)) {
                tempRes = reservations.get(i);
                break;
            }
        }


        patient_date = (TextView) findViewById(R.id.p_res_date);
        res_yes=(Button)findViewById(R.id.p_res_yes);
        res_no=(Button)findViewById(R.id.p_res_no);

        this.InitializeView();
        this.InitializeListener();

        patient_time = (Spinner) findViewById(R.id.p_res_time);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.spinner_time));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patient_time.setAdapter(arrayAdapter);
        patient_time.setSelection(0);


        patient_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                String currentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentTime);
                StringTokenizer st = new StringTokenizer(currentdate,"-");
                DatePickerDialog dialog = new DatePickerDialog(DResNextActivity.this, callbackMethod, Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken())-1 , Integer.parseInt(st.nextToken()));
                dialog.show();
            }
        });

    }

    public void InitializeView() {
        patient_date = (TextView) findViewById(R.id.p_res_date);

    }

    public void InitializeListener() {
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear+=1;
                patient_date.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        };
    }

    public void onClick(View view) {

        if (view.getId() == R.id.p_res_no) {
            Intent intent=new Intent(DResNextActivity.this,DAppActivity.class);
            startActivity(intent);
            finish();

        } else if(view.getId()==R.id.p_res_yes) {
            String tail = patient_time.getSelectedItem().toString();
            String date = String.valueOf(patient_date.getText()) + "/" + tail.substring(0, 2) + "/" + tail.substring(3);


            Reservation reservation = tempRes;
            tempRes.setDate(date);
            tempRes.setCompleted(true);
            tempRes.setDone(false);
            DBManager.createReservation(tempRes);


            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.putExtra("sms_body", getIntent().getStringExtra("mms_body"));
            intent.setData( Uri.parse( "smsto:"+getIntent().getStringExtra("mms_uri") ) );
            startActivity(intent);
            finish();
        }
    }


}


////////////////////////




