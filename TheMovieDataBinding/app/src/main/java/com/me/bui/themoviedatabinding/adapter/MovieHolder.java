package com.me.bui.themoviedatabinding.adapter;

import android.support.v7.widget.RecyclerView;

import com.me.bui.themoviedatabinding.databinding.ItemListBinding;
import com.me.bui.themoviedatabinding.model.Result;


/**
 * Created by mao.buidinh on 8/8/2017.
 */

public class MovieHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
    private static final String TAG = MovieHolder.class.getSimpleName();
    private ItemListBinding binding;

    public MovieHolder(ItemListBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind (Result result) {
        binding.setMovie(result);
        // set listener ...
        binding.setEvent(new EventHandler(binding.getRoot().getContext()));
        binding.executePendingBindings();
    }
}
