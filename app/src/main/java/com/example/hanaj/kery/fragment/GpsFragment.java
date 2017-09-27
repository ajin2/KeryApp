package com.example.hanaj.kery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hanaj.kery.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GpsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GpsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GpsFragment extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gps, container, false);
            }


}
