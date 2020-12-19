package com.knu.medifree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
//Firebase Auth를 위한 API
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knu.medifree.model.User;
import com.knu.medifree.util.DBManager;

import java.util.HashMap;
import java.util.Map;

public class SignupPatientActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String user_id;
    private ImageButton btn_reg;
    private EditText et_email, et_password, et_password_again, et_name, et_tel, et_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_patient);

        mAuth = FirebaseAuth.getInstance();
        btn_reg = (ImageButton) findViewById(R.id.signup_patient_btn_reg);
        et_email = (EditText) findViewById(R.id.signup_patient_email);
        et_password = (EditText) findViewById(R.id.signup_patient_password);
        et_password_again = (EditText) findViewById(R.id.signup_patient_password_again);
        et_name = (EditText) findViewById(R.id.signup_patient_name);
        et_tel = (EditText) findViewById(R.id.signup_patient_tel);
        et_address = (EditText) findViewById(R.id.signup_patient_address);

        // 클릭 리스너 할당
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 계정 생성후 PHome으로 이동
                createAccount_Patient();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void createAccount_Patient() {
        String email = ((TextView) findViewById(R.id.signup_patient_email)).getText().toString();
        String password = ((TextView) findViewById(R.id.signup_patient_password)).getText().toString();
        String passwordCheck = ((TextView) findViewById(R.id.signup_patient_password_again)).getText().toString();

        if (email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            if (password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 회원가입 성공
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입이 완료되었습니다.");

                                    //현재 유저의 uid가져오기.
                                    user_id = user.getUid();
                                    insert_user_Information(user_id);
                                } else {
                                    // 회원가입 실패=> 비밀번호 길이 및 아이디 중복 여부 등
                                    if (task.getException() != null){
                                        startToast(task.getException().toString());
                                    }
                                }
                            }
                        });
            } else {
                // 비밀번호 확인실패.
                startToast("비밀번호가 일치하지 않습니다.");
            }
        } else{
            startToast("이메일 또는 비밀번호를 입력해주세요.");
        }
    }
    //알림을 출력하는 method
    private void startToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }

    //생성된 uid 및 나머지 정보들 firestore에 넣는 작업.
    private void insert_user_Information(String uid) {

        String name = ((EditText)findViewById(R.id.signup_patient_name)).getText().toString();
        String phone = ((EditText)findViewById((R.id.signup_patient_tel))).getText().toString();
        String address = ((EditText)findViewById(R.id.signup_patient_address)).getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("userType","Patient");
        user.put("name",name);
        user.put("phoneNum",phone);
        user.put("Address",address);

        //실제 firestore에 추가하는 작업, add=> 자동으로 문서id(문서이름)를 만들어줌

        // Add a new document with a generated ID
        db.collection("Profile").document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        //홈화면으로 이동.
                        Intent intent = new Intent(getApplicationContext(), PHomeActivity.class);
                        DBManager.initDBManager(uid, User.TYPE_PATIENT);
                        DBManager.startActivityWithReservationReading(SignupPatientActivity.this, intent);

                        // Addition
                        LoginActivity act_login = (LoginActivity) LoginActivity.activity;
                        act_login.finish(); // Login activity termination
                        // End of Addition
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("정보저장에 실패하였습니다.");
                    }
                });
    }
}