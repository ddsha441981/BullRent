package com.example.bullrent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    //Variables
    private static int SPLASH_SCREEN_TIMEOUT = 5000;
    Animation top_Animation,bottom_Animation,slogan_animation;
    ImageView imageView;
    TextView logo,slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*------------------------Hide Status Bar----------------------------*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_screen);

        /*------------------------Animation----------------------------*/
        top_Animation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom_Animation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        slogan_animation = AnimationUtils.loadAnimation(this,R.anim.slogan_animation);


        /*------------------------Hooks----------------------------*/
        imageView = findViewById(R.id.imageId);
        logo = findViewById(R.id.logoId);
        slogan = findViewById(R.id.sloganId);

        /*------------------------Apply Animation----------------------------*/
        imageView.setAnimation(top_Animation);
        logo.setAnimation(bottom_Animation);
        slogan.setAnimation(slogan_animation);




        /*------------------------Splash Screen----------------------------*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, Login.class);

                //First to Second Activity Animation
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(imageView,"car_trans");
                pairs[1] = new Pair<View,String>(logo,"logo_txt");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);

                    startActivity(intent,activityOptions.toBundle());
                }



            }
        }, SPLASH_SCREEN_TIMEOUT);

    }
}
