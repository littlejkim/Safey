package com.example.android.safey;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

class Adapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    ArrayList<Report> reportArrayList;
    Toast mToast;

    public Adapter(Context c, ArrayList<Report> list) {
        this.context = c;
        this.reportArrayList = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_model,parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.comment.setText(reportArrayList.get(position).getComment());

        holder.setItemClickListener(new ItemClick() {
            @Override
            public void onItemClick(int pos) {
                if(mToast != null) {
                    mToast.cancel();
                }
                mToast.makeText(context, reportArrayList.get(pos).getComment(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return reportArrayList.size();
    }
}
