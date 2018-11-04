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

import com.mobdev.pxl.pokmart.data.AppDatabase;
import com.mobdev.pxl.pokmart.data.PokemonDao;
import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.utilities.HttpResponseLoader;
import com.mobdev.pxl.pokmart.utilities.JSONPokemonConverter;
import com.mobdev.pxl.pokmart.utilities.UrlGenerator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Shop_Start_Screen extends AppCompatActivity {

    private ArrayList<Pokemon> mPokemonList;
    private AppDatabase mDatabase;
    private PokemonDao mDao;
    private Button mStartButton;
    private LinearLayout mLoadingBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__start__screen);

        mDatabase = AppDatabase.getDatabase(getApplicationContext());
        mDao = mDatabase.pokemonDao();

        mStartButton = (Button) findViewById(R.id.startButton);
        mLoadingBox = (LinearLayout) findViewById(R.id.loadingBox);

        //getApplicationContext().deleteDatabase("pokemondb"); // TODO: DEBUG
        new cacheDbItems().execute();

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, Shop_Main_Screen.class);
        startActivity(intent);
    }

    public class cacheDbItems extends AsyncTask<Void, Void, List<Pokemon>> {

        @Override
        protected List<Pokemon> doInBackground(Void... voids) {
            if (mDao.getAll().size() != 387) {
                try {
                    Log.i("DATABASE", "Caching API...");
                    List<Pokemon> returnList = new ArrayList<Pokemon>();
                    for (int x = 0; x <= 386; x++) {
                        URL url = UrlGenerator.GeneratePokemonUrl(x + 1);
                        String jsonString = HttpResponseLoader.GetResponse(url);
                        Pokemon item = JSONPokemonConverter.GeneratePokemon(jsonString);
                        returnList.add(item);
                        mDao.insert(item);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return mDao.getAll();
        }

        @Override
        protected void onPostExecute(List<Pokemon> items) {
            Log.i("DATABASE", "size: " + items.size());
            mStartButton.setEnabled(true);
            mLoadingBox.setVisibility(View.INVISIBLE);
        }
    }
}
