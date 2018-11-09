package com.mobdev.pxl.pokmart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mobdev.pxl.pokmart.data.PokemonRepository;
import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.layout_items.PokemonRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<Pokemon> mPokemonList;
    private RecyclerView mRecyclerView;
    private PokemonRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);

        mPokemonList = new ArrayList<Pokemon>();

        mRecyclerView = (RecyclerView) findViewById(R.id.pokemonRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
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
                    transaction.disallowAddToBackStack();

                    transaction.commit();
                } else {
                    Intent intent = new Intent(getApplicationContext(), PokemonDetailActivity.class);
                    intent.putExtra("pokemon", item);
                    startActivity(intent);
                }
            }
        }, mPokemonList);
        new loadSearchResult().execute(getIntent().getStringExtra("searchQuery"));
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
            case R.id.cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private class loadSearchResult extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            PokemonRepository pokemonRepo = new PokemonRepository(getApplicationContext());
            mPokemonList.addAll(pokemonRepo.getPokemonByName(strings[0]));
            return strings[0];
        }

        @Override
        protected void onPostExecute(String query) {
            if(mPokemonList.size() == 0) {
                ((TextView) findViewById(R.id.searchResultLabel)).setText("No results found for " + "\'" + query + "\'");
            } else {
                ((TextView) findViewById(R.id.searchResultLabel)).setText("Search results for: " + "\'" + query + "\':");
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }
}
