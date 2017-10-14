package com.example.hanaj.kery.beacon;


import android.util.Log;

import com.example.hanaj.kery.MainActivity;

/**
 * Created by tnghk on 2017-08-21.
 */
public class DistanceCalculator {
    //거리값을 담을 String
    BeaconInfo beaconInfo;

    private static String beaconDirection="";
    private static double beaconDistance;
    private static String ultraSonicDirection="";
    private static String direction="";
    private static String total;

    private static String speed;

    private Kalman mKalmanAccLeft = new Kalman(0.0f);
    private Kalman mKalmanAccCenter = new Kalman(0.0f);
    private Kalman mKalmanAccRight = new Kalman(0.0f);


    //비콘과 센서를 통합하여 방향판단하는 쓰레드
    public synchronized void calculate(){
        beaconCalculate();
        ultraSonicCalculate();
        if(!ultraSonicDirection.equals(""))
            direction=ultraSonicDirection;

        total = direction + "/" + speed;
        ultraSonicDirection="";
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

        filteredLeft = (float) mKalmanAccLeft.update(beaconInfo.getLeft_distance());
        filteredCenter = (float) mKalmanAccCenter.update(beaconInfo.getCenter_distance());
        filteredRight = (float) mKalmanAccRight.update(beaconInfo.getRight_distance());
        beaconInfo.setLeft_distance(filteredLeft);
        beaconInfo.setCenter_distance(filteredCenter);
        beaconInfo.setRight_distance(filteredRight);

        /*
        if (filteredLeft < filteredRight) {
            beaconDirection = "13";
            if (filteredRight < filteredCenter)
                    beaconDirection = "12";
        } else {
            beaconDirection = "11";
            if (filteredLeft < filteredCenter)
                beaconDirection = "12";
        }
        */
        if( filteredCenter < filteredLeft && filteredCenter < filteredRight)
            beaconDirection = "12";
        else{
            if(filteredLeft > filteredRight)
                beaconDirection = "13";
            else
                beaconDirection = "11";
        }
        direction=beaconDirection;
        distanceCalculate();


    }
    //적외선센서를 통한 장애물 파악하는 메소드
    private void ultraSonicCalculate() {
        //아두이노 에서 넘어오는 값은 (숫자),(숫자)
        int idx = MainActivity.driveValues.indexOf(",");

        double usRight = Double.parseDouble(MainActivity.driveValues.substring(0, idx));
        double usLeft = Double.parseDouble(MainActivity.driveValues.substring(idx + 1));
        Log.d("ultra","Right->"+usRight+",Left->"+usLeft);
        if(usLeft<=40 || usRight<=40) {
            if (usRight > usLeft) {
                ultraSonicDirection="13";
            }
            else if(usLeft > usRight){
                ultraSonicDirection="11";
            }
        }
    }

    public static String getDirection() {
        Log.d("Calculator","Drirection->"+total);
        return total;

    }
    private void distanceCalculate() {
        if (beaconDirection.equals("13"))
            beaconDistance = beaconInfo.getRight_distance();
        else if (beaconDirection.equals("11"))
            beaconDistance = beaconInfo.getLeft_distance();
        else
            beaconDistance = beaconInfo.getCenter_distance();

        beaconDistance = beaconDistance*100;

        if (beaconDistance <= 150 )
            speed = "0";
        else if(beaconDistance > 150 &&  beaconDistance <= 200)
            speed ="1";
        else if(beaconDistance > 200 && beaconDistance <= 250)
            speed = "2";
        else if(beaconDistance > 250 && beaconDistance <= 300)
            speed = "3";
        else if(beaconDistance > 300 && beaconDistance <= 350)
            speed = "4";
        else if(beaconDistance > 350 && beaconDistance <= 400)
            speed = "5";
        else if(beaconDistance > 400 && beaconDistance <= 450)
            speed = "6";
        else
            speed = "7";
    }

}
