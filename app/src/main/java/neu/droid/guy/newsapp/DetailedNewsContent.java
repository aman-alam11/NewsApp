package neu.droid.guy.newsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailedNewsContent extends AppCompatActivity {

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


        if (bundle.getString("MAIN_CONTENT") != null) {
            mainNewsTextView.setText(bundle.getString("MAIN_CONTENT"));
            headlineTextView.setText(bundle.getString("HEADLINES"));
            //SET THE IMAGE
            byte[] byteArray = bundle.getByteArray("THUMBNAIL");
            Bitmap bmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ig.setImageBitmap(bmap);
            //If the content is from NYT,set the more button visible
            if (bundle.getString("NYT_CONTENT").equals("NYT")) {
                moreContent.setVisibility(View.VISIBLE);
            } else {
                moreContent.setVisibility(View.GONE);
            }
        }

        final Uri url = Uri.parse("http://alam-syedaman-webdev.herokuapp.com");

        moreContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,url);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
