package com.knu.medifree.model;

import java.io.Serializable;

public class Reservation implements Serializable {
    /* firestore에 사용된 필드
       변수명 일치화 시키는 게 좋음
       Doctor_id, data, id, patient_id
     */
    private String patient_id;
    private String doctor_id;
    private String date;
    private boolean completed;
    private boolean done;
    private String patient_name;
    private String doctor_name;
    private String id;

    // Constructor
    public Reservation(String patient_id, String Doctor_id, String date, Boolean completed,String patient_name, String doctor_name) {
        this.doctor_id = Doctor_id;
        this.date = date;
        this.patient_id = patient_id;
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
        this.completed = completed;
        this.done = false;
    }
    public Reservation(String patient_id, String doctor_id, String date) {
        this.doctor_id = doctor_id;
        this.date = date;
        this.patient_id = patient_id;
        this.completed = false;
        this.done = false;
    }
    public Reservation(String patient_id, String Doctor_id, String date, String res_id) {
        this.doctor_id =Doctor_id;
        this.date = date;
        this.patient_id = patient_id;
        this.completed = false;
        this.done = false;
    }
    public Reservation(String patient_id,String Patient_name, String Doctor_id, String Doctor_name,String date, Boolean completed, String res_id) {
        this.doctor_id = Doctor_id;
        this.doctor_name =Doctor_name;
        this.patient_name = Patient_name;
        this.date = date;
        this.patient_id = patient_id;
        this.completed = completed;
        this.id = res_id;
        this.done = false;
    }
    public Reservation(String patient_id,String Patient_name, String Doctor_id, String Doctor_name,String date, Boolean completed, String res_id, boolean done) {
        this.doctor_id = Doctor_id;
        this.doctor_name =Doctor_name;
        this.patient_name = Patient_name;
        this.date = date;
        this.patient_id = patient_id;
        this.completed = completed;
        this.id = res_id;
        this.done = done;
    }

    public void aceept(){
        this.completed = true;
    }

    public boolean isDone() {
        return this.done;
    }

    // Getter Setter
    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatient_name( ) {
        return patient_name;
    }
    public String getDoctor_name( ) {
        return doctor_name;
    }
    @Override
    public String toString() {
        return "Reservation{" +
                "doctor_id='" + doctor_id + '\'' +
                ", patient_id='" + patient_id + '\'' +
                ", date='" + date + '\'' +
                ", completed=" + completed +
                ", id='" + id + '\'' +
                '}';
    }
}
