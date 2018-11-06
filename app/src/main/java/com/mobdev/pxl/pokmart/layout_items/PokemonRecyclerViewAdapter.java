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
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Pokemon> mPokemonList;
    private onItemClickListener clickListener;

    public PokemonRecyclerViewAdapter(onItemClickListener listener, List<Pokemon> itemList) {
        clickListener = listener;
        mPokemonList = itemList;
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
        Picasso.get().load(currentListItem.sprite).into(pokemonImage);


        TextView pokemonPrice = currentItem.findViewById(R.id.pokemonPrice);
        pokemonPrice.setText(currentListItem.getCost() + "$");

    }

    @Override
    public int getItemCount() {
        return mPokemonList.size();
    }

    public interface onItemClickListener {
        void onItemClick(Pokemon item);
    }
}
