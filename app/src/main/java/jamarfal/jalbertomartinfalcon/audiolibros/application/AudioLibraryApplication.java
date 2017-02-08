package jamarfal.jalbertomartinfalcon.audiolibros.application;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jamarfal on 19/12/16.
 */

public class AudioLibraryApplication extends Application {


    private FirebaseAuth auth;
    private final static String BOOKS_CHILD = "libros";
    private final static String USERS_CHILD = "usuarios";
    private DatabaseReference usersReference;
    private DatabaseReference booksReference;


    @Override
    public void onCreate() {

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        booksReference = database.getReference().child(BOOKS_CHILD);
        usersReference = database.getReference().child(USERS_CHILD);
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public DatabaseReference getUsersReference() {
        return usersReference;
    }

    public DatabaseReference getBooksReference() {
        return booksReference;
    }
}
