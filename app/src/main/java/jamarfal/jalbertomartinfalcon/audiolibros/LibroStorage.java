package jamarfal.jalbertomartinfalcon.audiolibros;

/**
 * Created by jamarfal on 22/1/17.
 */

public interface LibroStorage {
    boolean hasLastBook();

    int getLastBook();

    void saveLastBook(int id);

    boolean isAutoPlay();
}
