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
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavActivity extends AppCompatActivity {
    PokemonViewModel pokemonViewModel;
    PokemonAdapter pokemonAdapter;
    RecyclerView recyclerView;
    Button btn_gotoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        btn_gotoHome = findViewById(R.id.btn_goToHome);
        recyclerView = findViewById(R.id.fav_recycle);
        recyclerView.setHasFixedSize(true);
        pokemonAdapter = new PokemonAdapter(FavActivity.this);
        recyclerView.setAdapter(pokemonAdapter);
        setupSwipe();
        btn_gotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavActivity.this,MainActivity.class));
            }
        });

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        pokemonViewModel.getAllPokemon();
        pokemonViewModel.getListLiveData().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                ArrayList<Pokemon> arrayList = new ArrayList<>();
                arrayList.addAll(pokemons);
                pokemonAdapter.setPokemonArrayList(arrayList);
                pokemonAdapter.notifyDataSetChanged();
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
                pokemonViewModel.deletePokemon(swipePoke.getName());
                pokemonAdapter.notifyDataSetChanged();
                Toast.makeText(FavActivity.this, "delete done", Toast.LENGTH_SHORT).show();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}