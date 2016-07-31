package com.traviswkim.nytimessearch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.traviswkim.nytimessearch.R;
import com.traviswkim.nytimessearch.SpacesItemDecoration;
import com.traviswkim.nytimessearch.adapters.ArticleAdapter;
import com.traviswkim.nytimessearch.fragments.SettingsDialogFragment;
import com.traviswkim.nytimessearch.models.Article;
import com.traviswkim.nytimessearch.models.SearchSetting;
import com.traviswkim.nytimessearch.utils.EndlessRecyclerViewScrollListener;
import com.traviswkim.nytimessearch.utils.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class SearchActivity extends AppCompatActivity implements SettingsDialogFragment.SettingsDialogListener {

    ArrayList<Article> articles;
    //ArrayAdapter<Article> articleAdapter;
    RecyclerView rvArticle;
    ArticleAdapter articleAdapter;
    //GridView gvResults;
    AsyncHttpClient client;
    SimpleDateFormat dspSdf = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat paramSdf = new SimpleDateFormat("yyyyMMdd");
    SearchSetting ss = new SearchSetting("", "", false, false, false);
    private SwipeRefreshLayout swipeContainer;
    private int searchPage;
    String searchQuery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();
        onArticleSearch("", 0);
    }

    public void setupViews(){
        //gvResults = (GridView) findViewById(R.id.gvResults);
        articles = new ArrayList<>();
        articleAdapter = new ArticleAdapter(this, articles);
        //gvResults.setAdapter(articleAdapter);
        rvArticle = (RecyclerView) findViewById(R.id.rvNYTSearch);
        rvArticle.setAdapter(articleAdapter);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvArticle.setLayoutManager(gridLayoutManager);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        rvArticle.addItemDecoration(decoration);
        rvArticle.setItemAnimator(new SlideInUpAnimator());

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                onArticleSearch(searchQuery, 0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Add the scroll listener
        rvArticle.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                searchPage++;
                onArticleSearch(searchQuery, searchPage);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchQuery = query;
                onArticleSearch(query, 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(query.trim() == ""){
                    onArticleSearch("", 0);
                    return true;
                }else {
                    return false;
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_search:
                return true;
            case R.id.action_settings:
                //FragmentManager fm = getSupportFragmentManager();
                //SettingsDialogFragment editTaskDialogFragment = SettingsDialogFragment.newInstance(ss);
                //editTaskDialogFragment.show(fm, "fragment_edit_name");
                showEditDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void onArticleSearch(String query, final int pageNumber) {
        if(NetworkUtil.isNetworkConnected(SearchActivity.this)) {
            //String query = etQuery.getText().toString();
            client = new AsyncHttpClient();
            String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

            RequestParams params = new RequestParams();
            params.put("api-key", "0fea1f783cd3445fb99d624c768ebb8b");
            if(!TextUtils.isEmpty(ss.getBeginDate())) {
                try {
                    Date sDate = dspSdf.parse(ss.getBeginDate());
                    params.put("begin_date", paramSdf.format(sDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(!TextUtils.isEmpty(ss.getSortOrder())) {
                params.put("sort", ss.getSortOrder());
            }
            if(pageNumber == 0) {
                params.put("page", 0);
            }else{
                searchPage = pageNumber;
                params.put("page", pageNumber);
            }
            if(!TextUtils.isEmpty(query)) {
                params.put("q", query);
            }
            StringBuffer sb = new StringBuffer();
            if(ss.isArts()){
                sb.append("\"Arts\"");
            }
            if(ss.isFasionStyle()){
                if(sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append("\"Fashion & Style\"");
            }
            if(ss.isSports()){
                if(sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append("\"Sports\"");
            }
            if(sb.length() > 0){
                params.put("fq", "news_desk:(" + sb.toString()+ ")");
            }
            Log.d("DEBUG", url + params.toString());
            client.get(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("DEBUG", response.toString());
                    JSONArray articleJsonResults = null;
                    try {
                        articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                        if(pageNumber == 0) {
                            articles.clear();
                            articleAdapter.clear();
                        }
                        articles.addAll(Article.fromJsonArray(articleJsonResults));
                        if(pageNumber == 0){
                            searchPage = 0;
                            articleAdapter.notifyDataSetChanged();
                        }else {
                            articleAdapter.notifyItemInserted(articleJsonResults.length()-1);
                            //rvArticle.scrollToPosition(articleAdapter.getItemCount()-1);
                        }
                        swipeContainer.setRefreshing(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }

    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SettingsDialogFragment settingsDialogFragment = SettingsDialogFragment.newInstance(ss);
        settingsDialogFragment.show(fm, "settings_fragment");
    }

    public void onFinishInputDialog(SearchSetting newSs) {
        this.ss.setBeginDate(newSs.getBeginDate());
        this.ss.setSortOrder(newSs.getSortOrder());
        this.ss.setArts(newSs.isArts());
        this.ss.setFasionStyle(newSs.isFasionStyle());
        this.ss.setSports(newSs.isSports());
    }
}
