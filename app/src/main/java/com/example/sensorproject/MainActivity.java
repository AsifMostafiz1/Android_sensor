package com.example.sensorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView lightX,lightY,lightZ,proximityX,proximityY,proximityZ,accumulatorX,accumulatorY,accumulatorZ,gyroscopeX,gyroscopeY,gyroscopeZ;
    private CardView lightCard,proximityCard,accumulatorCard,gyroscopeCard;
    private Sensor lightSensor,proximitySensor,accumulatorSensor,gyroscopeSensor;
    private SensorManager sensorManager;
    private Handler handler;
    private DatabaseHelper helper;


    int intervalLight= 300000;
    int intervalProximity= 300000;
    int intervalAccumulator= 300000;
    int intervalGyroscope= 300000;
    boolean lightFlag = true;
    boolean proximityFlag = true;
    boolean accumulatorFlag = true;
    boolean gyroscopeFlag = true;

    private final Runnable lightProcessSensors = new Runnable() {
        @Override
        public void run() {
            lightFlag = true;

            handler.postDelayed(this, intervalLight);
        }
    };


    private final Runnable proximityProcessSensors = new Runnable() {
        @Override
        public void run() {
            proximityFlag = true;

            handler.postDelayed(this, intervalProximity);
        }
    };


    private final Runnable accumulatorProcessSensors = new Runnable() {
        @Override
        public void run() {
            accumulatorFlag = true;

            handler.postDelayed(this, intervalAccumulator);
        }
    };

    private final Runnable gyroscopeProcessSensors = new Runnable() {
        @Override
        public void run() {
            gyroscopeFlag = true;

            handler.postDelayed(this, intervalGyroscope);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        sensorInitialization();

        lightSensorValueReading();

        proximitySensorReading();

        accumulatorSensorReading();

        gyroscopeSensorReading();

        lightCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lightIntent = new Intent(MainActivity.this,AllSensorValueActivity.class);
                lightIntent.putExtra("sensorName","Light");
                startActivity(lightIntent);
            }
        });


        proximityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent proximityIntent = new Intent(MainActivity.this,AllSensorValueActivity.class);
                proximityIntent.putExtra("sensorName","Proximity");
                startActivity(proximityIntent);
            }
        });

        accumulatorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accumulatorIntent = new Intent(MainActivity.this,AllSensorValueActivity.class);
                accumulatorIntent.putExtra("sensorName","Accumulator");
                startActivity(accumulatorIntent);
            }
        });

        gyroscopeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gyroscopeIntent = new Intent(MainActivity.this,AllSensorValueActivity.class);
                gyroscopeIntent.putExtra("sensorName","Gyroscope");
                startActivity(gyroscopeIntent);
            }
        });


    }



    private void gyroscopeSensorReading()
    {
        SensorEventListener gyroscopeSensorEvent = new SensorEventListener() {
            String date;
            float x, y, z;
            String sensorName = "Gyroscope";
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {


                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];
                String sx = Float.toString(x);
                String sy = Float.toString(y);
                String sz = Float.toString(z);

                SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
                date = s.format(new Date());

                gyroscopeX.setText("ValueX: "+sx);
                gyroscopeY.setText("ValueY: "+sy);
                gyroscopeZ.setText("ValueZ: "+sz);


                if (gyroscopeFlag)
                {
                    if (date != null && sx != null && sy != null && sz != null) {

                        long id = helper.insertData(sensorName, sx, sy, sz,date);
                        if (id == -1) {
                            Toast.makeText(MainActivity.this, "Error : Data  can not Inserted.", Toast.LENGTH_SHORT).show();
                        } else {

                            //Toast.makeText(MainActivity.this, "Gyroscope Data is Inserted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    gyroscopeFlag = false;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }

        };
        sensorManager.registerListener(gyroscopeSensorEvent,gyroscopeSensor,SensorManager.SENSOR_DELAY_NORMAL);
        handler.postDelayed(gyroscopeProcessSensors,intervalGyroscope);

    }

    private void accumulatorSensorReading()
    {
        SensorEventListener accumulatorSensorEvent = new SensorEventListener() {

            String date;
            float x, y, z;
            String sensorName = "Accumulator";

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];
                String sx = Float.toString(x);
                String sy = Float.toString(y);
                String sz = Float.toString(z);

                SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
                date = s.format(new Date());

                accumulatorX.setText("ValueX: "+sx);
                accumulatorY.setText("ValueY: "+sy);
                accumulatorZ.setText("ValueZ: "+sz);


                if (accumulatorFlag)
                {
                    if (date != null && sx != null && sy != null && sz != null) {

                        long id = helper.insertData(sensorName, sx, sy, sz,date);
                        if (id == -1) {
                            Toast.makeText(MainActivity.this, "Error : Data  can not Inserted.", Toast.LENGTH_SHORT).show();
                        } else {

                            // Toast.makeText(MainActivity.this, "Accumulator Data is Inserted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    accumulatorFlag = false;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(accumulatorSensorEvent,accumulatorSensor,SensorManager.SENSOR_DELAY_NORMAL);
        handler.postDelayed(accumulatorProcessSensors,intervalAccumulator);
    }

    private void proximitySensorReading()
    {
        SensorEventListener proximitySensorEvent = new SensorEventListener() {
            String date;
            float x, y, z;
            String sensorName = "Proximity";

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];
                String sx = Float.toString(x);
                String sy = Float.toString(y);
                String sz = Float.toString(z);

                SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
                date = s.format(new Date());

                proximityX.setText("ValueX: "+sx);
                proximityY.setText("ValueY: "+sy);
                proximityZ.setText("ValueZ: "+sz);


                if (proximityFlag)
                {
                    if (date != null && sx != null && sy != null && sz != null) {

                        long id = helper.insertData(sensorName, sx, sy, sz,date);
                        if (id == -1) {
                            Toast.makeText(MainActivity.this, "Error : Data  can not Inserted.", Toast.LENGTH_SHORT).show();
                        } else {

                            //Toast.makeText(MainActivity.this, "Proximity Data is Inserted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    proximityFlag = false;
                }


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(proximitySensorEvent,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
        handler.postDelayed(proximityProcessSensors,intervalProximity);

    }

    private void lightSensorValueReading()

    {
        SensorEventListener lightSensorEvent = new SensorEventListener() {
            String date;
            float x, y, z;
            String sensorName = "Light";

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];
                String sx = Float.toString(x);
                String sy = Float.toString(y);
                String sz = Float.toString(z);

                SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
                date = s.format(new Date());

                lightX.setText("ValueX: "+sx);
                lightY.setText("ValueY: "+sy);
                lightZ.setText("ValueZ: "+sz);

                if (lightFlag)
                {


                    if (date != null && sx != null && sy != null && sz != null) {

                        long id = helper.insertData(sensorName, sx, sy, sz,date);
                        if (id == -1) {
                            Toast.makeText(MainActivity.this, "Error : Data  can not Inserted.", Toast.LENGTH_SHORT).show();
                        } else {

                            //Toast.makeText(MainActivity.this, "Light Data is Inserted.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    lightFlag = false;
                }



            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(lightSensorEvent,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        handler.postDelayed(lightProcessSensors,intervalLight);
    }


    private void sensorInitialization()
    {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        accumulatorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }


    private void initialization()
    {
        lightX = findViewById(R.id.lightX);
        lightY = findViewById(R.id.lightY);
        lightZ = findViewById(R.id.lightZ);
        lightCard = findViewById(R.id.lightCard);


        proximityX = findViewById(R.id.proximityX);
        proximityY = findViewById(R.id.proximityY);
        proximityZ = findViewById(R.id.proximityZ);
        proximityCard =findViewById(R.id.prximityCard);


        accumulatorX = findViewById(R.id.accumulatorX);
        accumulatorY = findViewById(R.id.accumulatorY);
        accumulatorZ = findViewById(R.id.accumulatorZ);
        accumulatorCard = findViewById(R.id.accumulatorCard);

        gyroscopeX = findViewById(R.id.gyroscopeX);
        gyroscopeY = findViewById(R.id.gyroscopeY);
        gyroscopeZ = findViewById(R.id.gyroscopeZ);
        gyroscopeCard = findViewById(R.id.gyroscopeCard);

        handler = new Handler();
        helper = new DatabaseHelper(MainActivity.this);

    }
}

