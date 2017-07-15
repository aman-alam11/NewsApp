package neu.droid.guy.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
//                i.putExtra("Country",0);
                startActivity(i);
//                finish();
            }
        });

        ukNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, newsListView.class);
//                i.putExtra("Country",1);
                startActivity(i);
//                finish();
            }
        });

        euNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, newsListView.class);
//                i.putExtra("Country",2);
                startActivity(i);
//                finish();
            }
        });


        asNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, newsListView.class);
//                i.putExtra("Country",3);
                startActivity(i);
//                finish();
            }
        });


        //Use Loaders here to make a network call to fetch data from API and feed it here

    }


}
