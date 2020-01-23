package com.example.pagingtest2;


import androidx.paging.DataSource;
import androidx.room.Query;

import com.example.pagingtest2.pokemon.Pokemon;

public interface PersonDao {

    @Query("Select * from pokemons order by url desc")
    DataSource.Factory<Long, Pokemon> selectPaged();
}
