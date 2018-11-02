package com.mobdev.pxl.pokmart;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mobdev.pxl.pokmart.data.AppDatabase;
import com.mobdev.pxl.pokmart.layout_items.Pokemon;

import java.util.List;

public class Shop_Start_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__start__screen);

        //getApplicationContext().deleteDatabase("pokemondb"); // TODO: DEBUG

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "pokemondb").build();

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, Shop_Main_Screen.class);
        startActivity(intent);
    }
}
