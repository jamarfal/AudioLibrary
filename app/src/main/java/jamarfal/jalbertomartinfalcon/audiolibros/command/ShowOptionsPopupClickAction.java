package jamarfal.jalbertomartinfalcon.audiolibros.command;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import jamarfal.jalbertomartinfalcon.audiolibros.activity.MainActivity;

/**
 * Created by jamarfal on 12/1/17.
 */

public class ShowOptionsPopupClickAction implements LongClickAction {
    private final MainActivity mainActivity;
    private final RecyclerView recyclerView;


    public ShowOptionsPopupClickAction(MainActivity mainActivity, RecyclerView recyclerView) {
        this.mainActivity = mainActivity;
        this.recyclerView = recyclerView;
    }

    @Override
    public void execute(View v) {
        mainActivity.showBookActions(v, recyclerView);
    }
}
