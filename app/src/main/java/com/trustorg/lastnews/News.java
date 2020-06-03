package com.trustorg.lastnews;

public class News {
    private String mTitle;
    private String mType;
    private String mDate;
    private String mSection;
    private String mUrl;
    private String mAuthor;

    public News(String title, String type, String date, String section, String url, String author) {
        this.mTitle = title;
        this.mType = type;
        this.mDate = date;
        this.mSection = section;
        this.mUrl = url;
        this.mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getType() {
        return mType;
    }

    public String getDate() {
        return mDate;
    }

    public String getSection() {
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getAuthor() {
        return mAuthor;
    }


}
