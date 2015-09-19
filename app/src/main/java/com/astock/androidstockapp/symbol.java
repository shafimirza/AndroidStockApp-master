package com.astock.androidstockapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;


public class symbol extends ActionBarActivity {
    String title,market,open,high,low,close,price,volume,trddate,change;
    TextView opentx,closetx,hightx,lowtx,voltx,chgtx,datetx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symbol);
        Intent intent=getIntent();

        title=intent.getStringExtra("symbol");
        market=intent.getStringExtra("title");
        open=intent.getStringExtra("open");
        high=intent.getStringExtra("high");
        low=intent.getStringExtra("low");
        volume=intent.getStringExtra("volume");
        change=intent.getStringExtra("change");
        price=intent.getStringExtra("price");
        close=intent.getStringExtra("close");
        trddate=intent.getStringExtra("date");
                System.out.println("title:"+title);


        ActionBar bar=getSupportActionBar();
        opentx = (TextView) findViewById(R.id.open);
        hightx = (TextView) findViewById(R.id.high);
        lowtx = (TextView) findViewById(R.id.low);
        voltx = (TextView) findViewById(R.id.volume);
        datetx = (TextView) findViewById(R.id.date);
        closetx = (TextView) findViewById(R.id.price);
        chgtx = (TextView) findViewById(R.id.change);

        trddate="Trading Date:"+trddate;

        opentx.setText(open);
        hightx.setText(high);
        lowtx.setText(low);
        closetx.setText(price);
        voltx.setText(volume);

        if(change.contains("-"))
            chgtx.setTextColor(getResources().getColor(R.color.myColor));
        chgtx.setText(change);
        datetx.setText(trddate);





        bar.setTitle(title);
        bar.setSubtitle(market);
        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_symbol, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
