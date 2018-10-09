package com.tp.myapplication;


import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import java.util.List;


/**
 * Created by yoann on 09/10/2018.
 * classe principal de l'application, permettant d'initialiser la zone de dessin, de récupéré les valeur des capteur et de déplacer les éléments
 */


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, SensorEventListener {

    private float x;
    private float y;

    final String TAG = "sensor";

    MySurfaceView customSurfaceView = null;

    SensorManager mSensorManager;
    private Sensor mAccelerometer;

    /*private Camera camera;

    private Boolean isPreview;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorDetection();

        LinearLayout canvasLayout = findViewById(R.id.customViewLayout);

        // Make app full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        customSurfaceView = new MySurfaceView(this);
        customSurfaceView.setOnTouchListener(this);
        canvasLayout.addView(customSurfaceView);

        if (mSensorManager == null) {
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        }
        if (mSensorManager != null) {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        /*isPreview = false;
        camera = Camera.open();*/
    }

    /* If user finger touch the surfaceview object. */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        // If user touch the custom SurfaceView object.
        if (view instanceof SurfaceView) {

            // Create and set a red paint to custom surfaceview.
            x = motionEvent.getX();
            y = motionEvent.getY();
            customSurfaceView.drawRedBall(x, y);

            // Tell android os the onTouch event has been processed.
            return true;
        } else {
            // Tell android os the onTouch event has not been processed.
            return false;
        }
    }

    void sensorDetection() {
        SensorManager mSensorManager;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert mSensorManager != null;
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        if (deviceSensors != null && !deviceSensors.isEmpty()) {
            for (Sensor mySensor : deviceSensors) {
                Log.v(TAG, "info : " + mySensor.toString());
            }
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                Log.v(TAG, "info:Accelerometer_found_!");
            } else {
                Log.v(TAG, "info:Accelerometer_not_found");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorManager != null) {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Many sensors return 3 values , one for each axis .
        float Ax = sensorEvent.values[0];
        float Ay = sensorEvent.values[1];
        float Az = sensorEvent.values[2];

        if (x != 0 && y != 0) {
            // Create and set a red paint to custom surfaceview.
            x -= Ax;
            y += Ay;
            customSurfaceView.drawRedBall(x, y);

            if (x + 100 >= customSurfaceView.getWidth() | y <= 0 | x <= 0 | y + 100 >= customSurfaceView.getHeight()) {
                Vibrator vib;
                vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vib != null) {
                    vib.vibrate(100);
                }
            }
        }




        // Do something with this sensor value .
        Log.v(TAG, " TimeAcc = " + sensorEvent.timestamp + " Ax = " + Ax + " " + " Ay = " + Ay + " " + " Az = " + Az);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}


