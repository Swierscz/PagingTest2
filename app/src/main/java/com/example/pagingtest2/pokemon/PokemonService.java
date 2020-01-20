package com.example.pagingtest2.pokemon;

import com.example.pagingtest2.user.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonService {

    @GET("pokemon")
    Call<PokemonResponse> getPokemons(@Query("offset") long offset, @Query("limit") long limit);

}
