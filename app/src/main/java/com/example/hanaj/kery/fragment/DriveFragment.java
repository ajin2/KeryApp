package com.example.hanaj.kery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hanaj.kery.MainActivity;
import com.example.hanaj.kery.R;

public class DriveFragment extends Fragment {
    private Button drive_btn,lock_btn;
    private int drive_Btn_state,lock_Btn_state;

    public static DriveFragment newInstance() {
        DriveFragment fragment = new DriveFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DriveFragment() {
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
       View view=inflater.inflate(R.layout.fragment_drive, container, false);

        drive_btn = (Button) view.findViewById(R.id.btn_drive);
        drive_btn.setBackgroundResource(R.drawable.driving_off);
        drive_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (drive_Btn_state == 1)//주행중일때
                {
                    ((MainActivity)getActivity()).stopBeacon();
                    ((MainActivity)getActivity()).sendMessage("0");
                    drive_Btn_state = 0;
                    drive_btn.setBackgroundResource(R.drawable.driving_off);

                } else //주행중이 아닐때
                {
                    ((MainActivity)getActivity()).startBeacon();
                    ((MainActivity)getActivity()).sendMessage("1");
                    drive_btn.setBackgroundResource(R.drawable.driving_on);
                    drive_Btn_state = 1;
                }

            }
        });

        lock_btn = (Button) view.findViewById(R.id.btn_lock);
        lock_btn.setBackgroundResource(R.drawable.lock_off);
        lock_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (lock_Btn_state == 1)//잠금장치가 잠겨있을때
                {
                    ((MainActivity)getActivity()).stopBeacon();
                    ((MainActivity)getActivity()).sendMessage("2");
                    lock_Btn_state = 0;
                    lock_btn.setBackgroundResource(R.drawable.lock_off);

                } else //잠금장치가 안잠겨있을때
                {
                    ((MainActivity)getActivity()).startBeacon();
                    ((MainActivity)getActivity()).sendMessage("3");
                    lock_btn.setBackgroundResource(R.drawable.lock_on);
                    lock_Btn_state = 1;
                }

            }
        });


        return view;
    }


}
