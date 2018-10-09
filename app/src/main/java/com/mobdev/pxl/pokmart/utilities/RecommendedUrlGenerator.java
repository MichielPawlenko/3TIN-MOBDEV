package com.mobdev.pxl.pokmart.utilities;

import com.mobdev.pxl.pokmart.Shop_Main_Screen;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

public class RecommendedUrlGenerator {

    private static final Random RANDOM_NUMBER = new Random();

    public static URL GenerateUrl() {
        try {
            return new URL("https://pokeapi.co/api/v2/pokemon-form/" + RANDOM_NUMBER.nextInt(750) + "/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
