package com.traviswkim.nytimessearch.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.traviswkim.nytimessearch.R;

/**
 * Created by traviswkim on 7/29/16.
 */
public class ViewHolderArticleText extends RecyclerView.ViewHolder{
    //DynamicHeightImageView ivImage;
    public TextView tvTitle;
    public TextView tvSnippet;

    public ViewHolderArticleText(View itemView){
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvSnippet = (TextView) itemView.findViewById(R.id.tvSnippet);
    }
}
