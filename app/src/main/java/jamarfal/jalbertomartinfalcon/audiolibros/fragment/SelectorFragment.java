package jamarfal.jalbertomartinfalcon.audiolibros.fragment;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Vector;

import jamarfal.jalbertomartinfalcon.audiolibros.SearchObservable;
import jamarfal.jalbertomartinfalcon.audiolibros.adapter.AdaptadorLibrosFiltro;
import jamarfal.jalbertomartinfalcon.audiolibros.Libro;
import jamarfal.jalbertomartinfalcon.audiolibros.MainActivity;
import jamarfal.jalbertomartinfalcon.audiolibros.R;
import jamarfal.jalbertomartinfalcon.audiolibros.command.OpenDetailClickAction;
import jamarfal.jalbertomartinfalcon.audiolibros.command.ShowOptionsPopupClickAction;
import jamarfal.jalbertomartinfalcon.audiolibros.singleton.BooksSingleton;

/**
 * Created by jamarfal on 19/12/16.
 */

public class SelectorFragment extends Fragment implements Animator.AnimatorListener {
    private Activity activity;
    private RecyclerView recyclerView;
    private AdaptadorLibrosFiltro adapter;
    private Vector<Libro> vectorBooks;

    @Override
    public void onAttach(Context contexto) {
        super.onAttach(contexto);
        if (contexto instanceof Activity) {
            this.activity = (Activity) contexto;
            BooksSingleton booksSingleton = BooksSingleton.getInstance(contexto);
            adapter = booksSingleton.getAdapter();
            vectorBooks = booksSingleton.getVectorBooks();
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
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        recyclerView.setAdapter(adapter);


        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(2000);
        animator.setMoveDuration(2000);
        recyclerView.setItemAnimator(animator);

        adapter.setClickAction(new OpenDetailClickAction((MainActivity) getActivity()));
        adapter.setLongClickAction(new ShowOptionsPopupClickAction((MainActivity) getActivity(), recyclerView));


        setHasOptionsMenu(true);

        return vista;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        SearchObservable searchObservable = new SearchObservable();
        searchObservable.addObserver(adapter);
        searchView.setOnQueryTextListener(searchObservable);

        // Resetea la busqueda
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.setBusqueda("");
                adapter.notifyDataSetChanged();
                return true; // Para permitir cierre
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true; // Para permitir expansión
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ultimo) {
            ((MainActivity) activity).irUltimoVisitado();
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
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
