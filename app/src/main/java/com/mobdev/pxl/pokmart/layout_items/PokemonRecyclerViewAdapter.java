package com.mobdev.pxl.pokmart.layout_items;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobdev.pxl.pokmart.R;
import com.mobdev.pxl.pokmart.utilities.HttpResponseLoader;
import com.mobdev.pxl.pokmart.utilities.JSONPokemonConverter;
import com.mobdev.pxl.pokmart.utilities.UrlGenerator;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Pokemon> mPokemonList;

    public PokemonRecyclerViewAdapter() {
        for(int x = 0; x < 5; x++) {
            URL url = UrlGenerator.GeneratePokemonUrl(x + 1);
            try {
                String response = HttpResponseLoader.GetResponse(url);
                Pokemon item = JSONPokemonConverter.GeneratePokemon(response);
                mPokemonList.add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class pokemonViewHolder extends RecyclerView.ViewHolder {
        private CardView mListViewItem;
        public pokemonViewHolder(CardView listViewItem) {
            super(listViewItem);
            mListViewItem = listViewItem;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_list_item, parent, false);
        pokemonViewHolder viewHolder = new pokemonViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        pokemonViewHolder pokemonViewHolder =
                (PokemonRecyclerViewAdapter.pokemonViewHolder) viewHolder;
        
        CardView currentItem = pokemonViewHolder.mListViewItem;

        TextView pokemonName = currentItem.findViewById(R.id.pokemonListItemText);
        pokemonName.setText(mPokemonList.get(position).name);

        ImageView pokemonImage = currentItem.findViewById(R.id.pokemonListItemImage);
        pokemonImage.setImageBitmap(mPokemonList.get(position).sprite);


    }

    @Override
    public int getItemCount() {
        return mPokemonList.size();
    }
}
