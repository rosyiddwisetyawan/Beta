package com.media.beta.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.media.beta.R;
import com.media.beta.data.Politik;
import com.media.beta.galery.Galery;

import java.util.List;

/**
 * Created by Team 2 on 8/7/2018.
 */

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    private Context context;
    private List<Politik> list;

    public HorizontalAdapter(Context context, List<Politik> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_horizontal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Politik politik = list.get(position);
        Glide.with(context)
                .asBitmap()
                .load(politik.getImage())
                .into(holder.image);

        holder.title.setText(politik.getTitle());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, Galery.class);
                intent.putExtra("description", politik.getDescription());
                intent.putExtra("image", politik.getImage());
                intent.putExtra("title", politik.getTitle());
                intent.putExtra("sumber", politik.getSumber());
                intent.putExtra("link", politik.getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        LinearLayout parentLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.idImageTop);
            title = itemView.findViewById(R.id.idTitleTop);
            parentLayout = itemView.findViewById(R.id.parent_layout_horizontal);
        }
    }

}
