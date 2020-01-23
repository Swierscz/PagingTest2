package com.example.pagingtest2;


import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pagingtest2.pokemon.Pokemon;

@Dao
public interface PokemonDao {

    @Query("Select * from pokemons order by url desc")
    DataSource.Factory<Integer, Pokemon> selectPaged();

    @Insert
    void insert(Pokemon pokemon);
}
