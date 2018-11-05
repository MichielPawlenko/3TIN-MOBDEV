package com.mobdev.pxl.pokmart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mobdev.pxl.pokmart.layout_items.Pokemon;
import com.mobdev.pxl.pokmart.layout_items.PokemonCartViewAdapter;
import com.mobdev.pxl.pokmart.layout_items.PokemonRecyclerViewAdapter;
import com.mobdev.pxl.pokmart.utilities.ShoppingCartHelper;

import java.util.List;

public class Shop_Cart_Screen extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private TextView totalTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__cart__screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);

        totalTextView = (TextView)findViewById(R.id.cartTotal);
        mRecyclerView = (RecyclerView)findViewById(R.id.cartList);
        totalTextView = (TextView)findViewById(R.id.cartTotal);
        totalTextView.setText("" + ShoppingCartHelper.getCost() + "$");

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PokemonCartViewAdapter(ShoppingCartHelper.getCart());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void insertOrder(View view) {
        Intent intent = new Intent(this, Shop_Checkout_Screen.class);
        startActivity(intent);
    }
}