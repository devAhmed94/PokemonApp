package com.example.pokemonapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokemonapp.pojo.Pokemon;

import java.util.List;

@Dao
public interface PokemonDao {

    @Insert
    void insertPokemon(Pokemon pokemon);

    @Query("delete  from fav_table where name =:pokemonName ")
    void deletePokemon(String pokemonName);

    @Query("select * from fav_table ")
    LiveData<List<Pokemon>> getAllPokemon();
}
