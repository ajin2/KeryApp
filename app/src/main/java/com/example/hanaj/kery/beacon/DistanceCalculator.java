package com.example.hanaj.kery.beacon;


import android.util.Log;

/**
 * Created by tnghk on 2017-08-21.
 */
public class DistanceCalculator {
    //거리값을 담을 String
    BeaconInfo beaconInfo;

    private static String beaconDirection="";
    private static int beaconDistance;
    private static String infrareDirection="";
    private static String direction="";
    private static String total;

    private static String speed;

    private Kalman mKalmanAccLeft = new Kalman(0.0f);
    private Kalman mKalmanAccCenter = new Kalman(0.0f);
    private Kalman mKalmanAccRight = new Kalman(0.0f);


    //비콘과 센서를 통합하여 방향판단하는 쓰레드
    public synchronized void calculate(){
        beaconCalculate();
    }
    //비콘을 통한 위치확인 하는 메소드
    private void beaconCalculate(){
        /*
            LEFT = 11
            CENTER = 12
            RIGHT = 13
           문자로된 값보다 숫자로된 값이 더 빠를듯 하여 문자를 보내지않고 숫자를 보냄
         */
        /*beaconDirection = beaconInfo.getLeft_distance() > beaconInfo.getCenter_distance() ?
                beaconInfo.getCenter_distance()>beaconInfo.getRight_distance() ? "13" : "12" : "11";
        */
        float filteredLeft = 0.0f;
        float filteredCenter = 0.0f;
        float filteredRight = 0.0f;

        filteredLeft = (float) mKalmanAccLeft.update(beaconInfo.getLeft_rssi());
        filteredCenter = (float) mKalmanAccCenter.update(beaconInfo.getCenter_rssi());
        filteredRight = (float) mKalmanAccRight.update(beaconInfo.getRight_rssi());

        if (filteredLeft < filteredRight) {
            beaconDirection = "13";
            if (filteredRight < filteredCenter)
                    beaconDirection = "12";
        } else {
            beaconDirection = "11";
            if (filteredLeft < filteredCenter)
                beaconDirection = "12";
        }
        distanceCalculate();

        total = beaconDirection + "/" + speed;

    }
    //적외선센서를 통한 장애물 파악하는 메소드
    private void infraredCalculate(){

    }

    public static String getDirection() {
        Log.d("Calculator","Drirection->"+total);
        return total;
    }
    private void distanceCalculate() {
        if (beaconDirection.equals("13"))
            beaconDistance = beaconInfo.getRight_rssi();
        else if (beaconDirection.equals("11"))
            beaconDistance = beaconInfo.getLeft_rssi();
        else
            beaconDistance = beaconInfo.getCenter_rssi();

        if (beaconDistance >= -59)
            speed = "0";
        else if(beaconDistance < -59 &&  beaconDistance >= -62)
            speed ="1";
        else if(beaconDistance <-62 && beaconDistance >= -65)
            speed = "2";
        else if(beaconDistance <-65 && beaconDistance >=-68)
            speed = "3";
        else if(beaconDistance <-68 && beaconDistance >=-71)
            speed = "4";
        else if(beaconDistance <-71 && beaconDistance >=-74)
            speed = "5";
        else if(beaconDistance < -74 && beaconDistance >=-77)
            speed = "6";
        else
            speed = "7";
    }

}
