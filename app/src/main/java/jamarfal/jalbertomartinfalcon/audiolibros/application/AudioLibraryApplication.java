package jamarfal.jalbertomartinfalcon.audiolibros.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Vector;

import jamarfal.jalbertomartinfalcon.audiolibros.adapter.AdaptadorLibrosFiltro;
import jamarfal.jalbertomartinfalcon.audiolibros.Libro;

/**
 * Created by jamarfal on 19/12/16.
 */

public class AudioLibraryApplication extends Application {


    private FirebaseAuth auth;

    @Override
    public void onCreate() {
        auth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }
}
