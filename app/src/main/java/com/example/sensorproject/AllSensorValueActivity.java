package com.example.sensorproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AllSensorValueActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String sensorName;

    private List<Sensor> sensorsList;
    private AdapterSensor adapterSensor;
    private DatabaseHelper helper;
    private TextView sensorTypeTV;
    private int count = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sensor_value);

        Bundle bundle = getIntent().getExtras();

        if (bundle!=null)
        {
            sensorName = bundle.getString("sensorName");
            //Toast.makeText(this,sensorName, Toast.LENGTH_SHORT).show();

        }



        initialization();


        try {
            getdata();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void getdata() throws ParseException {

        sensorsList.clear();
        adapterSensor.notifyDataSetChanged();

        Cursor cursor = helper.showAllData();


        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
            String valueX = cursor.getString(cursor.getColumnIndex(helper.COL_X));
            String valueY = cursor.getString(cursor.getColumnIndex(helper.COL_Y));
            String valueZ = cursor.getString(cursor.getColumnIndex(helper.COL_Z));

            String date = cursor.getString(cursor.getColumnIndex(helper.COL_Date));
            String type = cursor.getString(cursor.getColumnIndex(helper.COL_TYPE));




            count++;
            if (sensorName.equals(type))
            {
                sensorsList.add(new Sensor(valueX,valueY,valueZ,date));
                adapterSensor.notifyDataSetChanged();
                sensorTypeTV.setText(sensorName+" Sensor Data");
            }


        }

    }

    private void initialization()
    {
        recyclerView = findViewById(R.id.recyclerViewId);
        sensorTypeTV = findViewById(R.id.select_sensor_type);



        sensorsList = new ArrayList<>();
        adapterSensor = new AdapterSensor(sensorsList);
        helper = new DatabaseHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterSensor);

        adapterSensor.notifyDataSetChanged();

    }

}