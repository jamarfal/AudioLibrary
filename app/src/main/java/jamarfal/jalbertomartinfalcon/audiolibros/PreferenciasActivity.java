package jamarfal.jalbertomartinfalcon.audiolibros;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jamarfal.jalbertomartinfalcon.audiolibros.fragment.PreferenciasFragment;

/**
 * Created by jamarfal on 21/12/16.
 */

public class PreferenciasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new
                PreferenciasFragment()).commit();
    }
}
