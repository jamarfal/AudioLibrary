package jamarfal.jalbertomartinfalcon.audiolibros.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.io.IOException;

import jamarfal.jalbertomartinfalcon.audiolibros.Libro;
import jamarfal.jalbertomartinfalcon.audiolibros.MainActivity;
import jamarfal.jalbertomartinfalcon.audiolibros.R;
import jamarfal.jalbertomartinfalcon.audiolibros.customviews.ZoomSeekBar;
import jamarfal.jalbertomartinfalcon.audiolibros.presenter.DetailPresenter;
import jamarfal.jalbertomartinfalcon.audiolibros.singleton.BooksSingleton;
import jamarfal.jalbertomartinfalcon.audiolibros.singleton.VolleySingleton;

/**
 * Created by jamarfal on 19/12/16.
 */

public class DetalleFragment extends Fragment implements View.OnTouchListener, MediaPlayer.OnPreparedListener,
        MediaController.MediaPlayerControl, DetailPresenter.View {
    public static String ARG_ID_LIBRO = "id_libro";
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private ZoomSeekBar zoomSeekBar;
    private DetailPresenter detailPresenter;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detalle,
                container, false);
        detailPresenter = new DetailPresenter(this, BooksSingleton.getInstance(getContext()));
        detailPresenter.setBookId(getIdFromArguments());
        detailPresenter.onCreate();
        return rootView;
    }

    private int getIdFromArguments() {
        Bundle args = getArguments();
        int position = 0;
        if (args != null) {
            position = args.getInt(ARG_ID_LIBRO);
        }
        return position;
    }

    @Override
    public void onResume() {
        DetalleFragment detalleFragment = (DetalleFragment)
                getFragmentManager().findFragmentById(R.id.detalle_fragment);
        if (detalleFragment == null) {
            ((MainActivity) getActivity()).mostrarElementos(false);
        }
        super.onResume();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("Audiolibros", "Entramos en onPrepared de MediaPlayer");
        initializeMediaPlayer(mediaPlayer);
    }

    private void initializeMediaPlayer(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(getView().findViewById(
                R.id.fragment_detalle));
        mediaController.setPadding(0, 0, 0, 110);
        mediaController.setEnabled(true);
        mediaController.show();
    }

    @Override
    public boolean onTouch(View vista, MotionEvent evento) {
        mediaController.show();
        return false;
    }

    @Override
    public void onStop() {
        mediaController.hide();
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e) {
            Log.d("Audiolibros", "Error en mediaPlayer.stop()");
        }
        super.onStop();
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        try {
            return mediaPlayer.getCurrentPosition();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void showBookInfo(Libro book) {
        ((TextView) rootView.findViewById(R.id.labelTitle)).setText(book.title);
        ((TextView) rootView.findViewById(R.id.labelAuthor)).setText(book.author);
        ((NetworkImageView) rootView.findViewById(R.id.portada)).setImageUrl(
                book.imageUrl, VolleySingleton.getInstance(getContext()).getLectorImagenes());
        zoomSeekBar = (ZoomSeekBar) rootView.findViewById(R.id.zoomseekbar);


        rootView.setOnTouchListener(this);

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(getActivity());
        Uri audio = Uri.parse(book.audioUrl);
        try {
            mediaPlayer.setDataSource(getActivity(), audio);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("Audiolibros", "ERROR: No se puede reproducir " + audio, e);
        }
    }
}
