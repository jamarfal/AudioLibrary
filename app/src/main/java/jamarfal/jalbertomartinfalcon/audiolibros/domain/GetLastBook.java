package jamarfal.jalbertomartinfalcon.audiolibros.domain;

import jamarfal.jalbertomartinfalcon.audiolibros.repository.BooksRepository;

/**
 * Created by jamarfal on 2/2/17.
 */

public class GetLastBook {

    private final BooksRepository booksRepository;

    public GetLastBook(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public String execute() {
        return booksRepository.getLastBook();
    }
}
