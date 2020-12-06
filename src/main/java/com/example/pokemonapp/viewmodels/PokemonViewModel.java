package com.example.pokemonapp.viewmodels;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokemonapp.pojo.Pokemon;
import com.example.pokemonapp.pojo.PokemonResponse;
import com.example.pokemonapp.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {
   private Repository repository;
    MutableLiveData<ArrayList<Pokemon>> listMutableLiveData = new MutableLiveData<>();
    LiveData<List<Pokemon>> listLiveData = null;

    public LiveData<List<Pokemon>> getListLiveData() {
        return listLiveData;
    }

    @ViewModelInject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Pokemon>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void getPokemons(){
        repository.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> list = pokemonResponse.getResults();
                        for (Pokemon pokemon :list){
                            String url = pokemon.getUrl();
                            String[] split = url.split("/");
                            pokemon.setUrl("https://pokeres.bastionbot.org/images/pokemon/"+split[split.length - 1]+".png");

                         }
                    return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result->listMutableLiveData.setValue(result) ,
                        error-> Log.e("ViewModel", "getPokemons: "+ error.getMessage()
                        ));


    }

    public void insertPokemon(Pokemon pokemon) {
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        repository.deletePokemon(pokemonName);
    }

    public void getAllPokemon() {
        listLiveData = repository.getAllPokemon();
    }
}
