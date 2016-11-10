package com.lisa.televisa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lisa.televisa.R;
import com.lisa.televisa.Single;
import com.lisa.televisa.model.Article;

import java.util.List;

/**
 * Created by hever on 11/10/16.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Article> newsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, excerpt;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);

            title   = (TextView) view.findViewById(R.id.txtTitle);
            excerpt = (TextView) view.findViewById(R.id.txtExcerpt);
            thumbnail = (ImageView)  view.findViewById(R.id.imageView2);
        }
    }

    public ArticlesAdapter(Context mContext, List<Article> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_news, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Article article = newsList.get(position);
        holder.title.setText(article.getTitle());

        Spanned result;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(article.getExcerpt(),Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(article.getExcerpt());
        }

        holder.excerpt.setText(result);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, Single.class);

                intent.putExtra("title", article.getTitle());
                intent.putExtra("content", article.getContent());

                mContext.startActivity(intent);

            }
        });

        // loading album cover using Glide library


        Glide.with(mContext).load("http://televisa.news/wp-content/uploads/2016/11/clinton_preventaja.jpg").into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
