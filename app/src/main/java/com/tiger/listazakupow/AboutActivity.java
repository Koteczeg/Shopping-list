package com.tiger.listazakupow;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 Created by Tygrysek on 3/19/2016.
 **/
public class AboutActivity extends AppCompatActivity implements AnimationEssentials
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final Button backButton = (Button)findViewById(R.id.backButton);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        SetBackButtonFeatures(backButton, animAlpha);

    }

    private void SetBackButtonFeatures(Button backButton, final Animation animAlpha)
    {
        backButton.getBackground().setColorFilter(lightblue, PorterDuff.Mode.MULTIPLY);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                v.startAnimation(animAlpha);
                v.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        finish();
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }, delay);
            }
        });
    }
}
