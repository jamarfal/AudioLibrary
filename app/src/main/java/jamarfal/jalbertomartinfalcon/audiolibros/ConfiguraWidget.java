package jamarfal.jalbertomartinfalcon.audiolibros;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import jamarfal.jalbertomartinfalcon.audiolibros.widget.MiAppWidgetProvider;

/**
 * Created by jamarfal on 28/12/16.
 */

public class ConfiguraWidget extends AppCompatActivity {
    int widgetId;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configura_widget);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        setResult(RESULT_CANCELED);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }
        widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    public void buttonOK(View view) {
        SharedPreferences prefs = getSharedPreferences("cambiarImagen",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("cambiarImagen" + widgetId, radioButton.isChecked());
        editor.apply();




        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
