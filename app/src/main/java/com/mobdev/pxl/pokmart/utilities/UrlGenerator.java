package com.mobdev.pxl.pokmart.utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class UrlGenerator {

    private static final Random RANDOM_NUMBER = new Random();
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public static URL GeneratePokemonUrl(int id) {
        try {
            return new URL(BASE_URL + "pokemon/" + id + "/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
