package com.example.android.safey;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView comment;
    ItemClick itemClickListener;
    public ViewHolder(View view) {
        super(view);

        comment = (TextView) itemView.findViewById(R.id.comment);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(getLayoutPosition());
    }

    public void setItemClickListener(ItemClick ic)
    {
        this.itemClickListener = ic;
    }
}
