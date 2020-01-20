package com.example.pagingtest2.pokemon;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class PokemonDataFactory extends DataSource.Factory<Long, Pokemon> {
    @NonNull
    @Override
    public DataSource<Long, Pokemon> create() {
        return new PokemonDataSource();
    }
}
