package jamarfal.jalbertomartinfalcon.audiolibros.domain;

import jamarfal.jalbertomartinfalcon.audiolibros.repository.BooksRepository;

/**
 * Created by jamarfal on 2/2/17.
 */

public class HasLastBook {

    private final BooksRepository booksRepository;

    public HasLastBook(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public boolean execute() {
        return booksRepository.hasLastBook();
    }
}
