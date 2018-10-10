package com.mobdev.pxl.pokmart.layout_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobdev.pxl.pokmart.R;

import java.util.List;

public class PokemonListViewAdapter extends ArrayAdapter<PokemonListViewItem> {

    private int resourceLayout;
    private Context mContext;

    public PokemonListViewAdapter(Context context, int resource, List<PokemonListViewItem> items) {
        super(context, resource, items);
        mContext = context;
        resourceLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            itemView = inflater.inflate(resourceLayout, null);
        }

        PokemonListViewItem currentItem = getItem(position);

        ImageView image = itemView.findViewById(R.id.pokemonListItemImage);
        image.setImageBitmap(currentItem.image);

        TextView name = itemView.findViewById(R.id.pokemonListItemText);
        name.setText(currentItem.name);

        return itemView;
    }
}

