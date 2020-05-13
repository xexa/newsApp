package com.example.newsappexample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsappexample.Model.Article;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewholder> {

    private Context context;
    private List<Article> articles;

    public NewsAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_items,parent,false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, final int position) {

        holder.title.setText(articles.get(position).getTitle());
        holder.source.setText(articles.get(position).getSource().getName());
        holder.date.setText(dateTime(articles.get(position).getPublishedAt()));

        String imageUrl =  articles.get(position).getUrlToImage();

        Picasso.get().load(imageUrl).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("title", articles.get(position).getTitle());
                intent.putExtra("source", articles.get(position).getSource().getName());
                intent.putExtra("time", dateTime(articles.get(position).getPublishedAt()));
                intent.putExtra("decs", articles.get(position).getDescription());
                intent.putExtra("imageUrl", articles.get(position).getUrlToImage());
                intent.putExtra("url", articles.get(position).getUrl());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        if (articles.size() != 0)
            return articles.size();
        return 0;
    }

    public class MyViewholder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView source;
        private TextView date;
        private ImageView imageView;
        private CardView cardView;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            source = itemView.findViewById(R.id.tv_source);
            date = itemView.findViewById(R.id.tv_date);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }


    public String dateTime(String t){
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm",Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();

        return country.toLowerCase();
    }
}
