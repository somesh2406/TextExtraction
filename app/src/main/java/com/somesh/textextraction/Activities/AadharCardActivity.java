package com.somesh.textextraction.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
import com.somesh.textextraction.PostProcessing.AadharProcessing;
import com.somesh.textextraction.R;
import com.somesh.textextraction.utils.CameraUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;





public class AadharCardActivity extends AppCompatActivity {

    private ImageView frontImageView;
    private ImageView backImageView;
    String mCurrentPhotoPath;
    String mCurrentPhotoPath2;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_TAKE_PHOTO_2 = 2;
    private Bitmap mImageBitmap;
    private Bitmap mImageBitmap2;
    private EditText name;
    private EditText yearOfBirth;
    private EditText gender;
    private EditText aadhaarNumber;
    private EditText pincode;
    private EditText addressLine1;
    private EditText addressLine2;
    private EditText fatherName;
    private Button reset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar_card);
        frontImageView =(ImageView)findViewById(R.id.imageviewfront);
        backImageView=findViewById(R.id.imageviewback);
        name=findViewById(R.id.name_edit_text);
        yearOfBirth=findViewById(R.id.yob_edit_text);
        gender=findViewById(R.id.gender_edit_text);
        aadhaarNumber=findViewById(R.id.aadhaar_edit_text);
        pincode=findViewById(R.id.pincode_edit_text);
        addressLine1=findViewById(R.id.address_lane1_edit_text);
        addressLine2=findViewById(R.id.address_lane2_edit_text);
        fatherName=findViewById(R.id.father_name_edit_text);
        reset=findViewById(R.id.reset);
        reset.setText("R"+"\n"+"E"+"\n"+"S"+"\n"+"E"+"\n"+"T");


    }

    public void takePicture(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = CameraUtils.createImageFile(this);
                mCurrentPhotoPath=photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this,"Error creating file",Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }

    public void takeBackPicture(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = CameraUtils.createImageFile(this);
                mCurrentPhotoPath2=photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this,"Error creating file",Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_2);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            mImageBitmap = CameraUtils.getBitmap(mCurrentPhotoPath);
            frontImageView.setImageBitmap(mImageBitmap);
            reset.setVisibility(View.VISIBLE);

        } else if (requestCode == REQUEST_TAKE_PHOTO_2 && resultCode == RESULT_OK) {

            mImageBitmap2 = CameraUtils.getBitmap(mCurrentPhotoPath2);
            backImageView.setImageBitmap(mImageBitmap2);
            reset.setVisibility(View.VISIBLE);

        }
    }

    public void reset(View view){
        mImageBitmap=null;
        mImageBitmap2=null;
        backImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.aadhaar_camera_back));
        frontImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.aadhaar_camera_front));
        reset.setVisibility(View.GONE);
    }

    public void extractInfo(View view){
        if(mImageBitmap!=null && mImageBitmap2!=null) {


            FirebaseVisionTextDetector detector = FirebaseVision.getInstance().getVisionTextDetector();
            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mImageBitmap);
            detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    HashMap<String ,String> dataMap=new AadharProcessing().processExtractedTextForFrontPic(firebaseVisionText,getApplicationContext());
                    presentFrontOutput(dataMap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            FirebaseVisionImage image2 = FirebaseVisionImage.fromBitmap(mImageBitmap2);
            detector.detectInImage(image2).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    HashMap<String ,String> dataMap=new AadharProcessing().processExtractedTextForBackPic(firebaseVisionText,getApplicationContext());
                    presentBackOutput(dataMap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
        else {
            Toast.makeText(this,"Please Take Both front and Back Pics first",Toast.LENGTH_SHORT).show();
        }


    }

    private void presentFrontOutput(HashMap<String,String> dataMap){
        if(dataMap!=null){
            aadhaarNumber.setText(dataMap.get("aadhaar"), TextView.BufferType.EDITABLE);
            gender.setText(dataMap.get("gender"), TextView.BufferType.EDITABLE);
            fatherName.setText(dataMap.get("fatherName"), TextView.BufferType.EDITABLE);
            yearOfBirth.setText(dataMap.get("yob"), TextView.BufferType.EDITABLE);
            name.setText(dataMap.get("name"), TextView.BufferType.EDITABLE);

        }
    }

    private void presentBackOutput(HashMap<String,String> dataMap){
        if(dataMap!=null){
            addressLine1.setText(dataMap.get("addressLine1"), TextView.BufferType.EDITABLE);
            addressLine2.setText(dataMap.get("addressLine2"), TextView.BufferType.EDITABLE);
            pincode.setText(dataMap.get("pincode"), TextView.BufferType.EDITABLE);

        }
    }



}
}