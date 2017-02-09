package jamarfal.jalbertomartinfalcon.audiolibros.repository;

/**
 * Created by jamarfal on 2/2/17.
 */

public class BooksRepository {

    private static BooksRepository INSTANCE = null;
    private final LibroStorage librosStorage;

    private BooksRepository(LibroStorage librosStorage) {
        this.librosStorage = librosStorage;
    }

    public static BooksRepository getINSTANCE(LibroStorage librosStorage) {
        if (INSTANCE == null) {
            INSTANCE = new BooksRepository(librosStorage);
        }
        return INSTANCE;
    }

    public String getLastBook() {
        return librosStorage.getLastBook();
    }

    public boolean hasLastBook() {
        return librosStorage.hasLastBook();
    }

    public void saveLastBook(String id) {
        this.librosStorage.saveLastBook(id);
    }
}
