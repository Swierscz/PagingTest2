package com.example.pagingtest2;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.pagingtest2.pokemon.Pokemon;
import com.example.pagingtest2.pokemon.PokemonDataFactory;
import com.example.pagingtest2.pokemon.PokemonDataSource;

import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;

public class ViewModel extends androidx.lifecycle.ViewModel {
    LiveData<PagedList<Pokemon>> getPokemons;
    PokemonDataFactory pokemonDataFactory;

    public ViewModel() {
        pokemonDataFactory = new PokemonDataFactory();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PokemonDataSource.LIMIT)
                .setPrefetchDistance(1)
                .build();

        getPokemons = new LivePagedListBuilder<>(pokemonDataFactory, config)
                .build();
    }

    void invalidatePokemons() {
        getPokemons.getValue().getDataSource().invalidate();
    }
}
