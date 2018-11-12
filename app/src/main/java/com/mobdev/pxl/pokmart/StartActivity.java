package com.mobdev.pxl.pokmart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.pxl.pokmart.data.PokemonRepository;
import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.utilities.HttpResponseLoader;
import com.mobdev.pxl.pokmart.utilities.JSONPokemonConverter;
import com.mobdev.pxl.pokmart.utilities.UrlGenerator;

import java.net.URL;

public class StartActivity extends AppCompatActivity {

    private PokemonRepository mPokemonRepo;
    private Button mStartButton;
    private LinearLayout mLoadingBox;
    private TextView mCacheCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            Log.e("GEO", "No location permission");
            Toast.makeText(this, "No location permissions", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        mPokemonRepo = new PokemonRepository(getApplicationContext());

        mCacheCounter = (TextView) findViewById(R.id.cachingCounter);

        mStartButton = (Button) findViewById(R.id.startButton);
        mLoadingBox = (LinearLayout) findViewById(R.id.loadingBox);

        new cacheDbItems().execute();
    }

    public void onClickStart(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("canGoBack", false);
        intent.putExtra("selectedItem", R.id.homeMenuItem);
        startActivity(intent);
    }

    public class cacheDbItems extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            if (mPokemonRepo.getSize() < 386) {
                try {
                    Log.i("DATABASE", "Caching API...");
                    for (int x = 0; x < 386; x++) {
                        if (mPokemonRepo.getPokemonById(x + 1) == null) {
                            URL url = UrlGenerator.GeneratePokemonUrl(x + 1);
                            String jsonString = HttpResponseLoader.GetResponse(url);
                            Pokemon item = JSONPokemonConverter.GeneratePokemon(jsonString);
                            mPokemonRepo.addPokemon(item);
                            publishProgress(x + 1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return mPokemonRepo.getSize();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mCacheCounter.setText(values[0] + "/386");
        }

        @Override
        protected void onPostExecute(Integer size) {
            Log.i("DATABASE", "Amount of database entries: " + size);
            mStartButton.setEnabled(true);
            mLoadingBox.setVisibility(View.INVISIBLE);
        }
    }
}
