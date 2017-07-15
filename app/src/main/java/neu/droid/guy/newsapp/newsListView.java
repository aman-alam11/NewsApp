package neu.droid.guy.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class newsListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_view);
        Log.e("SIMPLE_NAME", newsListView.class.getSimpleName());

        //Set the adapter to the ListView here
        ArrayList news = new ArrayList();
        news.add(new News("Title of News 1","Content Snippet 1", R.drawable.us_flag, "Main News 1"));
        news.add(new News("Title of News 2","Content Snippet 2", R.drawable.uk, "Main News 2"));
        news.add(new News("Title of News 3","Content Snippet 3", R.drawable.eu, "Main News 3"));


        ListView lv = (ListView) findViewById(R.id.list);
        NewsArrayAdapter adapter = new NewsArrayAdapter(newsListView.this, news);
        lv.setAdapter(adapter);
    }

}
