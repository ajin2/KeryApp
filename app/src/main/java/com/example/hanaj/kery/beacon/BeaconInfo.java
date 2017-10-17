package com.example.hanaj.kery.beacon;

public class BeaconInfo {

    //블루투스 모듈 MACADDRESS
    public String BEACON_LEFT_ADDRESS = "D4:36:39:9C:CA:F0";
    public String BEACON_CENTER_ADDRESS = "D4:36:39:9B:44:60";
    public String BEACON_RIGHT_ADDRESS = "D4:36:39:9B:38:0C";

    //거리값을 담을 String
    private static double left_distance;
    private static double center_distance;
    private static double right_distance;
    //rssi
    private static double left_rssi;
    private static double center_rssi;
    private static double right_rssi;

    public String getBEACON_LEFT_ADDRESS() {
        return BEACON_LEFT_ADDRESS;
    }
    public String getBEACON_CENTER_ADDRESS() {
        return BEACON_CENTER_ADDRESS;
    }
    public String getBEACON_RIGHT_ADDRESS() {
        return BEACON_RIGHT_ADDRESS;
    }

    public static double getLeft_distance() {
        return left_distance;
    }

    public static void setLeft_distance(double left_distance) {
        BeaconInfo.left_distance = left_distance;
    }

    public static double getCenter_distance() {
        return center_distance;
    }

    public static void setCenter_distance(double center_distance) {
        BeaconInfo.center_distance = center_distance;
    }

    public static double getRight_distance() {
        return right_distance;
    }

    public static void setRight_distance(double right_distance) {
        BeaconInfo.right_distance = right_distance;
    }

    public static double getLeft_rssi() {
        return left_rssi;
    }

    public static void setLeft_rssi(double left_rssi) {
        BeaconInfo.left_rssi = left_rssi;
    }

    public static double getCenter_rssi() {
        return center_rssi;
    }

    public static void setCenter_rssi(double center_rssi) {
        BeaconInfo.center_rssi = center_rssi;
    }

    public static double getRight_rssi() {
        return right_rssi;
    }

    public static void setRight_rssi(double right_rssi) {
        BeaconInfo.right_rssi = right_rssi;
    }
}

