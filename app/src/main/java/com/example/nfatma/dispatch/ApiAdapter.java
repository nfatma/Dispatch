package com.example.nfatma.dispatch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.MyViewHolder> implements View.OnClickListener {


    private Context context;
    private List<NewsModelClass.Articles> news;
    private int pos;


    public ApiAdapter(Context context, List<NewsModelClass.Articles> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;

        LayoutInflater vi;
        vi = LayoutInflater.from(context);
        v = vi.inflate(R.layout.news_row_display, null);

        return new ApiAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ApiAdapter.MyViewHolder myViewHolder, int i) {

        pos = i;
        String urlToImg = news.get(i).getUrlToImage();
        Picasso.with(context).load(urlToImg).into(myViewHolder.img);
        myViewHolder.display_title.setText(news.get(i).getTitle());
        myViewHolder.display_content.setText(news.get(i).getContent());
        myViewHolder.display_date.setText(news.get(i).getDate());

        myViewHolder.more.setOnClickListener(this);
        myViewHolder.share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, news.get(pos).getUrl());
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(news.get(pos).getUrl()));
        context.startActivity(intent);    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView display_title, display_content, display_date;
        ImageView img;
        Button more, share;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            display_title = itemView.findViewById(R.id.title);
            display_content = itemView.findViewById(R.id.content);
            display_date = itemView.findViewById(R.id.published_at);
            img = itemView.findViewById(R.id.image_ass);
            more = itemView.findViewById(R.id.more);
            share = itemView.findViewById(R.id.share);

        }
    }
}

