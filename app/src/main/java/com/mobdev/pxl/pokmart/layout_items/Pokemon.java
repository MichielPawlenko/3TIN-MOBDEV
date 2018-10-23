package com.mobdev.pxl.pokmart.layout_items;

import android.graphics.Bitmap;

import com.mobdev.pxl.pokmart.utilities.HttpResponseLoader;
import com.mobdev.pxl.pokmart.utilities.JSONPokemonConverter;
import com.mobdev.pxl.pokmart.utilities.UrlBitmapLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class Pokemon {

    public int id;
    public String name;
    public String[] types;
    public String description;
    public int baseXp;
    public Ability ability;
    public Bitmap sprite;

}
