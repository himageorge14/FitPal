package com.fitpal.fitpal;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fitpal.fitpal.model.MapFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class LocationTrackingHome extends AppCompatActivity {

    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_tracking_home);

        search=(Button)findViewById(R.id.locationSearchID);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),RestaurantResults.class);
                startActivity(i);

            }
        });


        MapFragment mf = new MapFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.replaceLayout, mf).commit();

    }


}
