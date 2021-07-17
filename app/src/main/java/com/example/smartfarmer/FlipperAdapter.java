package com.example.smartfarmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FlipperAdapter extends BaseAdapter {
        Context ctx;
        int[] images;
        String[] text;
        LayoutInflater inflater;

public FlipperAdapter(Context context,String[] mytext ,int[] myimages){
        this.ctx =context;
        this.images=myimages;
        this.text=mytext;
        inflater = LayoutInflater.from(context);

        }



        @Override
public int getCount() {
        return text.length;
        }

@Override
public Object getItem(int i) {
        return null;
        }

@Override
public long getItemId(int i) {
        return 0;
        }

@Override
public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.flipper_items, null);
        TextView txtView = view.findViewById(R.id.idtext);
        ImageView txtImage =  view.findViewById(R.id.imageindex);
        txtView.setText(text[i]);
        txtImage.setImageResource(images[i]);
        return view;
        }
        }
