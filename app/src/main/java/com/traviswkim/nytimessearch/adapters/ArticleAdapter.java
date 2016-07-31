package com.traviswkim.nytimessearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.traviswkim.nytimessearch.R;
import com.traviswkim.nytimessearch.activities.ArticleActivity;
import com.traviswkim.nytimessearch.models.Article;
import com.traviswkim.nytimessearch.viewholders.ViewHolderArticle;
import com.traviswkim.nytimessearch.viewholders.ViewHolderArticleText;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by traviswkim on 7/28/16.
 */
public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Article> mArticles;
    private Context mContext;
    private final int ARTICLE = 0, ARTICLE_TEXT = 1;

    public ArticleAdapter(Context context, List<Article> articles) {
        mArticles = articles;
        mContext = context;
    }
    private Context getContext(){
        return mContext;
    }

    @Override
    public int getItemViewType(int position) {
        Article article = mArticles.get(position);
        if(TextUtils.isEmpty(article.getWebUrl())){
            return ARTICLE_TEXT;
        }else{
            return ARTICLE;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType){
            case ARTICLE:
                View viewImage = inflater.inflate(R.layout.layout_viewholderarticle, parent, false);
                viewHolder = new ViewHolderArticle(viewImage);
                break;
            case ARTICLE_TEXT:
                View viewText = inflater.inflate(R.layout.layout_viewholderarticletext, parent, false);
                viewHolder = new ViewHolderArticleText(viewText);
                break;
            default:
                View viewDefault = inflater.inflate(R.layout.layout_viewholderarticletext, parent, false);
                viewHolder = new ViewHolderArticleText(viewDefault);
                break;
        }
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case ARTICLE:
                ViewHolderArticle vha = (ViewHolderArticle) viewHolder;
                configureViewHolderArticle(vha, position);
                break;
            case ARTICLE_TEXT:
                ViewHolderArticleText vhat = (ViewHolderArticleText) viewHolder;
                configureViewHolderArticleText(vhat, position);
                break;
            default:
                ViewHolderArticleText vhdefault = (ViewHolderArticleText) viewHolder;
                configureViewHolderArticleText(vhdefault, position);
                break;
        }
    }

    public void configureViewHolderArticle(ViewHolderArticle viewHolderArticle, final int position){
        // Get the data model based on position
        Article article = mArticles.get(position);
        if(article != null){
            //Populate the thumbnail image
            //Remote download the image in the background
            String thumbnail = article.getThumbnail();

            if(TextUtils.isEmpty(thumbnail)){
                viewHolderArticle.getIvCover().setImageResource(0);
            }else{
                Glide.with(getContext())
                        .load(thumbnail)
                        .placeholder(R.mipmap.ic_launcher)
                        .centerCrop()
                        .into(viewHolderArticle.getIvCover());
            }

            if(TextUtils.isEmpty(article.getHeadline())){
                viewHolderArticle.getTvTitle().setText("");
            }else{
                viewHolderArticle.getTvTitle().setText(article.getHeadline());
            }

            if(TextUtils.isEmpty(article.getSnippet())){
                viewHolderArticle.getTvSnippet().setText("");
            }else{
                viewHolderArticle.getTvSnippet().setText(article.getSnippet());
            }

            viewHolderArticle.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Article article = mArticles.get(position);
                    Intent i = new Intent(mContext, ArticleActivity.class);
                    i.putExtra("article", Parcels.wrap(article));
                    mContext.startActivity(i);
                }
            });

        }

    }
    public void configureViewHolderArticleText(ViewHolderArticleText viewHolderArticleText, final int position){
        // Get the data model based on position
        Article article = mArticles.get(position);
        if(article != null){
            if(TextUtils.isEmpty(article.getHeadline())){
                viewHolderArticleText.getTvTitle().setText("");
            }else{
                viewHolderArticleText.getTvTitle().setText(article.getHeadline());
            }

            if(TextUtils.isEmpty(article.getSnippet())){
                viewHolderArticleText.getTvSnippet().setText("");
            }else{
                viewHolderArticleText.getTvSnippet().setText(article.getSnippet());
            }
        }

        viewHolderArticleText.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article article = mArticles.get(position);
                Intent i = new Intent(mContext, ArticleActivity.class);
                i.putExtra("article", Parcels.wrap(article));
                mContext.startActivity(i);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mArticles.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Article> newArticles) {
        mArticles.addAll(newArticles);
        notifyDataSetChanged();
    }
}
