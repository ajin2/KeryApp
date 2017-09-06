package com.example.hanaj.kery;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hanaj.kery.bt.BluetoothService;


public class IntroActivity extends Activity {
    // Debugging
    private static final String TAG = "MAIN";
    private static final boolean D = true;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    //permissions
    private static final int MY_PERMISSIONS_REQUEST_LOCATION=1;




    // Member object for the chat services
    private BluetoothService btService = null;
    private StringBuffer mOutStringBuffer;
    private Button enter_btn;

    private final Handler mHandler = new Handler() {
        //핸들러의 기능을 수행할 클래스(hamdleMessage)
        public void handleMessage(Message msg) {
            //BluetoothService로부터 메시지(msg)를 받는다.
            super.handleMessage(msg);

            switch (msg.what){
                case MESSAGE_STATE_CHANGE:
                    if(D) Log.i(TAG,"MESSAGE_STATE_CHANGE: "+ msg.arg1);

                    switch (msg.arg1){
                        //블루투스 연결이 되었을 경우
                        case BluetoothService.STATE_CONNECTED:
                            Toast.makeText(getApplicationContext(),
                                    "블루투스 연결에 성공하였습니다!", Toast.LENGTH_SHORT).show();
                            //수정부분
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case BluetoothService.STATE_FAIL:
                            Toast.makeText(getApplicationContext(),
                                    "블루투스 연결에 실패하였습니다..", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        //위치 권한 체크(사용 권한이 없을 경우)
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우

            //최초 권한 요청인지, 혹은 사용자에 의한 재요청인지 확인
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //사용자가 임의로 권한을 취소시킨 경우
                //권한 재요청
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                //최초로 권한을 요청하는 경우(첫실행)
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }else {
            //사용권한이 있음을 확인한 경우
        }

        if(btService == null) {
            btService = new BluetoothService(this, mHandler);
            mOutStringBuffer = new StringBuffer("");
        }
        if(btService.getDeviceState()) {
            // 블루투스가 지원 가능한 기기일 때
            btService.enableBluetooth();
        } else {
            finish();
        }
        //시작 버튼
        enter_btn = (Button)findViewById(R.id.enter_btn);
        enter_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //블루투스 기기 목록창 띄움
               btService.scanDevice();
                // Next Step
            }
        });


    }

    @Override
    public  void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    //사용자가 권한 동의를 안함
                    //권한 동의 안함 버튼 선택
                    Toast.makeText(IntroActivity.this,"권한사용을 동의해주셔야 이용이 가능합니다", Toast.LENGTH_LONG).show();
                    finish();
                }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // 확인 눌렀을 때
                    //Next Step

                } else {
                    // 취소 눌렀을 때
                    Log.d(TAG, "Bluetooth is not enabled");

                    finish();
                }
                break;

            case REQUEST_CONNECT_DEVICE:
                if(resultCode == Activity.RESULT_OK)
                {
                    btService.getDeviceInfo(data);
                }
                break;
        }
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
