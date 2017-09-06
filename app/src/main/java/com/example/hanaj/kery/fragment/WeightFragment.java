package com.example.hanaj.kery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hanaj.kery.MainActivity;
import com.example.hanaj.kery.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeightFragment extends Fragment {
    private Button weight_btn;
    private TextView weight_value;
    public static WeightFragment newInstance() {
        WeightFragment fragment = new WeightFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public WeightFragment() {
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
        View view=inflater.inflate(R.layout.fragment_weight, container, false);

        weight_value=(TextView) view.findViewById(R.id.weight_value);
        weight_btn = (Button) view.findViewById(R.id.weight_Btn);
        weight_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ((MainActivity)getActivity()).sendMessage("4");
                weight_value.setText(MainActivity.values);
            }
        });

        return view;
    }



}
