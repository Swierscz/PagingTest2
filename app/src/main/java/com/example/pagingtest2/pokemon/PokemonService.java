package com.example.pagingtest2.pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonService {

    @GET("/api/v2/pokemon")
    Call<PokemonResponse> getPokemons(@Query("offset") long offset, @Query("limit") long limit);

}
