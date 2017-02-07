package jamarfal.jalbertomartinfalcon.audiolibros;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by jamarfal on 19/12/16.
 */

public class Libro {

    private String titulo;
    private String autor;
    private String urlImagen;
    private String urlAudio;
    private String genero; // Género literario
    private Boolean novedad; // Es una novedad
    private Map<String, Boolean> leido;
    private int colorVibrante, colorApagado;

    private final static String FILTER_ALL = "Todos los géneros";
    final static String FILTER_EPIC = "Poema épico";
    final static String FILTER_S_XIX = "Literatura siglo XIX";
    public final static String FILTER_THRILLER = "Suspense";
    public final static String[] FILTERS_ARRAY = new String[]{FILTER_ALL, FILTER_EPIC,
            FILTER_S_XIX, FILTER_THRILLER};



    public final static Libro EMPTY_BOOK = new Libro("", "anónimo", "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg", "", FILTER_ALL, false);

    private Libro(String titulo, String autor, String urlImagen,
                  String urlAudio, String genero, Boolean novedad) {
        this.titulo = titulo;
        this.autor = autor;
        this.urlImagen = urlImagen;
        this.urlAudio = urlAudio;
        this.genero = genero;
        this.novedad = novedad;
        this.leido = new HashMap<>();
        this.colorApagado = -1;
        this.colorVibrante = -1;
    }

    public Libro() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Boolean isNew() {
        return novedad;
    }

    public void setNew(Boolean aNew) {
        novedad = aNew;
    }

    public Map<String, Boolean> getLeido() {
        return leido;
    }

    public void setLeido(Map<String, Boolean> leido) {
        this.leido = leido;
    }

    public int getColorVibrante() {
        return colorVibrante;
    }

    public void setColorVibrante(int colorVibrante) {
        this.colorVibrante = colorVibrante;
    }

    public int getColorApagado() {
        return colorApagado;
    }

    public void setColorApagado(int colorApagado) {
        this.colorApagado = colorApagado;
    }

    public boolean leidoPor(String userID) {
        if (this.leido != null) {
            return this.leido.keySet().contains(userID);
        } else {
            return false;
        }
    }

    public static Vector<Libro> ejemploLibros() {
        final String SERVIDOR =
                "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Libro> libros = new Vector<Libro>();
        libros.add(new Libro("Kappa", "Akutagawa",
                SERVIDOR + "kappa.jpg", SERVIDOR + "kappa.mp3",
                Libro.FILTER_S_XIX, false));
        libros.add(new Libro("Avecilla", "Alas Clarín, Leopoldo",
                SERVIDOR + "avecilla.jpg", SERVIDOR + "avecilla.mp3",
                Libro.FILTER_S_XIX, false));
        libros.add(new Libro("Divina Comedia", "Dante",
                SERVIDOR + "divina_comedia.jpg", SERVIDOR + "divina_comedia.mp3",
                Libro.FILTER_EPIC, false));
        libros.add(new Libro("Viejo Pancho, El", "Alonso y Trelles, José",
                SERVIDOR + "viejo_pancho.jpg", SERVIDOR + "viejo_pancho.mp3",
                Libro.FILTER_S_XIX, true));
        libros.add(new Libro("Canción de Rolando", "Anónimo",
                SERVIDOR + "cancion_rolando.jpg", SERVIDOR + "cancion_rolando.mp3",
                Libro.FILTER_EPIC, true));
        libros.add(new Libro("Matrimonio de sabuesos", "Agata Christie",
                SERVIDOR + "matrim_sabuesos.jpg", SERVIDOR + "matrim_sabuesos.mp3", Libro.FILTER_THRILLER, true));
        libros.add(new Libro("La iliada", "Homero", SERVIDOR + "la_iliada.jpg", SERVIDOR + "la_iliada.mp3", Libro.FILTER_EPIC, false));
        return libros;
    }


    public static class LibroBuilder {
        private String title = "";
        private String author = "anónimo";
        private String imageUrl =
                "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg";
        private String audioUrl = "";
        private String genre = FILTER_ALL;
        private boolean isNew = true;
        private Map<String, Boolean> readedBooks;

        public LibroBuilder withTitle(String titulo) {
            this.title = titulo;
            return this;
        }

        public LibroBuilder withAuthor(String autor) {
            this.author = autor;
            return this;
        }

        public LibroBuilder withImageUrl(String urlImagen) {
            this.imageUrl = urlImagen;
            return this;
        }

        public LibroBuilder withAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
            return this;
        }

        public LibroBuilder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public LibroBuilder withReadedBooks(Map<String, Boolean> readedBooks) {
            this.readedBooks = readedBooks;
            return this;
        }

        public LibroBuilder withIsNew(boolean isNew) {
            this.isNew = isNew;
            return this;
        }

        public Libro build() {
            return new Libro(
                    title,
                    author,
                    imageUrl,
                    audioUrl,
                    genre,
                    isNew);
        }
    }
}
