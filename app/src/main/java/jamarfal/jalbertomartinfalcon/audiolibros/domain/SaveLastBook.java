package jamarfal.jalbertomartinfalcon.audiolibros.domain;

import jamarfal.jalbertomartinfalcon.audiolibros.LibroStorage;
import jamarfal.jalbertomartinfalcon.audiolibros.repository.BooksRepository;

/**
 * Created by jamarfal on 2/2/17.
 */

public class SaveLastBook {
    private final BooksRepository booksRepository;


    public SaveLastBook(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void execute(int id) {
        booksRepository.saveLastBook(id);
    }

}
