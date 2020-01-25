package com.example.pagingtest2;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.pagingtest2.pokemon.Pokemon;

public class DbRepo {
    public PokemonDao pokemonDao;
    public LiveData<PagedList<Pokemon>> pokemons;

    public DbRepo(PagedList.Config config, PagedList.BoundaryCallback boundaryCallback) {
        pokemonDao = RepositoryProvider.getInstance().getAppDatabase().pokemonDao();
        LivePagedListBuilder<Integer, Pokemon> livePagedListBuilderDb = new LivePagedListBuilder<>(pokemonDao.selectPaged(), config);
        livePagedListBuilderDb.setBoundaryCallback(boundaryCallback);
        pokemons = livePagedListBuilderDb.build();
    }
}
