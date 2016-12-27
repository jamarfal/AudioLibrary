package jamarfal.jalbertomartinfalcon.audiolibros.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.Vector;

import jamarfal.jalbertomartinfalcon.audiolibros.AdaptadorLibros;
import jamarfal.jalbertomartinfalcon.audiolibros.AdaptadorLibrosFiltro;
import jamarfal.jalbertomartinfalcon.audiolibros.Aplicacion;
import jamarfal.jalbertomartinfalcon.audiolibros.Libro;
import jamarfal.jalbertomartinfalcon.audiolibros.MainActivity;
import jamarfal.jalbertomartinfalcon.audiolibros.R;

/**
 * Created by jamarfal on 19/12/16.
 */

public class SelectorFragment extends Fragment implements Animator.AnimatorListener {
    private Activity actividad;
    private RecyclerView recyclerView;
    private AdaptadorLibrosFiltro adaptador;
    private Vector<Libro> vectorLibros;

    @Override
    public void onAttach(Context contexto) {
        super.onAttach(contexto);
        if (contexto instanceof Activity) {
            this.actividad = (Activity) contexto;
            Aplicacion app = (Aplicacion) actividad.getApplication();
            adaptador = app.getAdaptador();
            vectorLibros = app.getVectorLibros();
        }
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).mostrarElementos(true);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_selector, contenedor, false);
        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad, 2));
        recyclerView.setAdapter(adaptador);
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) actividad).mostrarDetalle(
                        (int) adaptador.getItemId(
                                recyclerView.getChildAdapterPosition(v)));
            }
        });

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(2000);
        animator.setMoveDuration(2000);
        recyclerView.setItemAnimator(animator);

        adaptador.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(final View v) {
                final int id = recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(actividad);
                CharSequence[] opciones = {"Compartir", "Borrar ", "Insertar"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion) {
                            case 0: //Compartir
                                Animator anim = AnimatorInflater.loadAnimator(actividad,
                                        R.animator.crecer);
                                anim.addListener(SelectorFragment.this);
                                anim.setTarget(v);
                                anim.start();
                                Libro libro = vectorLibros.elementAt(id);
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("text/plain");
                                i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                                i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);
                                startActivity(Intent.createChooser(i, "Compartir"));
                                break;
                            case 1:
                                Snackbar.make(v, "¿Estás seguro?", Snackbar.LENGTH_LONG)
                                        .setAction("SI", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
//                                                Animation anim = AnimationUtils.loadAnimation(actividad,
//                                                        R.anim.menguar);
//                                                anim.setAnimationListener(SelectorFragment.this);
//                                                v.startAnimation(anim);
//                                                adaptador.borrar(id);
                                                //adaptador.notifyDataSetChanged();

                                                Animator anim = AnimatorInflater.loadAnimator(actividad,
                                                        R.animator.menguar);
                                                anim.addListener(SelectorFragment.this);
                                                anim.setTarget(v);
                                                anim.start();
                                                adaptador.borrar(id);
                                            }
                                        }).show();
                                break;

                            case 2: //Insertar
                                int posicion = recyclerView.getChildLayoutPosition(v);
                                adaptador.insertar((Libro) adaptador.getItem(posicion));
                                adaptador.notifyItemInserted(0);
                                Snackbar.make(v, "Libro insertado", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                            }
                                        })
                                        .show();
                                break;
                        }
                    }
                });
                menu.create().show();
                return true;
            }
        });

        setHasOptionsMenu(true);

        return vista;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ultimo) {
            ((MainActivity) actividad).irUltimoVisitado();
            return true;
        } else if (id == R.id.menu_buscar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
