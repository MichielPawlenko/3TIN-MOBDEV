package com.mobdev.pxl.pokmart.utilities;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;

import java.util.List;
import java.util.Vector;

public class ShoppingCartHelper {
    private static List<Pokemon> cart;

    public static void createCart() {
        cart = new Vector<Pokemon>();
    }

    public static List<Pokemon> getCart() {
        if(cart == null) {
            createCart();
        }

        return cart;
    }

    public static void addPokemon(Pokemon pokemon) {
        if(cart == null) {
            createCart();
        }

        if(cart.contains(pokemon)) {
            return;
        }

        cart.add(pokemon);
    }

    public static int getCost() {
        if(cart == null) {
            return 0;
        }

        int cost = 0;
        for (Pokemon pok : cart) {
            cost += pok.getCost();
        }

        return cost;
    }
}
