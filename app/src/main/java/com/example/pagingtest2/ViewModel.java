package com.example.pagingtest2;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.example.pagingtest2.pokemon.Pokemon;

import java.util.List;

import static java8.util.stream.StreamSupport.stream;


public class ViewModel extends androidx.lifecycle.ViewModel {
    private final static String TAG = ViewModel.class.getSimpleName();
    private static final int PAGE_SIZE = 20;
    private static final int FETCH_SIZE = 50;

    LiveData<PagedList<Pokemon>> getPokemons;
    private NetworkDirectRepo networkDirectRepo;
    private DbRepo dbRepo;
    MutableLiveData<Boolean> isLoadingVisible = new MutableLiveData<>(false);
    private int offset = NetworkCache.offset;


    @SuppressLint("CheckResult")
    public ViewModel() {


        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build();

        dbRepo = new DbRepo(config, getBoundaryCallback());
        getPokemons = dbRepo.pokemons;

        networkDirectRepo = new NetworkDirectRepo(new NetworkDirectRepo.Callback() {
            @Override
            public void onSuccess(List<Pokemon> pokemons) {
                Thread thread = new Thread(() -> {
                    stream(pokemons).forEach(pokemon -> {
                        dbRepo.pokemonDao.insert(pokemon);
                    });
                    isLoadingVisible.postValue(false);
                    NetworkCache.isLoading = false;
                });
                thread.start();

            }

            @Override
            public void onError(String errorMsg) {
                isLoadingVisible.postValue(false);
                Log.i(TAG, "Error during fetching network data");
            }
        });

    }

    void invalidatePokemons() {
        dbRepo.pokemonDao.nukeTable();
        requestData();
        getPokemons.getValue().getDataSource().invalidate();
        offsetLiveData.postValue(0);
    }


    MutableLiveData<Integer> offsetLiveData = new MutableLiveData<>();

    private void requestData() {
        if (!NetworkCache.isLoading) {
            isLoadingVisible.postValue(true);
            networkDirectRepo.requestData(offset++, FETCH_SIZE);
            offsetLiveData.postValue(offset);
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
                super.onItemAtEndLoaded(itemAtEnd);
                Log.i(TAG, "Items at end loaded");
                requestData();
            }
        };
    }
}