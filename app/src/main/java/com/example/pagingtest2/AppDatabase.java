package com.example.pagingtest2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.pagingtest2.pokemon.Pokemon;

@Database(entities = {Pokemon.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PokemonDao pokemonDao();
}
