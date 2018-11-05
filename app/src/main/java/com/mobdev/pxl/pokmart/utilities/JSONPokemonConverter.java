package com.mobdev.pxl.pokmart.utilities;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class JSONPokemonConverter {

    public static Pokemon GeneratePokemon(String jsonString) {
        try {
            Pokemon pokemon = new Pokemon();
            pokemon.types = new ArrayList<String>();
            pokemon.abilities = new ArrayList<String>();
            JSONObject jsonItem = new JSONObject(jsonString);
            JSONObject spriteObject = jsonItem.getJSONObject("sprites");
            JSONArray typeArray = jsonItem.getJSONArray("types");
            JSONArray abilityArray = jsonItem.getJSONArray("abilities");
            pokemon.name = jsonItem.getString("name").toUpperCase();
            pokemon.baseXp = jsonItem.getInt("base_experience");
            pokemon.height = jsonItem.getDouble("height");
            pokemon.weight = jsonItem.getDouble("weight");
            pokemon.id = jsonItem.getInt("id");
            pokemon.sprite = spriteObject.getString("front_default");
            for(int x = 0; x < typeArray.length(); x++) {
                JSONObject typeObject = typeArray.getJSONObject(x);
                typeObject = typeObject.getJSONObject("type");
                pokemon.types.add(typeObject.getString("name"));
            }
            for(int x = 0; x < abilityArray.length(); x++) {
                JSONObject abilityObject = abilityArray.getJSONObject(x);
                abilityObject = abilityObject.getJSONObject("ability");
                pokemon.abilities.add(abilityObject.getString("name"));
            }
            return pokemon;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
