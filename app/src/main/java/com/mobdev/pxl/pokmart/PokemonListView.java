package com.mobdev.pxl.pokmart;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.mobdev.pxl.pokmart.data.PokemonRepository;
import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.layout_items.PokemonRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PokemonListView extends AppCompatActivity {

    private int mGeneration;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private DrawerLayout mDrawer;
    private List<Pokemon> mPokemonList;
    private PokemonRepository mPokemonRepo;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGeneration = getIntent().getIntExtra("generation", -1);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.getMenu().findItem(getIntent().getIntExtra("selectedItem", 0)).setEnabled(false);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawer.closeDrawers();

                        Context context = getApplicationContext();
                        Intent intent = new Intent(context, PokemonListView.class);
                        intent.putExtra("selectedItem", menuItem.getItemId());
                        intent.putExtra("generation", menuItem.getOrder());
                        startActivity(intent);

                        return true;
                    }
                });

        mDrawer = findViewById(R.id.generationDrawer);

        mPokemonList = new ArrayList<Pokemon>();
        mPokemonRepo = new PokemonRepository(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.pokemonRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PokemonRecyclerViewAdapter(new PokemonRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Pokemon item) {
                FrameLayout detailFragment = (FrameLayout) findViewById(R.id.detailFragment);
                if (detailFragment != null && detailFragment.isEnabled()) {
                    PokemonDetailFragment fragment = new PokemonDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pokemon", item);
                    fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.detailFragment, fragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
                } else {
                    Intent intent = new Intent(getApplicationContext(), PokemonDetailActivity.class);
                    intent.putExtra("pokemon", item);
                    startActivity(intent);
                }
            }
        }, mPokemonList);

        new initializeAdapterItems().execute();
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
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.cart:
                Intent intent = new Intent(this, Shop_Cart_Screen.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class initializeAdapterItems extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            switch (mGeneration) {
                case 1:
                    mPokemonList.addAll(mPokemonRepo.getGeneration1Pokemon());
                    break;
                case 2:
                    mPokemonList.addAll(mPokemonRepo.getGeneration2Pokemon());
                    break;
                case 3:
                    mPokemonList.addAll(mPokemonRepo.getGeneration3Pokemon());
                    break;
                default:
                    return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void arg) {
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
