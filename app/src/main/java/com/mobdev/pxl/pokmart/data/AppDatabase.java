package com.mobdev.pxl.pokmart.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;

@Database(entities = {Pokemon.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PokemonDao pokemonDao();
}
