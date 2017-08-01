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

    public static String query_URL_API = "http://content.guardianapis.com/uk-news?api-key=test&show-fields=all";
    public static ArrayList<News> newsArrayListFromAsyncTask = null;
    public static ArrayList<News> usaNewsArrayListFromAsyncTask = null;
    public static newsAsyncTask newNewsAsyncTask = new newsAsyncTask();
    public static nytNewsAsyncTask usNewsAsyncTask = new nytNewsAsyncTask();

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
                query_URL_API = "https://api.nytimes.com/svc/topstories/v2/home.json?&api-key=";
                if (!usNewsAsyncTask.getStatus().toString().equals("FINISHED")) {
                    usNewsAsyncTask.execute();
                }
                Intent i = new Intent(MainActivity.this, NewsListView.class);
                startActivity(i);
            }
        });

        ukNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newNewsAsyncTask.getStatus().toString().equals("FINISHED")) {
                    newNewsAsyncTask.execute();
                }

                Intent i = new Intent(MainActivity.this, NewsListView.class);
                startActivity(i);
            }
        });

        euNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newNewsAsyncTask.getStatus().toString().equals("FINISHED")) {
                    newNewsAsyncTask.execute();
                }
                Intent i = new Intent(MainActivity.this, NewsListView.class);
                startActivity(i);
            }
        });


        asNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newNewsAsyncTask.getStatus().toString().equals("FINISHED")) {
                    newNewsAsyncTask.execute();
                }
                Intent i = new Intent(MainActivity.this, NewsListView.class);
                startActivity(i);
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
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList a) {
            newsArrayListFromAsyncTask = a;
        }
    }

    //US NEWS ASYNC TASK
    public static class nytNewsAsyncTask extends AsyncTask<Void, Void, ArrayList> {

        @Override
        protected ArrayList<News> doInBackground(Void... voids) {
            ArrayList<News> list = new ArrayList<>();
            try {
                list = new FetchDataFromAPI().feedToAsyncTaskUSNEWS();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList a) {
            usaNewsArrayListFromAsyncTask = a;
        }
    }


}