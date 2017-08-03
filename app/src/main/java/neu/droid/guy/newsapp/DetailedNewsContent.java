package neu.droid.guy.newsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class DetailedNewsContent extends AppCompatActivity {

    private String mNewsUrl = null;
    private URL mUrlForIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news_content);

        Bundle bundle = getIntent().getExtras();
        String setTitleOfActivity = bundle.getString("HEADLINES");
        if (setTitleOfActivity != null) {
            setTitle(setTitleOfActivity.substring(0, 15) + "...");
        }


        TextView headlineTextView = (TextView) findViewById(R.id.headlineOnTop);
        TextView mainNewsTextView = (TextView) findViewById(R.id.mainNewsText);
        ImageView ig = (ImageView) findViewById(R.id.thumbnailImageOnTop);
        Button moreContent = (Button) findViewById(R.id.moreContentButton);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.shareNewsFAB);


        if (bundle.getString("MAIN_CONTENT") != null) {
            mNewsUrl = bundle.getString("NEWS_URL");
            mainNewsTextView.setText(bundle.getString("MAIN_CONTENT"));
            headlineTextView.setText(bundle.getString("HEADLINES"));
            //SET THE IMAGE
            byte[] byteArray = bundle.getByteArray("THUMBNAIL");
            Bitmap bmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ig.setImageBitmap(bmap);
            //If the content is from NYT,set the more button visible
//            if (bundle.getString("NYT_CONTENT").equals("NYT")) {
//                moreContent.setVisibility(View.VISIBLE);
//            } else {
//                moreContent.setVisibility(View.GONE);
//            }
        }

        if (mNewsUrl != null) {
            try {
                fab.setVisibility(View.VISIBLE);
                mUrlForIntent = new URL(mNewsUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(findViewById(android.R.id.content), mUrlForIntent.toString(), Snackbar.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, mUrlForIntent.toString());
                i.setType("text/plain");

                String title = "Share";
                Intent chooser = Intent.createChooser(i, title);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

//        moreContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Uri u = Uri.parse(mNewsUrl);
//                    Intent i = new Intent(Intent.ACTION_VIEW, u);
//                    startActivity(i);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
