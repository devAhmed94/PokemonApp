package com.example.pokemonapp.di;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pokemonapp.db.PokemonDB;
import com.example.pokemonapp.db.PokemonDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {
    @Provides
    @Singleton
    public static PokemonDB providePokemonDB(Application  application){
        return Room.databaseBuilder(application.getBaseContext()
                ,PokemonDB.class,"fav_table")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static PokemonDao providePokemonDao(PokemonDB pokemonDB){
        return pokemonDB.pokemonDao();
    }

}
