package neu.droid.guy.newsapp;


public class News {

    private String mTitle;
    private String mContentSnippet;
    private String mMainNews;
    private int mImageId;

    public News(String title, String contentSnippet, int imageId, String mainNews) {
        mImageId = imageId;
        mTitle = title;
        mContentSnippet = contentSnippet;
        mMainNews = mainNews;
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


    public int getImageResourceId() {
        return mImageId;
    }
}
