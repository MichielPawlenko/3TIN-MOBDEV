package com.mobdev.pxl.pokmart.data;

import android.content.Context;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;

import java.util.List;

public class PokemonRepository {
    private AppDatabase database;
    private PokemonDao pokemonDao;

    public PokemonRepository(Context context) {
        database = AppDatabase.getDatabase(context);
        pokemonDao = database.pokemonDao();
    }

    public int getSize() {
        return pokemonDao.getSize();
    }

    public List<Pokemon> getAll() {
        return pokemonDao.getAll();
    }

    public List<Pokemon> getPokemonByName(String name) {
        return pokemonDao.getPokemonByName(name.toUpperCase());
    }

    public void addPokemon(Pokemon pokemon) {
        pokemonDao.insert(pokemon);
    }

    public Pokemon getPokemonById(int id) {
        return pokemonDao.getById(id);
    }

    public List<Pokemon> getGeneration1Pokemon() { return pokemonDao.getGeneration1Pokemon(); }
    public List<Pokemon> getGeneration2Pokemon() { return pokemonDao.getGeneration2Pokemon(); }
    public List<Pokemon> getGeneration3Pokemon() { return pokemonDao.getGeneration3Pokemon(); }
}
