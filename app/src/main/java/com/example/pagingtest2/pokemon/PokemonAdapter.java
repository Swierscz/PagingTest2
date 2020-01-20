package com.example.pagingtest2.pokemon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagingtest2.R;
import com.example.pagingtest2.user.User;

public class PokemonAdapter extends PagedListAdapter<Pokemon, PokemonAdapter.ViewHolder> {


    public PokemonAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PokemonAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.user_row, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.ViewHolder holder, int position) {
        if(getItem(position) != null){
            holder.name.setText(getItem(position).getName());
            holder.lastname.setText(getItem(position).getUrl());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView lastname;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            lastname = itemView.findViewById(R.id.lastname);
        }
    }

    private static DiffUtil.ItemCallback<Pokemon> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Pokemon>() {
                @Override
                public boolean areItemsTheSame(@NonNull Pokemon oldItem, @NonNull Pokemon newItem) {
                    return oldItem.getUrl().equals(newItem.getUrl());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Pokemon oldItem, @NonNull Pokemon newItem) {
                    return oldItem.equals(newItem);
                }
            };

}