package com.example.sensorproject;

public class Sensor {

    private  String type,X,Y,Z,date;

    public Sensor() {
    }

    public Sensor(String type, String x, String y, String z, String date) {
        this.type = type;
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.date = date;
    }


    public Sensor(String x, String y, String z, String date) {
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public String getX() {
        return X;
    }

    public String getY() {
        return Y;
    }

    public String getZ() {
        return Z;
    }

    public String getDate() {
        return date;
    }
}