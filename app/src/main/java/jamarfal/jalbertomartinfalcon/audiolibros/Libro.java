package jamarfal.jalbertomartinfalcon.audiolibros;

import java.util.Vector;

/**
 * Created by jamarfal on 19/12/16.
 */

public class Libro {

    public String title;
    public String author;
    public String imageUrl;
    public String audioUrl;
    public String genre; // Género literario
    public Boolean isNew; // Es una isNew
    public Boolean isRead; // Leído por el usuario
    public final static String FILTER_ALL = "Todos los géneros";
    public final static String FILTER_EPIC = "Poema épico";
    public final static String FILTER_S_XIX = "Literatura siglo XIX";
    public final static String FILTER_THRILLER = "Suspense";
    public final static String[] FILTERS_ARRAY = new String[]{FILTER_ALL, FILTER_EPIC,
            FILTER_S_XIX, FILTER_THRILLER};

    private int colorVibrante, colorApagado;

    public final static Libro EMPTY_BOOK = new Libro("", "anónimo", "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg", "", FILTER_ALL, true, false);

    private Libro(String title, String author, String imageUrl,
                  String audioUrl, String genre, Boolean isNew, Boolean isRead) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.audioUrl = audioUrl;
        this.genre = genre;
        this.isNew = isNew;
        this.isRead = isRead;
        this.colorApagado = -1;
        this.colorVibrante = -1;
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

    public static Vector<Libro> ejemploLibros() {
        final String SERVIDOR =
                "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Libro> libros = new Vector<Libro>();
        libros.add(new Libro("Kappa", "Akutagawa",
                SERVIDOR + "kappa.jpg", SERVIDOR + "kappa.mp3",
                Libro.FILTER_S_XIX, false, false));
        libros.add(new Libro("Avecilla", "Alas Clarín, Leopoldo",
                SERVIDOR + "avecilla.jpg", SERVIDOR + "avecilla.mp3",
                Libro.FILTER_S_XIX, true, false));
        libros.add(new Libro("Divina Comedia", "Dante",
                SERVIDOR + "divina_comedia.jpg", SERVIDOR + "divina_comedia.mp3",
                Libro.FILTER_EPIC, true, false));
        libros.add(new Libro("Viejo Pancho, El", "Alonso y Trelles, José",
                SERVIDOR + "viejo_pancho.jpg", SERVIDOR + "viejo_pancho.mp3",
                Libro.FILTER_S_XIX, true, true));
        libros.add(new Libro("Canción de Rolando", "Anónimo",
                SERVIDOR + "cancion_rolando.jpg", SERVIDOR + "cancion_rolando.mp3",
                Libro.FILTER_EPIC, false, true));
        libros.add(new Libro("Matrimonio de sabuesos", "Agata Christie",
                SERVIDOR + "matrim_sabuesos.jpg", SERVIDOR + "matrim_sabuesos.mp3", Libro.FILTER_THRILLER, false, true));
        libros.add(new Libro("La iliada", "Homero", SERVIDOR + "la_iliada.jpg", SERVIDOR + "la_iliada.mp3", Libro.FILTER_EPIC, true, false));
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
        private boolean isRead = false;

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

        public LibroBuilder withIsRead(boolean isRead) {
            this.isRead = isRead;
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
                    isNew,
                    isRead);
        }
    }
}
