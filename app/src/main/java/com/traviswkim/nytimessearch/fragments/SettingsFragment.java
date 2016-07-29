package com.traviswkim.nytimessearch.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.traviswkim.nytimessearch.R;

/**
 * Created by traviswkim on 7/26/16.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
