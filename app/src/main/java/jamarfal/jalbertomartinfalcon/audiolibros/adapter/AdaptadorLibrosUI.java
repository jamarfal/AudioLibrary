package jamarfal.jalbertomartinfalcon.audiolibros.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import jamarfal.jalbertomartinfalcon.audiolibros.Libro;
import jamarfal.jalbertomartinfalcon.audiolibros.R;
import jamarfal.jalbertomartinfalcon.audiolibros.command.ClickAction;
import jamarfal.jalbertomartinfalcon.audiolibros.command.EmptyClickAction;
import jamarfal.jalbertomartinfalcon.audiolibros.command.EmptyLongClickAction;
import jamarfal.jalbertomartinfalcon.audiolibros.command.LongClickAction;
import jamarfal.jalbertomartinfalcon.audiolibros.singleton.VolleySingleton;

/**
 * Created by jamarfal on 19/12/16.
 */

public class AdaptadorLibrosUI extends RecyclerView.Adapter<AdaptadorLibrosUI.ViewHolder> implements ChildEventListener {
    private LayoutInflater inflador; //Crea Layouts a partir del XML
    protected DatabaseReference booksReference;
    private Context contexto;
    private ClickAction clickAction = new EmptyClickAction();
    private LongClickAction longClickAction = new EmptyLongClickAction();
    private ArrayList<String> keys;
    private ArrayList<DataSnapshot> items;


    public AdaptadorLibrosUI(Context contexto, DatabaseReference databaseReference) {
        keys = new ArrayList<String>();
        items = new ArrayList<DataSnapshot>();
        inflador = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.booksReference = databaseReference;
        this.contexto = contexto;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        items.add(dataSnapshot);
        keys.add(dataSnapshot.getKey());
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        int index = keys.indexOf(key);
        if (index != -1) {
            items.set(index, dataSnapshot);
            notifyItemChanged(index, dataSnapshot.getValue(Libro.class));
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        int index = keys.indexOf(key);
        if (index != -1) {
            keys.remove(index);
            items.remove(index);
            notifyItemRemoved(index);
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView portada;
        TextView titulo;

        ViewHolder(View itemView) {
            super(itemView);
            portada = (ImageView) itemView.findViewById(R.id.portada);
            portada.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            titulo = (TextView) itemView.findViewById(R.id.lblTitle);
        }
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_selector, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int posicion) {
        final Libro libro = getItem(posicion);
        final String key = getItemKey(posicion);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAction.execute(key);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction.execute(v);
                return true;
            }
        });

        VolleySingleton.getInstance(contexto).getLectorImagenes().get(libro.getUrlImagen(),
                new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        Bitmap bitmap = response.getBitmap();
                        holder.portada.setImageBitmap(bitmap);

                        //Extraemos el color principal de un bitmap
                        if (bitmap != null &&
                                !bitmap.isRecycled() &&
                                libro.getColorApagado() == -1 &&
                                libro.getColorVibrante() == -1) {
                            // Asynchronous
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    libro.setColorApagado(palette.getLightMutedColor(0));
                                    libro.setColorVibrante(palette.getLightVibrantColor(0));
                                    holder.itemView.setBackgroundColor(libro.getColorApagado());
                                    holder.titulo.setBackgroundColor(libro.getColorVibrante());

                                }
                            });
                        } else {
                            holder.itemView.setBackgroundColor(libro.getColorApagado());
                            holder.titulo.setBackgroundColor(libro.getColorVibrante());
                        }


                    }


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        holder.portada.setImageResource(R.drawable.books);
                    }

                });
        holder.titulo.setText(libro.getTitulo());
        holder.itemView.setScaleX(1);
        holder.itemView.setScaleY(1);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public DatabaseReference getRef(int pos) {
        return items.get(pos).getRef();
    }

    public Libro getItem(int pos) {
        return items.get(pos).getValue(Libro.class);
    }

    public String getItemKey(int pos) {
        return keys.get(pos);
    }

    public Libro getItemByKey(String key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            return items.get(index).getValue(Libro.class);
        } else {
            return null;
        }
    }


    public void setClickAction(ClickAction clickAction) {
        this.clickAction = clickAction;
    }

    public void setLongClickAction(LongClickAction longClickAction) {
        this.longClickAction = longClickAction;
    }

    public void activaEscuchadorLibros() {
        booksReference.addChildEventListener(this);
    }

    public void desactivaEscuchadorLibros() {
        booksReference.removeEventListener(this);
    }

}