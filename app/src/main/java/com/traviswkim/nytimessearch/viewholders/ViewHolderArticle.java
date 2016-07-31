package com.traviswkim.nytimessearch.viewholders;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.traviswkim.nytimessearch.R;
import com.traviswkim.nytimessearch.utils.DynamicHeightImageView;

/**
 * Created by traviswkim on 7/29/16.
 */
public class ViewHolderArticle extends RecyclerView.ViewHolder implements Target{
    public DynamicHeightImageView ivCover;
    //public ImageView ivCover;
    public TextView tvTitle;
    public TextView tvSnippet;

    public ViewHolderArticle(View itemView){
        super(itemView);
        //ivCover = (ImageView) itemView.findViewById(R.id.ivImage);
        ivCover = (DynamicHeightImageView) itemView.findViewById(R.id.ivImage);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvSnippet = (TextView) itemView.findViewById(R.id.tvSnippet);
        //itemView.setOnClickListener(this);
    }

    public DynamicHeightImageView getIvCover() {
        return ivCover;
    }

    public void setIvCover(DynamicHeightImageView ivCover) {
        this.ivCover = ivCover;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvSnippet() {
        return tvSnippet;
    }

    public void setTvSnippet(TextView tvSnippet) {
        this.tvSnippet = tvSnippet;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            // Calculate the image ratio of the loaded bitmap
            float ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
            // Set the ratio for the image
            ivCover.setHeightRatio(ratio);
            // Load the image into the view
            ivCover.setImageBitmap(bitmap);
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Log.d("HIYA", "onBitmapFailed");
        }
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Log.d("HIYA", "onPrepareLoad");
        }

}
