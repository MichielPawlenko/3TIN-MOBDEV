package com.mobdev.pxl.pokmart.layout_items;

import android.graphics.Bitmap;

import com.mobdev.pxl.pokmart.utilities.HttpResponseLoader;
import com.mobdev.pxl.pokmart.utilities.UrlBitmapLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class PokemonListViewItem {

    public String name;
    public Bitmap image;

    public PokemonListViewItem(URL url) {
        try {
            String jsonString = HttpResponseLoader.GetResponse(url);
            JSONObject jsonItem = new JSONObject(jsonString);
            JSONObject spriteObject = jsonItem.getJSONObject("sprites");
            String spriteString = spriteObject.getString("front_default");
            jsonItem = jsonItem.getJSONObject("pokemon");
            name = jsonItem.getString("name").toUpperCase();
            image = UrlBitmapLoader.LoadBitmapFromUrl(new URL(spriteString));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
