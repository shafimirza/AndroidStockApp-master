package com.astock.androidstockapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.astock.androidstockapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomeAdapter2 extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<HashMap<String, String>> values;
    private Context context;
    private int textViewResourceId;

    public CustomeAdapter2(Context context,int textViewResourceId, ArrayList<HashMap<String, String>> mylist) {
        mInflater = LayoutInflater.from(context);
        values = mylist;
        this.context=context;
        this.textViewResourceId=textViewResourceId;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if(convertView == null) {
            view = mInflater.inflate(textViewResourceId, parent, false);
            holder = new ViewHolder();
            holder.change = (TextView) view.findViewById(R.id.Name);
            holder.name = (TextView) view.findViewById(R.id.Price);
            holder.price = (TextView) view.findViewById(R.id.Changeprice);
            holder.slash= (TextView)view.findViewById(R.id.slash);
            holder.volume = (TextView) view.findViewById(R.id.Changeperc);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        holder.name.setText(values.get(position).get("name"));
        holder.price.setText(values.get(position).get("price"));
        holder.volume.setText(values.get(position).get("volume"));
        holder.slash.setText("/");
        holder.change.setText(values.get(position).get("change"));

        //if(values.get(position).get("change").contains("-"))
        //    holder.change.setTextColor(Color.RED);
       // else
        //    holder.change.setTextColor(Color.GREEN);


        return view;
    }

    private class ViewHolder {
        public TextView change;
        public TextView name;
        public TextView price;
public TextView slash;
        public TextView volume;
    }
}