package com.example.pagingtest2;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.pagingtest2.pokemon.Pokemon;
import com.example.pagingtest2.pokemon.PokemonDataFactory;
import com.example.pagingtest2.pokemon.PokemonDataSource;

import java.util.concurrent.Executor;

public class ViewModel extends androidx.lifecycle.ViewModel {

    LiveData<PagedList<Pokemon>> getPokemons;

    public ViewModel() {
        PokemonDataFactory pokemonDataFactory = new PokemonDataFactory();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PokemonDataSource.LIMIT)
                .setPrefetchDistance(1)
                .build();

        getPokemons = new LivePagedListBuilder<>(pokemonDataFactory, config)
                .setFetchExecutor(new Executor() {
                    @Override
                    public void execute(Runnable runnable) {
                        
                    }
                })
                .build();
    }
}
