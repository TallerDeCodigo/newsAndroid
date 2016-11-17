package com.lisa.televisa;

import android.app.Application;

import com.lisa.televisa.model.Article;

import java.util.List;

/**
 * Created by hever on 11/16/16.
 */

public class TelevisaNews extends Application {

    private List<Article> articleList;

    public List<Article> getPolitica() {
        return articleList;
    }

    public void setPolitica(List<Article> feed) {
        this.articleList = feed;
    }
}
