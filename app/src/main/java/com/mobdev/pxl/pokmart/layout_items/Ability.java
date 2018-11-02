package com.mobdev.pxl.pokmart.layout_items;

import android.arch.persistence.room.ColumnInfo;

public class Ability {
    @ColumnInfo(name = "ability_name")
    public String name;

    @ColumnInfo(name = "ability_description")
    public String description;
}
