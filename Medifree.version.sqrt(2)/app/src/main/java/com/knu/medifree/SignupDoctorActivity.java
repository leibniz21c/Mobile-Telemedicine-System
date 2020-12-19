package com.knu.medifree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knu.medifree.util.DBManager;

import java.util.HashMap;
import java.util.Map;

public class SignupDoctorActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btn_next;
    private FirebaseAuth mAuth;
    private EditText emailEditView, passwordEditView, pwChkEditView, hospitalNameEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_doctor);

        mAuth = FirebaseAuth.getInstance();

        // 객체 할당
        btn_next = (ImageButton) findViewById(R.id.next_step);
        emailEditView = (EditText) findViewById(R.id.email_D);
        passwordEditView = (EditText) findViewById(R.id.password_D);
        pwChkEditView = (EditText) findViewById(R.id.passwordCheck_D);

        // 클릭 리스너 할당
        btn_next.setOnClickListener(this);
    }
    //알림을 출력하는 method
    private void startToast(String msg) { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }

    private void registerUser() {
        String email = ((EditText) findViewById((R.id.email_D))).getText().toString();
        String password = ((EditText) findViewById(R.id.password_D)).getText().toString();
        String passwordCheck = ((EditText) findViewById((R.id.passwordCheck_D))).getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(passwordCheck)) {
            Toast.makeText(this, "Check Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }
        if (password.equals(passwordCheck)) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // 회원가입 성공
                                FirebaseUser user = mAuth.getCurrentUser();

                                //현재 유저의 uid가져오기.
                                String uid = user.getUid();
                                //user정보를 db에 집어넣가.
                                insert_user_Information(uid);

                            } else {
                                // 회원가입 실패=> 비밀번호 길이 및 아이디 중복 여부 등
                                if (task.getException() != null){
                                    startToast(task.getException().toString());
                                }
                            }
                        }
                    });
        }


    }
    //생성된 uid 및 나머지 정보들 firestore에 넣는 작업.
    private void insert_user_Information(final String uid) {

        String name = ((EditText)findViewById(R.id.name_D)).getText().toString();
        String phone = ((EditText)findViewById((R.id.phone_D))).getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("name",name);
        user.put("userType","Doctor");
        user.put("phoneNum",phone);
        //실제 firestore에 추가하는 작업, add=> 자동으로 문서id(문서이름)를 만들어줌

        // Add a new document with a generated ID
        db.collection("Profile").document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        //uid정보에 다음 페이지로 넘어가 hospital & major에 넣기.
                        Intent intent = new Intent(getApplicationContext(), SignupDoctor2Activity.class);
                        intent.putExtra("user_id", uid);
                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        DBManager.startActivityWithHospitalReading(SignupDoctorActivity.this, intent);
                        // Auto termination
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("정보저장에 실패하였습니다.");
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v == btn_next) {
            //TODO
            registerUser();
        }

    }
}