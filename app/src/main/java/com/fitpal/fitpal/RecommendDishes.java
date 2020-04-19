package com.fitpal.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecommendDishes extends AppCompatActivity {

    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_dishes);

        Button but1 = findViewById(R.id.rec1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                //Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
//                URL = "http://"+R.string.ipaddress+"/name?dish1=Masala%20dosa&meal=breakfast";
//                callapifun("1");
//                URL = "http://"+R.string.ipaddress+"/name?dish1=chana%20pulao&meal=lunch";
//                callapifun("2");
//                URL = "http://"+R.string.ipaddress+"/name?dish1=dal%20rice&meal=dinner";
//                callapifun("3");http://192.168.1.103:3000/name?dish1=dal%20rice&meal=dinner

                URL = "http://192.168.1.100:3000/name?dish1=Masala%20dosa&meal=breakfast";
                callapifun("1");
                URL = "http://192.168.1.100:3000/name?dish1=chana%20pulao&meal=lunch";
                callapifun("2");
                URL = "http://192.168.1.100:3000/name?dish1=dal%20rice&meal=dinner";
                callapifun("3");
                Log.d("aaa","called");

            }
        }) ;

        GridLayout grid1 = findViewById(R.id.grid1);
        grid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                TextView next1 = (TextView)findViewById(R.id.food1);
                Toast.makeText(getApplicationContext(),next1.getText().toString(),Toast.LENGTH_SHORT).show();

                Intent i=new Intent(getApplicationContext(),LocationTrackingResults.class);
                i.putExtra("dishName",next1.getText().toString());
                i.putExtra("prevpage","rec");
                startActivity(i);

            }
        }) ;

        GridLayout grid2 = findViewById(R.id.grid2);
        grid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                TextView next2 = (TextView)findViewById(R.id.food2);
                Toast.makeText(getApplicationContext(),next2.getText().toString(),Toast.LENGTH_SHORT).show();

                Intent i=new Intent(getApplicationContext(),LocationTrackingResults.class);
                i.putExtra("dishName",next2.getText().toString());
                i.putExtra("prevpage","rec");
                startActivity(i);
            }
        }) ;

        GridLayout grid3 = findViewById(R.id.grid3);
        grid3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                TextView next3 = (TextView)findViewById(R.id.food3);
                Toast.makeText(getApplicationContext(),next3.getText().toString(),Toast.LENGTH_SHORT).show();

                Intent i=new Intent(getApplicationContext(),LocationTrackingResults.class);
                i.putExtra("dishName",next3.getText().toString());
                i.putExtra("prevpage","rec");
                startActivity(i);
            }
        }) ;

//        Button but2 = findViewById(R.id.rec2);
//        but2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
////                Toast.makeText(getApplicationContext(),"Hello2",Toast.LENGTH_SHORT).show();
//                URL = "http://192.168.1.102:3000/name?dish1=Puri%20Bhaji";
//                callapifun();
//            }
//        }) ;
    }

    public void callapifun(String a)
    {
//        Toast.makeText(getApplicationContext(),"Helloinside api",Toast.LENGTH_SHORT).show();
        final String b=a;
        Log.d("aaa","called2");

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jr = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override

                    public void onResponse(JSONArray response) {
                        TextView fn1,ca1,qt1;
                        Log.e("rrrr", response.toString());
                        Log.e("rrrr1", "resp1");

                        try{
                            for(int i=0;i<1;i++) {
                                JSONObject object1=response.getJSONObject(i);
                                String dish =object1.getString("Dish");
                                String calories =object1.getString("Calories");
                                Log.e("resttt",dish);
                                if(b=="1")
                                {
                                    fn1 = (TextView)findViewById(R.id.food1);
                                    qt1 =(TextView)findViewById(R.id.qty1);
                                    ca1 =(TextView)findViewById(R.id.cal1);

                                }
                                else if(b=="2")
                                {
                                    fn1 = (TextView)findViewById(R.id.food2);
                                    qt1 =(TextView)findViewById(R.id.qty2);
                                    ca1 =(TextView)findViewById(R.id.cal2);
                                }
                                else
                                {
                                    fn1 = (TextView)findViewById(R.id.food3);
                                    qt1 =(TextView)findViewById(R.id.qty3);
                                    ca1 =(TextView)findViewById(R.id.cal3);
                                }
                                fn1.setText(dish);
                                qt1.setText("1");
                                ca1.setText(calories);

                            }}
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("erroree", error.toString());
                        Log.e("erroree1", "error1");
                    }
                }
        );

        jr.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jr);
    }



}
