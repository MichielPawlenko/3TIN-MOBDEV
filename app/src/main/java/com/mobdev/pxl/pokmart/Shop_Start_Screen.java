package com.mobdev.pxl.pokmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Shop_Start_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__start__screen);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, Shop_Main_Screen.class);
        startActivity(intent);
    }
}
