package com.example.pagingtest2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pagingtest2.pokemon.PokemonAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "elo" ;
    SwipeRefreshLayout swipeRefreshLayout;
    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        RepositoryProvider.getInstance().initDatabase(getBaseContext());

        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        swipeRefreshLayout.setOnRefreshListener(() -> viewModel.invalidatePokemons());

        RecyclerView recyclerView = findViewById(R.id.rv_users);
        final PokemonAdapter adapter = new PokemonAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);




        viewModel.getPokemons.observe(this, pagedList -> {
            adapter.submitList(pagedList);
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}
