package com.example.hanaj.kery.beacon;
/*
** Created by tnghk on 2017-10-02.
*/
public class Kalman{
    private double Q = 0.00001;
    private double R = 0.001;
    private double P = 1;
    private double X = 0;
    private double K;
    // 첫 번째값을입력받아초기화한다. 예전값들을계산해서현재값에적용해야하므로반드시하나이상의값이필요하므로~
    Kalman(double initValue){
        X = initValue;
    }
    // 예전값들을공식으로계산한다
    private void measurementUpdate(){
        K = (P + Q) / (P + Q + R);P = R * (P + Q) / (P + Q + R);
    }
    // 현재값을받아계산된공식을적용하고반환한다
    public double update(double measurement){
        measurementUpdate();
        X = X + (measurement - X) * K;return X;
    }
}