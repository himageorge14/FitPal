package com.fitpal.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BarcodeResults extends AppCompatActivity {

    TextView barcodeNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_results);
        barcodeNo=(TextView)findViewById(R.id.barcodeNumberId);
        Intent i=getIntent();
        barcodeNo.setText(i.getStringExtra("code"));
    }
}
