package com.example.sndtcsi.newsfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>
{
    protected List<Articles> articles;
    protected Context context;

    public NewsAdapter(List<Articles> articles, Context context)
    {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        myViewHolder.title.setText(articles.get(i).getTitle());
        myViewHolder.author.setText(articles.get(i).getAuthor());

        String initialDate = articles.get(i).getPublishedAt();
        String finalDate = getDate(initialDate);

        myViewHolder.date.setText(finalDate);

        myViewHolder.content.setText(articles.get(i).getContent());

        Picasso.with(context).load(articles.get(i).getUrlToImage()).into(myViewHolder.image);
    }

    @Override
    public int getItemCount()
    {
        return articles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, author, date, content;
        public ImageView image;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.newsTitleTextView);
            author = (TextView) itemView.findViewById(R.id.newsAuthorNameTextView);
            date = (TextView) itemView.findViewById(R.id.newsDateTimeTextView);
            content = (TextView) itemView.findViewById(R.id.newsContentTextView);
            image = (ImageView) itemView.findViewById(R.id.newsImageView);
        }
    }

    public String getDate(String initialDate)
    {
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

        Date date = new Date();
        try {
            date = inputFormat.parse(initialDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);
        return formattedDate;
    }
}
