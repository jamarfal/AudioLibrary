package jamarfal.jalbertomartinfalcon.audiolibros.adapter;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import jamarfal.jalbertomartinfalcon.audiolibros.Libro;
import jamarfal.jalbertomartinfalcon.audiolibros.singleton.Lecturas;

/**
 * Created by jamarfal on 20/12/16.
 */

public class AdaptadorLibrosFiltroUi extends AdaptadorLibrosUI implements Observer {
    private Vector<Integer> indiceFiltro; // Índice en vectorSinFiltro de cada elemento de vectorLibros
    private String busqueda = "";
    private String genero = "";
    private boolean novedad = false;
    private boolean leido = false;
    private int librosUltimoFiltro; //Número libros del padre en último filtro


    public AdaptadorLibrosFiltroUi(Context contexto,
                                   DatabaseReference reference) {
        super(contexto, reference);
        recalculaFiltro();
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda.toLowerCase();
        recalculaFiltro();
    }

    public void setGenero(String genero) {
        this.genero = genero;
        recalculaFiltro();
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
        recalculaFiltro();
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
        recalculaFiltro();
    }

    public void recalculaFiltro() {
        indiceFiltro = new Vector<Integer>();
        librosUltimoFiltro = super.getItemCount();
        for (int i = 0; i < librosUltimoFiltro; i++) {
            Libro libro = super.getItem(i);
            boolean userHasRead = Lecturas.getInstance().hasReadBook(super.getItemKey(i));
            if ((libro.getTitulo().toLowerCase().contains(busqueda) ||
                    libro.getAutor().toLowerCase().contains(busqueda))
                    && (libro.getGenero().startsWith(genero))
                    && (!novedad || (novedad && libro.isNovedad()))
                    && (!leido || (leido && userHasRead))) {
                indiceFiltro.add(i);
            }
        }


    }


    public Libro getItem(int posicion) {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        return super.getItem(indiceFiltro.elementAt(posicion));
    }

    public int getItemCount() {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        return indiceFiltro.size();
    }

    public long getItemId(int posicion) {
        return indiceFiltro.elementAt(posicion);
    }

    public Libro getItemById(int id) {
        return super.getItem(id);
    }

    public String getItemKey(int posicion) {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        int id = indiceFiltro.elementAt(posicion);
        return super.getItemKey(id);
    }


    public void borrar(int posicion) {
        DatabaseReference reference = getRef(indiceFiltro.elementAt(posicion));
        reference.removeValue();
        recalculaFiltro();
    }

    public void insertar(Libro libro) {
        booksReference.push().setValue(libro);
        recalculaFiltro();
    }

    @Override
    public void update(Observable observable, Object data) {
        setBusqueda((String) data);
        notifyDataSetChanged();
    }
}
