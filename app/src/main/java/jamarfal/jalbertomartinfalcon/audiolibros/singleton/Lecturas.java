package jamarfal.jalbertomartinfalcon.audiolibros.singleton;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jamarfal on 7/2/17.
 */
public class Lecturas implements ChildEventListener {

    private static Lecturas ourInstance = null;
    private String UIDActual;
    private DatabaseReference referenciaMisLecturas;
    private ArrayList<String> idLibros;

    private Lecturas() {
        UIDActual = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        referenciaMisLecturas =
                database.getReference().child("lecturas").child(UIDActual);
        idLibros = new ArrayList<>();
    }

    public static Lecturas getInstance() {
        if (ourInstance == null) {
            ourInstance = new Lecturas();
        }
        return ourInstance;
    }


    public void activaEscuchadorMisLecturas() {
        referenciaMisLecturas.addChildEventListener(this);
    }

    public void desactivaEscuchadorMisLecturas() {
        referenciaMisLecturas.removeEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (!idLibros.contains(dataSnapshot.getKey())) {
            idLibros.add(dataSnapshot.getKey());
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Log.d("Entra", "entra");
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        idLibros.remove(dataSnapshot.getKey());
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public boolean hasReadBook(String key) {
        return idLibros.contains(key);
    }

    public void markBookAsRead(String key, boolean read) {
        if (read) {
            referenciaMisLecturas.child(key).setValue(true);
        } else {
            referenciaMisLecturas.child(key).getRef().removeValue();
            idLibros.remove(key);
        }
    }
}
