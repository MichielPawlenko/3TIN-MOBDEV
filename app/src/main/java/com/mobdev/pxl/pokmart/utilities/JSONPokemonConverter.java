package com.mobdev.pxl.pokmart.utilities;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class JSONPokemonConverter {

    public static Pokemon GeneratePokemon(String jsonString) {
        try {
            Pokemon pokemon = new Pokemon();
            JSONObject jsonItem = new JSONObject(jsonString);
            JSONObject spriteObject = jsonItem.getJSONObject("sprites");
            String spriteString = spriteObject.getString("front_default");
            pokemon.name = jsonItem.getString("name").toUpperCase();
            pokemon.baseXp = jsonItem.getInt("base_experience");
            pokemon.id = jsonItem.getInt("id");
            pokemon.sprite = UrlBitmapLoader.LoadBitmapFromUrl(new URL(spriteString));
            return pokemon;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
