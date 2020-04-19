package com.fitpal.fitpal.model;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitpal.fitpal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.fragment.app.FragmentManager;


import java.util.ArrayList;

import androidx.annotation.NonNull;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class MapFragment extends androidx.fragment.app.Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    private ArrayList<Locations> initialMapMarkers;
    DatabaseReference databaseReference;
    String key;


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync((OnMapReadyCallback) this);


        //initialize array;
        initialMapMarkers = new ArrayList<>();

        return v;
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng si= new LatLng(19.0735, 72.8995);
        mMap.addMarker(new MarkerOptions().position(si).title("Current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(si));

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        // I suppressed the missing-permission warning because this wouldn't be executed in my
        // case without location services being enabled
        if (checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        double userLat = lastKnownLocation.getLatitude();
        double userLong = lastKnownLocation.getLongitude();

        // Add a marker in Sydney and move the camera
        LatLng s= new LatLng(userLat, userLong);
        mMap.addMarker(new MarkerOptions().position(s).title("Current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(s));



        //refreshData();

    }



    private void refreshData(){
//        if(!initialMapMarkers.isEmpty()){
//            initialMapMarkers.clear();
//        }

//        databaseReference = FirebaseDatabase.getInstance().getReference("Reports");
//        key = databaseReference.getKey();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                    Locations myReport=dataSnapshot1.getValue(Locations.class);
//                    initialMapMarkers.add(myReport);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//        ArrayList<Locations> mapItems=new ArrayList<>();

//        for(int i=0;i<initialMapMarkers.size();i++){
//            Locations tempReport=initialMapMarkers.get(i);
//            LatLng tempLatLng=new LatLng(tempReport.getLat(),tempReport.getLong());
//            mMap.addMarker(new MarkerOptions().position(tempLatLng)
//                    .title(tempReport.getDescription().toString())
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.black_marker)));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(tempLatLng));
//        }


    }

}
