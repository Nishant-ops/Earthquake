package com.example.earthquake;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.lang.reflect.AccessibleObject;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {
List<EarthQuake> current;
    public EarthQuakeAdapter(Activity context, List<EarthQuake> earthQuakes)
    {
        super(context,0,earthQuakes);
        current=earthQuakes;
    }

    private int getMagnitudeColor(double mag)
    {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(mag);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemGroup=convertView;

        if(listItemGroup==null)
        {
            listItemGroup= LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_view,parent,false);
        }

        EarthQuake earthQuake=current.get(position);

        TextView t1=(TextView) listItemGroup.findViewById(R.id.mag);
        GradientDrawable magnitudeCircle = (GradientDrawable) t1.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthQuake.getMag());

        magnitudeCircle.setColor(magnitudeColor);


        double mag=earthQuake.getMag();
        DecimalFormat decimalFormat=new DecimalFormat("0.0");
        String mag1=decimalFormat.format(mag);
        t1.setText(mag1);

        TextView t2=(TextView) listItemGroup.findViewById(R.id.dest);
        String s=earthQuake.getPlace();

        int index=0;
        for(int i=1;i<s.length();i++) {
            if (s.charAt(i-1) == 'o' && s.charAt(i) == 'f') {
                index = i;
                break;
            }
        }

        String dest=s.substring(0,index+1);
        String place=s.substring(index+2,s.length());
        TextView t3=(TextView) listItemGroup.findViewById(R.id.place);

        t2.setText(dest);
        t3.setText(place);
        long date=earthQuake.getDate();

        TextView t4=(TextView) listItemGroup.findViewById(R.id.date);
        t4.setText(formatDate(date));

        TextView t5=(TextView) listItemGroup.findViewById(R.id.time);
        t5.setText(formatTime(date));


        return listItemGroup;
    }

    public String formatTime(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h: mm a");
        return  dateFormat.format(date);
    }

    private String formatDate(long date ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM DD, yyyy");
        return dateFormat.format(date);
    }
}
