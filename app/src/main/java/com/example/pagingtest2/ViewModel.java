package com.example.pagingtest2;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.PagedList;

import com.example.pagingtest2.pokemon.NetworkPokemonDataSource;
import com.example.pagingtest2.pokemon.Pokemon;

import java.util.List;

import static java8.util.stream.StreamSupport.stream;

public class ViewModel extends androidx.lifecycle.ViewModel {
    public final static String TAG = ViewModel.class.getSimpleName();
    MediatorLiveData<PagedList<Pokemon>> getPokemons = new MediatorLiveData<>();
    NetworkDirectRepo networkDirectRepo;
    NetworkRepo networkRepo;
    DbRepo dbRepo;


    PagedList.BoundaryCallback boundaryCallback;

    @SuppressLint("CheckResult")
    public ViewModel() {


        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(NetworkPokemonDataSource.LIMIT)
                .setPrefetchDistance(10)
                .build();

        boundaryCallback = getBoundaryCallback();

        dbRepo = new DbRepo(config, boundaryCallback);
        getPokemons.addSource(dbRepo.pokemons, getPokemons::setValue);


        networkDirectRepo = new NetworkDirectRepo(new NetworkDirectRepo.Callback() {
            @Override
            public void onSuccess(List<Pokemon> pokemons) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        stream(pokemons).forEach(pokemon -> {
                            Log.i(TAG, "Pokemon: " + pokemon.getName() + " inserted");
                            dbRepo.pokemonDao.insert(pokemon);
                        });
                    }
                });
            }

            @Override
            public void onError(String errorMsg) {
                Log.i(TAG, "Error during fetching network data");
            }
        });

    }

    void invalidatePokemons() {
        getPokemons.getValue().getDataSource().invalidate();
    }

    void requestData() {
        if (!NetworkCache.isLoading) {
            networkDirectRepo.requestData(NetworkCache.offset++, 20);
            NetworkCache.isLoading = true;
        }
    }

    PagedList.BoundaryCallback<Pokemon> getBoundaryCallback() {
        return new PagedList.BoundaryCallback<Pokemon>() {
            @Override
            public void onZeroItemsLoaded() {
                super.onZeroItemsLoaded();
                requestData();
                Log.i(TAG, "Zero items loaded");
            }


            @Override
            public void onItemAtEndLoaded(@NonNull Pokemon itemAtEnd) {
                Log.i(TAG, "Items at end loaded");
                requestData();
                super.onItemAtEndLoaded(itemAtEnd);
            }
        };
    }
}