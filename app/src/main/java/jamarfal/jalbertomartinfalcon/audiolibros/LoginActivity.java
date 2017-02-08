package jamarfal.jalbertomartinfalcon.audiolibros;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    public static final String PASSWORD = "password";
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = ((AudioLibraryApplication) getApplicationContext()).getAuth();
        doLogin();
    }

    private void doLogin() {
        final FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            guardarUsuario(currentUser);
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            String provider = currentUser.getProviders().get(0);
            saveUserInfo(name, email, provider);
            if (provider.equalsIgnoreCase(PASSWORD)) {
                isMailVerified(currentUser);
            } else {
                goToMainActivity();
            }

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

    private void isMailVerified(final FirebaseUser currentUser) {
        if (currentUser.isEmailVerified()) {
            goToMainActivity();
        } else {
            showVerificationDialog(currentUser);
            currentUser.sendEmailVerification();
        }
    }

    private void showVerificationDialog(final FirebaseUser currentUser) {
        android.app.AlertDialog.Builder alertDialogAbout = new android.app.AlertDialog.Builder(this);
        alertDialogAbout.setMessage("No ha verificado su mail, por favor compruebe su bandeja de entrada y verifíquelo\"")
                .setTitle("Verificación de email requerida")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        currentUser.sendEmailVerification();
                        logout();

                    }
                })
                .show();
    }

    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void logout() {
        AuthUI.getInstance().signOut(LoginActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        LibroSharedPreferenceStorage libroSharedPreferenceStorage = LibroSharedPreferenceStorage.getInstance(LoginActivity.this);
                        libroSharedPreferenceStorage.removeEmail();
                        libroSharedPreferenceStorage.removeProvider();
                        libroSharedPreferenceStorage.removeUserName();
                        Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                });
    }

    private void saveUserInfo(String name, String email, String provider) {
        LibroSharedPreferenceStorage libroSharedPreferenceStorage = LibroSharedPreferenceStorage.getInstance(this);
        libroSharedPreferenceStorage.setUserName(name);
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
