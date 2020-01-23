package com.example.pagingtest2.pokemon;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.pagingtest2.FetchDataStatus;
import com.example.pagingtest2.RetrofitManager;

import java.util.List;

import io.reactivex.subjects.ReplaySubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.pagingtest2.FetchDataStatus.LOADED_SUCCESSFULLY;
import static com.example.pagingtest2.FetchDataStatus.LOADING;
import static com.example.pagingtest2.FetchDataStatus.Status.FAILED;
import static java8.util.stream.StreamSupport.stream;

public class NetworkPokemonDataSource extends PageKeyedDataSource<Long, Pokemon> {
    private final static long FIRST_PAGE = 1;
    public final static int LIMIT = 20;
    public MutableLiveData<FetchDataStatus> fetchDataStatus = new MutableLiveData<>();
    public final ReplaySubject<Pokemon> pokemonObservable = ReplaySubject.create();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Pokemon> callback) {
        fetchDataStatus.postValue(LOADING);
        PokemonService service = RetrofitManager.buildService(PokemonService.class);
        Call<PokemonResponse> call = service.getPokemons(0, LIMIT);
        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                List<Pokemon> pokemons = response.body().pokemons;
                stream(pokemons).forEach(pokemonObservable::onNext);
                callback.onResult(pokemons, null, FIRST_PAGE + 1);
                fetchDataStatus.postValue(LOADED_SUCCESSFULLY);
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                fetchDataStatus.postValue(new FetchDataStatus(FAILED, t.getMessage()));
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Pokemon> callback) {
        fetchDataStatus.postValue(LOADING);
        PokemonService service = RetrofitManager.buildService(PokemonService.class);
        Call<PokemonResponse> call = service.getPokemons(params.key * 20, LIMIT);
        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.body() != null) {
                    List<Pokemon> pokemons = response.body().pokemons;
                    if (pokemons != null) {
                        long key;
                        if (params.key > 1) {
                            key = params.key - 1;
                        } else {
                            key = 0;
                        }
                        callback.onResult(pokemons, key);
                        stream(pokemons).forEach(pokemonObservable::onNext);
                        fetchDataStatus.postValue(LOADED_SUCCESSFULLY);
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.i(TAG, "Failure");
                fetchDataStatus.postValue(new FetchDataStatus(FAILED, t.getMessage()));
            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Pokemon> callback) {
        fetchDataStatus.postValue(LOADING);
        PokemonService service = RetrofitManager.buildService(PokemonService.class);
        Call<PokemonResponse> call = service.getPokemons(params.key * 20, LIMIT);

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.body() != null) {
                    List<Pokemon> pokemons = response.body().pokemons;
                    callback.onResult(pokemons, params.key + 1);
                    stream(pokemons).forEach(pokemonObservable::onNext);
                    fetchDataStatus.postValue(LOADED_SUCCESSFULLY);
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.i(TAG, "Failure");
                fetchDataStatus.postValue(new FetchDataStatus(FAILED, t.getMessage()));
            }
        });

    }
}
