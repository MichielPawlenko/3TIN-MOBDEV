package com.mobdev.pxl.pokmart;

import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.mobdev.pxl.pokmart.layout_items.PokemonListViewAdapter;
import com.mobdev.pxl.pokmart.layout_items.PokemonListViewItem;
import com.mobdev.pxl.pokmart.utilities.HttpResponseLoader;
import com.mobdev.pxl.pokmart.utilities.RecommendedUrlGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Shop_Main_Screen extends AppCompatActivity {

    List<PokemonListViewItem> itemsList = new ArrayList<PokemonListViewItem>();
    PokemonListViewAdapter adapter;
    ListView recommendedListView;
    DrawerLayout mainScreenDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__main__screen);

        mainScreenDrawer = findViewById(R.id.mainScreenDrawer);
        Toolbar toolbar = findViewById(R.id.mainScreenToolbar);
        setSupportActionBar(toolbar);

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

    public class AddItemsListTask extends AsyncTask<Void, Void, List<PokemonListViewItem>> {

        @Override
        protected List<PokemonListViewItem> doInBackground(Void... voids) {
            try {
                List<PokemonListViewItem> returnList = new ArrayList<PokemonListViewItem>();
                for (int x = 0; x < 3; x++) {
                    PokemonListViewItem item = new PokemonListViewItem(RecommendedUrlGenerator.GenerateUrl());
                    returnList.add(item);
                }

                return returnList;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<PokemonListViewItem> items) {
            itemsList = new ArrayList<>(items);
            recommendedListView = (ListView) findViewById(R.id.recommendedList);
            adapter.addAll(itemsList);
            recommendedListView.setAdapter(adapter);
        }
    }
}
