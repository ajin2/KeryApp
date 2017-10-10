package com.example.hanaj.kery;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;


import com.example.hanaj.kery.beacon.BeaconInfo;
import com.example.hanaj.kery.beacon.DistanceCalculator;
import com.example.hanaj.kery.bt.BluetoothService;
import com.example.hanaj.kery.fragment.GpsFragment;
import com.example.hanaj.kery.pagerAdapter.PagerAdapter;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

import static android.R.attr.delay;

public class
MainActivity extends AppCompatActivity implements BeaconConsumer{

    ImageView bt_status;
    //아두이노 받은 값을 받는 변수
    public static String gpsValues="0,0";
    public static String weight="0";
    public static String driveValues="0,0";
    // synchronized flags
    private static final int STATE_SENDING = 1 ;
    private static final int STATE_NO_SENDING = 2 ;
    private int mSendingState ;

    //위도 경도
    public static String mLat="0";
    public static String mLon="0";
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Member object for the chat services
    private BluetoothService btService = null;
    private StringBuffer mOutStringBuffer;

    private BeaconManager beaconManager;
    private BeaconInfo beaconInfo;
    private DistanceCalculator distanceCalculator;

    private final Handler mHandler = new Handler() {
        //핸들러의 기능을 수행할 클래스(hamdleMessage)
        public void handleMessage(Message msg) {
            //BluetoothService로부터 메시지(msg)를 받는다.
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        bt_status= (ImageView)findViewById(R.id.bt_status);

        // Register for broadcasts on BluetoothAdapter state change
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);

        //fragment를 위한 설정들
        PagerAdapter mPagerAdapter = new PagerAdapter(
                getSupportFragmentManager()
        );
        ViewPager mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mViewpager.setAdapter(mPagerAdapter);
        TabLayout mTab=(TabLayout) findViewById(R.id.tabs);
        mTab.setupWithViewPager(mViewpager);
        mTab.setTabTextColors(getResources().getColorStateList(R.color.white));
        mTab.getTabAt(0).setIcon(R.drawable.carrier);
        mTab.getTabAt(1).setIcon(R.drawable.weight);
        mTab.getTabAt(2).setIcon(R.drawable.gps);

        // Set TabSelectedListener
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 2) {
                    sendMessage("5");
                    Log.d("val2", MainActivity.gpsValues);
                    if (gpsValues.equals("0,0")) {

                    }
                    else {
                        int idx = MainActivity.gpsValues.indexOf(",");

                        // @ 앞부분을 추출
                        // substring은 첫번째 지정한 인덱스는 포함하지 않는다.
                        // 아래의 경우는 첫번째 문자열인 a 부터 추출된다.
                        mLat = MainActivity.gpsValues.substring(0, idx);

                        //
                        // 아래 substring은 @ 바로 뒷부분인 n부터 추출된다.
                        mLon = MainActivity.gpsValues.substring(idx + 1);
                    }

                }
                else if (tab.getPosition() == 1)
                {
                    MainActivity.gpsValues="0,0";
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //////////////////////////////////////////////////////////////

        //통신을 위한 블루투스  객체 설정
        if(btService == null) {
            btService = new BluetoothService(this,mHandler);
            mOutStringBuffer= new StringBuffer("");
            Log.d("btservicestate", "state->" + btService.getState());
        }

        //비콘매니저 객체를 초기화
        beaconManager = BeaconManager.getInstanceForApplication(this);
        distanceCalculator = new DistanceCalculator();
        beaconInfo = new BeaconInfo();

        //기기에 따라 setBeaconLayout안의 내용을 바꿔줘야 하는듯 함.
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        //비콘 탐지 시작 및 아두이노에 신호 송신

        sendMessage("5");

    }
    //비콘 탐색 시작
    public void startBeacon(){
        //0.5초에 한번씩 탐색
        beaconManager.setForegroundScanPeriod(101l);
        beaconManager.setForegroundBetweenScanPeriod(0);
        beaconManager.bind(this);
    }
    //비콘 탐색 종료
    public void stopBeacon(){
        beaconManager.unbind(this);
    }
    @Override
    public void onBeaconServiceConnect(){
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            //비콘이 감지되면 해당 함수가 호출됨. Collection<Beacon> beacons에는 감지된 비콘의 리스트가
            //region에는 비콘들에 대응하는 Region객체가 들어옴
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, org.altbeacon.beacon.Region region) {
                for (Beacon beacon : beacons) {
                    //비콘 모듈의 이름이 일시적으로 바뀌지 않아 맥어드레스로 대체
                    String address = beacon.getBluetoothAddress();
                    int rssi = (int)beacon.getRunningAverageRssi();

                    if (address.equals(beaconInfo.BEACON_LEFT_ADDRESS))
                        beaconInfo.setLeft_rssi(rssi);
                    else if (address.equals(beaconInfo.BEACON_CENTER_ADDRESS))
                        beaconInfo.setCenter_rssi(rssi);
                    else if (address.equals(beaconInfo.BEACON_RIGHT_ADDRESS))
                        beaconInfo.setRight_rssi(rssi);
                    distanceCalculator.calculate();

                    sendMessage(distanceCalculator.getDirection());

                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("MyRangingUniqueId",null,null,null));
        } catch (RemoteException e){ }
    }


    /*메시지를 보낼 메소드 정의*/
    public synchronized void sendMessage(String message) {

        if (mSendingState == STATE_SENDING) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mSendingState = STATE_SENDING;
        // Check that we're actually connected before trying anything
        if (btService.getState() != BluetoothService.STATE_CONNECTED) {
            mSendingState = STATE_NO_SENDING;
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            btService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }

        mSendingState = STATE_NO_SENDING;
        notify();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d("btSTATE", "start");

            switch(action){
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    bt_status.setImageResource(R.drawable.bluetooth_black_on);
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    bt_status.setImageResource(R.drawable.bluetooth_black_off);
                    break;
            }

            /*if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
                bt_status.setImageResource(R.drawable.bluetooth_black_on);
                Log.d("btSTATE", "on");

            }else if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
                bt_status.setImageResource(R.drawable.bluetooth_black_off);
                Log.d("btSTATE","off");

            }*/

            /*if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        bt_status.setImageResource(R.drawable.bluetooth_black_off);
                        Log.d("btSTATE","off");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        break;
                    case BluetoothAdapter.STATE_ON:
                        bt_status.setImageResource(R.drawable.bluetooth_black_on);
                        Log.d("btSTATE", "on");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;
                }
            }
            */
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

       unregisterReceiver(mReceiver);
    }

}
