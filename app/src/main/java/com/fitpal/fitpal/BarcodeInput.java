package com.fitpal.fitpal;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import info.androidhive.barcode.BarcodeReader;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class BarcodeInput extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener{

    OkHttpClient client = new OkHttpClient();
    String brand;

    Fragment barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_input);
        barcodeReader = getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);


    }
    @Override
    public void onScanned(final Barcode barcode) {

        // playing barcode reader beep sound
        //barcodeReader.playBeep();

        Log.d("Resultssssss",barcode.displayValue);

        String URLline = "https://world.openfoodfacts.org/api/v0/product/"+ barcode.displayValue +".json";
        JSONObject jsonObject1=new JSONObject();
        try {
            jsonObject1.put("barcode", barcode.displayValue);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String res;
        JsonObjectRequest jRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, URLline,jsonObject1,new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(BarcodeInput.this,response,Toast.LENGTH_LONG).show();
                        try{

                            //Log.d("resss",String.valueOf(response));
                            //JSONArray ja = jsonObject.getJSONArray("data");
                            JSONObject j=response.getJSONObject("product");
                            brand=j.get("brands").toString();
                            Log.d("bbb",brand);

                            String size=j.get("serving_size").toString();
                            String product=j.get("product_name_fr").toString();
                            String ingr=j.get("ingredients_text").toString();
//                            String nutrScore=j.get("nutrition_score_debug").toString();
//                            String nutrScore=j.get("nutrition_score_debug").toString();

                            String carbsBar=j.getJSONObject("nutriments").get("carbohydrates_value").toString();
                            String fatsBar=j.getJSONObject("nutriments").get("fat_value").toString();
//                            String fiberBar=j.getJSONObject("nutriments").get("fiber_value").toString();
                            String caloriesBar=j.getJSONObject("nutriments").get("energy-kcal_serving").toString();
                            String proteinBar=j.getJSONObject("nutriments").get("proteins_serving").toString();
//                            String calciumBar=j.getJSONObject("nutriments").get("calcium").toString();


                            String code=barcode.displayValue;

//                            Toast.makeText(getApplicationContext(),String.valueOf(response),Toast.LENGTH_LONG).show();
//                            Toast.makeText(getApplicationContext(),brand,Toast.LENGTH_LONG).show();
//                            Toast.makeText(getApplicationContext(),brand,Toast.LENGTH_LONG).show();
                          //  carbohydrates_serving
                            Intent i=new Intent(getApplicationContext(),BarcodeResults.class);
                            i.putExtra("page","barcode");
                            i.putExtra("brand",brand);
                            i.putExtra("size",size);
                            i.putExtra("product",product);
                            i.putExtra("ingr",ingr);
                           // i.putExtra("nutr",nutrScore);
                            i.putExtra("barcode",barcode.displayValue);

   //                         i.putExtra("calcium",calciumBar);
                            i.putExtra("protein",proteinBar);
                            i.putExtra("calories",caloriesBar);
//                            i.putExtra("fiber",fiberBar);
                            i.putExtra("fats",fatsBar);
                            i.putExtra("carbs",carbsBar);

                            startActivity(i);
                            //finish();


                        } catch (JSONException e)

                        {
                            e.printStackTrace();
                        }
                    }


                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BarcodeInput.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.d("errr",error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("","");
                params.put("","");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jRequest);

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
        Log.d("serr","scaaa");
    }
}
