package com.traviswkim.nytimessearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.traviswkim.nytimessearch.R;
import com.traviswkim.nytimessearch.activities.ArticleActivity;
import com.traviswkim.nytimessearch.models.Article;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by traviswkim on 7/28/16.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //DynamicHeightImageView ivImage;
        public ImageView ivCover;
        public TextView tvTitle;
        public TextView tvSnippet;

        public ViewHolder(View itemView){
            super(itemView);
            ivCover = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSnippet = (TextView) itemView.findViewById(R.id.tvSnippet);
            itemView.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            Article article = mArticles.get(position);

            Intent i = new Intent(mContext, ArticleActivity.class);
            i.putExtra("article", Parcels.wrap(article));
            mContext.startActivity(i);
        }
        /*
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ivImage = new DynamicHeightImageView(mContext);
            // Calculate the image ratio of the loaded bitmap
            float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
            // Set the ratio for the image
            ivImage.setHeightRatio(ratio);
            // Load the image into the view
            ivImage.setImageBitmap(bitmap);
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Log.d("HIYA", "onBitmapFailed");
        }
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Log.d("HIYA", "onPrepareLoad");
        }
        */
    }

    private List<Article> mArticles;
    private Context mContext;
    public ArticleAdapter(Context context, List<Article> articles) {
        mArticles = articles;
        mContext = context;
    }
    private Context getContext(){
        return mContext;
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_article_result, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);

        // Set item views based on your views and data model
        //ImageView ivCover = viewHolder.ivCover;
        TextView tvTitle = viewHolder.tvTitle;
        TextView tvSnippet = viewHolder.tvSnippet;
        //Clear out recycled image from convertView from last time
        //ivCover.setImageResource(0);

        //Populate the thumbnail image
        //Remote download the image in the background
        String thumbnail = article.getThumbnail();

        if(!TextUtils.isEmpty(thumbnail)){
            Picasso.with(getContext()).load(thumbnail)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(viewHolder.ivCover);
        }
        tvTitle.setText(article.getHeadline());
        tvSnippet.setText(article.getSnippet());
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
