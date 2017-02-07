package jamarfal.jalbertomartinfalcon.audiolibros;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import jamarfal.jalbertomartinfalcon.audiolibros.application.AudioLibraryApplication;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = ((AudioLibraryApplication) getApplicationContext()).getAuth();
        doLogin();
    }

    private void doLogin() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            guardarUsuario(currentUser);
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            String provider = currentUser.getProviders().get(0);
            saveUserInfo(currentUser, email, provider);
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder().setProviders(Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                    .setIsSmartLockEnabled(false).build(), RC_SIGN_IN);
        }
    }

    private void saveUserInfo(FirebaseUser currentUser, String email, String provider) {
        LibroSharedPreferenceStorage libroSharedPreferenceStorage = LibroSharedPreferenceStorage.getInstance(this);
        libroSharedPreferenceStorage.setUserName(currentUser.getDisplayName());
        libroSharedPreferenceStorage.setUserProvider(provider);
        if (email != null) {
            libroSharedPreferenceStorage.setUserEmail(email);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == ResultCodes.OK) {
                doLogin();
                finish();
            }
        }
    }


//    void guardarUsuario(final FirebaseUser user) {
//        DatabaseReference usersReference = ((AudioLibraryApplication) getApplicationContext())
//                .getUsersReference();
//
//        final DatabaseReference currentUserReference = usersReference.child(user.getUid());
//
//        ValueEventListener userListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (!dataSnapshot.exists()) {
//                    currentUserReference.setValue(new User(
//                            user.getDisplayName(), user.getEmail()));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        currentUserReference.addListenerForSingleValueEvent(userListener);
//    }

    void guardarUsuario(final FirebaseUser user) {
        AudioLibraryApplication audioLibraryApplication = ((AudioLibraryApplication) getApplicationContext());
        DatabaseReference userReference = audioLibraryApplication.getUsersReference().child(user.getUid());
        userReference.setValue(new User(user.getDisplayName(), user.getEmail()));
    }
}
