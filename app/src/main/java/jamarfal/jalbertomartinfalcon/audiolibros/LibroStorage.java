package jamarfal.jalbertomartinfalcon.audiolibros;

/**
 * Created by jamarfal on 22/1/17.
 */

public interface LibroStorage {
    boolean hasLastBook();

    String getLastBook();

    void saveLastBook(String id);

    boolean isAutoPlay();
}
