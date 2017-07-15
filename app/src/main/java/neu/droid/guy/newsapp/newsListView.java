package neu.droid.guy.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import static neu.droid.guy.newsapp.MainActivity.newNewsAsyncTask;
import static neu.droid.guy.newsapp.MainActivity.newsArrayListFromAsyncTask;

public class newsListView extends AppCompatActivity {
    public ProgressBar pBar;
    public ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_view);
//        Log.e("SIMPLE_NAME", newsListView.class.getSimpleName());

        pBar = (ProgressBar) findViewById(R.id.progressBarToBeHidden);
        lv = (ListView) findViewById(R.id.list);
        new trackAsyncTask().execute();

    }// End of onCreate()

    public void setAdapterOnAsyncTaskComplete() {

        //Hide the progress bar
        pBar.setVisibility(View.INVISIBLE);

        //Set the adapter to the ListView here
        //Make the List View Visible
        lv.setVisibility(View.VISIBLE);
        NewsArrayAdapter adapter = new NewsArrayAdapter(newsListView.this, newsArrayListFromAsyncTask);
        lv.setAdapter(adapter);
    }


    public class trackAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            while (
                    ((newNewsAsyncTask.getStatus() == Status.RUNNING)
                            || (newNewsAsyncTask.getStatus() == Status.PENDING)
                            || (newsArrayListFromAsyncTask == null)
                    )
                    ) {
                int count = 0;
                count = count + 1;
                Log.e("COUNT VALUE: ", String.valueOf(count));
                Log.e("getStatus(): ", newNewsAsyncTask.getStatus().toString());
                Log.e("newsArrayListFrom", newsArrayListFromAsyncTask.toString());
            }

            Log.e("getStatus(): ", newNewsAsyncTask.getStatus().toString());
            Log.e("newsArrayListFrom", newsArrayListFromAsyncTask.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("POST EXECUTE: ", "INSIDE POST EXECUTE");
            super.onPostExecute(aVoid);
            setAdapterOnAsyncTaskComplete();
        }
    }

}
