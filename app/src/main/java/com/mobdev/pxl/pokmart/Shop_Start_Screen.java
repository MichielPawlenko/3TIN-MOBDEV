package com.mobdev.pxl.pokmart;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobdev.pxl.pokmart.data.AppDatabase;
import com.mobdev.pxl.pokmart.data.PokemonDao;
import com.mobdev.pxl.pokmart.data.PokemonRepository;
import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.utilities.HttpResponseLoader;
import com.mobdev.pxl.pokmart.utilities.JSONPokemonConverter;
import com.mobdev.pxl.pokmart.utilities.UrlGenerator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Shop_Start_Screen extends AppCompatActivity {

    private PokemonRepository mPokemonRepo;
    private Button mStartButton;
    private LinearLayout mLoadingBox;
    private TextView mCacheCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__start__screen);

        mPokemonRepo = new PokemonRepository(getApplicationContext());

        mCacheCounter = (TextView) findViewById(R.id.cachingCounter);

        mStartButton = (Button) findViewById(R.id.startButton);
        mLoadingBox = (LinearLayout) findViewById(R.id.loadingBox);

        //getApplicationContext().deleteDatabase("pokemondb"); // TODO: DEBUG
        new cacheDbItems().execute();

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, Shop_Main_Screen.class);
        startActivity(intent);
    }

    public class cacheDbItems extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            if (mPokemonRepo.getSize() != 387) { //change to 386 if API is not yet cached
                try {
                    Log.i("DATABASE", "Caching API...");
                    for (int x = 0; x < 386; x++) {
                        URL url = UrlGenerator.GeneratePokemonUrl(x + 1);
                        String jsonString = HttpResponseLoader.GetResponse(url);
                        Pokemon item = JSONPokemonConverter.GeneratePokemon(jsonString);
                        mPokemonRepo.addPokemon(item);
                        publishProgress(x + 1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return mPokemonRepo.getSize();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mCacheCounter.setText(values[0] + " of 386");
        }

        @Override
        protected void onPostExecute(Integer size) {
            Log.i("DATABASE", "Amount of database entries: " + size);
            mStartButton.setEnabled(true);
            mLoadingBox.setVisibility(View.INVISIBLE);
        }
    }
}
