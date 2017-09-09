package com.example.hanaj.kery;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.hanaj.kery.beacon.BeaconInfo;
import com.example.hanaj.kery.beacon.DistanceCalculator;
import com.example.hanaj.kery.bt.BluetoothService;
import com.example.hanaj.kery.pagerAdapter.PagerAdapter;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    //아두이노 받은 값을 받는 변수
    public static String values="0";
    // synchronized flags
    private static final int STATE_SENDING = 1 ;
    private static final int STATE_NO_SENDING = 2 ;
    private int mSendingState ;

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

    }

    //비콘 탐색 시작
    public void startBeacon(){
        beaconManager.setForegroundScanPeriod(100l);
        beaconManager.setForegroundBetweenScanPeriod(100l);
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
                    Double distance = beacon.getDistance();
                    if (address.equals(beaconInfo.getBEACON_LEFT_ADDRESS()))
                        beaconInfo.setLeft_distance((int) (distance * 100));
                    else if (address.equals(beaconInfo.getBEACON_CENTER_ADDRESS()))
                        beaconInfo.setCenter_distance((int) (distance * 100));
                    else if (address.equals(beaconInfo.getBEACON_RIGHT_ADDRESS()))
                        beaconInfo.setRight_distance((int) (distance * 100));
                }
                Log.d("distances", "Left->" + beaconInfo.getLeft_distance() + " " + "Center->" + beaconInfo.getCenter_distance() + " " + "Right->" + beaconInfo.getRight_distance());
                distanceCalculator.calculate();
                sendMessage(distanceCalculator.getDirection());
            }
        });
        try {

            beaconManager.startRangingBeaconsInRegion(new Region("MyRangingUniqueId",null,null,null));
        } catch (RemoteException e){ }
    }

    /*메시지를 보낼 메소드 정의*/
    public synchronized void sendMessage( String message) {

        if ( mSendingState == STATE_SENDING ) {
            try {
                wait() ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mSendingState = STATE_SENDING ;
        Log.d("DRIVEAC","state=>"+btService.getState());
        // Check that we're actually connected before trying anything
        if ( btService.getState() != BluetoothService.STATE_CONNECTED ) {
            mSendingState = STATE_NO_SENDING ;
            return ;
        }

        // Check that there's actually something to send
        if ( message.length() > 0 ) {
            // Get the message bytes and tell the BluetoothChatService to write
            Log.d("DRIVE", "send->"+message);
            byte[] send = message.getBytes() ;
            btService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0) ;
        }

        mSendingState = STATE_NO_SENDING ;
        notify() ;
    }
}
