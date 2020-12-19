package com.knu.medifree.model;

import android.util.Log;

import com.knu.medifree.util.DBManager;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Patient {
    private String name;
    private String time;


    public Patient(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public String getTime(){
        return time;
    }

    public static ArrayList getPatient() {
        ArrayList patient = new ArrayList();
        ArrayList<Reservation> list_reservations = DBManager.getReservations();

        for(int i=0;i<list_reservations.size();i++){
            if (list_reservations.get(i).isCompleted() == false) {
                patient.add(new Patient(
                        list_reservations.get(i).getPatient_name(),
                        list_reservations.get(i).getDate()
                ));
            }
        }

        return patient;
    }

    public static ArrayList getPatientFromTime(String time){
        ArrayList patient = new ArrayList();
        ArrayList<Reservation> list_reservations = DBManager.getReservations();

        SimpleDateFormat todaySdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        //한국기준 날짜
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());
        todaySdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String todayDate = todaySdf.format(date);

        todayDate  = todayDate+"/"+time.charAt(0)+time.charAt(1);
        for(int i=0;i<list_reservations.size();i++){

            if (list_reservations.get(i).isCompleted()) {

                String temp = list_reservations.get(i).getDate().substring(0,13);
                if (temp.equals(todayDate)) {
                    patient.add(new Patient(
                            list_reservations.get(i).getPatient_name(),
                            list_reservations.get(i).getDate()
                    ));
                }
            }
        }

        return patient;
    }

    public static ArrayList getPatientForDiagnosis(){
        ArrayList patient = new ArrayList();
        ArrayList<Reservation> list_reservations = DBManager.getReservations();

        SimpleDateFormat todaySdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        //한국기준 날짜
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTimeInMillis());
        todaySdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String todayDate = todaySdf.format(date);

        Log.e("asdf",todayDate);
        for(int i=0;i<list_reservations.size();i++){

            if (list_reservations.get(i).isCompleted()) {

                String temp = list_reservations.get(i).getDate().substring(0,10);
                Log.e("asdffff",temp);
                if (temp.equals(todayDate)) {
                    patient.add(new Patient(
                            list_reservations.get(i).getPatient_name(),
                            list_reservations.get(i).getDate()
                    ));
                }
            }
        }
        return patient;
    }
}
