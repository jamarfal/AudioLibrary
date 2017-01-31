package jamarfal.jalbertomartinfalcon.audiolibros.command;

import jamarfal.jalbertomartinfalcon.audiolibros.MainActivity;

/**
 * Created by jamarfal on 12/1/17.
 */

public class OpenDetailClickAction implements ClickAction {

    private final MainActivity mainActivity;

    public OpenDetailClickAction(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void execute(int position) {
        mainActivity.showDetail(position);
    }
}
