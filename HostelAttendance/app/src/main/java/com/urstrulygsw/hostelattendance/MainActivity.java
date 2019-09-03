package com.urstrulygsw.hostelattendance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    //1
    private FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPassword;
    Button btnLogin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);




        //2
        mAuth = FirebaseAuth.getInstance();


        //4
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(etEmail,etPassword);
            }


        });





    }

    private void signIn(EditText etEmail, EditText etPassword) {

        final String strEmail = etEmail.getText().toString();
        final String strPassword = etPassword.getText().toString();


        mAuth.signInWithEmailAndPassword(strEmail,strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("sahi", "signInWithEmail:success");

                            Toast.makeText(MainActivity.this,"Successfully Logged In",Toast.LENGTH_SHORT).show();
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if(currentUser!= null){
                                Intent intent=new Intent(MainActivity.this,MainPage.class);
                                startActivity(intent);

                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("galat", "signInWithEmail:failure", task.getException());

                            Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });


    }

    //3
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!= null){
            Intent intent=new Intent(this,MainPage.class);
            startActivity(intent);

        }
        else
        {

        }

    }

}

