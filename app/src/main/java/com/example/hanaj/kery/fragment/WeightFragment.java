package com.example.hanaj.kery.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView imageView;
    private Button weight_btn;
    private TextView weight_value;
    TextView text_kg;
    private float Data = 0;
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
        imageView = (ImageView) view.findViewById(R.id.imageView);
        text_kg = (TextView) view.findViewById(R.id.text_kg);

        weight_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Data = Float.parseFloat(MainActivity.weight);
                ((MainActivity) getActivity()).sendMessage("4");
                //Log.d("ffffff",MainActivity.weight);
                weight_value.setText(MainActivity.weight);
                if(Data <= 0){
                    weight_value.setText("0.0");
                    imageView.setImageResource(R.drawable.weight0);}
                else if(Data <= 1){imageView.setImageResource(R.drawable.weight1);}
                else if(1 < Data && Data <=2){imageView.setImageResource(R.drawable.weight2);}
                else if(2 < Data && Data <=3){imageView.setImageResource(R.drawable.weight3);}
                else if(3 < Data && Data <=4){imageView.setImageResource(R.drawable.weight4);}
                else if(4 < Data && Data <=5){imageView.setImageResource(R.drawable.weight5);}
                else if(5 < Data && Data <=6){imageView.setImageResource(R.drawable.weight6);}
                else if(6 < Data && Data <=7){imageView.setImageResource(R.drawable.weight7);}
                else if(7 < Data && Data <=8){imageView.setImageResource(R.drawable.weight8);}
                else if(8 < Data && Data <=9){imageView.setImageResource(R.drawable.weight9);}
                else {imageView.setImageResource(R.drawable.weight10);}
            }

        });

        return view;
    }



}
