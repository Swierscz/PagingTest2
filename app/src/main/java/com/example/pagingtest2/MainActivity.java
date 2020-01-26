package com.example.pagingtest2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pagingtest2.pokemon.PokemonAdapter;
import com.github.ybq.android.spinkit.SpinKitView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "elo";
    SwipeRefreshLayout swipeRefreshLayout;
    ViewModel viewModel;
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int offset = SharefPrefUtil.getPage(getBaseContext());
        NetworkCache.offset = offset == -1 ? 0 : offset;

        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        spinKitView = findViewById(R.id.spin_kit);
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

        viewModel.offsetLiveData.observe(this, offsetValue -> SharefPrefUtil.savePage(getBaseContext(), offsetValue));

        viewModel.isLoadingVisible.observe(this, isVisible -> spinKitView.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharefPrefUtil.savePage(getBaseContext(), NetworkCache.offset);
    }
}
