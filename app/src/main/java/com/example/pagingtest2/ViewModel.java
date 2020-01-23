package com.example.pagingtest2;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.pagingtest2.pokemon.NetworkPokemonDataSource;
import com.example.pagingtest2.pokemon.Pokemon;
import com.example.pagingtest2.pokemon.PokemonNetworkDataFactory;

import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class ViewModel extends androidx.lifecycle.ViewModel {
    MediatorLiveData<PagedList<Pokemon>> getPokemons = new MediatorLiveData<>();
    PokemonNetworkDataFactory pokemonNetworkDataFactory;
    PokemonDao pokemonDao;
    PagedList.BoundaryCallback boundaryCallback;

    LiveData<PagedList<Pokemon>> networkPokemons;
    LiveData<PagedList<Pokemon>> dbPokemons;

    @SuppressLint("CheckResult")
    public ViewModel() {
        pokemonNetworkDataFactory = new PokemonNetworkDataFactory();
        pokemonDao = RepositoryProvider.getInstance().getAppDatabase().pokemonDao();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(NetworkPokemonDataSource.LIMIT)
                .setPrefetchDistance(10)
                .build();

        boundaryCallback = getBoundaryCallback();

        LivePagedListBuilder<Long, Pokemon> livePagedListBuilder = new LivePagedListBuilder<>(pokemonNetworkDataFactory, config);
        livePagedListBuilder.setBoundaryCallback(boundaryCallback);
        networkPokemons = livePagedListBuilder.build();


        LivePagedListBuilder<Long, Pokemon> livePagedListBuilderDb = new LivePagedListBuilder<>(pokemonDao.selectPaged(), config);
        dbPokemons = livePagedListBuilderDb.build();

        getPokemons.addSource(networkPokemons, value ->
                getPokemons.setValue(value));

        pokemonNetworkDataFactory.source.pokemonObservable.observeOn(Schedulers.io())
                .subscribe(pokemon -> {
                    pokemonDao.insert(pokemon);
                });

    }

    void invalidatePokemons() {
        getPokemons.getValue().getDataSource().invalidate();
    }

    PagedList.BoundaryCallback<Pokemon> getBoundaryCallback() {
        return new PagedList.BoundaryCallback<Pokemon>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                Log.i(TAG, "ZERO LOADED");

                getPokemons.addSource(dbPokemons, value -> {
                    getPokemons.setValue(value);
                    getPokemons.removeSource(dbPokemons);
                });
            }
        };
    }
}
