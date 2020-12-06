package com.example.pokemonapp.repository;

import androidx.lifecycle.LiveData;

import com.example.pokemonapp.db.PokemonDB;
import com.example.pokemonapp.db.PokemonDao;
import com.example.pokemonapp.network.PokemonApiServices;
import com.example.pokemonapp.pojo.Pokemon;
import com.example.pokemonapp.pojo.PokemonResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    PokemonApiServices pokemonApiServices;
    PokemonDao pokemonDao;

    @Inject
    public Repository(PokemonApiServices pokemonApiServices, PokemonDao pokemonDao) {
        this.pokemonApiServices = pokemonApiServices;
        this.pokemonDao = pokemonDao;
    }


    public Observable<PokemonResponse> getPokemons() {
        return pokemonApiServices.getPokemons();
    }

    public void insertPokemon(Pokemon pokemon) {
        pokemonDao.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        pokemonDao.deletePokemon(pokemonName);
    }

    public LiveData<List<Pokemon>> getAllPokemon() {
        return pokemonDao.getAllPokemon();
    }

}
