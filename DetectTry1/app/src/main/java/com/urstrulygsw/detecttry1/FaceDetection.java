package com.urstrulygsw.detecttry1;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class FaceDetection extends Application {
    public static final String strRESULT="RESULT";
    public static final String strRESULT_DIALOG="RESULT_DIALOG";


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
