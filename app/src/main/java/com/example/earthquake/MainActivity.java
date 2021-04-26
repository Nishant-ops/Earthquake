 package com.example.earthquake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.loader.content.Loader;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<EarthQuake>> {
    private static final int Loader_Id=1;
    private TextView mtext;
private static String USG="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
private EarthQuakeAdapter earthQuakeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        earthQuakeAdapter = new EarthQuakeAdapter(this, new ArrayList<EarthQuake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthQuakeAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EarthQuake earthQuake = earthQuakeAdapter.getItem(i);

                Uri earthquakeuri = Uri.parse(earthQuake.getUrl());

                Intent web = new Intent(Intent.ACTION_VIEW, earthquakeuri);

                startActivity(web);
            }
        });

        getSupportLoaderManager().initLoader(Loader_Id,null,this);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, Setting_Activity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @NonNull
    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String min_mag=sharedPreferences.getString(getString(R.string.settings_min_magnitude_key),getString(R.string.settings_min_magnitude_default));

        Uri uri=Uri.parse(USG);

        Uri.Builder builder=uri.buildUpon();
        builder.appendQueryParameter("format","geojson");
        builder.appendQueryParameter("limit","10");
        builder.appendQueryParameter("minmag",min_mag);
        builder.appendQueryParameter("orderby","time");

        return new EarthQuakeLoader(this,builder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<EarthQuake>> loader, List<EarthQuake> data) {
        earthQuakeAdapter.clear();

        if(data!=null&&!data.isEmpty())
            earthQuakeAdapter.addAll(data);

        Log.i(LOCATION_SERVICE,"VALUE"+earthQuakeAdapter.getItem(0).getPlace());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<EarthQuake>> loader) {
        earthQuakeAdapter.clear();
    }
}
