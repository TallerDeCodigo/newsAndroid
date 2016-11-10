package com.lisa.televisa;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lisa.televisa.adapter.ArticlesAdapter;
import com.lisa.televisa.model.Article;
import com.lisa.televisa.request.News;
import com.lisa.televisa.utils.Helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import com.lisa.televisa.seccions.noticia;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = MainActivity.class.getName();
    private RecyclerView recyclerView;
    private ArticlesAdapter adapter;
    private List<Article> articleList;
    public News newsRequest;
    public Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getBreakingNews();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        articleList  = new ArrayList<>();
        adapter      = new ArticlesAdapter(this, articleList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        Class fragmentClass;

        int id = item.getItemId();



        if(id == R.id.nav_cdmx){
            fragmentClass = noticia.class;
        }


        //if (id == R.id.nav_camera) {
            // Handle the camera action
        //} else if (id == R.id.nav_gallery) {

        //} else if (id == R.id.nav_slideshow) {

        //} else if (id == R.id.nav_manage) {

        //} else if (id == R.id.nav_share) {

        //} else if (id == R.id.nav_send) {

        //}

        Log.d(TAG, "Menu Click");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getBreakingNews(){

        helpers = new Helpers();

        newsRequest = new News(getApplicationContext(), "https://www.televisa.news/wp-json/wp/v2/noticia", new News.NewsListener() {

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
                            String link             = jsonArray.getJSONObject(i).getString("link");
                            String modified         = jsonArray.getJSONObject(i).getString("modified");
                            String modified_gmt     = jsonArray.getJSONObject(i).getString("modified_gmt");
                            String slug             = jsonArray.getJSONObject(i).getString("slug");
                            String title            = jsonArray.getJSONObject(i).getJSONObject("title").getString("rendered");

                            String type             = jsonArray.getJSONObject(i).getString("type");
                            String _links           = jsonArray.getJSONObject(i).getString("_links");

                            //featured_media          = helpers.getImageJSON(featured_media);

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

                Toast.makeText(MainActivity.this, "Error en la carga de las noticias", Toast.LENGTH_SHORT).show();
            }
        });

        newsRequest.execute();
    }


    public void setImagesIntoModel(List<Article> articleList){

        helpers = new Helpers();

        for (int i=0; i<articleList.size(); i++) {

            final Article article = articleList.get(i);

            String url = "https://televisa.news/wp-json/wp/v2/media/" + article.getFeatured_media();

            newsRequest = new News(getApplicationContext(),url , new News.NewsListener() {

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
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
