package com.example.pagingtest2.pokemon;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import static android.content.ContentValues.TAG;

public class PokemonDataFactory extends DataSource.Factory<Long, Pokemon> {
    @NonNull
    @Override
    public DataSource<Long, Pokemon> create() {
        Log.i(TAG, "Pipa");
        return new PokemonDataSource();
    }
}
