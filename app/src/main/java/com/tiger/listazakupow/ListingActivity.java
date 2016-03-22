package com.tiger.listazakupow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 Created by Tygrysek on 3/19/2016.
 **/
public class ListingActivity extends AppCompatActivity implements AnimationEssentials
{
    final List<String> list = new LinkedList<>();
    ListAdapter theAdapter;
    ListView theListView;
    int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        final Button backButton   = (Button)findViewById(R.id.backButton);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        final Button addButton    = (Button) findViewById(R.id.addItem);
        final Button removeButton = (Button) findViewById(R.id.removeButton);
        final Button editButton   = (Button) findViewById(R.id.editButton);
        final EditText text       = (EditText)findViewById(R.id.textItem);

        SetRemoveButtonEditButtonColorChanging(removeButton, editButton, text);
        SetEditTextFeatures(addButton, text);
        SetBackButtonFeatures(backButton, animAlpha);
        InitializeAdapterAndListView();
        SetAddButtonFeatures(animAlpha, addButton);
        SetRemoveButtonFeatures(animAlpha, removeButton);
        SetEditButtonFeatures(animAlpha, editButton);
    }

    private void SetRemoveButtonEditButtonColorChanging(final Button removeButton, final Button editButton, EditText text)
    {
        text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                removeButton.getBackground().setColorFilter(lightgrey, PorterDuff.Mode.MULTIPLY);
                editButton.getBackground().setColorFilter(lightgrey, PorterDuff.Mode.MULTIPLY);
            }
        });
        text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                removeButton.getBackground().setColorFilter(lightgrey, PorterDuff.Mode.MULTIPLY);
                editButton.getBackground().setColorFilter(lightgrey, PorterDuff.Mode.MULTIPLY);
                return false;
            }
        });
    }

    private void SetEditTextFeatures(final Button addButton, EditText text)
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
                addButton.getBackground().setColorFilter(text.isEmpty() ? lightgrey : lightblue, PorterDuff.Mode.MULTIPLY);
            }
        });
    }

    private void SetAddButtonFeatures(final Animation animAlpha, Button addButton)
    {
        addButton.getBackground().setColorFilter(lightgrey, PorterDuff.Mode.MULTIPLY);
        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText text = (EditText) findViewById(R.id.textItem);
                String txt = text.getText().toString();
                if (txt.isEmpty()) return;
                v.startAnimation(animAlpha);
                v.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EditText text = (EditText) findViewById(R.id.textItem);
                        String txt = text.getText().toString();
                        if (txt.isEmpty()) return;
                        list.add(txt);
                        text.setText("");
                        ListAdapter theAdapter = new CustomAdapter(getApplicationContext(), list);

                        final ListView theListView = (ListView) findViewById(R.id.listShopping);
                        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                            {
                                if (pos != -1)
                                {
                                    View v = theListView.getChildAt(pos);
                                    if (v != null)
                                    {
                                        v.setBackgroundColor(Color.WHITE);
                                    }
                                }
                                setPos(position);
                                view.setBackgroundColor(lightblue);
                            }
                        });
                        theListView.setAdapter(theAdapter);
                    }
                }, delay);
                HideKeyboard();
            }
        });
    }

    private void setPos(int position)
    {
        pos = position;
        final Button removeButton = (Button) findViewById(R.id.removeButton);
        final Button editButton   = (Button) findViewById(R.id.editButton);
        removeButton.getBackground().setColorFilter(pos==-1 ? lightgrey : lightblue, PorterDuff.Mode.MULTIPLY);
        editButton.getBackground().setColorFilter(pos==-1 ? lightgrey : lightblue, PorterDuff.Mode.MULTIPLY);
    }

    private void HideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
    }

    private void SetRemoveButtonFeatures(final Animation animAlpha, final Button removeButton)
    {
        removeButton.getBackground().setColorFilter(lightgrey, PorterDuff.Mode.MULTIPLY);
        removeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(pos==-1) return;
                v.startAnimation(animAlpha);
                v.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(pos==-1) return;
                        list.remove(pos);
                        setPos(-1);
                        removeButton.setActivated(false);
                        ListAdapter theAdapter = new CustomAdapter(getApplicationContext(), list);
                        final ListView theListView = (ListView) findViewById(R.id.listShopping);
                        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                            {
                                if (pos != -1)
                                {
                                    View v = theListView.getChildAt(pos);
                                    if (v != null)
                                    {
                                        v.setBackgroundColor(Color.WHITE);
                                    }
                                }
                                setPos(position);
                                view.setBackgroundColor(lightblue);
                            }
                        });
                        theListView.setAdapter(theAdapter);
                    }
                }, delay);

            }
        });
    }

    private void SetEditButtonFeatures(final Animation animAlpha, Button editButton)
    {
        editButton.getBackground().setColorFilter(lightgrey, PorterDuff.Mode.MULTIPLY);
        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(pos==-1) return;
                v.startAnimation(animAlpha);
                v.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        saveData();
                        Intent intent = new Intent(getApplicationContext(), EditWindow.class);
                        intent.putExtra("position", pos);
                        startActivity(intent);
                    }
                }, delay);
            }
        });
    }

    private void InitializeAdapterAndListView()
    {
        theAdapter = new CustomAdapter(this, list);
        theListView = (ListView) findViewById(R.id.listShopping);
        theListView.setAdapter(theAdapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(pos!=-1)
                {
                    View v=theListView.getChildAt(pos);
                    if(v!=null)
                    {
                        v.setBackgroundColor(Color.WHITE);
                    }
                }
                setPos(position);
                view.setBackgroundColor(lightblue);
            }
        });
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

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        saveData();
        super.onSaveInstanceState(outState);
    }

    private void loadData()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("lista", null);
        Type type = new TypeToken<List<String>>(){}.getType();
        if(json==null || type==null)
            return;
        List<String> lista = gson.fromJson(json, type);
        list.clear();
        for (String item: lista)
        {
            list.add(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        setPos(item.getItemId());
        Button removeButton = (Button) findViewById(R.id.removeButton);
        removeButton.setActivated(true);
        return super.onOptionsItemSelected(item);
    }

    private void saveData()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("lista", json);
        editor.apply();
    }
}
