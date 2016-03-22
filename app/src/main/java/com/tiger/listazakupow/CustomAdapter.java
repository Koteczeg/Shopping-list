package com.tiger.listazakupow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 Created by Tygrysek on 3/19/2016.
 **/
class CustomAdapter extends ArrayAdapter<String>
{
    List<String> list;

    public CustomAdapter(Context context, List<String> values)
    {
        super(context, R.layout.row_layout, values);
        this.list = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.row_layout, parent, false);
        String item = getItem(position);
        TextView theTextView = (TextView) theView.findViewById(R.id.textView1);
        theTextView.setText(item);
        ImageView theImageView = (ImageView) theView.findViewById(R.id.imageView1);
        theImageView.setImageResource(R.drawable.pusheenicon);
        return theView;

    }
}
