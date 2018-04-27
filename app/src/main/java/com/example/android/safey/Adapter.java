package com.example.android.safey;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

class Adapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    ArrayList<Report> reportArrayList;
    Toast mToast;
    int val = 0;

    public Adapter(Context c, ArrayList<Report> list) {
        this.context = c;
        this.reportArrayList = list;
    }
    // Create view holder for Recyclerview
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_model,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);

        return viewHolder;
    }

    //Bind views (individual reports) to view holder
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), String.valueOf(holder.likes), Toast.LENGTH_SHORT).show();
                if(val == 0) {
                    holder.imageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                    val = 1;
                    holder.likes = holder.likes + 1;
                    Log.d("Likes: ", String.valueOf(holder.likes));
                } else {
                    holder.imageButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    val = 0;
                }
            }
        });

        holder.comment.setText(reportArrayList.get(position).getComment());

        holder.setItemClickListener(new ItemClick() {
            @Override
            public void onItemClick(int pos) {
                if(mToast != null) {
                    mToast.cancel();
                }
                mToast.makeText(context, reportArrayList.get(pos).getDate(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return reportArrayList.size();
    }
}
