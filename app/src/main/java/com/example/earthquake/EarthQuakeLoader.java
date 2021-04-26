package com.example.earthquake;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class EarthQuakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

private static String Log=EarthQuakeLoader.class.getName();

private String murl;

public EarthQuakeLoader(Context context,String url)
{
    super(context);
    murl=url;
}

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<EarthQuake> loadInBackground() {
        if(murl==null)
            return  null;

        List<EarthQuake> earthQuakes=Query.fetchEarthQuakeData(murl);
        return earthQuakes;
    }
}
