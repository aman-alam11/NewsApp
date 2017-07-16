package neu.droid.guy.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


        if(bundle.getString("MAIN_CONTENT") != null){
            mainNewsTextView.setText(bundle.getString("MAIN_CONTENT"));
            headlineTextView.setText(bundle.getString("HEADLINES"));
            //SET THE IMAGE
            byte[] byteArray = bundle.getByteArray("THUMBNAIL");
            Bitmap bmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ig.setImageBitmap(bmap);
        }

    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
