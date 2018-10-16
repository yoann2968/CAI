package com.tp.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class CapteurActivity extends AppCompatActivity implements SensorEventListener {

    final String TAG = "sensor";

    SensorManager mSensorManager;
    private Sensor mAccelerometer; //Accelerometre
    private Sensor mLightSensor; //Capteur de lumiere

    private TextView accSensorText; //Accelerometre
    private TextView lightSensorText; //Capteur de lumiere

    //Variable pour définir les spinners
    Spinner spinner_sensor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteur);
        accSensorText= findViewById(R.id.capteur1); //Accelerometre
        lightSensorText= findViewById(R.id.capteur2); //Capteur de lumiere

        //Gestion des differents spinner servant à choisir le capteur
        spinner_sensor = findViewById(R.id.spinner_sensor);
        ArrayAdapter<CharSequence> adapter_sensor = ArrayAdapter.createFromResource(this, R.array.sensor, android.R.layout.simple_spinner_item);
        adapter_sensor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sensor.setAdapter(adapter_sensor);

        sensorDetection(); //Detection des capteurs

        if (mSensorManager == null) {
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        }
        if (mSensorManager != null) {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        //Initialisation du bouton permettant de voir les capteurs disponible
        final Button envoie_sms = findViewById(R.id.envoie_sms);
        envoie_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appel de la méthode pour envoyer un sms
                sendSMS();
            }
        });

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
            mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            // La valeur de la lumière
            float lv = sensorEvent.values[0];
            //On affiche la valeur
            lightSensorText.setText(" TimeAcc = " + sensorEvent.timestamp + "\n Light value = " + lv);
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float Ax = sensorEvent.values[0];
            float Ay = sensorEvent.values[1];
            float Az = sensorEvent.values[2];

            accSensorText.setText(" TimeAcc = " + sensorEvent.timestamp + "\n Ax = " + Ax + " " + "\n Ay = " + Ay + " " + "\n Az = " + Az);
            Log.v(TAG, " TimeAcc = " + sensorEvent.timestamp + " Ax = " + Ax + " " + " Ay = " + Ay + " " + " Az = " + Az);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void sendSMS() {
        final int PERMISSION_REQUEST_CODE = 1;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.SEND_SMS) ==
                    PackageManager.PERMISSION_DENIED) {
                Log.d(" permission ", " permission denied to SEND_SMS - requesting it ");
                String[] permissions = {android.Manifest.permission.SEND_SMS};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }
        String msg = " Hello ";

        String num ="0658795964";
        SmsManager.getDefault().sendTextMessage(num, null, msg, null, null);
        /*Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        smsIntent.setDataAndType(Uri.parse(" sms : ")," vnd . android - dir / mms - sms ");
        smsIntent.putExtra(" sms_body ", msg);
        startActivity(smsIntent);*/
    }

}
