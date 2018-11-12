package vostore.salaoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    Animation fromlogo;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        fromlogo = AnimationUtils.loadAnimation(this, R.anim.fromlogo);
        logo = findViewById(R.id.logo);
        logo.setAnimation(fromlogo);


//        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, Loginctivity.class));
                finish();
            }
        }, 5000);

        //Delay Millis
        //is the time the activity will be open. . As it is in milliseconds, 5000 represents 5 seconds. You can change the value according to your need
    }
}
