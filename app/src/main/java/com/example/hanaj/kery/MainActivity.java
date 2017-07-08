package com.example.hanaj.kery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hanaj.kery.gPS.GPSActivity;
import com.example.hanaj.kery.option.OptionActivity;
import com.example.hanaj.kery.weight.WeightActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button autoDrvingBtn = (Button)findViewById(R.id.autoDrivingBtn);
        Button lockBtn = (Button)findViewById(R.id.lockBtn);
        Button gpsBtn = (Button)findViewById(R.id.gpsBtn);
        Button weightBth = (Button)findViewById(R.id.weightBtn);
        Button optionBth = (Button)findViewById(R.id.optionBtn);

        autoDrvingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
        //        Intent intent = new Intent(MainActivity.this, AutoDrivingActivity.class);
        //        startActivity(intent);
                Toast.makeText(getApplicationContext(), "자율주행 버튼이 눌렸습니다", Toast.LENGTH_SHORT).show();
            }
        });

        lockBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
         //       Intent intent = new Intent(MainActivity.this, LockActivity.class);
         //       startActivity(intent);
                Toast.makeText(getApplicationContext(), "잠김 버튼이 눌렸습니다", Toast.LENGTH_SHORT).show();
            }
        });

        gpsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, GPSActivity.class);
                startActivity(intent);
            }
        });

        weightBth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, WeightActivity.class);
                startActivity(intent);
            }
        });

        optionBth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, OptionActivity.class);
                startActivity(intent);
            }
        });
    }
}
