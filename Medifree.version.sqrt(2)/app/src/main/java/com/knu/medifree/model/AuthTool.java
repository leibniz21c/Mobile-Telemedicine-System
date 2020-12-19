package com.knu.medifree.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
public class AuthTool {
    private static FirebaseFirestore db = null;
    private static FirebaseAuth mAuth;
    public static void getInstance(){
        if(db == null)
            db = FirebaseFirestore.getInstance();
    }

    public static FirebaseAuth getmAuth(){
        mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }
}