package jamarfal.jalbertomartinfalcon.audiolibros.singleton;

import android.content.Context;

import java.util.Vector;

import jamarfal.jalbertomartinfalcon.audiolibros.Libro;
import jamarfal.jalbertomartinfalcon.audiolibros.adapter.AdaptadorLibrosFiltro;

/**
 * Created by jamarfal on 30/1/17.
 */

public class BooksSingleton {

    public static BooksSingleton INSTANCE = null;
    private Vector<Libro> vectorBooks;
    private AdaptadorLibrosFiltro adapter;

    private BooksSingleton(Context context) {
        vectorBooks = Libro.ejemploLibros();
        adapter = new AdaptadorLibrosFiltro(context, vectorBooks);
    }

    public static BooksSingleton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new BooksSingleton(context);
        }
        return INSTANCE;
    }

    public AdaptadorLibrosFiltro getAdapter() {
        return adapter;
    }

    public Vector<Libro> getVectorBooks() {
        return vectorBooks;
    }
}
