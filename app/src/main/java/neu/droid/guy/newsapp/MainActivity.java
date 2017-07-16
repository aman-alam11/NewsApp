package neu.droid.guy.newsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<News> newsArrayListFromAsyncTask = null;
    public static newsAsyncTask newNewsAsyncTask = new newsAsyncTask();

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
                newNewsAsyncTask.execute();
                Intent i = new Intent(MainActivity.this, NewsListView.class);
                startActivity(i);
                finish();
            }
        });

        ukNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNewsAsyncTask.execute();
                Intent i = new Intent(MainActivity.this, NewsListView.class);
                startActivity(i);
                finish();
            }
        });

        euNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNewsAsyncTask.execute();
                Intent i = new Intent(MainActivity.this, NewsListView.class);
                startActivity(i);
                finish();
            }
        });


        asNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNewsAsyncTask.execute();
                Intent i = new Intent(MainActivity.this, NewsListView.class);
                startActivity(i);
                finish();
            }
        });

    }


    //TODO: Replace this asynctask with a Loader
    public static class newsAsyncTask extends AsyncTask<Void, Void, ArrayList> {


        @Override
        protected ArrayList<News> doInBackground(Void... voids) {
            ArrayList<News> list = new ArrayList<>();
            try {
                list = new FetchDataFromAPI().feedToAsyncTask();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(ArrayList a) {
            newsArrayListFromAsyncTask = a;
        }

    }


}