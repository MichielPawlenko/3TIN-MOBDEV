package com.mobdev.pxl.pokmart;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.mobdev.pxl.pokmart.data.PokemonRepository;
import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.layout_items.PokemonRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shop_Main_Screen extends AppCompatActivity {

    List<Pokemon> mPokemonList;
    PokemonRepository mRepo;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    DrawerLayout mainScreenDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__main__screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mRepo = new PokemonRepository(getApplicationContext());
        mPokemonList = new ArrayList<Pokemon>();
        mainScreenDrawer = findViewById(R.id.generationDrawer);

        mRecyclerView = (RecyclerView) findViewById(R.id.pokemonRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PokemonRecyclerViewAdapter(new PokemonRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Pokemon item) {
                Intent intent = new Intent(getApplicationContext(), PokemonDetailActivity.class);
                intent.putExtra("pokemon", item);
                startActivity(intent);
            }
        }, mPokemonList);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mainScreenDrawer.closeDrawers();

                        Context context = Shop_Main_Screen.this;
                        Intent intent = new Intent(context, PokemonListView.class);
                        intent.putExtra("selectedItem", menuItem.getItemId());
                        intent.putExtra("generation", menuItem.getOrder());
                        startActivity(intent);

                        return true;
                    }
                });
        new loadAdapterItems().execute();

        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mainScreenDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class loadAdapterItems extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Random rng = new Random();
                for (int x = 0; x < 3; x++) {
                    mPokemonList.add(mRepo.getPokemonById(rng.nextInt(385) + 1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void nullargs) {
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
