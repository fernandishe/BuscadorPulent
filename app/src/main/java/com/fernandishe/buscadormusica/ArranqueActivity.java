package com.fernandishe.buscadormusica;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ArranqueActivity extends AppCompatActivity {

    private final int DURACION_SPLASH = 3000;
    public SQLiteDatabase basePulent;
    public String nomDB = "basePastilla.DB";
    public String rutaBD = "";
    private TextView txtVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arranque);
        getSupportActionBar().hide();

        txtVersion = findViewById(R.id.lblVersion);
        txtVersion.setText("v" + BuildConfig.VERSION_NAME);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent = new Intent(ArranqueActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, DURACION_SPLASH);
    }

    @SuppressLint("WrongConstant")
    public String CreaBase() {
        try {
            basePulent = openOrCreateDatabase(nomDB, SQLiteDatabase.CREATE_IF_NECESSARY, null);
            basePulent.setVersion(1);
            basePulent.setLocale(Locale.getDefault());
            basePulent.setLockingEnabled(true);
            basePulent.execSQL("CREATE TABLE IF NOT EXISTS tMusica(" +
                    "artworkUrl100 varchar(100)," +
                    "isStreamable varchar(100)," +
                    "trackTimeMillis varchar(100)," +
                    "longDescription varchar(100)," +
                    "country varchar(100)," +
                    "previewUrl varchar(100)," +
                    "collectionHdPrice varchar(100)," +
                    "artistId varchar(100)," +
                    "trackName varchar(100)," +
                    "collectionName varchar(100)," +
                    "artistViewUrl varchar(100)," +
                    "discNumber varchar(100)," +
                    "trackCount varchar(100)," +
                    "artworkUrl30 varchar(100)," +
                    "wrapperType varchar(100)," +
                    "currency varchar(100)," +
                    "collectionId varchar(100)," +
                    "trackExplicitness varchar(100)," +
                    "collectionViewUrl varchar(100)," +
                    "trackHdPrice varchar(100)," +
                    "contentAdvisoryRating varchar(100)," +
                    "trackNumber varchar(100)," +
                    "releaseDate varchar(100)," +
                    "kind varchar(100)," +
                    "trackId varchar(100)," +
                    "collectionPrice varchar(100)," +
                    "shortDescription varchar(100)," +
                    "discCount varchar(100)," +
                    "primaryGenreName varchar(100)," +
                    "trackPrice varchar(100)," +
                    "collectionExplicitness varchar(100)," +
                    "trackViewUrl varchar(100)," +
                    "artworkUrl60 varchar(100)," +
                    "trackCensoredName varchar(100)," +
                    "artistName varchar(100)," +
                    "collectionCensoredName varchar(100))"
            );
            rutaBD = basePulent.getPath();
        }catch (SQLException sql)
        {
            sql.printStackTrace();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return rutaBD;
    }
}
