package com.knu.medifree;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.knu.medifree.model.AuthTool;
import com.knu.medifree.model.User;
import com.knu.medifree.util.DBManager;

public class LoginActivity extends AppCompatActivity {
    // View
    private EditText et_email, et_password;
    private ImageButton btn_signin;
    private Button btn_signup;

    // Activity controller
    public static Activity activity;

    // Auth
    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //DBManager.refreshHospitals();

        // Controller
        activity = LoginActivity.this;

        // Loading
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        // DB Instance
        AuthTool.getInstance();
        mAuth = AuthTool.getmAuth();

        // Assigning
        btn_signin= (ImageButton) findViewById(R.id.login_btn_signin);
        btn_signup = (Button) findViewById(R.id.login_btn_signup);
        et_email = (EditText) findViewById(R.id.login_et_email);
        et_password = (EditText) findViewById(R.id.login_et_password);


        // Listeners
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Go PHomeActivity */

                // get string values
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                // Go in below method
                signin(email, password);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go TypeActivity
                Intent intent = new Intent(getApplicationContext(), TypeActivity.class);
                startActivity(intent);
            }
        });

    } // End of onCreate()

    // completed
    private void signin(String email, String password) {
        if (email.length() > 0 && password.length() > 0 ){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // 로그인 성공
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인 되었습니다.");
                                String uid = user.getUid();

                                //Firestore db로 부터 uid를 사용하여 현재 user의 userType을 가져오는 함수.
                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                DocumentReference docRef = db.collection("Profile").document(uid);
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                String userType= document.getData().get("userType").toString();
                                                if ( userType.equals("Patient")){
                                                    //patient일때 patient 홈 화면으로 간다.

                                                    Intent intent = new Intent(getApplicationContext(), PHomeActivity.class);
                                                    DBManager.initDBManager(uid, User.TYPE_PATIENT);
                                                    DBManager.startActivityWithReservationReading(LoginActivity.this, intent);
                                                    // Auto Termination
                                                }else {
                                                    //Doctor라면 Doctor홈화면으로 간다.
                                                    Intent intent = new Intent(getApplicationContext(), DHomeActivity.class);
                                                    DBManager.initDBManager(uid, User.TYPE_DOCTOR);
                                                    startActivity(intent);
                                                    finish();
                                                    // Auto Termination
                                                }

                                            } else {
                                                startToast("document가 없습니다.");
                                            }
                                        } else {
                                            startToast("get failed with "+ task.getException());
                                        }
                                    }
                                });

                            }
                            else {
                                // 로그인 실패=> 비밀번호 길이 및 아이디 중복 여부 등
                                if (task.getException() != null) {
                                    startToast(task.getException().toString());
                                }
                                else {
                                    startToast("NULL value!");
                                }
                            }
                        }
                    });
        }
        else
            startToast("이메일 또는 비밀번호를 입력해주세요.");
    } // End of Method
    private void startToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


}