package com.knu.medifree.model;

import java.util.ArrayList;

public class Hospital {

    // Bit mask approach
    public final static int MAJOR1 = 0b00000001;
    public final static String MAJOR1_TAG = "피부과";
    public final static int MAJOR2 = 0b00000010;
    public final static String MAJOR2_TAG = "산부인과";
    public final static int MAJOR3 = 0b00000100;
    public final static String MAJOR3_TAG = "정형외과";
    public final static int MAJOR4 = 0b00001000;
    public final static String MAJOR4_TAG = "내과";
    public final static int MAJOR5 = 0b00010000;
    public final static String MAJOR5_TAG = "비뇨기과";
    public final static int MAJOR6 = 0b00100000;
    public final static String MAJOR6_TAG = "신경외과";
    public final static int MAJOR7 = 0b01000000;
    public final static String MAJOR7_TAG = "안과";
    public final static int MAJOR8 = 0b10000000;
    public final static String MAJOR8_TAG = "치과";

    private String hospitalName;
    private String id;
    private int major_bit_mask;

    public Hospital(String hospitalName, String id, int major_bit_flag){
        this.hospitalName = hospitalName;
        this.id = id;
        this.major_bit_mask = major_bit_flag;
    }
    public Hospital(String hospitalName, int major_bit_flag){
        this.hospitalName = hospitalName;
        this.major_bit_mask = major_bit_flag;
    }

    public Hospital(String hospital_name) {
        this.hospitalName = hospital_name;
        this.major_bit_mask = 0b00000000;
    }

    public String getHospitalId() {
        return this.id;
    }
    public String getHospitalName(){
        return hospitalName;
    }
    public static int getBitmaskByMajorTag(String major) {
        if (major.equals(MAJOR1_TAG)) return MAJOR1;
        else if (major.equals(MAJOR2_TAG)) return MAJOR2;
        else if (major.equals(MAJOR3_TAG)) return MAJOR3;
        else if (major.equals(MAJOR4_TAG)) return MAJOR4;
        else if (major.equals(MAJOR5_TAG)) return MAJOR5;
        else if (major.equals(MAJOR6_TAG)) return MAJOR6;
        else if (major.equals(MAJOR7_TAG)) return MAJOR7;
        else if (major.equals(MAJOR8_TAG)) return MAJOR8;
        return 0;
    }

    public int getBitmask() {
        return this.major_bit_mask;
    }

    public ArrayList<String> getMajors() {
        ArrayList<String> result = new ArrayList<String>();

        if ((this.major_bit_mask & MAJOR1) != 0)  result.add(MAJOR1_TAG);
        if ((this.major_bit_mask & MAJOR2) != 0)  result.add(MAJOR2_TAG);
        if ((this.major_bit_mask & MAJOR3) != 0)  result.add(MAJOR3_TAG);
        if ((this.major_bit_mask & MAJOR4) != 0)  result.add(MAJOR4_TAG);
        if ((this.major_bit_mask & MAJOR5) != 0)  result.add(MAJOR5_TAG);
        if ((this.major_bit_mask & MAJOR6) != 0)  result.add(MAJOR6_TAG);
        if ((this.major_bit_mask & MAJOR7) != 0)  result.add(MAJOR7_TAG);
        if ((this.major_bit_mask & MAJOR8) != 0)  result.add(MAJOR8_TAG);

        return result;
    }
    public void setMajor(String major) {
        if (major.equals(MAJOR1_TAG)) {
            if ((this.major_bit_mask & MAJOR1) != 0) this.major_bit_mask += MAJOR1;
        } else if (major.equals(MAJOR2_TAG)) {
            if ((this.major_bit_mask & MAJOR2) != 0) this.major_bit_mask += MAJOR2;
        } else if (major.equals(MAJOR3_TAG)) {
            if ((this.major_bit_mask & MAJOR3) != 0) this.major_bit_mask += MAJOR3;
        } else if (major.equals(MAJOR4_TAG)) {
            if ((this.major_bit_mask & MAJOR4) != 0) this.major_bit_mask += MAJOR4;
        } else if (major.equals(MAJOR5_TAG)) {
            if ((this.major_bit_mask & MAJOR5) != 0) this.major_bit_mask += MAJOR5;
        } else if (major.equals(MAJOR6_TAG)) {
            if ((this.major_bit_mask & MAJOR6) != 0) this.major_bit_mask += MAJOR6;
        } else if (major.equals(MAJOR7_TAG)) {
            if ((this.major_bit_mask & MAJOR7) != 0) this.major_bit_mask += MAJOR7;
        } else if (major.equals(MAJOR8_TAG)) {
            if ((this.major_bit_mask & MAJOR8) != 0) this.major_bit_mask += MAJOR8;
        }
    }
    public void setMajor(int major_bit_flag) {
        if ((this.major_bit_mask & major_bit_flag) != 0) this.major_bit_mask += major_bit_flag;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "hospitalName='" + hospitalName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
