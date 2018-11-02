package com.mobdev.pxl.pokmart;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.layout_items.PokemonListViewAdapter;
import com.mobdev.pxl.pokmart.layout_items.PokemonRecyclerViewAdapter;

import java.io.Console;

public class PokemonListView extends AppCompatActivity {

    private int mGeneration;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mRecyclerView = (RecyclerView) findViewById(R.id.pokemonRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mDrawer = findViewById(R.id.generationDrawer);

        mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new initializeAdapter().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class initializeAdapter extends AsyncTask<Void, Void, PokemonRecyclerViewAdapter> {

        @Override
        protected PokemonRecyclerViewAdapter doInBackground(Void... voids) {
            return new PokemonRecyclerViewAdapter(new PokemonRecyclerViewAdapter.onItemClickListener() {
                @Override public void onItemClick(Pokemon item) {
                    Log.i("CLICKER", "CLICK OK");
                }
            });
        }

        @Override
        protected void onPostExecute(PokemonRecyclerViewAdapter pokemonRecyclerViewAdapter) {
            mAdapter = pokemonRecyclerViewAdapter;
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
