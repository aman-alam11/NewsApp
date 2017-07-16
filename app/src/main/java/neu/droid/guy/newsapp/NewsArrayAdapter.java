package neu.droid.guy.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsArrayAdapter extends ArrayAdapter<News> {


    public NewsArrayAdapter(Context context, ArrayList<News> arrayList) {
        super(context, 0, arrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        // Check if the existing view is being reused, otherwise inflate the view

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }

        News news = getItem(position);

        //Set Title of News
        TextView getTitle = (TextView) listItemView.findViewById(R.id.titleNewsTextView);
        getTitle.setText(news.getTitle());

        //Set News Content Snippet
        TextView getNewsSnippet = (TextView) listItemView.findViewById(R.id.newsSnippetTextView);
        getNewsSnippet.setText(news.getContentSnippet());

        //Set the Thumbnail View of Image
        ImageView getThumbnailViewNews = (ImageView) listItemView.findViewById(R.id.newsThumbnailImage);
        getThumbnailViewNews.setImageBitmap(news.getImageResourceId());

        return listItemView;

    }

}
