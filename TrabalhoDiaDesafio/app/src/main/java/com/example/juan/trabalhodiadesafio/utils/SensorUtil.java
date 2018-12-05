package com.example.juan.trabalhodiadesafio.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorUtil implements SensorEventListener {

    private final Context context;

    private SensorManager sensorManager;
    private Sensor acelerometro;

    private float x;
    private float y;
    private float z;

    private float variacaoX;
    private float variacaoY;
    private float variacaoZ;


    public SensorUtil(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);

        zerarVariacao();
    }

    public void pausarMonitoramento() {
        sensorManager.unregisterListener(this);
    }

    public void retomarMonitoramento() {
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void encerrarMonitoramento() {
        pausarMonitoramento();
        zerarVariacao();
    }

    public float getVariacao() {
        return variacaoX + variacaoY + variacaoZ;
    }

    private void zerarVariacao() {
        x = 0;
        y = 0;
        z = 0;

        variacaoX = 0;
        variacaoY = 0;
        variacaoZ = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (x > event.values[0]) {
            variacaoX += x - event.values[0];
        } else {
            variacaoX += event.values[0] - x;
        }

        if (y > event.values[1]) {
            variacaoY += y - event.values[1];
        } else {
            variacaoY += event.values[1] - y;
        }

        if (z > event.values[2]) {
            variacaoZ += z - event.values[2];
        } else {
            variacaoZ += event.values[2] - z;
        }

        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
