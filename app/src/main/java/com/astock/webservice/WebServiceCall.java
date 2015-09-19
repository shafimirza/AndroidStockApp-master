package com.astock.webservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.app.Activity;
import android.app.Fragment;

import com.astock.androidstockapp.PortfolioFragment;
import com.astock.androidstockapp.TabFragment;
import com.astock.androidstockapp.TabFragment2;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * Created by priyanka on 3/27/15.
 */


public class WebServiceCall {

        private ArrayList<HashMap<String,String>> tickerList=new ArrayList<HashMap<String, String>>();
        PortfolioFragment pf;
        TabFragment usf;
        TabFragment2 usf2;
        HashMap<String, String> map;
        private Activity mActivity;
        AsyncHttpClient client;
        ProgressDialog prgDialog;
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, ''yy");
        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd");
        ImageView graph;
        HashMap<String, String> hm;

    public void callData(String sym,Activity a,PortfolioFragment f){
                mActivity=a;
                pf=f;
                System.out.println("inside call Data method");
                RequestParams parm = new RequestParams();
                parm.put("symbol", sym);
                invokeWS(parm);

        }
        public void callUnusualData(String sym,Activity a,TabFragment f){
                mActivity=a;
                usf=f;
                System.out.println("inside call unusual Data method");
                RequestParams parm = new RequestParams();
                parm.put("market", sym);
                invokeWSforUnusualStock(parm);

        }
        public void callUnusualData2(String sym,Activity a,TabFragment2 f){
                mActivity=a;
                usf2=f;
                System.out.println("inside call unusual Data method");
                RequestParams parm = new RequestParams();
                parm.put("market", sym);
                invokeWSforUnusualStock(parm);

        }

        /**
         * Method that performs RESTful webservice invocations
         *
         * @param        */

         public void invokeWS( RequestParams parm) {

                System.out.println("inside invoke service method");
                prgDialog = new ProgressDialog(mActivity);
                // Set Progress Dialog Text
                prgDialog.setMessage("Please wait...");
                // Set Cancelable as False
                prgDialog.setCancelable(false);

                // Show Progress Dialog
                prgDialog.show();
                // Make RESTful webservice call using AsyncHttpClient object
                client = new AsyncHttpClient();
                client.get(
                        "http://104.154.46.96:8080/stockanalysis/marketdata/displaydata",
                      //  client.get(
                       // 	"http://192.168.1.4:8080/stockanalysis/marketdata/displaydata",
                        parm, new AsyncHttpResponseHandler() {
                                // When the response returned by REST has Http response code
                                // '200'
                                @Override
                                public void onSuccess(int responseCode, Header[] responseHeaders, byte[] responsebody) {
                                        String response = new String(responsebody);

                                        // Hide Progress Dialog
                                        prgDialog.hide();
                                        System.out.println("service call is successfull.");
                                        try {
                                                // JSON Object

                                                JSONArray jarr = new JSONArray(response);
                                                JSONObject job = jarr.getJSONObject(0);
                                                String market = job.getString("Market");
                                                String close = job.getString("Close");
                                                String name = job.getString("name");
                                                String op = job.getString("Open");
                                                String hi = job.getString("High");
                                                String lo = job.getString("Low");
                                                String vol = job.getString("Volume");
                                                String sym = job.getString("Symbol");
                                                String da = job.getString("Date");
                                                String change = job.getString("change");
                                                try {
                                                     //   System.out.println("date:"+da);
                                                       Date d = sdm.parse(da);
                                                       // System.out.println("date:"+d);
                                                        da = df.format(d);
                                                        //System.out.println("date:"+da);
                                                } catch (Exception e) {

                                                }
                                                System.out.println("name:"+name);
                                                map=new HashMap<String,String>();
                                                map.put("symbol",sym);
                                                map.put("market",market+":"+sym);
                                                map.put("name",name+"\n"+market+":"+sym);
                                                map.put("price",close);
                                                map.put("volume",vol);
                                                map.put("open",op);
                                                map.put("close",close);
                                                map.put("low",lo);
                                                map.put("high",hi);
                                                map.put("low",lo);
                                                map.put("date",da);
                                                map.put("change",change);
                                                map.put("title",market);

                                                //System.out.println("map value:" + map.get("name"));
                                                //System.out.println("Test Print:"+map);
                                                pf.setMap2(map,mActivity);



                                        } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                Toast.makeText(
                                                        mActivity.getApplicationContext(),
                                                        "Error Occured [Server's JSON response might be invalid]!",
                                                        Toast.LENGTH_LONG).show();
                                                e.printStackTrace();

                                        }
                                }

                                // When the response returned by REST has Http response code
                                // other than '200'
                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                                        error) {
                                        // Hide Progress Dialog
                                        prgDialog.hide();
                                        // When Http response code is '404'
                                        if (statusCode == 404) {
                                                Toast.makeText(mActivity.getApplicationContext(),
                                                        "Requested resource not found",
                                                        Toast.LENGTH_LONG).show();
                                        }
                                        // When Http response code is '500'
                                        else if (statusCode == 500) {
                                                Toast.makeText(mActivity.getApplicationContext(),
                                                        "Something went wrong at server end",
                                                        Toast.LENGTH_LONG).show();
                                        }
                                        // When Http response code other than 404, 500
                                        else {
                                                Toast.makeText(
                                                        mActivity.getApplicationContext(),
                                                        "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
                                                        Toast.LENGTH_LONG).show();
                                        }
                                }
                        });
                client=null;
        }

        public ArrayList<HashMap<String,String>> invokeWSforTickerList(Activity a) {
                mActivity = a;
                prgDialog = new ProgressDialog(mActivity);
                // Set Progress Dialog Text
                prgDialog.setMessage("Please wait...");
                // Set Cancelable as False
                prgDialog.setCancelable(false);
                // Show Progress Dialoglocalhost:8080/stockanalysis/mm
                prgDialog.show();
                // Make RESTful webservice call using AsyncHttpClient objkeyect
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(
                        "http://104.154.46.96:8080/stockanalysis/marketdata/tickerlist",
               // client.get(
                 //       "http://192.168.1.4:8080/stockanalysis/marketdata/tickerlist",

                        new AsyncHttpResponseHandler() {
                                // When the response returned by REST has Http response code
                                // '200'
                                @Override
                                public void onSuccess(int responseCode, Header[] responseHeaders, byte[] responsebody) {
                                        String response = new String(responsebody);
                                        // Hide Progress Dialog
                                        prgDialog.hide();
                                        try {
                                                // JSON Object
                                                JSONArray jarr = new JSONArray(response);
                                                System.out.println(jarr.length());

                                                for (int i = 0; i < jarr.length(); i++) {
                                                         hm = new HashMap<String,String>();

                                                        JSONObject job = jarr.getJSONObject(i);
                                                        String name = job.getString("name");
                                                        String sym = job.getString("Symbol");
                                                        hm.put("sym", sym);
                                                        hm.put("name",name);
                                                        tickerList.add(hm);

                                                }
                                                System.out.println(tickerList);

                                        } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                Toast.makeText(
                                                        mActivity.getApplicationContext(),
                                                        "Error Occured [Server's JSON response might be invalid]!",
                                                        Toast.LENGTH_LONG).show();
                                                e.printStackTrace();

                                        }
                                }

                                // When the response returned by REST has Http response code
                                // other than '200'
                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                                        error) {
                                        // Hide Progress Dialog
                                        prgDialog.hide();
                                        // When Http response code is '404'
                                        if (statusCode == 404) {
                                                Toast.makeText(mActivity.getApplicationContext(),
                                                        "Requested resource not found",
                                                        Toast.LENGTH_LONG).show();
                                        }
                                        // When Http response code is '500'
                                        else if (statusCode == 500) {
                                                Toast.makeText(mActivity.getApplicationContext(),
                                                        "Something went wrong at server end",
                                                        Toast.LENGTH_LONG).show();
                                        }
                                        // When Http response code other than 404, 500
                                        else {
                                                Toast.makeText(
                                                        mActivity.getApplicationContext(),
                                                        "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
                                                        Toast.LENGTH_LONG).show();
                                        }
                                }
                        });
                return tickerList;

        }


        public void invokeWSforUnusualStock( RequestParams parm) {

                System.out.println("inside invoke service method");
                prgDialog = new ProgressDialog(mActivity);
                // Set Progress Dialog Text
                prgDialog.setMessage("Please wait...");
                // Set Cancelable as False
                prgDialog.setCancelable(false);

                // Show Progress Dialog
                prgDialog.show();
                // Make RESTful webservice call using AsyncHttpClient object
                final int DEFAULT_TIMEOUT = 20 * 1000;
                client = new AsyncHttpClient();
                client.setTimeout(DEFAULT_TIMEOUT);
                client.get(
                        "http://104.154.46.96:8080/stockanalysis/marketdata/unusualstock",
                //client.get(
                     //   "http://192.168.1.4:8080/stockanalysis/marketdata/unusualstock",
                        parm, new AsyncHttpResponseHandler() {
                                // When the response returned by REST has Http response code
                                // '200'
                                @Override
                                public void onSuccess(int responseCode, Header[] responseHeaders, byte[] responsebody) {
                                        String response = new String(responsebody);
                                        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
                                        // Hide Progress Dialog
                                        prgDialog.hide();
                                        System.out.println("service call is successfull.");
                                        try {
                                                // JSON Object

                                                JSONArray jarr = new JSONArray(response);
                                                System.out.println(jarr.length());
                                                for (int i = 0; i < jarr.length(); i++) {
                                                        JSONArray jarr1 = jarr.getJSONArray(i);
                                                        JSONObject job=jarr1.getJSONObject(0);
                                                        String market = job.getString("Market");
                                                        String close = job.getString("Close");
                                                        String name = job.getString("name");
                                                        String op = job.getString("Open");
                                                        String hi = job.getString("High");
                                                        String lo = job.getString("Low");
                                                        String vol = job.getString("Volume");
                                                        String sym = job.getString("Symbol");
                                                        String da = job.getString("Date");
                                                        String chg=job.getString("change");
                                                        try {
                                                                Date d = new Date();
                                                                d = sdm.parse(da);
                                                                da = df.format(d);
                                                        } catch (Exception e) {

                                                        }
                                                        System.out.println("name:"+name);
                                                        map=new HashMap<String,String>();
                                                        map.put("symbol",sym);
                                                        map.put("market",market+":"+sym);
                                                        map.put("name",name+"\n"+market+":"+sym);
                                                        map.put("price",close);
                                                        map.put("volume",vol);
                                                        map.put("change",chg);
                                                        System.out.println("map value:"+map.get("name"));
                                                        System.out.println("Test Print:"+map);
                                                        mylist.add(map);


                                                }
                                                usf.setMap2(mylist,mActivity);



                                        } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                Toast.makeText(
                                                        mActivity.getApplicationContext(),
                                                        "Error Occured [Server's JSON response might be invalid]!",
                                                        Toast.LENGTH_LONG).show();
                                                         e.printStackTrace();

                                        }

                                }

                                // When the response returned by REST has Http response code
                                // other than '200'
                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                                        error) {
                                        // Hide Progress Dialog
                                        prgDialog.hide();
                                        // When Http response code is '404'
                                        if (statusCode == 404) {
                                                Toast.makeText(mActivity.getApplicationContext(),
                                                        "Requested resource not found",
                                                        Toast.LENGTH_LONG).show();
                                        }
                                        // When Http response code is '500'
                                        else if (statusCode == 500) {
                                                Toast.makeText(mActivity.getApplicationContext(),
                                                        "Something went wrong at server end",
                                                        Toast.LENGTH_LONG).show();
                                        }
                                        // When Http response code other than 404, 500
                                        else {
                                                Toast.makeText(
                                                        mActivity.getApplicationContext(),
                                                        "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]",
                                                        Toast.LENGTH_LONG).show();
                                                System.out.println("priyanka check:"+statusCode);

                                        }
                                }
                        });
                client=null;
        }



}
