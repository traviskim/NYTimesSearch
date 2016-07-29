package com.traviswkim.nytimessearch.models;

import org.parceler.Parcel;

/**
 * Created by traviswkim on 7/27/16.
 */
@Parcel
public class SearchSetting {
    String beginDate = "";
    String sortOrder = "";
    boolean arts = false;
    boolean fasionStyle = false;
    boolean sports = false;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isArts() {
        return arts;
    }

    public void setArts(boolean arts) {
        this.arts = arts;
    }

    public boolean isFasionStyle() {
        return fasionStyle;
    }

    public void setFasionStyle(boolean fasionStyle) {
        this.fasionStyle = fasionStyle;
    }

    public boolean isSports() {
        return sports;
    }

    public void setSports(boolean sports) {
        this.sports = sports;
    }

    public SearchSetting(){}

    public SearchSetting(String beginDate, String sortOrder, boolean arts, boolean fasionStyle, boolean sports){
        this.beginDate = beginDate;
        this.sortOrder = sortOrder;
        this.arts = arts;
        this.fasionStyle = fasionStyle;
        this.sports = sports;
    }
}
