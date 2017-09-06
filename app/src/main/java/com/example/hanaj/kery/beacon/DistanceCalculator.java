package com.example.hanaj.kery.beacon;


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
        beaconDirection = beaconInfo.getLeft_distance() > beaconInfo.getCenter_distance() ? "12" :
                beaconInfo.getLeft_distance() > beaconInfo.getRight_distance() ? "13" : "11";
        distanceCalculate();
        total = beaconDirection + "/" + speed;

    }
    //적외선센서를 통한 장애물 파악하는 메소드
    private void infraredCalculate(){

    }

    public static String getDirection() {
        return total;
    }
    private void distanceCalculate(){
        beaconDistance = beaconInfo.getLeft_distance() > beaconInfo.getCenter_distance() ? beaconInfo.getCenter_distance() :
                beaconInfo.getLeft_distance() > beaconInfo.getRight_distance() ? beaconInfo.getRight_distance() : beaconInfo.getLeft_distance();
        if(beaconDistance<=100)
            speed="0";
        else if(beaconDistance>100 && beaconDistance<=200)
            speed="1";
        else if(beaconDistance>200 && beaconDistance<=300)
            speed="2";
        else if(beaconDistance>300 && beaconDistance<=400)
            speed="3";
        else if(beaconDistance>400 && beaconDistance<=500)
            speed="4";
        else if(beaconDistance>500 && beaconDistance<=600)
            speed="5";
        else if(beaconDistance>600 && beaconDistance<=700)
            speed="6";
        else if(beaconDistance>700 && beaconDistance<=800)
            speed="7";
        else if(beaconDistance>800 && beaconDistance<=900)
            speed="8";
        else if(beaconDistance>900 && beaconDistance<=1000)
            speed="9";
        else
            speed="10";
    }
}
