package com.example.hanaj.kery.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hanaj.kery.MainActivity;
import com.example.hanaj.kery.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GpsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GpsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GpsFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    //private float mLat=0;
    //private float mLon=0;
    public static GpsFragment newInstance() {
        GpsFragment fragment = new GpsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public GpsFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("val1",MainActivity.mLat+","+MainActivity.mLon);

        View view=inflater.inflate(R.layout.fragment_gps, container, false);;
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        mMap.clear();


        LatLng kery = new LatLng(Float.valueOf(MainActivity.mLat),Float.valueOf(MainActivity.mLon));
        mMap.addMarker(new MarkerOptions().position(kery).title("My kery"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kery,18));

    }
}
