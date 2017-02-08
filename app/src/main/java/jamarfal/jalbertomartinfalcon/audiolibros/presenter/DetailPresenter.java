package jamarfal.jalbertomartinfalcon.audiolibros.presenter;

import jamarfal.jalbertomartinfalcon.audiolibros.model.Libro;
import jamarfal.jalbertomartinfalcon.audiolibros.singleton.BooksSingleton;

/**
 * Created by jamarfal on 31/1/17.
 */

public class DetailPresenter {

    private View view;
    private String bookId;
    private final BooksSingleton booksSingleton;

    public DetailPresenter(View view, BooksSingleton booksSingleton) {
        this.view = view;
        this.booksSingleton = booksSingleton;
    }

    public void onCreate() {
        Libro book = booksSingleton.getAdapter().getItemByKey(bookId);
        view.showBookInfo(book, bookId);
    }


    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Libro getBook() {
        return booksSingleton.getAdapter().getItemByKey(bookId);
    }


    public interface View {

        void showBookInfo(Libro book, String key);

    }
}
