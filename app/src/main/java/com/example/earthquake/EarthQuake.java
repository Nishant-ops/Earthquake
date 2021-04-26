package com.example.earthquake;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EarthQuake {

    private double mag;
    private String place;
    private long date;
    private String url;

    public EarthQuake(double m,String p,long d,String url)
    {
        mag=m;
        place=p;
        date=d;
        this.url=url;
    }

    public long getDate() {
        return date;
    }

    public double getMag() {
        return mag;
    }

    public String getPlace() {
        return place;
    }

    public String getUrl() {
        return url;
    }




    }

