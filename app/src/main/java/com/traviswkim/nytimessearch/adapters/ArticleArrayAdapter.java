package com.traviswkim.nytimessearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.traviswkim.nytimessearch.R;
import com.traviswkim.nytimessearch.activities.ArticleActivity;
import com.traviswkim.nytimessearch.models.Article;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by traviswkim on 7/24/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(Context context, List<Article> articles){
        super(context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the data item from for position
        final Article article = this.getItem(position);
        //Check to see if existing view being used
        //not using a recycled view -> inflate the layout
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }
        //Find the image view
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);

        //Clear out recycled image from convertView from last time
        imageView.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvSnippet = (TextView) convertView.findViewById(R.id.tvSnippet);
        tvTitle.setText(article.getHeadline());
        tvSnippet.setText(article.getSnippet());

        //Populate the thumbnail image
        //Remote download the image in the background
        String thumbnail = article.getThumbnail();
        if(!TextUtils.isEmpty(thumbnail)){
            Picasso.with(getContext()).load(thumbnail)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
        }

        //Hook up listener for the grid click
        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ArticleActivity.class);
                i.putExtra("article", Parcels.wrap(article));
                getContext().startActivity(i);
            }
        });
        return convertView;
    }
}
