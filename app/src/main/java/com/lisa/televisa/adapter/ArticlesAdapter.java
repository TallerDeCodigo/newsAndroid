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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hever on 11/10/16.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Article> newsList;

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
                intent.putExtra("image",article.getFeatured_media());

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
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext).load(article.getFeatured_media()).fitCenter().into(holder.thumbnail);

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareThis(article.getLink());
            }
        });


        //String dateString = article.getDate_gmt();

        //SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", new Locale("es","MX"));

        //Date convertedDate = new Date();
    /*
        try {
            convertedDate = dateFormat.parse(dateString);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    */
        holder.date.setText(article.getDate_gmt());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void ShareThis(String message)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(sendIntent, "Compartir"));
    }

}
