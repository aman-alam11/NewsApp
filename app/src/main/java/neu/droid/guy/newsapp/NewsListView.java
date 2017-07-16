package neu.droid.guy.newsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;

import static neu.droid.guy.newsapp.MainActivity.newNewsAsyncTask;
import static neu.droid.guy.newsapp.MainActivity.newsArrayListFromAsyncTask;

public class NewsListView extends AppCompatActivity {
    public ProgressBar pBar;
    public ListView lv;
    public Button moreB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_view);
//        Log.e("SIMPLE_NAME", NewsListView.class.getSimpleName());

        pBar = (ProgressBar) findViewById(R.id.progressBarToBeHidden);
        lv = (ListView) findViewById(R.id.list);
        moreB = (Button) findViewById(R.id.moreButton);

        new trackAsyncTask().execute();

    }// End of onCreate()


    public void setAdapterOnAsyncTaskComplete() {

        //Hide the progress bar
        pBar.setVisibility(View.GONE);

        //Set the adapter to the ListView here
        //Make the Relative Layout Visible
        lv.setVisibility(View.VISIBLE);
//        moreB.setVisibility(View.VISIBLE);
        final NewsArrayAdapter adapter = new NewsArrayAdapter(NewsListView.this, newsArrayListFromAsyncTask);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                News newsItem = adapter.getItem(i);
                Intent showMainNewsIntent = new Intent(NewsListView.this, DetailedNewsContent.class);

                //Transfer Image Through Intent
                Bitmap bmap = newsItem.getImageResourceId();
                ByteArrayOutputStream sendBmp = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.WEBP, 100, sendBmp);
                byte[] byteArr = sendBmp.toByteArray();

                showMainNewsIntent.putExtra("THUMBNAIL", byteArr);
                showMainNewsIntent.putExtra("MAIN_CONTENT", newsItem.getMainNews());
                showMainNewsIntent.putExtra("HEADLINES", newsItem.getTitle());
                startActivity(showMainNewsIntent);
            }
        });


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int preLast = 0;

                switch (absListView.getId()) {
                    case R.id.list:

                        final int lastItem = firstVisibleItem + visibleItemCount;

                        if (lastItem == totalItemCount) {
                            if (preLast != lastItem) {
                                moreB.setVisibility(View.VISIBLE);
                                preLast = lastItem;
                            }
                        }
                }
            }
        });
    }


    public class trackAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            while (
                    ((newNewsAsyncTask.getStatus() == Status.RUNNING)
                            || (newNewsAsyncTask.getStatus() == Status.PENDING)
                            || (newsArrayListFromAsyncTask == null))
                    ) {
                //DO NOTHING
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setAdapterOnAsyncTaskComplete();
        }

    }//End of AsyncTask trackAsyncTask

}
