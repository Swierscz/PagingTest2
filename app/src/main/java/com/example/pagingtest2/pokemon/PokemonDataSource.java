package com.example.pagingtest2.pokemon;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.pagingtest2.RetrofitManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PokemonDataSource extends PageKeyedDataSource<Long, Pokemon> {
    private final static long FIRST_PAGE = 1;
    public final static int LIMIT = 20;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Pokemon> callback) {
        PokemonService service = RetrofitManager.buildService(PokemonService.class);
        Call<PokemonResponse> call = service.getPokemons(0, LIMIT);
        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                List<Pokemon> pokemons = response.body().pokemons;
                callback.onResult(pokemons, null, FIRST_PAGE + 1);
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.i(TAG, "Failure");
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Pokemon> callback) {
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
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.i(TAG, "Failure");
            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Pokemon> callback) {
        PokemonService service = RetrofitManager.buildService(PokemonService.class);
        Call<PokemonResponse> call = service.getPokemons(params.key * 20, LIMIT);

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.body() != null) {
                    callback.onResult(response.body().pokemons, params.key + 1);
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.i(TAG, "Failure");
            }
        });

    }
}
