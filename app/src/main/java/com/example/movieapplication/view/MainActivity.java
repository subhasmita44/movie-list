package com.example.movieapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movieapplication.R;
import com.example.movieapplication.model.MovieDetails;
import com.example.movieapplication.model.Search;
import com.example.movieapplication.viewmodel.MoviesViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText keywordEditText;
    private Button searchButton;
    String apikey="5d81e1ce";
    MovieAdapter movieAdapter;
    String page="1";
    String type="movie";
    private MoviesViewModel model;
    private List<Search> movielist;
    private List<Search> movielist2;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movielist = new ArrayList<>();
        movielist2 = new ArrayList<>();
        model = ViewModelProviders.of(this).get(MoviesViewModel.class);
        keywordEditText =findViewById(R.id.moviename);
        image=findViewById(R.id.imview);
        searchButton = findViewById(R.id.movie_search);

       // model = ViewModelProviders.of(MainActivity.this).get(MoviesViewModel.class);
        model.init();
        model.getMovieResponseLiveData().observe(this, new Observer<MovieDetails>() {
            @Override
            public void onChanged(MovieDetails movieDetails) {
                if (movieDetails != null) {

                    movielist2=movieDetails.getSearch();

                    setupRecycler();
                    //adapter.setResults(volumesResponse.getItems());
                }
            }
        });

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // clear(movielist,movieAdapter);
        // movielist.addAll(movie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(recyclerView, movielist, MainActivity.this);

        recyclerView.setAdapter(movieAdapter);
        Log.v("TAG","liston"+movielist.size());
      movieAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (movielist.size() <= 10) {
                    movielist.add(null);
                movieAdapter.notifyItemChanged(movielist.size() - 1);
                movieAdapter.setResults(movielist);
                    movieAdapter.notifyDataSetChanged();

                     movieAdapter.notifyItemInserted(movielist.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            movielist.remove(movielist.size() - 1);
                            movieAdapter.notifyItemRemoved(movielist.size());

                            //Generating more data
                            int index = movielist.size();
                            int end = movielist2.size();

                            // Search contact = new Search();

                            movielist.addAll(movielist2);

                            movieAdapter.notifyDataSetChanged();
                            movieAdapter.setLoaded();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(MainActivity.this, "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setVisibility(View.GONE);
                if(movielist.size()>0) {
                    movielist.clear();
                    movielist2.clear();

                    movieAdapter.notifyDataSetChanged();


                    performSearch();
                }else{
                    //movieAdapter.notifyDataSetChanged();
                    performSearch();


                }

            }
        });

    }

    private void performSearch() {

        Log.v("TAG","call");
        String keyword = keywordEditText.getEditableText().toString();
        int length = keywordEditText.length();

        if(!TextUtils.isEmpty(keyword) && length>=3) {
            model.searchMovie(apikey, type, page, keyword);
            try {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                // TODO: handle exception
            }


        }else {
            Toast.makeText(MainActivity.this, "Enter Movie Name", Toast.LENGTH_LONG).show();
        }
    }

    private void setupRecycler() {
        if(movielist2!=null) {
            Log.v("TAG", "list" + movielist2.size());

            movielist.addAll(movielist2);
            movieAdapter.setResults(movielist);
            movieAdapter.notifyDataSetChanged();
        }


    }



}