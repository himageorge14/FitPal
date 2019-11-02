package com.fitpal.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BarcodeResults extends AppCompatActivity {

    private TextView name,brand,size,ingr,ntr,barcode;
    private Button readAgain;
    private String pr,bar,br,si,ing,nut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_results);

        name=findViewById(R.id.BFoodNameId);
        brand=findViewById(R.id.BBrandId);
        size=findViewById(R.id.BServeId);
        ingr=findViewById(R.id.BIngrId);
        ntr=findViewById(R.id.BNtrScoreId);
        barcode=findViewById(R.id.BBarId);

//        i.putExtra("page","barcode");
//        i.putExtra("brand",brand);
//        i.putExtra("size",size);
//        i.putExtra("product",product);
//        i.putExtra("ingr",ingr);
//        i.putExtra("nutr",nutrScore);

        Intent t=getIntent();
        pr=t.getStringExtra("product");
        bar=t.getStringExtra("barcode");
        br=t.getStringExtra("brand");
        si=t.getStringExtra("size");
        ing=t.getStringExtra("ingr");
        nut=t.getStringExtra("nutr");


        name.setText(pr);
        brand.setText(br);
        size.setText(si);
        ingr.setText(ing);
        ntr.setText(nut);
        barcode.setText(bar);





        readAgain=findViewById(R.id.BReadAgainID);
        readAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),BarcodeInput.class);
                startActivity(i);
                finish();
            }
        });
    }
}
