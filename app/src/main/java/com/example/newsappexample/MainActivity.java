package com.example.newsappexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsappexample.Model.Article;
import com.example.newsappexample.Model.NewsApiResponse;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Article> articles;
    public static final String API_KEY = "fb5fc9f339a9498d9f73d74c8a4545d0";

    private EditText etQuery;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etQuery= findViewById(R.id.etQuery);
        btnSearch = findViewById(R.id.btnSearch);

        swipeRefreshLayout=findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final String country = getCountry();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("",country,API_KEY);
            }
        });

        retrieveJson("",country , API_KEY);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etQuery.getText().toString().equals("")){
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson(etQuery.getText().toString(),country,API_KEY);
                        }
                    });
                    retrieveJson(etQuery.getText().toString(),country , API_KEY);
                }else {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson("",country,API_KEY);
                        }
                    });

                    retrieveJson("",country , API_KEY);
                }
            }
        });
    }

    public void retrieveJson(String query, String country, final String apiKey){
        swipeRefreshLayout.setRefreshing(true);
        Call<NewsApiResponse> mService;

        if (!etQuery.getText().toString().equals("")){
            mService = ApiClient.getInstance().getApi().searchNews(query,apiKey);
        }else {
            mService = ApiClient.getInstance().getApi().getNews(country,apiKey);
        }


        mService.enqueue(new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful() && response.body().getArticles() != null){
                    articles = response.body().getArticles();
                    newsAdapter = new NewsAdapter(MainActivity.this,articles);
                    recyclerView.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                call.cancel();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();

        return country.toLowerCase();
    }
}
