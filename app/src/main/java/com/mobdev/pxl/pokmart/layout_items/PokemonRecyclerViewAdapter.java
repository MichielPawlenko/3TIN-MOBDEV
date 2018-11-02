package com.mobdev.pxl.pokmart.layout_items;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobdev.pxl.pokmart.R;
import com.mobdev.pxl.pokmart.utilities.HttpResponseLoader;
import com.mobdev.pxl.pokmart.utilities.JSONPokemonConverter;
import com.mobdev.pxl.pokmart.utilities.UrlGenerator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Pokemon> mPokemonList;
    private onItemClickListener clickListener;

    public PokemonRecyclerViewAdapter(onItemClickListener listener) {
        clickListener = listener;
        mPokemonList = new ArrayList<Pokemon>();
        for(int x = 0; x < 10; x++) {
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
        public pokemonViewHolder(final CardView listViewItem) {
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
        final Pokemon currentListItem = mPokemonList.get(position);

        currentItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(currentListItem);
            }
        });

        TextView pokemonName = currentItem.findViewById(R.id.pokemonListItemText);
        String name = currentListItem.id + "# - " + currentListItem.name.toUpperCase();
        pokemonName.setText(name);

        ImageView pokemonImage = currentItem.findViewById(R.id.pokemonListItemImage);
        pokemonImage.setImageBitmap(currentListItem.sprite);

        TextView pokemonPrice = currentItem.findViewById(R.id.pokemonPrice);
        pokemonPrice.setText(currentListItem.baseXp * 4 + "$");


    }

    @Override
    public int getItemCount() {
        return mPokemonList.size();
    }

    public interface onItemClickListener {
        void onItemClick(Pokemon item);
    }
}
