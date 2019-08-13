package com.urstrulygsw.detecttry1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button cameraButton;
    private final int REQUEST_IMAGE_CAPTURE=1010;

//    private FirebaseVisionImage image;
//    private FirebaseVisionFaceDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);

        Toast.makeText(this,"Yehhh",Toast.LENGTH_SHORT).show();

        FirebaseApp.initializeApp(MainActivity.this);
        cameraButton = findViewById(R.id.btnCamera);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK && data!=null){

              //cameraButton.setText("yes");
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            if (bitmap != null) {

                detectFace(bitmap);
            }



        }

    }

    private void detectFace(Bitmap bitmap) {
        //cameraButton.setText("yes");
        FirebaseVisionFaceDetectorOptions highAccuracyOpts =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        //.setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .build();


        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(highAccuracyOpts);
        //image=FirebaseVisionImage.fromBitmap(bitmap);
//        cameraButton.setText("yes");
//        detector=FirebaseVision.getInstance()
//                    .getVisionFaceDetector(highAccuracyOpts);


        Task<List<FirebaseVisionFace>> result =
                detector.detectInImage(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<FirebaseVisionFace>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionFace> faces) {

                                        String result="";
                                        int i=1;
                                        for(FirebaseVisionFace face: faces){
                                        result=result.concat("\n"+i+".")
                                        .concat("\n Smile:"+face.getSmilingProbability()*100+"%")
                                        .concat("\n id: "+face.getTrackingId());

                                        i++; //for more than one face
                                        }
                                        if(faces.size()==0){
                                            Toast.makeText(MainActivity.this,"No Faces",Toast.LENGTH_SHORT).show(); }
                                        else {

                                              cameraButton.setText(result);
//                                            Bundle bundle=new Bundle();
//                                            bundle.putString(FaceDetection.strRESULT,result);
//                                            DialogFragment dialogFragment=new ResultDialog();
//                                            dialogFragment.setArguments(bundle);
//                                            dialogFragment.setCancelable(false);//important
//                                            dialogFragment.show(getSupportFragmentManager(),FaceDetection.strRESULT_DIALOG);
                                        }


                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                        cameraButton.setText("no");
                                    }
                                });

//
//
//




//        FirebaseVisionFaceDetectorOptions highAccuracyOpts =
//                new FirebaseVisionFaceDetectorOptions.Builder()
//                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
//                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
//                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
//                        //.enableTracking()
//                        .build();
//
//            image=FirebaseVisionImage.fromBitmap(bitmap);
//            detector=FirebaseVision.getInstance()
//                    .getVisionFaceDetector(highAccuracyOpts);
//            if(detector!=null){
//                cameraButton.setText("yes");
//            }
//




//        try {
//            image=FirebaseVisionImage.fromBitmap(bitmap);
//            detector=FirebaseVision.getInstance()
//                    .getVisionFaceDetector(highAccuracyOpts);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            cameraButton.setText("going in catch block");
//        }



    }
}







//        FirebaseApp.initializeApp(this);
//        cameraButton = findViewById(R.id.btnCamera);
//
//        cameraButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(intent.resolveActivity(getPackageManager())!=null){//has all permission
//                    startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
//                    //Toast.makeText(MainActivity.this,"yaaaar capturing or not",Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//       //super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Toast.makeText(MainActivity.this,"dekho "+requestCode,Toast.LENGTH_LONG).show();
//
//            Bundle bundle = data.getExtras();
//            Bitmap bitmap = (Bitmap) bundle.get("data");
//            if (bitmap != null) {
//                Toast.makeText(MainActivity.this,"Bitmap is not null",Toast.LENGTH_LONG).show();
//            }
//
//             else{
//                    Toast.makeText(MainActivity.this,"Bitmap is null",Toast.LENGTH_LONG).show();
//                }
//
//
//                detectFace(bitmap);
//
//
//                //Toast.makeText(MainActivity.this, "Bitmap has data", Toast.LENGTH_SHORT).show();
//                detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
//                    @Override
//                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
//                        Toast.makeText(MainActivity.this, "on Success", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                })
//                .addOnFailureListener(
//                        new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(MainActivity.this, "On Fail", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//
//
//            }
//            else{
//            Toast.makeText(MainActivity.this,"Our Capture code has major problem",Toast.LENGTH_LONG).show();
//
//
//        }
//
//        }
//
//
//
//////
//////                detector.detectInImage(image)
//////                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
//////                            @Override
//////                            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
//////                                for (FirebaseVisionFace face : firebaseVisionFaces) {
//////
//////                                    // If classification was enabled:
//////                                    if (face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
//////                                        float smileProb = face.getSmilingProbability();
//////                                    }
//////                                    if (face.getRightEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
//////                                        float rightEyeOpenProb = face.getRightEyeOpenProbability();
//////                                    }
//////
//////                                    // If face tracking was enabled:
//////                                    if (face.getTrackingId() != FirebaseVisionFace.INVALID_ID) {
//////                                        int id = face.getTrackingId();
//////                                    }
//////
//////
//////                                }
//////
//////                            }
//////                        })
//////                        .addOnFailureListener(new OnFailureListener() {
//////                            @Override
//////                            public void onFailure(@NonNull Exception e) {
//////                                Toast.makeText(MainActivity.this, "On Fail something something", Toast.LENGTH_SHORT).show();
//////                            }
//////                        });
////
////
////            } else {
////                Toast.makeText(MainActivity.this, "Bitmap has no data", Toast.LENGTH_SHORT).show();
////            }
////
////        } else {
////            Toast.makeText(MainActivity.this, "Something went wrong with" + requestCode, Toast.LENGTH_SHORT).show();
////        }
////    }
//
//
//
//
//
//
//
//    private void detectFace(Bitmap bitmap) {
//
//
//        FirebaseVisionFaceDetectorOptions highAccuracyOpts =
//                new FirebaseVisionFaceDetectorOptions.Builder()
//                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
//                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
//                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
//                        //.enableTracking()
//                        .setMinFaceSize(0.15f)
//                        .build();
//
//        try {
//            image=FirebaseVisionImage.fromBitmap(bitmap);
//            detector=FirebaseVision.getInstance()
//                    .getVisionFaceDetector(highAccuracyOpts);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Toast.makeText(this,"hightaccuracy has something ",Toast.LENGTH_LONG).show();
//
//
//        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
//            @Override
//            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
//                Toast.makeText(MainActivity.this,"something is happending",Toast.LENGTH_SHORT).show();
//
//                String result="";
//                int i=1;
//                for(FirebaseVisionFace face: firebaseVisionFaces){
//                    result=result.concat("\n"+i+".")
//                            .concat("\n Smile:"+face.getSmilingProbability()*100+"%")
//                            .concat("\n id: "+face.getTrackingId());
//
//                    i++; //for more than one face
//                }
//                if(firebaseVisionFaces.size()==0){
//                    Toast.makeText(MainActivity.this,"No Faces",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Bundle bundle=new Bundle();
//                    bundle.putString(FaceDetection.strRESULT,result);
//                    DialogFragment dialogFragment=new ResultDialog();
//                    dialogFragment.setArguments(bundle);
//                    dialogFragment.setCancelable(false);//important
//                    dialogFragment.show(getSupportFragmentManager(),FaceDetection.strRESULT_DIALOG);
//                }
//
//
//
//            }
//
//
//
//
//
//
//
//        });
//
//    }
//}
