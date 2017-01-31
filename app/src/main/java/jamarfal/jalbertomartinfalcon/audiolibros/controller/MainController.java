package jamarfal.jalbertomartinfalcon.audiolibros.controller;

import jamarfal.jalbertomartinfalcon.audiolibros.LibroStorage;

/**
 * Created by jamarfal on 31/1/17.
 */

public class MainController {
    LibroStorage libroStorage;

    public MainController(LibroStorage libroStorage) {
        this.libroStorage = libroStorage;
    }

    public void saveLastBook(int id) {
        libroStorage.saveLastBook(id);
    }

    public int getLastBook() {
        return libroStorage.getLastBook();
    }
}
