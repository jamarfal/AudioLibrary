package jamarfal.jalbertomartinfalcon.audiolibros.adapter;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import jamarfal.jalbertomartinfalcon.audiolibros.Libro;

/**
 * Created by jamarfal on 20/12/16.
 */

public class AdaptadorLibrosFiltro extends AdaptadorLibros implements Observer {
    private Vector<Libro> vectorSinFiltro;// Vector con todos los libros
    private Vector<Integer> indiceFiltro; // Índice en vectorSinFiltro de cada elemento de vectorLibros
    private String busqueda = "";
    private String genero = "";
    private boolean novedad = false;
    private boolean leido = false;

    public AdaptadorLibrosFiltro(Context contexto,
                                 Vector<Libro> vectorLibros) {
        super(contexto, vectorLibros);
        vectorSinFiltro = vectorLibros;
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
        vectorLibros = new Vector<Libro>();
        indiceFiltro = new Vector<Integer>();
        for (int i = 0; i < vectorSinFiltro.size(); i++) {
            Libro libro = vectorSinFiltro.elementAt(i);
            if ((libro.title.toLowerCase().contains(busqueda) ||
                    libro.author.toLowerCase().contains(busqueda))
                    && (libro.genre.startsWith(genero))
                    && (!novedad || (novedad && libro.isNew))
                    && (!leido || (leido && libro.isRead))) {
                vectorLibros.add(libro);
                indiceFiltro.add(i);
            }
        }
    }


    public Libro getItem(int posicion) {
        return vectorSinFiltro.elementAt(indiceFiltro.elementAt(posicion));
    }

    public long getItemId(int posicion) {
        return indiceFiltro.elementAt(posicion);
    }

    public void borrar(int posicion) {
        vectorSinFiltro.remove((int) getItemId(posicion));
        recalculaFiltro();
    }

    public void insertar(Libro libro) {
        vectorSinFiltro.add(0, libro);
        recalculaFiltro();
    }

    @Override
    public void update(Observable observable, Object data) {
        setBusqueda((String) data);
        notifyDataSetChanged();
    }
}
