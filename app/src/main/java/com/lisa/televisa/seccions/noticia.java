package com.lisa.televisa.seccions;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import com.lisa.televisa.R;
import com.lisa.televisa.adapter.ArticlesAdapter;
import com.lisa.televisa.model.Article;
import com.lisa.televisa.request.News;
import com.lisa.televisa.utils.Helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class noticia extends Fragment {

    public static final String TAG = noticia.class.getName();
    private RecyclerView recyclerView;
    private ArticlesAdapter adapter;
    private List<Article> articleList;
    public News newsRequest;
    public Helpers helpers;
    public String seccion;


    public noticia() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBreakingNews();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_noticia, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        articleList  = new ArrayList<>();
        adapter      = new ArticlesAdapter(getContext(), articleList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void getBreakingNews(){

        helpers = new Helpers();

        newsRequest = new News(getContext(), "https://www.televisa.news/wp-json/wp/v2/noticia", new News.NewsListener() {

            @Override
            public void onGetNews(String json) {

                try {

                    JSONArray jsonArray = new JSONArray(json);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {

                            String content          = jsonArray.getJSONObject(i).getJSONObject("content").getString("rendered");
                            String date_gmt         = jsonArray.getJSONObject(i).getString("date_gmt");
                            String excerpt          = jsonArray.getJSONObject(i).getJSONObject("excerpt").getString("rendered");
                            String featured_media   = jsonArray.getJSONObject(i).getString("featured_media");
                            String guid             = jsonArray.getJSONObject(i).getString("guid");
                            int id                  = 0;
                            String link             = jsonArray.getJSONObject(i).getString("post_link");
                            String modified         = jsonArray.getJSONObject(i).getString("modified");
                            String modified_gmt     = jsonArray.getJSONObject(i).getString("modified_gmt");
                            String slug             = jsonArray.getJSONObject(i).getString("slug");
                            String title            = jsonArray.getJSONObject(i).getJSONObject("title").getString("rendered");

                            String type             = jsonArray.getJSONObject(i).getString("type");
                            String _links           = jsonArray.getJSONObject(i).getString("_links");


                            Article n = new Article(content, date_gmt, excerpt, featured_media, guid, id, link, modified, modified_gmt, slug, title, type, _links);
                            articleList.add(n);

                            Log.d(TAG, featured_media);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

                setImagesIntoModel(articleList);
            }

            @Override
            public void onGetNewsFaliure() {

            }
        });

        newsRequest.execute();
    }


    public void setImagesIntoModel(List<Article> articleList){

        helpers = new Helpers();

        for (int i=0; i<articleList.size(); i++) {

            final Article article = articleList.get(i);

            String url = "https://televisa.news/wp-json/wp/v2/media/" + article.getFeatured_media();

            newsRequest = new News(getContext(),url , new News.NewsListener() {

                String urlImage = "";

                @Override
                public void onGetNews(String jsonArticles) {
                    try {

                        JSONObject jsonObject = new JSONObject(jsonArticles);

                        try {

                            urlImage   = jsonObject.getString("source_url");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    article.setFeatured_media(urlImage);
                }

                @Override
                public void onGetNewsFaliure() {

                }
            });
            newsRequest.execute();
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
