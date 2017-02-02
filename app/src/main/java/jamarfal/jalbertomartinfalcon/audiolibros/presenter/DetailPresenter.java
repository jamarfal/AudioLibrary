package jamarfal.jalbertomartinfalcon.audiolibros.presenter;

import jamarfal.jalbertomartinfalcon.audiolibros.Libro;
import jamarfal.jalbertomartinfalcon.audiolibros.LibroStorage;
import jamarfal.jalbertomartinfalcon.audiolibros.singleton.BooksSingleton;

/**
 * Created by jamarfal on 31/1/17.
 */

public class DetailPresenter {

    private View view;
    private int bookId;
    private final BooksSingleton booksSingleton;

    public DetailPresenter(View view, BooksSingleton booksSingleton) {
        this.view = view;
        this.booksSingleton = booksSingleton;
    }

    public void onCreate() {
        Libro book = booksSingleton.getVectorBooks().get(bookId);
        view.showBookInfo(book);
    }


    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Libro getBook() {
        return booksSingleton.getVectorBooks().get(bookId);
    }



    public interface View {

        void showBookInfo(Libro book);

    }
}
