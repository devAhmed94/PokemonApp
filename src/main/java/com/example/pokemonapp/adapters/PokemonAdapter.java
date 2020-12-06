package com.example.pokemonapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokemonapp.R;
import com.example.pokemonapp.pojo.Pokemon;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    ArrayList<Pokemon> pokemonArrayList =new ArrayList<>();
    Context context;



    public PokemonAdapter(Context context) {
    this.context =context;

    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.textView.setText(pokemonArrayList.get(position).getName());
        Glide.with(context)
                .load(pokemonArrayList.get(position).getUrl())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return pokemonArrayList.size();
    }

    public ArrayList<Pokemon> getPokemonArrayList() {
        return pokemonArrayList;
    }

    public void setPokemonArrayList(ArrayList<Pokemon> PokemonArrayList) {
        this.pokemonArrayList = PokemonArrayList;
        notifyDataSetChanged();
    }

    public Pokemon getPokeWithPos(int position){
        return pokemonArrayList.get(position);
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            textView =itemView.findViewById(R.id.item_text);


        }
    }
}
