package com.mobdev.pxl.pokmart;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    TextView recommendedView;
    List<PokemonListViewItem> itemsList = new ArrayList<PokemonListViewItem>();
    PokemonListViewAdapter adapter;
    ListView recommendedListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__main__screen);

        /*
        recommendedView = (TextView) findViewById(R.id.recommendedList);
        AddItemsTask itemAdder = new AddItemsTask();
        itemAdder.execute();
        */
        adapter = new PokemonListViewAdapter(this, R.layout.pokemon_list_item, itemsList);
        new AddItemsListTask().execute();

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
    public class AddItemsTask extends AsyncTask<Void, Void, String[]>
    {
        @Override
        protected String[] doInBackground(Void... voids) {
            try {
                String[] result = new String[3];
                for(int x = 0; x < 3; x++) {
                    result[x] = HttpResponseLoader.GetResponse(RecommendedUrlGenerator.GenerateUrl());
                    }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] itemArray) {
            try {
                for(int x = 0; x < 3; x++) {
                    JSONObject jsonData = new JSONObject(itemArray[x]);
                    recommendedView.append(jsonData.getString("name") + "\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
