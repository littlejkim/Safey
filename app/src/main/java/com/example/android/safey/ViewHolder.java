package com.example.android.safey;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView comment;
    ItemClick itemClickListener;
    ImageButton imageButton;
    int likes = 0;
    ImageView imageView;
    public ViewHolder(View view) {
        super(view);
        imageView = (ImageView) itemView.findViewById(R.id.img);
        comment = (TextView) itemView.findViewById(R.id.comment);
        imageButton = (ImageButton) itemView.findViewById(R.id.like_button);
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
