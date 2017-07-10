package com.example.hanaj.kery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hanaj.kery.bluetooth.BluetoothService;
import com.example.hanaj.kery.gPS.GPSActivity;
import com.example.hanaj.kery.option.OptionActivity;
import com.example.hanaj.kery.weight.WeightActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Debugging
    private static final String TAG = "Main";

    //Intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout
    private Button bluetoothBtn;

    private BluetoothService btService = null;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bluetoothBtn = (Button)findViewById(R.id.bluetoothBtn);
        Button autoDrvingBtn = (Button)findViewById(R.id.autoDrivingBtn);
        Button lockBtn = (Button)findViewById(R.id.lockBtn);
        Button gpsBtn = (Button)findViewById(R.id.gpsBtn);
        Button weightBth = (Button)findViewById(R.id.weightBtn);
        Button optionBth = (Button)findViewById(R.id.optionBtn);

        bluetoothBtn.setOnClickListener(this);

        // BluetoothService 클래스 생성
        if (btService == null) {
            btService = new BluetoothService(this, mHandler);
        }

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

        bluetoothBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(btService.getDeviceState()) {
            // 블루투스가 지원 가능한 기기일 때
            btService.enableBluetooth();
        }
        else {
            finish();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult " + requestCode);

        switch(requestCode) {

            case REQUEST_CONNECT_DEVICE:
                // When DeiceListActivity returns with a device to connect
                if(resultCode == Activity.RESULT_OK) {
                    btService.getDeviceInfo(data);
                }
                break;

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if(resultCode == Activity.RESULT_OK) {
                    btService.scanDevice();
                } else {
                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
        }
    }
}




