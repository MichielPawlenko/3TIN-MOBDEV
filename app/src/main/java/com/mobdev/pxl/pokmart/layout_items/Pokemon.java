package com.mobdev.pxl.pokmart.layout_items;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Bitmap;

import com.mobdev.pxl.pokmart.data.ArrayListConverter;

import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Pokemon implements Serializable {
    @PrimaryKey
    public int id;

    public String name;

    @TypeConverters(ArrayListConverter.class)
    public ArrayList<String> types;

    @TypeConverters(ArrayListConverter.class)
    public ArrayList<String> abilities;
    public int baseXp;
    public double height;
    public double weight;
    public String sprite;

    public int getCost() {
        return baseXp * 4;
    }
}
