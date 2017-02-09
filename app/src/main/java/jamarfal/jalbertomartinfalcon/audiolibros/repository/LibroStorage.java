package jamarfal.jalbertomartinfalcon.audiolibros.repository;

/**
 * Created by jamarfal on 22/1/17.
 */

public interface LibroStorage {
    boolean hasLastBook();

    String getLastBook();

    void saveLastBook(String id);

    boolean isAutoPlay();
}
