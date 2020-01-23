package com.example.pagingtest2.pokemon;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class PokemonNetworkDataFactory extends DataSource.Factory<Long, Pokemon> {
    public NetworkPokemonDataSource source;

    public PokemonNetworkDataFactory() {
        source = new NetworkPokemonDataSource();
    }

    @NonNull
    @Override
    public NetworkPokemonDataSource create() {
        return source;
    }
}
