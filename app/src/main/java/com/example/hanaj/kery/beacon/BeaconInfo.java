package com.example.hanaj.kery.beacon;

public class BeaconInfo {

    //블루투스 모듈 MACADDRESS
    public String BEACON_LEFT_ADDRESS = "D4:36:39:9C:CA:F0";
    public String BEACON_CENTER_ADDRESS = "D4:36:39:9D:9C:0A";
    public String BEACON_RIGHT_ADDRESS = "D4:36:39:9B:3D:74";

    //거리값을 담을 String
    private static int left_rssi;
    private static int center_rssi;
    private static int right_rssi;

    public String getBEACON_LEFT_ADDRESS() {
        return BEACON_LEFT_ADDRESS;
    }
    public String getBEACON_CENTER_ADDRESS() {
        return BEACON_CENTER_ADDRESS;
    }
    public String getBEACON_RIGHT_ADDRESS() {
        return BEACON_RIGHT_ADDRESS;
    }

    public static int getLeft_rssi() {
        return left_rssi;
    }

    public static void setLeft_rssi(int left_distance) {
        BeaconInfo.left_rssi = left_distance;
    }

    public static int getCenter_rssi() {
        return center_rssi;
    }

    public static void setCenter_rssi(int center_distance) {
        BeaconInfo.center_rssi = center_distance;
    }

    public static int getRight_rssi() {
        return right_rssi;
    }

    public static void setRight_rssi(int right_distance) {
        BeaconInfo.right_rssi = right_distance;
    }
}

