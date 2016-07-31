package com.traviswkim.nytimessearch.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.traviswkim.nytimessearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by traviswkim on 7/29/16.
 */
public class ViewHolderArticleText extends RecyclerView.ViewHolder{
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvSnippet) TextView tvSnippet;

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

    public ViewHolderArticleText(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
