package com.media.beta.galery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.media.beta.R;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class Galery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery_politik);
        getIncomingIntent();
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("description") && getIntent().hasExtra("image") && getIntent().hasExtra("title")){

            String description = getIntent().getStringExtra("description");
            String image = getIntent().getStringExtra("image");
            String title = getIntent().getStringExtra("title");
            String release = getIntent().getStringExtra("release");
            String vote = getIntent().getStringExtra("vote");
            String populer = getIntent().getStringExtra("populer");
            System.out.print(description+"_"+image+"_"+title);

            setImage(description, image, title, release, vote, populer);
        }
    }

    private void setImage(String mDescription, String mImage, String mTitle, String mRelease, String mVote, String mPopuler){

        TextView title = findViewById(R.id.txtTitle);
        title.setText(mTitle);

        TextView description = findViewById(R.id.description);
        description.setText(mDescription);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+mImage)
                .apply(centerCropTransform()
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(image);

    }
}
