package com.knu.medifree.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.knu.medifree.R;
import com.knu.medifree.model.Doctor;
import com.knu.medifree.model.Hospital;
import com.knu.medifree.model.Reservation;
import com.knu.medifree.model.User;
import com.knu.medifree.DHomeActivity;
import com.knu.medifree.DResNextActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DBManager extends Thread {
    private static DateFormat dateformat;
    private static String TAG = "DBManager";

    // Encapsulate target
    private static ArrayList<Reservation> reservations_list = new ArrayList<Reservation>();
    private static ArrayList<Hospital> hospitals_list = new ArrayList<Hospital>();
    private static ArrayList<Doctor> doctors_list = new ArrayList<Doctor>() ;
    private static String result_patient_name = new String();
    private static String result_tel = new String();


    // User info
    private static String uid;
    private static int utype;

    public static void initDBManager(String uid, int utype)
    /*  LoginActivity.class 에서 DBManager 초기화후 진입합니다(사용할 일 없습니다.)
     *   Usage :
     *   DBManager.initDBManger(user_id, User.TYPE_PATIENT);
     *
     *   */
    {
        dateformat = new SimpleDateFormat("yyyy/MM/dd/kk/mm");
        DBManager.uid = uid;
        DBManager.utype = utype;
    }

    // Creator
    public static void createReservation(Reservation reservation)
    /*  Firestore DB 에 Reservation을 추가합니다.
     *   Usage :
     *   DBManager.createReservation(reservation);
     *
     *   */
    {
        // Return reservation_id
        Map<String, Object> resMap = new HashMap<>();

        // Create map
        resMap.put("patient_id", reservation.getPatient_id());
        resMap.put("doctor_id", reservation.getDoctor_id());
        resMap.put("date", reservation.getDate());
        resMap.put("completed", reservation.isCompleted());
        resMap.put("patient_name", reservation.getPatient_name());
        resMap.put("doctor_name", reservation.getDoctor_name());
        resMap.put("done", reservation.isDone());
        // Set
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Reservation")
                .add(resMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("DBManager", documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("DBManager", "setReservation() error!", e);
                    }
                });
    }

    public static void createHospital(Hospital hospital)
        /*  Firestore DB 에 Hospital을 추가합니다.
         *  Hospital 에서 name 필드만 형성되고, 각각의 Major 필드는 createMajor를 사용하셔야 됩니다.
         *   Usage :
         *   DBManager.createHospital(hospital);
         *
         *   */
    {
        // Return reservation_id
        Map<String, Object> resMap = new HashMap<>();

        // Create map
        resMap.put("name", hospital.getHospitalName());
        resMap.put("major_mask", Integer.toString(hospital.getBitmask()));

        // Set
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Hospital")
                .add(resMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("DBManager", documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("DBManager", "setReservation() error!", e);
                    }
                });
    }

    // Updator
    public static void updateReservation(Reservation reservation) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference reservationDocRef =
                db.collection("Reservation")
                        .document(reservation.getId());

        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(reservationDocRef);

                // Note: this could be done without a transaction
                //       by updating the population using FieldValue.increment()
                transaction.update(reservationDocRef, "completed", reservation.isCompleted());
                transaction.update(reservationDocRef, "date", reservation.getDate());
                transaction.update(reservationDocRef, "doctor_id", reservation.getDoctor_id());
                transaction.update(reservationDocRef, "doctor_name", reservation.getDoctor_name());
                transaction.update(reservationDocRef, "patient_id", reservation.getPatient_id());
                transaction.update(reservationDocRef, "patient_name", reservation.getPatient_name());
                transaction.update(reservationDocRef, "done", reservation.isDone());

                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                    }
                });
    }

    // Getter
    public static ArrayList<Reservation> getReservations()
    /*  Firestore DB 에서
     *   initDBManager()에서 세팅된 user_id에 대응하는 Reservation 객체들을 ArrayList 타입으로 받아옵니다.
     *   반드시, 이전 액티비티에서 startActivityWithReservationReading() 메소드가 선행되어야 합니다.
     *   Usage :
     *   ArrayList<Reservation> reservationList = getReservations()
     *
     *   */
    {
        return DBManager.reservations_list;
    }

    public static ArrayList<Hospital> getHospitals()
        /*  Firestore DB 에서
         *   Hospital 객체들을 ArrayList 타입으로 받아옵니다.
         *   반드시, 이전 액티비티에서 startActivityWithHospitalReading() 메소드가 선행되어야 합니다.
         *   Usage :
         *   ArrayList<Hospital> hospitalList = getHospital()
         *
         *   */
    {
        return DBManager.hospitals_list;
    }

    public static ArrayList<Doctor> getDoctors()
        /*  Firestore DB 에서
         *   이전 액티비티에서 선택된 Hospital와 Major에 존재하는 Doctor 객체들을 ArrayList 타입으로 받아옵니다.
         *   반드시, 이전 액티비티에서 startActivityWithMajorDoctor() 메소드가 선행되어야 합니다.
         *   Usage :
         *   ArrayList<Doctor> doctorList = getDoctors()
         *
         *   */
    {
        return DBManager.doctors_list;
    }

    public static String getHospitalId(String hospital_name)
        /*      Must use after startActivityWithHospitalReading() please.   */
        /*      return : (Success : hospital_id), (Failure : null)          */
    {
        for (int i = 0 ;i < DBManager.hospitals_list.size() ;i ++)
            if (DBManager.hospitals_list.get(i).getHospitalName().equals(hospital_name))
                return DBManager.hospitals_list.get(i).getHospitalId();
        return null;
    }

    public static String getPatientName() {
        return DBManager.result_patient_name;
    }

    // Deleter
    public static void deleteHospital(String hospital_id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Hospital").document(hospital_id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public static void deleteReservation(String reservation_id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Reservation").document(reservation_id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    // Activity Starter
    public static void startActivityWithReservationReading(Activity from, Intent to)
    /*
     *   from Activity에서 to Activity로 넘어갑니다. 이때, from Activity는 종료됩니다.
     *   넘어가면서, DB에서 initDBManager()에서 초기화한 uid를 기준으로 Reservation관련 정보들을 받아옵니다.
     *   Usage :
     *      Intent intent = new Intent(getApplicationContext(), PHomeActivity.class);
     *      intent.putExtra("info", info);
     *      DBManager.startActivityWithReservationReading(FromActivity.this, intent);
     *
     *   */
    {

        if (DBManager.utype == User.TYPE_DOCTOR) {
            // Query Setting
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference resRef = db.collection("Reservation");
            Query resDataQuery = resRef
                    .whereEqualTo("doctor_id", DBManager.uid)
                    .whereEqualTo("done", false);
            Log.i("HEESUNG", "Waiting DB Callback...");
            resDataQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.i("HEESUNG", "Getting DB Callback...");
                        reservations_list = new ArrayList<Reservation>();
                        // Critical section control
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            reservations_list.add(new Reservation(
                                    document.getString("patient_id")
                                    , document.getString("patient_name")
                                    , document.getString("doctor_id")
                                    , document.getString("doctor_name")
                                    , document.getString("date")
                                    , document.getBoolean("completed")
                                    , document.getId()
                                    , document.getBoolean("done")
                            ));
                        }
                        Log.i("HEESUNG", "Reservation DB Updeate complete.");
                        from.startActivity(to);

                    }
                }
            });
        } else if (DBManager.utype == User.TYPE_PATIENT) {
            // Query Setting
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference resRef = db.collection("Reservation");
            Query resDataQuery = resRef
                    .whereEqualTo("patient_id", DBManager.uid)
                    .whereEqualTo("done", false);
            Log.i("HEESUNG", "Waiting DB Callback...");
            resDataQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.i("HEESUNG", "Getting DB Callback...");
                        reservations_list = new ArrayList<Reservation>();
                        // Critical section control
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            reservations_list.add(new Reservation(
                                    document.getString("patient_id")
                                    , document.getString("patient_name")
                                    , document.getString("doctor_id")
                                    , document.getString("doctor_name")
                                    , document.getString("date")
                                    , document.getBoolean("completed")
                                    , document.getId()
                                    , document.getBoolean("done")
                            ));
                        }
                        Log.i("HEESUNG", "Reservation DB Updeate complete.");
                        from.startActivity(to);
                        from.finish();
                    }
                }
            });
        } // End of if (type == User.TYPE_DOCTOR)
    }

    public static void startActivityWithHospitalReading(Activity from, Intent to) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Hospital");

        Log.i("HEESUNG", "Waiting DB Callback...");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.i("HEESUNG", "Getting DB Callback...");
                    hospitals_list = new ArrayList<Hospital>();

                    // Critical section control
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        hospitals_list.add(new Hospital(
                                document.getString("name")
                                , document.getId()
                                , Integer.valueOf(document.getString("major_mask"))
                        ));
                        Log.e("HEESUNG", Integer.toString(Integer.valueOf(document.getString("major_mask"))));
                    }
                    Log.i("HEESUNG", "Reservation DB Updeate complete.");
                    from.startActivity(to);
                    from.finish();
                }
            }
        });
    }

    public static void startActivityWithDoctorReading(String hospital_name, String major_name, Activity from, Intent to) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Profile");

        Log.i("HEESUNG", "Waiting DB Callback...");
        collectionReference
                .whereEqualTo("Hospital_Name", hospital_name)
                .whereEqualTo("Major", major_name)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.i("HEESUNG", "Getting DB Callback...");
                    doctors_list = new ArrayList<Doctor>();

                    // Critical section control
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        doctors_list.add(new Doctor(
                                document.getString("Hospital_Name")
                                , document.getString("Major")
                                , document.getString("name")
                                , document.getString("phoneNum")
                                , document.getId()
                        ));
                    }
                    Log.i("HEESUNG", "Reservation DB Updeate complete.");
                    from.startActivity(to);
                    from.finish();
                }
            }
        });
    }

    public static void startActivityWithPatientReading(String patient_id, Activity from, Intent to) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Profile").document(patient_id);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DBManager.result_patient_name = document.getString("name");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
                from.startActivity(to);
                from.finish();
            }
        });
    }

    public static void showDialogWithGetPatientTel(String patient_id, String content, Activity activity, Dialog dialog, String reservation_id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Profile").document(patient_id);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        DBManager.result_tel = document.getString("phoneNum");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

                dialog.show();
                Button noBtn = dialog.findViewById(R.id.noBtn);
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        Uri smsUri = Uri.parse("tel:" + DBManager.result_tel);
                        Intent intent = new Intent(Intent.ACTION_VIEW, smsUri); // 보내는 화면이 팝업됨

                        intent.putExtra("address", DBManager.result_tel); // 받는 번호
                        intent.putExtra("sms_body", content); // 보낼 문자내용
                        intent.setType("vnd.android-dir/mms-sms");
                        activity.startActivity(intent);

                        activity.startActivity(new Intent(activity.getApplicationContext(), DHomeActivity.class));
                        activity.finish();
                    }
                });
                dialog.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        Intent toIntent = new Intent(activity.getApplicationContext(), DResNextActivity.class);
                        toIntent.putExtra("Reservation_ID", reservation_id);
                        toIntent.putExtra("mms_uri", DBManager.result_tel);
                        toIntent.putExtra("mms_body", content);
                        activity.startActivity(toIntent);
                        activity.finish();
                    }
                });


            }
        });

    }

}