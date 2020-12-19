package com.knu.medifree.model;

public class User {
    private String name;
    private String user_id;
    private String email;
    private String password;
    private String phoneNumber;
    private int type;

    // Static member
    public static final int TYPE_PATIENT = 1;
    public static final int TYPE_DOCTOR = 2;
    public static final int TYPE_PHARMACIST = 3;

    public User(String user_id) {
        this.user_id = user_id;


    }


    
}

