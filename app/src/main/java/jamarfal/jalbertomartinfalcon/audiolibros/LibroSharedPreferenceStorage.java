package jamarfal.jalbertomartinfalcon.audiolibros;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jamarfal on 22/1/17.
 */

public class LibroSharedPreferenceStorage implements LibroStorage {

    public static final String PREF_AUDIOLIBRARY = "com.example.audiolibros_internal";
    public static final String KEY_LAST_BOOK = "ultimo";
    public static final String KEY_AUTOPLAY = "pref_autoreproducir";
    private final Context context;
    private static LibroSharedPreferenceStorage instance;

    public static LibroSharedPreferenceStorage getInstance(Context context) {
        if (instance == null) {
            instance = new LibroSharedPreferenceStorage(context);
        }
        return instance;
    }

    private LibroSharedPreferenceStorage(Context context) {
        this.context = context;
    }

    @Override
    public boolean hasLastBook() {
        return getPreference().contains(KEY_LAST_BOOK);
    }

    private SharedPreferences getPreference() {
        return context.getSharedPreferences(
                PREF_AUDIOLIBRARY, Context.MODE_PRIVATE);
    }

    @Override
    public int getLastBook() {
        return getPreference().getInt(KEY_LAST_BOOK, -1);
    }

    @Override
    public void saveLastBook(int id) {
        SharedPreferences pref = getPreference();
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("ultimo", id);
        editor.apply();
    }

    @Override
    public boolean isAutoPlay() {
        return getPreference().getBoolean(KEY_AUTOPLAY, true);
    }
}
