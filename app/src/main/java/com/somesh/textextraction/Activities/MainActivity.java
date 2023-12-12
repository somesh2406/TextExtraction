package com.somesh.textextraction.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.somesh.textextraction.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openRelaventActivity(View view) {
        int id = view.getId();

        if( id == R.id.aadharCardView){
                Intent aadharIntent = new Intent( this, AadharCardActivity.class);
                startActivity(aadharIntent);
        } else if(id == R.id.panCardView) {
            Intent panIntent = new Intent(this, PanCardActivity.class);
            startActivity(panIntent);
        } else{
            Intent voterIntent = new Intent( this, VoterCardActivity.class);
            startActivity(voterIntent);
        }

    }
}