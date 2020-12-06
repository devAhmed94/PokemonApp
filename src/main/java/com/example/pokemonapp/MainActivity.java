package com.example.pokemonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pokemonapp.adapters.PokemonAdapter;
import com.example.pokemonapp.pojo.Pokemon;
import com.example.pokemonapp.viewmodels.PokemonViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    PokemonViewModel pokemonViewModel;
    PokemonAdapter pokemonAdapter;
    RecyclerView recyclerView;
    Button btn_gotoFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_gotoFav = findViewById(R.id.btn_goToFav);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        pokemonAdapter = new PokemonAdapter(MainActivity.this);
        recyclerView.setAdapter(pokemonAdapter);
        setupSwipe();

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        pokemonViewModel.getPokemons();
        pokemonViewModel.getListMutableLiveData().observe(this, new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                pokemonAdapter.setPokemonArrayList(pokemons);
                pokemonAdapter.notifyDataSetChanged();
            }
        });
        btn_gotoFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FavActivity.class));
            }
        });
    }


    private void setupSwipe(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int pos = viewHolder.getAdapterPosition();
                Pokemon swipePoke = pokemonAdapter.getPokeWithPos(pos);
                pokemonViewModel.insertPokemon(swipePoke);
                pokemonAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "insert done", Toast.LENGTH_SHORT).show();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}