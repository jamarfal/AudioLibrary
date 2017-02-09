package jamarfal.jalbertomartinfalcon.audiolibros.controller;

import jamarfal.jalbertomartinfalcon.audiolibros.repository.LibroStorage;

/**
 * Created by jamarfal on 31/1/17.
 */

public class MainController {
    LibroStorage libroStorage;

    public MainController(LibroStorage libroStorage) {
        this.libroStorage = libroStorage;
    }

    public void saveLastBook(String id) {
        libroStorage.saveLastBook(id);
    }

    public String getLastBook() {
        return libroStorage.getLastBook();
    }
}
