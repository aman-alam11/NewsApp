package neu.droid.guy.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class newsListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_view);


        //Set the adapter to the ListView here
        ArrayList news = new ArrayList();
        news.add(new News("Title of News 1","Content Snippet 1","Main News 1"));


        ListView lv = (ListView) findViewById(R.id.list);
        NewsArrayAdapter adapter = new NewsArrayAdapter(newsListView.this, news);
        lv.setAdapter(adapter);
    }
}
