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
    private String speed;
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
        int left=beaconInfo.getLeft_distance();
        int center=beaconInfo.getCenter_distance();
        int right=beaconInfo.getRight_distance();

        if(left>right) {
            beaconDirection = "13";
            if(right>center)
                beaconDirection="12";
        }else {
            beaconDirection = "11";
            if(left>center)
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
    private void distanceCalculate(){
        if(beaconDirection.equals("13"))
            beaconDistance=beaconInfo.getRight_distance();
        else if(beaconDirection.equals("11"))
            beaconDistance=beaconInfo.getLeft_distance();
        else
            beaconDistance=beaconInfo.getCenter_distance();

        if(beaconDistance<=1000)
            speed="0";
        else if(beaconDistance>1000 && beaconDistance<=1200)
            speed="1";
        else if(beaconDistance>1200 && beaconDistance<=1700)
            speed="2";
        else if(beaconDistance>1700 && beaconDistance<=2500)
            speed="3";
        else if(beaconDistance>2500 && beaconDistance<=3500)
            speed="4";
        else if(beaconDistance>3500 && beaconDistance<=5000)
            speed="5";
        else if(beaconDistance>5000 && beaconDistance<=6000)
            speed="6";
        else if(beaconDistance>6000 && beaconDistance<=7000)
            speed="7";
        else if(beaconDistance>7000 && beaconDistance<=8000)
            speed="8";
        else if(beaconDistance>8000 && beaconDistance<=9000)
            speed="9";
        else
            speed="10";
    }
}
