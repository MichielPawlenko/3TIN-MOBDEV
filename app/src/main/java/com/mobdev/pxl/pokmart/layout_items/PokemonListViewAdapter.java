package com.mobdev.pxl.pokmart.layout_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobdev.pxl.pokmart.R;
import com.mobdev.pxl.pokmart.utilities.UrlBitmapLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PokemonListViewAdapter extends ArrayAdapter<Pokemon> {

    private int resourceLayout;
    private Context mContext;

    public PokemonListViewAdapter(Context context, int resource, List<Pokemon> items) {
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

        Pokemon currentItem = getItem(position);

        ImageView image = itemView.findViewById(R.id.pokemonListItemImage);
        try {
            image.setImageBitmap(UrlBitmapLoader.LoadBitmapFromUrl(new URL(currentItem.sprite)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        TextView name = itemView.findViewById(R.id.pokemonListItemText);
        name.setText(currentItem.name);

        return itemView;
    }
}

