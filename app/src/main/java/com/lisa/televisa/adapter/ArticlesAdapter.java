package com.lisa.televisa.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.lisa.televisa.R;
import com.lisa.televisa.Single;
import com.lisa.televisa.model.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hever on 11/10/16.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Article> newsList;
    public static final String TAG = ArticlesAdapter.class.getName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, excerpt, date;
        public ImageView thumbnail, share;

        public MyViewHolder(View view) {
            super(view);

            title       = (TextView) view.findViewById(R.id.txtTitle);
            excerpt     = (TextView) view.findViewById(R.id.txtExcerpt);
            thumbnail   = (ImageView)  view.findViewById(R.id.imageView2);
            date        = (TextView) view.findViewById(R.id.txtTime);
            share       = (ImageView) view.findViewById(R.id.share_btn);

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

        holder.title.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, Single.class);

                intent.putExtra("title", article.getTitle());
                intent.putExtra("content", article.getContent());
                intent.putExtra("image", article.getFeatured_media());
                intent.putExtra("date", article.getDate_gmt());

                mContext.startActivity(intent);

            }
        });

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
                intent.putExtra("image",article.getFeatured_media());
                intent.putExtra("date",article.getDate_gmt());
                intent.putExtra("link",article.getLink());
                mContext.startActivity(intent);
            }
        });


        Glide.with(mContext).load(article.getFeatured_media()).dontAnimate().fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareThis(article.getLink());
            }
        });

        String datetime = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd, yyyy");

        try {
            Date date = format.parse(article.getDate_gmt());
            datetime = dateformat.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       holder.date.setText(datetime.substring(0, 1).toUpperCase() + datetime.substring(1));
        //holder.date.setText(article.getDate_gmt());

        //Log.i(TAG, "Info");

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void ShareThis(String message)
    {
        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(sendIntent.EXTRA_SUBJECT, "televisa.NEWS");
        sendIntent.putExtra(sendIntent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");

        mContext.startActivity(Intent.createChooser(sendIntent, "Compartir"));
    }

}
