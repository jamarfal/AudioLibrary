package jamarfal.jalbertomartinfalcon.audiolibros.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import jamarfal.jalbertomartinfalcon.audiolibros.Libro;
import jamarfal.jalbertomartinfalcon.audiolibros.LibroSharedPreferenceStorage;
import jamarfal.jalbertomartinfalcon.audiolibros.MainActivity;
import jamarfal.jalbertomartinfalcon.audiolibros.R;
import jamarfal.jalbertomartinfalcon.audiolibros.application.AudioLibraryApplication;
import jamarfal.jalbertomartinfalcon.audiolibros.singleton.BooksSingleton;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jamarfal on 10/1/17.
 */

public class MiAppWidgetProvider extends AppWidgetProvider {


    /*La primera identifica un nuevo tipo de anuncio broadcast que vamos a difundir y la segunda,
    un parámetro que añadiremos a los extras de la intención.*/
    public static final String ACCION_PLAY = "jamarfal.jalbertomartinfalcon.audiolibros.ACCION_PLAY";
    public static final String EXTRA_PARAM = "jamarfal.jalbertomartinfalcon.audiolibros.EXTRA_ID";
    private static Libro libro;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            actualizaWidget(context, widgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(ACCION_PLAY)) {
            int widgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            String param = intent.getStringExtra(EXTRA_PARAM);
            Toast.makeText(context, "Parámetro:" + param,
                    Toast.LENGTH_SHORT).show();
            actualizaWidget(context, widgetId);

        }
        super.onReceive(context, intent);
    }

    public static void actualizaWidget(Context context, int widgetId) {

        int id = LibroSharedPreferenceStorage.getInstance(context).getLastBook();
        libro = BooksSingleton.getInstance(context).getVectorBooks().elementAt(id);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.lblAutor, "Autor: " + libro.author);
        remoteViews.setTextViewText(R.id.lblTitulo, "Titulo: " + libro.title);


        boolean shouldChangeWidgetImage = LibroSharedPreferenceStorage.getInstance(context).shouldChangeWidgetImage();
        if (shouldChangeWidgetImage) {
            remoteViews.setImageViewResource(R.id.actionButton, R.drawable.estrella);

        }
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.imgLista, pendingIntent);

        intent = new Intent(context, MiAppWidgetProvider.class);
        intent.setAction(ACCION_PLAY);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        intent.putExtra(EXTRA_PARAM, "otro parámetro");
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
        AppWidgetManager.getInstance(context).updateAppWidget(widgetId, remoteViews);
    }


    private static int incrementaContador(Context context, int widgetId) {
        SharedPreferences prefs = context.getSharedPreferences("contadores", MODE_PRIVATE);
        int cont = prefs.getInt("cont_" + widgetId, 0);
        cont++;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("cont_" + widgetId, cont);
        editor.commit();
        return cont;
    }
}
