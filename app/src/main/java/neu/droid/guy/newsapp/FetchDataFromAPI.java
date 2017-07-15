package neu.droid.guy.newsapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class FetchDataFromAPI {

    public static String query_URL_API = "http://content.guardianapis.com/us-news?api-key=test";
    public static String json = null;
    private URL url_to_hit = null;


    public FetchDataFromAPI() throws IOException {
//        url_to_hit = convertStringToURL(query_URL_API);
//        make_api_call(url_to_hit);
    }

    //STEP 1: Convert String to URL

    private URL convertStringToURL(String url) {
//        Log.e("convertStringToURL","INSIDE convertStringToURL");
        try {
            url_to_hit = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url_to_hit;
    }

    //STEP 2: Make the call
    public String make_api_call(URL url) throws IOException{

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


    public String readUsingBufferedStream(InputStream stream) throws IOException {

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


    private void parseAPIResponse(String res){}

}
