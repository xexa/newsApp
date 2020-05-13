package com.example.newsappexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvSource, tvTime ,tvDecs;
    private WebView webView;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvSource = findViewById(R.id.tv_source);
        tvTime = findViewById(R.id.tv_date);
        tvDecs = findViewById(R.id.tvDecs);
        webView = findViewById(R.id.web_view);
        imageView = findViewById(R.id.detail_image);
        progressBar = findViewById(R.id.web_view_loader);


        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        String decs = intent.getStringExtra("decs");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");

        tvTitle.setText(title);
        tvSource.setText(source);
        tvTime.setText(time);
        tvDecs.setText(decs);
        tvTitle.setText(title);

        Picasso.get().load(imageUrl).into(imageView);
        progressBar.setVisibility(View.VISIBLE);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        if (webView.isShown()){
            progressBar.setVisibility(View.INVISIBLE);
        }


    }
}
