package com.urstrulygsw.machineleaning;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button cameraButton;
    private final static int REQUEST_IMAGE_CAPTURE=1010;
    private FirebaseVisionImage image;
    private FirebaseVisionFaceDetector detector;
    //MADE-11

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this); //MADE-12  need in every activity
        cameraButton=findViewById(R.id.camera_button);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) !=null){
                    startActivityForResult(intent,REQUEST_IMAGE_CAPTURE); //MADE-13 picture is taken in intent
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            //each camera operation requires this if
            //RESULT_OK  for selecting image
            Bundle bundle=data.getExtras();
            Bitmap bitmap= (Bitmap) bundle.get("data");
            if(bitmap!=null){
                Toast.makeText(MainActivity.this,"Bitmap has data",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Bitmap has no data",Toast.LENGTH_SHORT).show();
            }
            detectFace(bitmap);

            //MADE-14

        }
        else{
            Toast.makeText(MainActivity.this,"Something went wrong with"+requestCode,Toast.LENGTH_SHORT).show();

        }
    }

    private void detectFace(final Bitmap bitmap) {
        //MADE-15
        FirebaseVisionFaceDetectorOptions highAccuracyOpts =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setModeType(FirebaseVisionFaceDetectorOptions.ACCURATE_MODE)
                        .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .setMinFaceSize(0.15f)
                        .setTrackingEnabled(true)//circle over the face
                        .build();

        try {
            image=FirebaseVisionImage.fromBitmap(bitmap);
            detector=FirebaseVision.getInstance()
                    .getVisionFaceDetector(highAccuracyOpts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
            @Override
            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                String result="";
                int i=1;
                for(FirebaseVisionFace face: firebaseVisionFaces){
                    result=result.concat("\n"+i+".")
                            .concat("\n Smile:"+face.getSmilingProbability()*100+"%");

                    i++; //for more than one face
                }
                if(firebaseVisionFaces.size()==0){
                    Toast.makeText(MainActivity.this,"No Faces",Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle=new Bundle();
                    bundle.putString(FaceDetection.strRESULT,result);
                    DialogFragment dialogFragment=new ResultDialog();
                    dialogFragment.setArguments(bundle);
                    dialogFragment.setCancelable(false);//important
                    dialogFragment.show(getSupportFragmentManager(),FaceDetection.strRESULT_DIALOG);
                }

            }
        });
    }
}
