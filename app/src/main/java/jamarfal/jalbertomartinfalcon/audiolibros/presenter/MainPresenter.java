package jamarfal.jalbertomartinfalcon.audiolibros.presenter;

import jamarfal.jalbertomartinfalcon.audiolibros.domain.GetLastBook;
import jamarfal.jalbertomartinfalcon.audiolibros.domain.HasLastBook;
import jamarfal.jalbertomartinfalcon.audiolibros.domain.SaveLastBook;

/**
 * Created by jamarfal on 31/1/17.
 */

public class MainPresenter {
    private HasLastBook hasLastBook;
    private GetLastBook getLastBook;
    private SaveLastBook saveLastBook;
    private final View view;

    public MainPresenter(SaveLastBook saveLastBook, GetLastBook getLastBook, HasLastBook hasLastBook, MainPresenter.View view) {
        this.getLastBook = getLastBook;
        this.hasLastBook = hasLastBook;
        this.saveLastBook = saveLastBook;
        this.view = view;
    }

    public void clickFavoriteButton() {
        if (hasLastBook.execute()) {
            view.showDetail(getLastBook.execute());
        } else {
            view.showNoLastVisit();
        }
    }

    public void openDetalle(String id) {
        saveLastBook.execute(id);
        view.showFragmentDetail(id);
    }

    public interface View {
        void showDetail(String lastBook);

        void showFragmentDetail(String key);

        void showNoLastVisit();
    }
}
