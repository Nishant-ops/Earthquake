package com.example.earthquake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.*;

import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

public class Setting_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_);


    }
    public static class EarthquakePreferenceFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);

            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);

        }

        private void bindPreferenceSummaryToValue(Preference preference)
        {
           
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");

            onPreferencesChange(preference, preferenceString);
        }

        public boolean onPreferencesChange(Preference preference,Object value)
        {
            String mag=value.toString();
            preference.setSummary(mag);
            return true;
        }
    }
}