package neu.droid.guy.newsapp;


import android.graphics.Bitmap;

public class News {

    private String mTitle;
    private String mContentSnippet;
    private String mMainNews;
    private Bitmap mImageId;
    private String mNewsUrl;

    public News(String title, String contentSnippet, Bitmap imageId, String mainNews) {
        mImageId = imageId;
        mTitle = title;
        mContentSnippet = contentSnippet;
        mMainNews = mainNews;
    }

    public News(String title, String contentSnippet, Bitmap imageId, String mainNews, String newsUrl) {
        mImageId = imageId;
        mTitle = title;
        mContentSnippet = contentSnippet;
        mMainNews = mainNews;
        mNewsUrl = newsUrl;
    }


    public String getNewsUrl(){
        return mNewsUrl;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getContentSnippet(){
        return mContentSnippet;
    }

    public String getMainNews(){
        return mMainNews;
    }


    public Bitmap getImageResourceId() {
        return mImageId;
    }
}
