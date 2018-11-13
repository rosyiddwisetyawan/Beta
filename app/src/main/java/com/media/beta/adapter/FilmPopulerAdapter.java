package com.media.beta.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.media.beta.R;
import com.media.beta.data.Film;
import com.media.beta.data.Politik;
import com.media.beta.galery.Galery;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * Created by Team 2 on 10/31/2018.
 */

public class FilmPopulerAdapter extends RecyclerView.Adapter<FilmPopulerAdapter.ViewHolder> {

    private Context context;
    private List<Film> list;

    public FilmPopulerAdapter(Context context, List<Film> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_filmpopuler, parent, false);
        FilmPopulerAdapter.ViewHolder holder = new FilmPopulerAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Film film = list.get(position);
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+film.getImage())
                .apply(centerCropTransform()
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(holder.image);

        Glide.with(context)
                .load("http://www.pngpix.com/wp-content/uploads/2016/11/PNGPIX-COM-Star-Vector-PNG-Transparent-Image-500x481.png")
                .apply(centerCropTransform()
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(holder.imagerate);

        holder.title.setText(film.getTitle());
        holder.rate.setText(film.getVote());
        holder.date.setText("release: "+film.getRelease());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, Galery.class);
                intent.putExtra("description", film.getDescription());
                intent.putExtra("image", film.getImage());
                intent.putExtra("title", film.getTitle());
                intent.putExtra("release", film.getRelease());
                intent.putExtra("vote", film.getVote());
                intent.putExtra("populer", film.getPopular());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        ImageView imagerate;
        TextView title;
        TextView rate;
        TextView date;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.idDate);
            rate = itemView.findViewById(R.id.idrate);
            imagerate = itemView.findViewById(R.id.image_rate);
            image = itemView.findViewById(R.id.idImageTop);
            title = itemView.findViewById(R.id.idTitleTop);
            parentLayout = itemView.findViewById(R.id.parent_layout_filmpopuler);
        }
    }
}
