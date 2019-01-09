package com.example.nfatma.dispatch;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class RowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewClickListener mListener;

    RowViewHolder(View v, RecyclerViewClickListener listener) {
        super(v);
        mListener = listener;
        v.setOnClickListener(this);
    }

}
