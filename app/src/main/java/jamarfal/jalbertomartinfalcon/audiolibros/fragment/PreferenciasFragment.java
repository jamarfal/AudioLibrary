package jamarfal.jalbertomartinfalcon.audiolibros.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import jamarfal.jalbertomartinfalcon.audiolibros.R;

/**
 * Created by jamarfal on 21/12/16.
 */

public class PreferenciasFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
