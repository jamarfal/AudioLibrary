package jamarfal.jalbertomartinfalcon.audiolibros;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.ui.auth.ui.User;

/**
 * Created by jamarfal on 22/1/17.
 */

public class LibroSharedPreferenceStorage implements LibroStorage, UserStorage {

    public static final String PREF_AUDIOLIBRARY = "com.example.audiolibros_internal";
    public static final String KEY_LAST_BOOK = "ultimo";
    public static final String KEY_AUTOPLAY = "pref_autoreproducir";
    public static final String KEY_CHANGE_WIDGET_IMAGE = "cambiarImagen";
    public static final String USER_NAME = "user_name";
    public static final String USER_PROVIDER = "user_provider";
    public static final String USER_EMAIL = "user_email";

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
        editor.putInt(KEY_LAST_BOOK, id);
        editor.apply();
    }

    @Override
    public boolean isAutoPlay() {
        return getPreference().getBoolean(KEY_AUTOPLAY, true);
    }

    public void setChangeWidgetImage(int widgetId, boolean shouldChange) {
        SharedPreferences pref = getPreference();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(KEY_CHANGE_WIDGET_IMAGE + widgetId, shouldChange);
        editor.apply();
    }

    public boolean shouldChangeWidgetImage() {
        return getPreference().getBoolean(KEY_CHANGE_WIDGET_IMAGE, false);
    }

    @Override
    public void setUserName(String userName) {
        SharedPreferences preferences = getPreference();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    @Override
    public String getUserName() {
        return getPreference().getString(USER_NAME, "Pepe");
    }

    @Override
    public void setUserProvider(String userProvider) {
        SharedPreferences preferences = getPreference();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_PROVIDER, userProvider);
        editor.apply();
    }

    @Override
    public String getUserProvider() {
        return getPreference().getString(USER_PROVIDER, "Provider");
    }

    @Override
    public void setUserEmail(String userEmail) {
        SharedPreferences preferences = getPreference();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_EMAIL, userEmail);
        editor.apply();
    }

    @Override
    public String getUserEmail() {
        return getPreference().getString(USER_EMAIL, "Email");
    }

    @Override
    public void removeUserName() {
        SharedPreferences preferences = getPreference();
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USER_NAME);
        editor.apply();
    }

    @Override
    public void removeProvider() {
        SharedPreferences preferences = getPreference();
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USER_PROVIDER);
        editor.apply();
    }

    @Override
    public void removeEmail() {
        SharedPreferences preferences = getPreference();
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USER_EMAIL);
        editor.apply();
    }
}
