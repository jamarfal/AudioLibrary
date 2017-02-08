package jamarfal.jalbertomartinfalcon.audiolibros;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jamarfal.jalbertomartinfalcon.audiolibros.adapter.AdaptadorLibrosFiltro;
import jamarfal.jalbertomartinfalcon.audiolibros.domain.GetLastBook;
import jamarfal.jalbertomartinfalcon.audiolibros.domain.HasLastBook;
import jamarfal.jalbertomartinfalcon.audiolibros.domain.SaveLastBook;
import jamarfal.jalbertomartinfalcon.audiolibros.fragment.DetalleFragment;
import jamarfal.jalbertomartinfalcon.audiolibros.fragment.SelectorFragment;
import jamarfal.jalbertomartinfalcon.audiolibros.model.Libro;
import jamarfal.jalbertomartinfalcon.audiolibros.presenter.MainPresenter;
import jamarfal.jalbertomartinfalcon.audiolibros.repository.BooksRepository;
import jamarfal.jalbertomartinfalcon.audiolibros.singleton.BooksSingleton;
import jamarfal.jalbertomartinfalcon.audiolibros.singleton.VolleySingleton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Animator.AnimatorListener, MainPresenter.View {

    private AdaptadorLibrosFiltro adaptador;
    private AppBarLayout appBarLayout;
    private TabLayout tabs;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private MainPresenter mainPresenter;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BooksRepository booksRepository = BooksRepository.getINSTANCE(LibroSharedPreferenceStorage.getInstance(this));
        mainPresenter = new MainPresenter(
                new SaveLastBook(booksRepository),
                new GetLastBook(booksRepository),
                new HasLastBook(booksRepository),
                this);

        userName = LibroSharedPreferenceStorage.getInstance(this).getUserName();

        Toolbar toolbar = initializeToolBar();

        adaptador = BooksSingleton.getInstance(this).getAdapter();

        initializeFloatingActionButton();

        initializeTabs();

        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        initializeActionBar();

        initializeNavigationDrawer(toolbar);

        createFragment();






    }

    private void initializeNavigationDrawer(Toolbar toolbar) {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(
                R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtName = (TextView) headerLayout.findViewById(R.id.txtName);
        txtName.setText(String.format(getString(R.string.welcome_message), userName));

        // Foto de usuario
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        Uri urlImagen = usuario.getPhotoUrl();
        if (urlImagen != null) {
            NetworkImageView fotoUsuario = (NetworkImageView)
                    headerLayout.findViewById(R.id.imageView);
            VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
            fotoUsuario.setImageUrl(urlImagen.toString(),
                    volleySingleton.getLectorImagenes());
        }
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initializeTabs() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Todos"));
        tabs.addTab(tabs.newTab().setText("Nuevos"));
        tabs.addTab(tabs.newTab().setText("Leidos"));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: //Todos
                        adaptador.setNovedad(false);
                        adaptador.setLeido(false);
                        break;
                    case 1: //Nuevos
                        adaptador.setNovedad(true);
                        adaptador.setLeido(false);
                        break;
                    case 2: //Leidos
                        adaptador.setNovedad(false);
                        adaptador.setLeido(true);
                        break;
                }
                adaptador.notifyDataSetChanged();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void initializeFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.clickFavoriteButton();
            }
        });
    }

    private Toolbar initializeToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void createFragment() {
        if ((findViewById(R.id.contenedor_pequeno) != null) && (getSupportFragmentManager().findFragmentById(
                R.id.contenedor_pequeno) == null)) {
            SelectorFragment primerFragment = new SelectorFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor_pequeno, primerFragment).commit();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_todos) {
            adaptador.setGenero("");
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_epico) {
            adaptador.setGenero(Libro.FILTER_EPIC);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_XIX) {
            adaptador.setGenero(Libro.FILTER_S_XIX);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_suspense) {
            adaptador.setGenero(Libro.FILTER_THRILLER);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_signout) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            LibroSharedPreferenceStorage libroSharedPreferenceStorage = LibroSharedPreferenceStorage.getInstance(MainActivity.this);
                            libroSharedPreferenceStorage.removeEmail();
                            libroSharedPreferenceStorage.removeProvider();
                            libroSharedPreferenceStorage.removeUserName();
                            Intent i = new Intent(MainActivity.this, CustomLoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                    });
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_preferencias) {
            Intent i = new Intent(this, PreferenciasActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.menu_acerca) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Mensaje de Acerca De");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void irUltimoVisitado() {
        mainPresenter.clickFavoriteButton();
    }


    @Override
    public void showFragmentDetail(String key) {
        DetalleFragment detalleFragment = (DetalleFragment)
                getSupportFragmentManager().findFragmentById(R.id.detalle_fragment);
        if (detalleFragment != null) {

            detalleFragment.showBookInfo(BooksSingleton.getInstance(this).getAdapter().getItemByKey(key), key);
        } else {
            DetalleFragment nuevoFragment = new DetalleFragment();
            Bundle args = new Bundle();
            args.putString(DetalleFragment.ARG_ID_LIBRO, key);
            nuevoFragment.setArguments(args);
            FragmentTransaction transaccion = getSupportFragmentManager()
                    .beginTransaction();
            transaccion.replace(R.id.contenedor_pequeno, nuevoFragment);
            transaccion.addToBackStack(null);
            transaccion.commit();
        }
    }

    public void mostrarElementos(boolean mostrar) {
        appBarLayout.setExpanded(mostrar);
        toggle.setDrawerIndicatorEnabled(mostrar);
        if (mostrar) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            tabs.setVisibility(View.VISIBLE);

        } else {
            tabs.setVisibility(View.GONE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }


    public void showBookActions(final View v, final RecyclerView recyclerView) {
        final int id = recyclerView.getChildAdapterPosition(v);
        AlertDialog.Builder menu = new AlertDialog.Builder(this);
        CharSequence[] opciones = {"Compartir", "Borrar ", "Insertar"};
        menu.setItems(opciones, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int opcion) {
                switch (opcion) {
                    case 0: //Compartir
                        Animator anim = AnimatorInflater.loadAnimator(MainActivity.this,
                                R.animator.crecer);
                        anim.addListener(MainActivity.this);
                        anim.setTarget(v);
                        anim.start();
                        Libro libro = BooksSingleton.getInstance(MainActivity.this).getAdapter().getItemById(id);
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, libro.getTitulo());
                        i.putExtra(Intent.EXTRA_TEXT, libro.getUrlAudio());
                        startActivity(Intent.createChooser(i, "Compartir"));
                        break;
                    case 1: // Borrar
                        Snackbar.make(v, "¿Estás seguro?", Snackbar.LENGTH_LONG)
                                .setAction("SI", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Otra forma de animar
//                                                Animation anim = AnimationUtils.loadAnimation(actividad,
//                                                        R.anim.menguar);
//                                                anim.setAnimationListener(SelectorFragment.this);
//                                                v.startAnimation(anim);
//                                                adaptador.borrar(id);
                                        //adaptador.notifyDataSetChanged();

                                        Animator anim = AnimatorInflater.loadAnimator(MainActivity.this,
                                                R.animator.menguar);
                                        anim.addListener(MainActivity.this);
                                        anim.setTarget(v);
                                        anim.start();
                                        adaptador.borrar(id);
                                    }
                                }).show();
                        break;

                    case 2: //Insertar
                        int posicion = recyclerView.getChildLayoutPosition(v);
                        adaptador.insertar((Libro) adaptador.getItem(posicion));
                        adaptador.notifyItemInserted(adaptador.getItemCount() + 1);
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

    @Override
    public void showDetail(String lastBook) {
        mainPresenter.openDetalle(lastBook);
    }

    @Override
    public void showNoLastVisit() {
        Toast.makeText(this, "Sin última vista", Toast.LENGTH_LONG).show();
    }
}
