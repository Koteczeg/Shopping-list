package com.tiger.listazakupow;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements AnimationEssentials
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button aboutButton = (Button)findViewById(R.id.aboutButton);
        final Button startButton = (Button)findViewById(R.id.startButton);
        final Button exitButton = (Button)findViewById(R.id.exitButton);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        SetAboutButtonFeatures(aboutButton, animAlpha);
        SetStartButtonFeatures(startButton, animAlpha);
        SetExitButtonFeatures(exitButton);

    }

    private void SetExitButtonFeatures(Button exitButton)
    {
        exitButton.getBackground().setColorFilter(lightblue, PorterDuff.Mode.MULTIPLY);
        exitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                System.exit(0);
            }});
    }

    private void SetStartButtonFeatures(Button startButton, final Animation animAlpha)
    {
        startButton.getBackground().setColorFilter(lightblue, PorterDuff.Mode.MULTIPLY);
        startButton.setOnClickListener(new View.OnClickListener()
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
                        Intent intent = new Intent(v.getContext(), ListingActivity.class);
                        startActivity(intent);
                    }
                }, delay);

            }
        });
    }

    private void SetAboutButtonFeatures(Button aboutButton, final Animation animAlpha)
    {
        aboutButton.getBackground().setColorFilter(lightblue, PorterDuff.Mode.MULTIPLY);
        aboutButton.setOnClickListener(new View.OnClickListener()
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
                        Intent intent = new Intent(v.getContext(), AboutActivity.class);
                        startActivity(intent);
                    }
                }, delay);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
