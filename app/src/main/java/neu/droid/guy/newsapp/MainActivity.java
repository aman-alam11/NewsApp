package neu.droid.guy.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView usNews = (TextView) findViewById(R.id.usNews);
        TextView ukNews = (TextView) findViewById(R.id.ukNews);
        TextView euNews = (TextView) findViewById(R.id.euNews);
        TextView asNews = (TextView) findViewById(R.id.asiaNews);


        usNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, newsListView.class);
            }
        });

        ukNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, newsListView.class);
            }
        });

        euNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, newsListView.class);
            }
        });


        asNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, newsListView.class);
            }
        });


        //Use Loaders here to make a network call to fetch data from API and feed it here
        getLoaderManager().initLoader(0,null, MainActivity.this);
    }


    @Override
    public Loader<Object> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object o) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    


}
