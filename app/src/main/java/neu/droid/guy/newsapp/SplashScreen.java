package neu.droid.guy.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //  Initialize SharedPreferences
        SharedPreferences getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        //  Create a new boolean and preference and set it to true
        boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

        //  If the activity has never started before...
        if (isFirstStart) {

            //  Launch app intro
            final Intent i = new Intent(SplashScreen.this, Tutorial.class);
            startActivity(i);
            finish();

            //  Make a new preferences editor
            SharedPreferences.Editor e = getPrefs.edit();

            //  Edit preference to make it false because we don't want this to run again
            e.putBoolean("firstStart", false);

            //  Apply changes
            e.apply();
        } else {

            /**Start a time for splash screen*/
            new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long l) {
                }

                @Override
                public void onFinish() {
                    Intent i = new Intent(SplashScreen.this, CountryNewsMapsActivity.class);
                    startActivity(i);
                    finish();
                }
            }.start();
        }
    }
}