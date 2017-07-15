package neu.droid.guy.newsapp;


public class News {

    private String mTitle;
    private String mContentSnippet;
    private String mMainNews;

    public News(String title, String contentSnippet, String mainNews) {
        mTitle = title;
        mContentSnippet = contentSnippet;
        mMainNews = mainNews;
    }


    public News(String title){
        this.mTitle = title;
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

}
