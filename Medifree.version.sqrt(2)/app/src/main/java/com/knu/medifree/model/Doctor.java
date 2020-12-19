package com.knu.medifree.model;

public class Doctor  implements Comparable<Doctor>{
    private String hospital_name;
    private String major;
    private String name;
    private String phoneNum;
    private String id;

    public Doctor(
            String hospital_name
            , String major
            , String name
            , String phoneNum
            , String id)
    {
        this.hospital_name = hospital_name;
        this.major = major;
        this.name = name;
        this.phoneNum = phoneNum;
        this.id = id;
    }
    public String getMajor() {
        return major;
    }

    public String getDocid() { return id; }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public int compareTo(Doctor o) {
        return this.major.compareTo(o.major);
    }
}
