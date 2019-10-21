package com.fitpal.fitpal;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import info.androidhive.barcode.BarcodeReader;

public class BarcodeInput extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener{

    Fragment barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_input);
        barcodeReader = getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {

        // playing barcode reader beep sound
        //barcodeReader.playBeep();

        Log.d("Resultssssss",barcode.displayValue);

        Intent i=new Intent(getApplicationContext(),BarcodeResults.class);
        i.putExtra("code",barcode.displayValue);
        startActivity(i);


        // ticket details activity by passing barcode
//        Intent intent = new Intent(getApplicationContext(), FoodResults.class);
//        intent.putExtra("code", barcode.displayValue);
//        startActivity(intent);
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }
}
