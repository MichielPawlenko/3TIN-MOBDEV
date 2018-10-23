package com.mobdev.pxl.pokmart.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;

import java.util.List;

@Dao
public interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    List<Pokemon> getAll();

    @Query("SELECT * FROM pokemon WHERE id = :id LIMIT 1")
    Pokemon getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Pokemon pokemon);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Pokemon... pokemons);

    @Delete
    void delete(Pokemon pokemon);
}
