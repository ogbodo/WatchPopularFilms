package com.izuking.udacity.android.watchmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.izuking.udacity.android.watchmovies.utils.JSONObject;
import com.izuking.udacity.android.watchmovies.utils.MoviePreference;
import com.izuking.udacity.android.watchmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickListener {


    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ProgressBar mLoadingIndicator;
    private TextView errMessage;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        recyclerView = (RecyclerView) findViewById(R.id.rv_activity_main);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        errMessage = (TextView) findViewById(R.id.tv_error_message);

        movieAdapter = new MovieAdapter(this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(movieAdapter);

        movieInit(getString(R.string.popular));


    }

    private void movieInit(String sortType) {
        showMovieView();
        toggleActionBar(sortType);
        new FetchMovie().execute(getString(R.string.api_key), sortType);
    }

    private void toggleActionBar(String sortType) {

        if (sortType.equals(getString(R.string.popular)))
            actionBar.setTitle(getString(R.string.action_popular));

        else if (sortType.equals(getString(R.string.top_rated)))
            actionBar.setTitle(getString(R.string.action_toprated));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action actionBar item clicks here. The action actionBar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_refresh) {
            String sortType = MoviePreference.getSortOrder(this);
            movieInit(sortType);
        } else if (id == R.id.nav_popular) {
            MoviePreference.setSortOrder(this, getString(R.string.popular));
            movieInit(getString(R.string.popular));
        } else if (id == R.id.nav_toprated) {
            MoviePreference.setSortOrder(this, getString(R.string.top_rated));
            movieInit(getString(R.string.top_rated));
        }

        return true;
    }

    @Override
    public void onclick(JSONObject.JSONResponse movieInfo) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(getString(R.string.movie_title), movieInfo.getOriginal_title());
        intent.putExtra(getString(R.string.movie_img), movieInfo.getPoster_path());
        intent.putExtra(getString(R.string.movie_overview), movieInfo.getOverview());
        intent.putExtra(getString(R.string.rating), movieInfo.getVote_average());
        intent.putExtra(getString(R.string.release_date), movieInfo.getRelease_date());

        startActivity(intent);
    }

    private void showErrorView() {
        recyclerView.setVisibility(View.INVISIBLE);
        errMessage.setVisibility(View.VISIBLE);
    }

    private void showMovieView() {
        errMessage.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public class FetchMovie extends AsyncTask<String, Void, List<JSONObject.JSONResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<JSONObject.JSONResponse> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }
            String apiKey = params[0];
            String sortOrder = params[1];

            URL movieRequestURL = NetworkUtils.buildUrl(sortOrder, apiKey);

            try {
                String movieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestURL);
                List<JSONObject.JSONResponse> jsonResponse = new JSONObject().getMovieList(movieResponse);

                return jsonResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<JSONObject.JSONResponse> responseList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (responseList != null) {
                showMovieView();
                movieAdapter.setMovieData(responseList);
            } else {
                showErrorView();
            }
        }
    }
}
