package com.mobdev.pxl.pokmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;

public class PokemonDetailActivity extends AppCompatActivity {

    private Pokemon mPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
    }
}
