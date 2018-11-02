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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.layout_items.PokemonListViewAdapter;
import com.mobdev.pxl.pokmart.layout_items.PokemonListViewItem;
import com.mobdev.pxl.pokmart.utilities.HttpResponseLoader;
import com.mobdev.pxl.pokmart.utilities.JSONPokemonConverter;
import com.mobdev.pxl.pokmart.utilities.UrlGenerator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Shop_Main_Screen extends AppCompatActivity {

    List<Pokemon> itemsList = new ArrayList<Pokemon>();
    PokemonListViewAdapter adapter;
    ListView recommendedListView;
    DrawerLayout mainScreenDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__main__screen);

        mainScreenDrawer = findViewById(R.id.generationDrawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mainScreenDrawer.closeDrawers();

                        Context context = Shop_Main_Screen.this;
                        Intent intent = new Intent(context, PokemonListView.class);
                        startActivity(intent);

                        return true;
                    }
                });

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        adapter = new PokemonListViewAdapter(this, R.layout.pokemon_list_item, itemsList);
        new AddItemsListTask().execute();

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

    public class AddItemsListTask extends AsyncTask<Void, Void, List<Pokemon>> {

        @Override
        protected List<Pokemon> doInBackground(Void... voids) {
            try {
                List<Pokemon> returnList = new ArrayList<Pokemon>();
                for (int x = 0; x < 3; x++) {
                    URL url = UrlGenerator.GenerateRecommendedUrl();
                    String jsonString = HttpResponseLoader.GetResponse(url);
                    Pokemon item = JSONPokemonConverter.GeneratePokemon(jsonString);
                    returnList.add(item);
                }

                return returnList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Pokemon> items) {
            itemsList = new ArrayList<>(items);
            recommendedListView = (ListView) findViewById(R.id.recommendedList);
            adapter.addAll(itemsList);
            recommendedListView.setAdapter(adapter);
        }
    }
}
