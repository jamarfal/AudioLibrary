package jamarfal.jalbertomartinfalcon.audiolibros.singleton;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by jamarfal on 30/1/17.
 */

public class VolleySingleton {

    private static VolleySingleton INSTANCE = null;

    private static RequestQueue colaPeticiones;
    private static ImageLoader lectorImagenes;

    private VolleySingleton(Context context) {
        colaPeticiones = Volley.newRequestQueue(context);

        lectorImagenes = new ImageLoader(colaPeticiones, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(10);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new VolleySingleton(context);
        }
        return INSTANCE;
    }

    public RequestQueue getColaPeticiones() {
        return colaPeticiones;
    }

    public ImageLoader getLectorImagenes() {
        return lectorImagenes;
    }
}
