package com.fitpal.fitpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitpal.fitpal.model.Restaurant;
import com.fitpal.fitpal.model.RestaurantAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class LocationTrackingResults extends AppCompatActivity {

    private List<Restaurant> restList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RestaurantAdapter rAdapter;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    JSONArray jsonArrayRestaurants;
    JSONObject oneRestaurant;
    String resName,resAddress,resCost,dish,requesturl,requesturl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_tracking_results);

        recyclerView = (RecyclerView) findViewById(R.id.ltrRecyclerID);
        rAdapter = new RestaurantAdapter(restList);


//        Restaurant r1=new Restaurant("KKK","axaxax","3000");
//        restList.add(r1);
//
//        Restaurant r2=new Restaurant("rrr","axhax","400");
//        restList.add(r2);
//
//        rAdapter = new RestaurantAdapter(restList);
//        recyclerView.setAdapter(rAdapter);



        displayLocationSettingsRequest(getApplicationContext());


        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        // I suppressed the missing-permission warning because this wouldn't be executed in my
        // case without location services being enabled
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        double longitude1 = lastKnownLocation.getLongitude();
        double latitude1 = lastKnownLocation.getLatitude();

//        Log.wtf("latrr","randommm");
//
//
        Log.d("longrr",String.valueOf(longitude1));
        Log.d("latrr",String.valueOf(latitude1));

        Intent i=getIntent();
        String prevpage=i.getStringExtra("prevpage");



        final String locationAPIkey="7af596f781ccad44c3f61efa6fee6381";

        if(prevpage.equals("main")){
            requesturl="https://developers.zomato.com/api/v2.1/search?lat=19.0803&lon=72.888&sort=real_distance" ;
        }
        else{
            requesturl="https://developers.zomato.com/api/v2.1/search?lat="+latitude1+"&lon="+longitude1+"&cuisines="+dish+"&sort=real_distance" ;
            dish=i.getStringExtra("dishName");
            dish=dish.replaceAll(" ","%20");
            if(dish.equals("")){
                Log.d("dishhhn","nahimila");
            }else{
                Log.d("dishhhn",dish);
            }
        }






        RequestQueue queue = Volley.newRequestQueue(this);




        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, requesturl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("resonse11", response.toString());

                        try {
                            jsonArrayRestaurants=response.getJSONArray("restaurants");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        oneRestaurant=null;

                        for(int i=0;i<jsonArrayRestaurants.length();i++){

                            try {
                                oneRestaurant=jsonArrayRestaurants.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            Log.d("objjj",String.valueOf(productFood.get("nf_p").toString()));
//                            Log.d("obj2",String.valueOf(productFood.get("nf_total_fat").toString()));

                            try {
                                JSONObject jtemp= (JSONObject) oneRestaurant.get("restaurant");
                                resName=jtemp.get("name").toString();
                                //resAddress=jtemp.get("location").get("address").toString();
                                JSONObject j2= (JSONObject) jtemp.get("location");
                                resAddress=j2.get("address").toString();
                                resCost=jtemp.get("average_cost_for_two").toString();

                                Log.d("reshh",resName);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            };

                            Restaurant rtemp=new Restaurant(resName,resAddress,resCost);
                            restList.add(rtemp);
                            Log.d("reslist",String.valueOf(restList));


                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(rAdapter);

                            rAdapter = new RestaurantAdapter(restList);

                            rAdapter.notifyDataSetChanged();


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("err11", "error => " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user-key", locationAPIkey);
                params.put("Accept", "application/json");

                return params;
            }
        };
        queue.add(postRequest);


        rAdapter.notifyDataSetChanged();


    }


    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("locperm", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("locperm", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(LocationTrackingResults.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("locperm", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("locperm", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }
}
