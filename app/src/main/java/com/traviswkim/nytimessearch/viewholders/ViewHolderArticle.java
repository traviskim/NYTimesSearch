package com.traviswkim.nytimessearch.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.traviswkim.nytimessearch.R;
import com.traviswkim.nytimessearch.utils.DynamicHeightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by traviswkim on 7/29/16.
 */
public class ViewHolderArticle extends RecyclerView.ViewHolder{
    @BindView(R.id.ivImage) DynamicHeightImageView ivCover;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvSnippet) TextView tvSnippet;

    public ViewHolderArticle(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
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

}
