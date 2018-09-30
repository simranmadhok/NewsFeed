package com.example.sndtcsi.newsfeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(3000);
                }catch (InterruptedException e){}
                finally
                {
                    Intent switch1= new Intent(SplashActivity.this, MainActivity.class);
                    //switch1.putExtra("fragment", 1);
                    startActivity(switch1);
                    finish();
                }
            }

        };
        thread.start();
    }
}
