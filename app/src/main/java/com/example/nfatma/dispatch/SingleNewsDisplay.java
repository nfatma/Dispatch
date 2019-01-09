package com.example.nfatma.dispatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SingleNewsDisplay extends AppCompatActivity {

    private ImageView img;
    private TextView tv;
    private String urlToImg = "";
    private String content = "";
    private List<NewsModelClass.Articles> articles;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_news_display);

        img = findViewById(R.id.ass_img);
        tv = findViewById(R.id.ass_content);

        i = getIntent();
        int position = i.getIntExtra("position", 0);

        urlToImg = articles.get(position).getUrlToImage();
        content = articles.get(position).getContent();

        Picasso.with(SingleNewsDisplay.this).load(urlToImg).into(img);
        tv.setText(content);


    }
}
