package neu.droid.guy.newsapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class Tutorial extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(AppIntroFragment.newInstance("NewsMapp",
                "News + Map: A cool way to learn Geography and read news",
                R.drawable.first,
                getResources().getColor(R.color.grey)));

        addSlide(AppIntroFragment.newInstance("NewsMapp",
                "Do you know where Cambodia is ? or Lesotho? or even Denmark?",
                R.drawable.second
                , getResources().getColor(R.color.cyan)));

        addSlide(AppIntroFragment.newInstance("NewsMapp",
                "Want to learn the recent events of countries by just playing with the Map?"
                , R.drawable.third,
                getResources().getColor(R.color.grey)));

        addSlide(AppIntroFragment.newInstance("NewsMapp",
                "Or just want to read news of all countries and not just the superpowers?"
                , R.drawable.fourth,
                getResources().getColor(R.color.cyan)));

        addSlide(AppIntroFragment.newInstance("NewsMapp",
                "The catch is: you have to find the country on the map to read its news"
                , R.drawable.fifth,
                getResources().getColor(R.color.grey)));

        addSlide(AppIntroFragment.newInstance("NewsMapp",
                "Then you have come to the right place. Let us get started"
                , R.drawable.sixth,
                getResources().getColor(R.color.cyan)));

        setBarColor(Color.parseColor("#263238"));
        setSeparatorColor(Color.parseColor("#263238"));

        showSkipButton(true);
        setProgressButtonEnabled(true);

        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent i = new Intent(this, CountryNewsMapsActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent i = new Intent(this, CountryNewsMapsActivity.class);
        startActivity(i);
        finish();
    }
}