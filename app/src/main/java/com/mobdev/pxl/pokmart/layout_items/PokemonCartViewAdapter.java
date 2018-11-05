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
import com.mobdev.pxl.pokmart.utilities.ShoppingCartHelper;
import com.mobdev.pxl.pokmart.utilities.UrlBitmapLoader;
import com.mobdev.pxl.pokmart.utilities.UrlGenerator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PokemonCartViewAdapter extends RecyclerView.Adapter {
    private List<Pokemon> pokemonCartList;

    public PokemonCartViewAdapter(List<Pokemon> itemList) {
        pokemonCartList = itemList;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView view = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cart_list_item, viewGroup, false);
        PokemonCartViewAdapter.pokemonViewHolder viewHolder = new PokemonCartViewAdapter.pokemonViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PokemonCartViewAdapter.pokemonViewHolder pokemonViewHolder =
                (PokemonCartViewAdapter.pokemonViewHolder) viewHolder;

        CardView currentItem = pokemonViewHolder.mListViewItem;
        final Pokemon currentListItem = pokemonCartList.get(i);


        TextView pokemonName = currentItem.findViewById(R.id.pokemonListItemText);
        String name = currentListItem.id + "# - " + currentListItem.name.toUpperCase();
        pokemonName.setText(name);

        ImageView pokemonImage = currentItem.findViewById(R.id.pokemonListItemImage);

        TextView pokemonPrice = currentItem.findViewById(R.id.pokemonPrice);
        pokemonPrice.setText(currentListItem.getCost() + "$");
    }

    @Override
    public int getItemCount() {
        return pokemonCartList.size();
    }
}
