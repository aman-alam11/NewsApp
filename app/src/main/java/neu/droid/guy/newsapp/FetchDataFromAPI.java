package neu.droid.guy.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class FetchDataFromAPI extends ArrayList<News> {

    public static String query_URL_API = "http://content.guardianapis.com/uk-news?api-key=test&show-fields=all";
    public static String json = null;
    private static URL url_to_hit = null;


    public void FetchDataFromAPI() {
    }

    public ArrayList<News> feedToAsyncTask() throws IOException, JSONException {
        url_to_hit = convertStringToURL(query_URL_API);
        String json = make_api_call(url_to_hit);
        return parseAPIResponse(json);
    }

    //STEP 1: Convert String to URL

    private static URL convertStringToURL(String url) {
//        Log.e("convertStringToURL","INSIDE convertStringToURL");
        try {
            url_to_hit = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url_to_hit;
    }

    //STEP 2: Make the call
    public static String make_api_call(URL url) throws IOException {

        HttpURLConnection urlConnection = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                InputStream stream = urlConnection.getInputStream();
                json = readUsingBufferedStream(stream);
            }

        } catch (Exception e) {
            urlConnection.getResponseCode();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return json;
    }//End of make_api_call function


    public static String readUsingBufferedStream(InputStream stream) throws IOException {

        InputStreamReader streamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder sb = new StringBuilder();


        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }
        return sb.toString();
    }//End of readUsingBufferedStream


    public static ArrayList<News> parseAPIResponse(String res) throws JSONException {
        JSONObject mainObject = null;
        URL imageString = null;
        Bitmap thumbnail = null;
        ArrayList<News> newsArrayList = new ArrayList();

        //Assign the JSON string to a JSON object to be parsed
        try {
            mainObject = new JSONObject(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject response = mainObject.getJSONObject("response");
        JSONArray parsedArrayResultsFromAPI = response.getJSONArray("results");
        JSONObject[] arrayOfJSONObject = new JSONObject[parsedArrayResultsFromAPI.length()];
        JSONObject[] arrayOfShowFields = new JSONObject[arrayOfJSONObject.length];

        for (int i = 0; i < parsedArrayResultsFromAPI.length(); i++) {
            // Take a empty JSONArray and assign each Object and
            // make a new JSONArray for each object to iterate over its specific
            // elements of each JSON object
            arrayOfJSONObject[i] = parsedArrayResultsFromAPI.getJSONObject(i);
            arrayOfShowFields[i] = arrayOfJSONObject[i].getJSONObject("fields");
        }


        if (arrayOfJSONObject.length != 0) {

            for (int i = 0; i < parsedArrayResultsFromAPI.length(); i++) {


                JSONObject fields = arrayOfJSONObject[i].getJSONObject("fields");

                //Extract data by iterating on that JSON Array

                //The title of the news
                String titleOfNews = arrayOfJSONObject[i].getString("webTitle");
//                Log.e("TITLE", titleOfNews);


                //Set the content Snippet
                String newsSnippet = fields.getString("trailText");

                //Set the main Content
                String mainContent = fields.getString("bodyText");
//                Log.e("MAIN_CONTENT", mainContent);


                try {
                    imageString = new URL(fields.getString("thumbnail"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) imageString.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream stream = urlConnection.getInputStream();

                        thumbnail = BitmapFactory.decodeStream(stream);
                    } else {
                        urlConnection.disconnect();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                newsArrayList.add(new News(titleOfNews, newsSnippet, thumbnail, mainContent));
            }
        }

        return newsArrayList;
    }//End of parseAPIResponse

}//End of class

