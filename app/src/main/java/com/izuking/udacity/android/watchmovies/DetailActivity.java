package com.izuking.udacity.android.watchmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.izuking.udacity.android.watchmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Izuking on 4/14/2017.
 */
public class DetailActivity extends AppCompatActivity {
    private String title, posterImg, synopsis, voteAverage, releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.movie_title))) {
            title = intent.getStringExtra(getString(R.string.movie_title));
            posterImg = intent.getStringExtra(getString(R.string.movie_img));
            releaseDate = intent.getStringExtra(getString(R.string.release_date));
            voteAverage = intent.getStringExtra(getString(R.string.rating));
            synopsis = intent.getStringExtra(getString(R.string.movie_overview));

        }

        ImageView imageView = (ImageView) findViewById(R.id.backdrop);

        Picasso.with(this).load(NetworkUtils.IMGURL + posterImg).placeholder(R.drawable.placeholder).into(imageView);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ((TextView) findViewById(R.id.movie_title)).setText(title);
        ratingBar.setRating(Float.parseFloat(voteAverage));
        ((TextView) findViewById(R.id.release_date)).setText(releaseDate);
        ((TextView) findViewById(R.id.synopsis)).setText(synopsis);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
