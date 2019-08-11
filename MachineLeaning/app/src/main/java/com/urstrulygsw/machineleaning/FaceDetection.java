package com.urstrulygsw.machineleaning;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class FaceDetection extends Application { //MADE-6
    public static final String strRESULT="RESULT";
    public static final String strRESULT_DIALOG="RESULT_DIALOG";
    //MADE-7

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this); //MADE-8
    }
}
