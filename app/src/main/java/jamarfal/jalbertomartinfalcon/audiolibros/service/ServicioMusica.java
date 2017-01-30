package jamarfal.jalbertomartinfalcon.audiolibros.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;



import java.io.IOException;

import jamarfal.jalbertomartinfalcon.audiolibros.Libro;

import jamarfal.jalbertomartinfalcon.audiolibros.application.AudioLibraryApplication;

/**
 * Created by jamarfal on 6/11/16.
 */

public class ServicioMusica extends Service implements MediaPlayer.OnPreparedListener {
    private static final int ID_NOTIFICACION_CREAR = 1;
    MediaPlayer reproductor;

    @Override
    public void onCreate() {

        SharedPreferences pref = getSharedPreferences(
                "com.example.audiolibros_internal", MODE_PRIVATE);
        int id = pref.getInt("ultimo", -1);
        Libro libro = ((AudioLibraryApplication) getApplication())
                .getVectorLibros().elementAt(id);
        reproductor = new MediaPlayer();
        reproductor.setOnPreparedListener(this);
        Uri audio = Uri.parse(libro.urlAudio);
        try {
            reproductor.setDataSource(getBaseContext(), audio);
            reproductor.prepareAsync();
        } catch (IOException e) {
            Log.e("Audiolibros", "ERROR: No se puede reproducir " + audio, e);
        }
    }


    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        reproductor.stop();
    }


    @Override
    public IBinder onBind(Intent intencion) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        reproductor.start();
    }
}
