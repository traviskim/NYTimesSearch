package com.traviswkim.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.traviswkim.nytimessearch.R;
import com.traviswkim.nytimessearch.models.Article;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {
    private ShareActionProvider miShareAction;
    private Intent shareIntent;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.wvArticle) WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Article article = (Article) Parcels.unwrap(getIntent().getParcelableExtra("article"));
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(article.getWebUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_article_detail, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        //pass in the URL currently being used by the WebView
        shareIntent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());

        miShareAction.setShareIntent(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
        //return super.onCreateOptionsMenu(menu);
        return true;

    }
}
