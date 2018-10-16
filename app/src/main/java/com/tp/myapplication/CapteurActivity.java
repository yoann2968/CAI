package com.tp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import android.widget.AdapterView.OnItemSelectedListener;


public class CapteurActivity extends AppCompatActivity implements SensorEventListener, OnItemSelectedListener {

    final String TAG = "sensor";

    SensorManager mSensorManager;
    private Sensor mAccelerometer; //Accelerometre
    private Sensor mLightSensor; //Capteur de lumiere

    private TextView accSensorText; //Accelerometre
    private TextView lightSensorText; //Capteur de lumiere

    private EditText numero;

    //Variable correspondant au valeur du capteur d'accélérometre
    private String acce;
    private String light;

    //Variable pour définir les spinners
    Spinner spinner_sensor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteur);
        accSensorText= findViewById(R.id.capteur1); //Accelerometre
        lightSensorText= findViewById(R.id.capteur2); //Capteur de lumiere

        numero = findViewById(R.id.numero);

        //Gestion des differents spinner servant à choisir le capteur
        //Spinner element
        spinner_sensor = findViewById(R.id.spinner_sensor);
        ArrayAdapter<CharSequence> adapter_sensor = ArrayAdapter.createFromResource(this, R.array.sensor, android.R.layout.simple_spinner_item);
        adapter_sensor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sensor.setAdapter(adapter_sensor);

        // Spinner click listener
        spinner_sensor.setOnItemSelectedListener(this);

        //Detection des capteurs
        sensorDetection();

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

    //Création du menu identique pour toutes les activités sauf l'accueil
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all, menu);
        return true;
    }

    //Gestion du choix de l'item dans le menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Parcours les différents items pouvant être sélectionner
        switch (item.getItemId()) {
            case R.id.Home:
                //Si l'utilisateur click sur le logo de maisson on revient a l'accueil
                Intent i = new Intent(CapteurActivity.this, MainActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
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

            light = " TimeAcc = " + sensorEvent.timestamp + "\n Light value = " + lv+"\n";
            //On affiche la valeur
            lightSensorText.setText(light);
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float Ax = sensorEvent.values[0];
            float Ay = sensorEvent.values[1];
            float Az = sensorEvent.values[2];

            acce = " TimeAcc = " + sensorEvent.timestamp + "\n Ax = " + Ax + " " + "\n Ay = " + Ay + " " + "\n Az = " + Az + "\n";

            // Do something with this sensor value .
            accSensorText.setText(acce);
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

        String msg = light + acce;

        String num = numero.getText().toString();

        if (num.length()== 10 ){
            SmsManager.getDefault().sendTextMessage(num, null, msg, null, null);
            numero.setText("");
        }
        else{
            //On affiche un petit message d'erreur dans un Toast
            Toast toast = Toast.makeText(CapteurActivity.this , "Veuilliez écrire un numero a 10 chiffres", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = spinner_sensor.getItemAtPosition(i).toString();

        if (i==0) {
            accSensorText.setVisibility(View.VISIBLE);
            lightSensorText.setVisibility(View.INVISIBLE);
        }

        else if (i==1) {
            lightSensorText.setVisibility(View.VISIBLE);
            accSensorText.setVisibility(View.INVISIBLE);
        }

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }

}
