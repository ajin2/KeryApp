package com.example.hanaj.kery.beacon;

/**
 * Created by tnghk on 2017-08-21.
 */
public class BeaconInfo {
    //블루투스 모듈 MACADDRESS
    public String BEACON_LEFT_ADDRESS = "D4:36:39:9D:9C:27";
    public String BEACON_CENTER_ADDRESS = "D4:36:39:9D:9C:0A";
    public String BEACON_RIGHT_ADDRESS = "D4:36:39:9B:3D:74";

    //거리값을 담을 String
    private static int left_distance;
    private static int center_distance;
    private static int right_distance;

    public String getBEACON_LEFT_ADDRESS() {
        return BEACON_LEFT_ADDRESS;
    }

    public String getBEACON_CENTER_ADDRESS() {
        return BEACON_CENTER_ADDRESS;
    }

    public String getBEACON_RIGHT_ADDRESS() {
        return BEACON_RIGHT_ADDRESS;
    }

    public void setBEACON_LEFT_ADDRESS(String BEACON_LEFT_ADDRESS) {
        this.BEACON_LEFT_ADDRESS = BEACON_LEFT_ADDRESS;
    }

    public static int getLeft_distance() {
        return left_distance;
    }

    public static void setLeft_distance(int left_distance) {
        BeaconInfo.left_distance = left_distance;
    }

    public static int getCenter_distance() {
        return center_distance;
    }

    public static void setCenter_distance(int center_distance) {
        BeaconInfo.center_distance = center_distance;
    }

    public static int getRight_distance() {
        return right_distance;
    }

    public static void setRight_distance(int right_distance) {
        BeaconInfo.right_distance = right_distance;
    }
}


