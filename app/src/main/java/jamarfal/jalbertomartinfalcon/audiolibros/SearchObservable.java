package jamarfal.jalbertomartinfalcon.audiolibros;

import android.support.v7.widget.SearchView;

import java.util.Observable;

/**
 * Created by jamarfal on 12/1/17.
 */

public class SearchObservable extends Observable implements SearchView.OnQueryTextListener {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        setChanged();
        notifyObservers(newText);
        return false;
    }
}
