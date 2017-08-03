package neu.droid.guy.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class NewsListView extends AppCompatActivity implements LoaderManager.LoaderCallbacks {
    public ProgressBar pBar;
    public ListView lv;
    public Button moreB;
    public static String query_URL_API;
    private ArrayList<News> mNewsArrayListFromAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_view);

        pBar = (ProgressBar) findViewById(R.id.progressBarToBeHidden);
        lv = (ListView) findViewById(R.id.list);
        moreB = (Button) findViewById(R.id.moreButton);
//        TextView emptyView = (TextView) findViewById(R.id.emptyView);
//        lv.setEmptyView(emptyView);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("URL_TO_HIT") != null) {
            query_URL_API = bundle.getString("URL_TO_HIT");
            if (query_URL_API.contains("guardianapis")) {
                setTitle(bundle.getString("COUNTRY_NAME"));
            }
//            else if (query_URL_API.contains("nytimes")) {
//                setTitle("NYT in short");
//            }
            getSupportLoaderManager().initLoader(0, null, this);
        }


    }// End of onCreate()


    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, CountryNewsMapsActivity.class);
        startActivity(i);
        finish();
    }

    public void setAdapterOnLoaderComplete() {

        if (mNewsArrayListFromAsyncTask != null) {
            /**Hide the progress bar*/
            pBar.setVisibility(View.GONE);

            /**Set the adapter to the ListView here*/
            lv.setVisibility(View.VISIBLE);
            final NewsArrayAdapter adapter = new NewsArrayAdapter(NewsListView.this, mNewsArrayListFromAsyncTask);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    News newsItem = adapter.getItem(i);
                    Intent showMainNewsIntent = new Intent(NewsListView.this, DetailedNewsContent.class);

                    //Transfer Image Through Intent
                    assert newsItem != null;
                    Bitmap bmap = newsItem.getImageResourceId();
                    if (bmap != null) {
                        ByteArrayOutputStream sendBmp = new ByteArrayOutputStream();
                        bmap.compress(Bitmap.CompressFormat.WEBP, 100, sendBmp);
                        byte[] byteArr = sendBmp.toByteArray();
                        showMainNewsIntent.putExtra("THUMBNAIL", byteArr);
                        showMainNewsIntent.putExtra("NEWS_URL", newsItem.getNewsUrl());
                        showMainNewsIntent.putExtra("MAIN_CONTENT", newsItem.getMainNews());
                        showMainNewsIntent.putExtra("HEADLINES", newsItem.getTitle());
                        String contentFrom = "newsPaperName";
                        if (query_URL_API.contains("nytimes")) {
                            contentFrom = "NYT";
                        }
                        showMainNewsIntent.putExtra("NYT_CONTENT", contentFrom);
                        startActivity(showMainNewsIntent);
                    }
                }

            });


//        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//            }

//            @Override
//            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int preLast = 0;

//                switch (absListView.getId()) {
//                    case R.id.list:

//                        final int lastItem = firstVisibleItem + visibleItemCount;

//                        if (lastItem == totalItemCount) {
//                            if (preLast != lastItem) {
//                                moreB.setVisibility(View.VISIBLE);
//                                preLast = lastItem;
//                            }
//                        }
//                }
//            }
//        });
        }
    }


    /**
     * Implement Loader and make network call here
     */
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
//        Log.e("INSIDE_on create loader","on create loader");
//        new NewsListAsyncLoader(this).onStartLoading();
        new NewsListAsyncLoader(this).forceLoad();
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
//        setAdapterOnLoaderComplete();
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }


    public class NewsListAsyncLoader extends AsyncTaskLoader<Object> {

        public NewsListAsyncLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            /** If the data is there, don't start again*/
            if (mNewsArrayListFromAsyncTask != null) {
//                Log.e("INSIDE_onstartloading","DATA NULL directly deliver result");
                deliverResult(mNewsArrayListFromAsyncTask);
            } else {
                /**Start the loader*/
//                Log.e("INSIDE_ forceload","data not null");
                forceLoad();
            }
        }

        @Override
        public Object loadInBackground() {
            try {
//                Log.e("INSIDE_LOAD_INBG","Load in bg");
                mNewsArrayListFromAsyncTask = new FetchDataFromAPI().feedToAsyncTask();
//                Log.e("mNewsArrayList", mNewsArrayListFromAsyncTask.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        public void deliverResult(Object data) {
//            Log.e("INSIDE_DELIVER_RESULT","deliver result");
            setAdapterOnLoaderComplete();
        }
    }
}
