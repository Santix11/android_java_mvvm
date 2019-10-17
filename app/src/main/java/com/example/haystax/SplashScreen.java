package com.example.haystax;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends BaseActivity
{
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        getSupportActionBar().hide();

        tv = (TextView)findViewById(R.id.tv);
        iv = (ImageView)findViewById(R.id.iv);


        Animation anim = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.mytransition);
        tv.startAnimation(anim);
        iv.startAnimation(anim);

        final Intent i = new Intent(this,IncidentListActivity.class);
        Thread timer = new Thread(){
            public void run()
            {
                try
                {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(i);
                    finish();
                }
            }
        };

        timer.start();
    }
}
