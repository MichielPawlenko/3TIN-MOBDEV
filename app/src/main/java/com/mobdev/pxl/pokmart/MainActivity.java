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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mobdev.pxl.pokmart.data.PokemonRepository;
import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.layout_items.PokemonRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static List<Pokemon> mPokemonList;
    private PokemonRepository mRepo;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private EditText mSearchTextBox;
    DrawerLayout mainScreenDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mSearchTextBox = (EditText) findViewById(R.id.searchTextBox);
        mSearchTextBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
                    ((ImageButton) findViewById(R.id.searchButton)).setEnabled(true);
                } else {
                    ((ImageButton) findViewById(R.id.searchButton)).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mRepo = new PokemonRepository(getApplicationContext());
        if(mPokemonList == null) {
            mPokemonList = new ArrayList<Pokemon>();
        }
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.homeMenuItem).setEnabled(false);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mainScreenDrawer.closeDrawers();

                        Context context = MainActivity.this;
                        Intent intent = new Intent(context, GenerationListActivity.class);
                        intent.putExtra("selectedItem", menuItem.getItemId());
                        intent.putExtra("generation", menuItem.getOrder());
                        startActivity(intent);
                        return true;
                    }
                });
        if(mPokemonList.size() != 3) {
            new loadAdapterItems().execute();
        } else {
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void onSearch(View view) {
        String searchString = ((EditText) findViewById(R.id.searchTextBox)).getText().toString();
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.putExtra("searchQuery", searchString);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mainScreenDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(getIntent().getBooleanExtra("canGoBack", true)) {
            super.onBackPressed();
        }
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
        protected void onPostExecute(Void voidarg) {
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
