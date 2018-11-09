package com.mobdev.pxl.pokmart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.utilities.ShoppingCartHelper;
import com.squareup.picasso.Picasso;

public class PokemonDetailFragment extends Fragment {

    private Pokemon mPokemon;

    public PokemonDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);
        Bundle arguments = getArguments();

        if(arguments != null) {
            mPokemon = (Pokemon) arguments.getSerializable("pokemon");
            Picasso.get().load(mPokemon.sprite).into((ImageView) view.findViewById(R.id.pokemonImage));
            ((TextView) view.findViewById(R.id.pokemonNameTextView)).setText(mPokemon.name);
            ((TextView) view.findViewById(R.id.pokemonPriceTextView)).setText(mPokemon.getCost() + "$");
            String typeString = "Type: ";
            if (mPokemon.types.size() == 2) {
                typeString = "Types:\n" + mPokemon.types.get(0).toUpperCase() + "\n" + mPokemon.types.get(1).toUpperCase() + "\n";
            } else {
                typeString += mPokemon.types.get(0).toUpperCase();
            }
            ((TextView) view.findViewById(R.id.pokemonType)).setText(typeString);
            String abilityString = "Abilities: \n\n";
            for (String ability : mPokemon.abilities) {
                abilityString += "+" + ability.toUpperCase() + "\n\n";
            }

            ((TextView) view.findViewById(R.id.pokemonAbilities)).setText(abilityString);
            ((TextView) view.findViewById(R.id.pokemonHeight)).setText("Height: " + mPokemon.height / 10 + " m");
            ((TextView) view.findViewById(R.id.pokemonWeight)).setText("Weight: " + mPokemon.weight / 10 + " kg");
            ((Button) view.findViewById(R.id.addToCartButton)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShoppingCartHelper.addPokemon(mPokemon);
                    Toast.makeText(v.getContext(), "Pokemon added to cart.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
}
