package jamarfal.jalbertomartinfalcon.audiolibros.singleton;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import jamarfal.jalbertomartinfalcon.audiolibros.adapter.AdaptadorLibrosFiltro;
import jamarfal.jalbertomartinfalcon.audiolibros.adapter.AdaptadorLibrosFiltroUi;
import jamarfal.jalbertomartinfalcon.audiolibros.adapter.AdaptadorLibrosUI;
import jamarfal.jalbertomartinfalcon.audiolibros.application.AudioLibraryApplication;

/**
 * Created by jamarfal on 30/1/17.
 */

public class BooksSingleton {

    public static BooksSingleton INSTANCE = null;
    //    private AdaptadorLibrosFiltro adapter;
    private AdaptadorLibrosFiltroUi adapter;

    private BooksSingleton(Context context) {
        DatabaseReference booksReference = ((AudioLibraryApplication) context.getApplicationContext()).getBooksReference();
//        adapter = new AdaptadorLibrosFiltro(context, booksReference);
        adapter = new AdaptadorLibrosFiltroUi(context, booksReference);
    }

    public static BooksSingleton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new BooksSingleton(context);
        }
        return INSTANCE;
    }

    public AdaptadorLibrosFiltroUi getAdapter() {
        return adapter;
    }

}
