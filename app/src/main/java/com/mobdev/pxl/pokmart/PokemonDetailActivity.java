package com.mobdev.pxl.pokmart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.utilities.UrlBitmapLoader;

import java.net.MalformedURLException;
import java.net.URL;

public class PokemonDetailActivity extends AppCompatActivity {

    private Pokemon mPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);


        Intent intent = getIntent();
        mPokemon = (Pokemon) intent.getSerializableExtra("pokemon");
        ((TextView) findViewById(R.id.pokemonNameTextView)).setText(mPokemon.name);
        ((TextView) findViewById(R.id.pokemonPriceTextView)).setText(mPokemon.baseXp * 4 + "$");
        new loadPokemonImage().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class loadPokemonImage extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                return UrlBitmapLoader.LoadBitmapFromUrl(new URL(mPokemon.sprite));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ((ImageView) findViewById(R.id.pokemonImage)).setImageBitmap(bitmap);
        }
    }
}
