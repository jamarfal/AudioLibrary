package jamarfal.jalbertomartinfalcon.audiolibros.presenter;

import jamarfal.jalbertomartinfalcon.audiolibros.LibroStorage;

/**
 * Created by jamarfal on 31/1/17.
 */

public class MainPresenter {
    private final LibroStorage libroStorage;
    private final View view;

    public MainPresenter(LibroStorage libroStorage, MainPresenter.View view) {
        this.libroStorage = libroStorage;
        this.view = view;
    }

    public void clickFavoriteButton() {
        if (libroStorage.hasLastBook()) {
            view.showDetail(libroStorage.getLastBook());
        } else {
            view.showNoLastVisit();
        }
    }

    public void openDetalle(int id) {
        libroStorage.saveLastBook(id);
        view.showDetail(id);
    }

    public interface View {
        void showDetail(int lastBook);

        void showNoLastVisit();
    }
}
