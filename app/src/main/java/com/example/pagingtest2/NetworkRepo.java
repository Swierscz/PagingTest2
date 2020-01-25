package com.example.pagingtest2;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.pagingtest2.pokemon.Pokemon;
import com.example.pagingtest2.pokemon.PokemonNetworkDataFactory;

public class NetworkRepo {
    public PokemonNetworkDataFactory pokemonDataFactory;
    public LiveData<PagedList<Pokemon>> pokemons;


    public NetworkRepo(PagedList.Config config) {
        pokemonDataFactory = new PokemonNetworkDataFactory();
        LivePagedListBuilder<Long, Pokemon> livePagedListBuilder = new LivePagedListBuilder<>(pokemonDataFactory, config);
        pokemons = livePagedListBuilder.build();
    }
}
