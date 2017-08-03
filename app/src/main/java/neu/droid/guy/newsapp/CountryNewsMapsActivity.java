package neu.droid.guy.newsapp;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CountryNewsMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions mCountryNameMarker;
    private String newsPaperName;
    private String mCountryName = null;
    private Snackbar showNewsPaper = null;
    public static String query_URL_API = null;
    public static int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_news_maps);
        /** Obtain the SupportMapFragment and get notified when the map is ready to be used.*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoom = 4;
        double Latitude = 53.400088;
        double Longitude = 16.258973;
        LatLng latLng = new LatLng(Latitude, Longitude);
        if (count < 1) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            count++;
        }
        mCountryNameMarker = new MarkerOptions();

        /**Add an icon to the marker*/
        mCountryNameMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_newspaper_pin));

        /**Add a listener to the map*/
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mMap.clear();

                /**
                 * Check if there is already a Snackbar
                 * If there is, dismiss it first
                 * */
                if (showNewsPaper != null) {
//                    Log.e("SNACK", showNewsPaper.toString());
                    showNewsPaper.dismiss();
                }

                /**Get the position on click and add it to marker*/
                mCountryNameMarker.position(latLng);

                /**Move camera to that position*/
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                /**Get country name where the marker was placed*/
                Geocoder geocoder = new Geocoder(CountryNewsMapsActivity.this, Locale.getDefault());
                try {
                    List<Address> jsonAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                    Log.e("JSONADDRESS", jsonAddress.toString());
                    if (jsonAddress.size() > 0) {

//                        Toast.makeText(CountryNewsMapsActivity.this, String.valueOf(jsonAddress.get(0)), Toast.LENGTH_LONG).show();
                        /**Get the country name*/
                        mCountryName = jsonAddress.get(0).getCountryName();

                        /**Add a title to the marker*/
                        mCountryNameMarker.title(jsonAddress.get(0).getCountryName()).visible(true);

                        /**Add newspaper name to marker as snippet*/
                        addTitleToMarker();

                        /** Add a marker on click*/
                        mMap.addMarker(mCountryNameMarker).showInfoWindow();
                    } else {
                        Snackbar.make(getWindow().getDecorView(),
                                "Oops, the newspaper got wet here! Sorry",
                                Snackbar.LENGTH_SHORT).show();
//                        addTitleToMarker();
                    }

                } catch (IOException e) {
//                    Log.e("IOEXCEPTION", e.toString());
                    e.printStackTrace();
                }
            }
        });//End of onMapClick Listener

    }//End of onMapReady


    private void addTitleToMarker() {
        int val = 0;
        switch (mCountryName) {
//            case "United States":
//                query_URL_API = "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=";
//                newsPaperName = "Read New York Times";
//                break;


            //Planned Toronto Star
//            case "Canada":
//                query_URL_API = "http://content.guardianapis.com/search?q=canada&api-key=test&show-fields=all";
//                newsPaperName = "Canadian Content";
//                break;


            //Planned De Ziet
//            case "Germany":
//                query_URL_API = "http://content.guardianapis.com/search?q=germany&api-key=test&show-fields=all";
//                newsPaperName = "Read The Bild";
//                newsPaperName = "German news in English";
//                break;


            //Planned Le Monde
//            case "France":
//                newsPaperName = "Read French News in English";
//                query_URL_API = "http://content.guardianapis.com/search?q=france&api-key=test&show-fields=all";
//                break;

            //Planned TOI
//            case "India":
//                newsPaperName = "Read Indian News";
//                query_URL_API = "http://content.guardianapis.com/search?q=india&api-key=test&show-fields=all";
//                break;

//            case "Australia":
//                newsPaperName = "Read The Guardian Australia Edition";
//                query_URL_API = "http://content.guardianapis.com/search?q=australia&api-key=test&show-fields=all";
//                break;

            case "United Kingdom":
                query_URL_API = "http://content.guardianapis.com/uk-news?api-key=test&show-fields=all";
                newsPaperName = "Read The Guardian";
                break;

            default:
//                newsPaperName = "Sorry, We do not support this country for now";
                newsPaperName = "Read " + mCountryName + "'s news in English";
                query_URL_API = "http://content.guardianapis.com/search?q=" + mCountryName + "&api-key=test&show-fields=all";
//                val = -1;
                break;
        }

        showNewsPaper = Snackbar.make(findViewById(android.R.id.content), newsPaperName, Snackbar.LENGTH_INDEFINITE);
        /**Show snackbar whenever user selects one of the countries which we support*/
        if (val != -1) {
            showNewsPaper.show();
        }

        /**Start the intent if user confirms reading newspaper*/
        showNewsPaper.setAction("READ", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CountryNewsMapsActivity.this, NewsListView.class);
                i.putExtra("URL_TO_HIT", query_URL_API);
                i.putExtra("COUNTRY_NAME", mCountryName);
                startActivity(i);
                finish();
            }
        });

        mCountryNameMarker.snippet(newsPaperName).visible(true);
    }

}