package com.example.pagingtest2;

import android.util.Log;

import com.example.pagingtest2.pokemon.Pokemon;
import com.example.pagingtest2.pokemon.PokemonResponse;
import com.example.pagingtest2.pokemon.PokemonService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.pagingtest2.pokemon.NetworkPokemonDataSource.LIMIT;

public class NetworkDirectRepo {
    public final static String TAG = NetworkDirectRepo.class.getSimpleName();
    private PokemonService service = RetrofitManager.buildService(PokemonService.class);
    private Callback callback;

    interface Callback {
        void onSuccess(List<Pokemon> pokemons);

        void onError(String errorMsg);
    }

    NetworkDirectRepo(Callback callback) {
        this.callback = callback;
    }

    void requestData(int offset, int limit) {
        service.getPokemons(offset * limit, limit).enqueue(new retrofit2.Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                Log.i(TAG, "Request succed");
                callback.onSuccess(response.body() != null ? response.body().getPokemons() : new ArrayList<>());
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.i(TAG, "Request failed");
                callback.onError(t.getMessage());
            }
        });
    }


}
