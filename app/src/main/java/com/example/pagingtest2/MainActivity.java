package com.example.pagingtest2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pagingtest2.pokemon.PokemonAdapter;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_users);
        final PokemonAdapter adapter = new PokemonAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.getPokemons.observe(this, adapter::submitList);
    }
}
