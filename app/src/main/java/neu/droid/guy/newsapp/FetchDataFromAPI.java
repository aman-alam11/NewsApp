package neu.droid.guy.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

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

import static neu.droid.guy.newsapp.NewsListView.query_URL_API;


public class FetchDataFromAPI extends ArrayList<News> {


    public static String json = null;
    private static URL url_to_hit = null;

    /**
     * Starting point for API CALLS
     */
    ArrayList<News> feedToAsyncTask() throws IOException, JSONException {

        /**Convert String to URL*/
        url_to_hit = convertStringToURL(query_URL_API);

        /**Make the actual API call*/
        /**Helper function called from within to read as buffered reader*/
        String json = make_api_call(url_to_hit);


        //TODO: Remove if else later on and write a URI matcher kind of function
        if (query_URL_API.contains("guardian")) {

            /**
             * PARSE RESPONSE FROM GUARDIAN API
             * */
            return parseAPIResponse_GUARDIAN(json);
        } else if (query_URL_API.contains("nytimes")) {
            /**
             * PARSE RESPONSE FROM NYT API
             * */
            return parseAPIResponse_NYT(json);
        } else {
            Log.e("UNABLE TO HIT API", "Unable to hit any api");
        }
        return null;
    }


    /**
     * STEP 1: Convert String to URL
     */
    private static URL convertStringToURL(String url) {
        try {
            url_to_hit = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url_to_hit;
    }

    /**
     * STEP 2: Make the call
     */
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
            Log.e("ERROR_RESPONSE_CODE", String.valueOf(urlConnection.getResponseCode()));
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


    /**
     * PARSE API RESPONSE: In This Case, Response from GUARDIAN NEWS
     */
    public static ArrayList<News> parseAPIResponse_GUARDIAN(String res) throws JSONException {
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
        try {
            JSONArray parsedArrayResultsFromAPI = response.getJSONArray("results");
            JSONObject[] arrayOfJSONObject = new JSONObject[parsedArrayResultsFromAPI.length()];
            JSONObject[] arrayOfShowFields = new JSONObject[arrayOfJSONObject.length];
            String[] arrayOfWebUrls = new String[arrayOfJSONObject.length];

            for (int i = 0; i < parsedArrayResultsFromAPI.length(); i++) {
                // Take a empty JSONArray and assign each Object and
                // make a new JSONArray for each object to iterate over its specific
                // elements of each JSON object
                arrayOfJSONObject[i] = parsedArrayResultsFromAPI.getJSONObject(i);
                arrayOfShowFields[i] = arrayOfJSONObject[i].getJSONObject("fields");
                arrayOfWebUrls[i] = arrayOfJSONObject[i].getString("webUrl");
            }

            if (arrayOfJSONObject.length != 0) {

                for (int i = 0; i < parsedArrayResultsFromAPI.length(); i++) {

                    JSONObject fields = arrayOfJSONObject[i].getJSONObject("fields");

                    /**The title of the news*/
                    String titleOfNews = arrayOfJSONObject[i].getString("webTitle");

                    /**Set the content Snippet*/
                    String newsSnippet = fields.getString("trailText");

                    /**Set the main Content*/
                    String mainContent = fields.getString("bodyText");

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

                    newsArrayList.add(new News(titleOfNews, newsSnippet, thumbnail, mainContent, arrayOfWebUrls[i]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return newsArrayList;
    }//End of parseAPIResponse GUARDIAN


    /**
     * PARSE API RESPONSE: In This Case, Response from New York Times
     */
    public static ArrayList<News> parseAPIResponse_NYT(String res) throws JSONException {

        ArrayList<News> newsArrayList = new ArrayList<>();
        URL imageUrl = null;
        try {
            Bitmap thumbnail_NYT = null;

            JSONObject mainResponse = new JSONObject(res);
            JSONArray parsedResult = mainResponse.getJSONArray("results");
            JSONObject[] eachRes = new JSONObject[parsedResult.length()];


            for (int j = 0; j < parsedResult.length(); j++) {
                eachRes[j] = parsedResult.getJSONObject(j);
                String titleOfNews = eachRes[j].getString("title");
                String newsSnippet = eachRes[j].getString("abstract");
                if (eachRes[j].getJSONArray("multimedia").length() > 0) {
                    JSONArray multimedia = eachRes[j].getJSONArray("multimedia");

                    /**
                     * Get the Array of JSON Objects containing multimedia info.
                     * But we just need the first image from the first object as we need the smallest image
                     * */

                    JSONObject multimediaObject = multimedia.getJSONObject(1);
                    String imageUrlString = multimediaObject.getString("url");
                    try {
                        imageUrl = new URL(imageUrlString);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Image_URL", "Unable to convert URL String to URL");
                    }

                    //Make a network call and get the image as Bitmap
                    HttpURLConnection urlConnection = (HttpURLConnection) imageUrl.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream getImage_NYT = urlConnection.getInputStream();
                        thumbnail_NYT = BitmapFactory.decodeStream(getImage_NYT);
                        int width = (int) (thumbnail_NYT.getWidth() + 0.5f);
                        int height = (thumbnail_NYT.getHeight() * 2);
                        thumbnail_NYT = Bitmap.createScaledBitmap(thumbnail_NYT, width, height, true);

                    } else {
                        //Add alternate image
                        thumbnail_NYT = Bitmap.createBitmap(150, 300, Bitmap.Config.ARGB_8888);
                        urlConnection.disconnect();
                    }
                } else {
                    //Add alternate image
                    thumbnail_NYT = Bitmap.createBitmap(150, 300, Bitmap.Config.ARGB_4444);
                }

                newsArrayList.add(new News(titleOfNews, newsSnippet, thumbnail_NYT, newsSnippet));
            }

//            Log.e("NEWS_ARRAY_LIST_NYT", newsArrayList.toString());
        } catch (Exception e) {
            Log.e("PARSE_NYT", "Parse NYT throws Exception" + e.toString());
            e.printStackTrace();
        }
        return newsArrayList;
    }

}//End of class

