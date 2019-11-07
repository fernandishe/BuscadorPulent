package com.fernandishe.buscadormusica;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.InputStream;
import java.net.URL;

public class DetalleMusica extends AppCompatActivity {

    private VideoView videoPreview;
    private ImageView imagen;
    private TextView albumView;
    private WebView webView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;
    private String urlPreview;
    private String album;
    private String urlLista;
    private String urlImagen;
    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_musica);
        Bundle extras = getIntent().getExtras();
        urlPreview = extras.getString("urlVideo");
        urlLista = extras.getString("urlLista");
        album = extras.getString("album");
        urlImagen = extras.getString("urlImagen");
        imagen = findViewById(R.id.imagenDetalle);
        webView = findViewById(R.id.listaDetalle);
        videoPreview = findViewById(R.id.videoDetalle);
        albumView = findViewById(R.id.bandaDetalle);
        albumView.setText(album);
        try {
            drawable = Drawable.createFromStream((InputStream)
                    new URL(urlImagen).getContent(), "src");
            imagen.setImageDrawable(drawable);
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        WebSettings ajustesVisorWeb = webView.getSettings();
        ajustesVisorWeb.setJavaScriptEnabled(true);
        webView.loadUrl(urlLista);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        //set the media controller buttons
        if (mediaControls == null) {
            mediaControls = new MediaController(DetalleMusica.this);
        }


        // create a progress bar while the video file is loading
        progressDialog = new

                ProgressDialog(DetalleMusica.this);
        // set a title for the progress bar
        progressDialog.setTitle("Cargando vista previa video");
        // set a message for the progress bar
        progressDialog.setMessage("Cargando...");
        //set the progress bar not cancelable on users' touch
        progressDialog.setCancelable(false);
        // show the progress bar
        progressDialog.show();

        try {
            //set the media controller in the VideoView
            videoPreview.setMediaController(mediaControls);

            //set the uri of the video to be played
            videoPreview.setVideoURI(Uri.parse(urlPreview));

        } catch (
                Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoPreview.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        videoPreview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                videoPreview.seekTo(position);
                if (position == 0) {
                    videoPreview.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    videoPreview.pause();
                }
            }


        });
    }
}
