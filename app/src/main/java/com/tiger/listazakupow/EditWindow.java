package com.tiger.listazakupow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
  Created by Tygrysek on 3/21/2016.
 **/
public class EditWindow extends Activity implements AnimationEssentials {

    List<String> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final int pos = getPos(savedInstanceState);
        InitializeList();
        InitializeEditText(pos);

        final Button saveButton   = (Button) findViewById(R.id.savebutton);
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        final EditText text       = (EditText) findViewById(R.id.editText);

        SetCancelButtonFeatures(cancelButton, animAlpha);
        SetEditTextFeatures(saveButton, text);
        SetSaveButtonFeatures(pos, saveButton, animAlpha);
    }

    private void SetSaveButtonFeatures(final int pos, Button saveButton, final Animation animAlpha)
    {
        saveButton.getBackground().setColorFilter(0xFF7ec0ee, PorterDuff.Mode.MULTIPLY);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) findViewById(R.id.editText);
                final String txt = text.getText().toString();
                if (txt.isEmpty()) return;
                v.startAnimation(animAlpha);
                v.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        lista.set(pos, txt);
                        saveData();
                        finish();
                        Intent intent = new Intent(getApplicationContext(), ListingActivity.class);
                        startActivity(intent);
                    }
                }, delay);
            }
        });
    }

    private void SetEditTextFeatures(final Button saveButton, EditText text)
    {
        text.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {}

            @Override
            public void afterTextChanged(Editable s)
            {
                String text = s.toString();
                saveButton.getBackground().setColorFilter(text.isEmpty() ? lightgrey : lightblue, PorterDuff.Mode.MULTIPLY);
            }
        });
    }

    private void SetCancelButtonFeatures(Button cancelButton, final Animation animAlpha)
    {
        cancelButton.getBackground().setColorFilter(lightblue, PorterDuff.Mode.MULTIPLY);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.startAnimation(animAlpha);
                v.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), ListingActivity.class);
                        startActivity(intent);
                    }
                }, delay);
            }
        });
    }

    private void InitializeEditText(int pos)
    {
        String toEdit = lista.get(pos);
        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setText(toEdit);
    }

    private boolean InitializeList()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("lista", null);
        Type type = new TypeToken<List<String>>() {}.getType();
        if(json==null || type==null) return true;
        lista = gson.fromJson(json, type);
        return false;
    }

    private int getPos(Bundle savedInstanceState)
    {
        int pos;
        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            pos= extras.getInt("position");
        }
        else
        {
            pos= (int) savedInstanceState.getSerializable("position");
        }
        return pos;
    }

    private void saveData()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lista);
        editor.putString("lista", json);
        editor.apply();
    }
}
