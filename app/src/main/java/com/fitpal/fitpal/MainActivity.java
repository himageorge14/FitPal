package com.fitpal.fitpal;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button voice,barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voice=findViewById(R.id.voiceRedirectButtonId);
        barcode=findViewById(R.id.barcodeRedirectButtonId);

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iVoice=new Intent(getApplicationContext(),VoiceInput.class);
                startActivity(iVoice);
            }
        });
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iBar=new Intent(getApplicationContext(),BarcodeInput.class);
                startActivity(iBar);
            }
        });
    }
}
