package com.astock.androidstockapp;
import com.astock.webservice.*;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;




public class TabFragment2 extends Fragment {
    ListView list, list_head;
    LinearLayout layout;
    ArrayList<HashMap<String, String>> mylist_nyse ;
    ArrayList<HashMap<String, String>> mylist_amex;
    ArrayList<HashMap<String, String>> mylist_nasdaq ;
    ArrayList<HashMap<String, String>> mylist_title;
    BaseAdapter adapter_title, adapter;
    CustomeAdapter2 arrayAdapter;
    WebServiceCall2 wsc;

    public HashMap<String, String> map1, map2 = new HashMap<String, String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wsc =new WebServiceCall2();
        mylist_nyse =new ArrayList<HashMap<String, String>>();
        mylist_amex= new ArrayList<HashMap<String, String>>();
        mylist_nasdaq = new ArrayList<HashMap<String, String>>();
        callData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater
                .inflate(R.layout.fragment_tab2, container, false);
        //TextView tv = (TextView) view.findViewById(R.id.text);
//        tv.setText(this.getTag() + " Stocks with Unusual Volume.");
        list = (ListView) view.findViewById(R.id.listView2);
        list_head = (ListView) view.findViewById(R.id.listView1);
        layout = (LinearLayout) view.findViewById(R.id.unusualresultview);
        //callData();
        showActivity();
        return view;
    }

    public void callData() {

        System.out.println(this.getTag());
        if (this.getTag().equals("NYSE")&&mylist_nyse.isEmpty())
            wsc.callUnusualData2(this.getTag(), getActivity(), this);
        if (this.getTag().equals("NASDAQ")&& mylist_nasdaq.isEmpty())
            wsc.callUnusualData2(this.getTag(), getActivity(), this);
        if (this.getTag().equals("AMEX")&& mylist_amex.isEmpty())
            wsc.callUnusualData2(this.getTag(), getActivity(), this);
    }

    public void showActivity() {

        System.out.println("inside unusual stock show Activity");

        mylist_title = new ArrayList<HashMap<String, String>>();
        /********** Display the headings ************/

        map1 = new HashMap<String, String>();

        map1.put("name", "Sector");
        map1.put("price", " Price");
        //map1.put("volume", " Volume");
        map1.put("change", "Change");
        mylist_title.add(map1);

        try {
            adapter_title = new SimpleAdapter(getActivity(), mylist_title,
                    R.layout.market_row, new String[] { "name", "price", "change" },
                    new int[] { R.id.Name, R.id.Price, R.id.Changeprice,});
            list_head.setAdapter(adapter_title);

        } catch (Exception e) {

        }

        /********************************************************/

        /********** Display the contents ************/

        try {
            if (this.getTag().equals("NYSE")) {
                arrayAdapter=new CustomeAdapter2(getActivity(),R.layout.market_row,mylist_nyse);
                //  list.setAdapter(arrayAdapter);

               /* adapter = new SimpleAdapter(getActivity(), mylist_nyse,
                        R.layout.unusualstocksrow,
                        new String[] { "name", "price", "volume","change" }, new int[] {
                        R.id.nametxt, R.id.pricetxt, R.id.volumetxt,R.id.changetxt });*/
            }
            if (this.getTag().equals("NASDAQ")) {
                arrayAdapter=new CustomeAdapter2(getActivity(),R.layout.market_row,mylist_nasdaq);

              /*  adapter = new SimpleAdapter(getActivity(), mylist_nasdaq,
                        R.layout.unusualstocksrow,
                        new String[] { "name", "price", "volume","change" }, new int[] {
                        R.id.nametxt, R.id.pricetxt, R.id.volumetxt,R.id.changetxt});*/
            }
            if (this.getTag().equals("AMEX")) {
                arrayAdapter=new CustomeAdapter2(getActivity(),R.layout.market_row,mylist_amex);

             /*   adapter = new SimpleAdapter(getActivity(), mylist_amex,
                        R.layout.unusualstocksrow,
                        new String[] { "name", "price", "volume","change" }, new int[] {
                        R.id.nametxt, R.id.pricetxt, R.id.volumetxt,R.id.changetxt });*/
            }

            list.setAdapter(arrayAdapter);
            this.arrayAdapter.notifyDataSetChanged();

        } catch (Exception e) {

        }

    }

    public void setMap2(ArrayList<HashMap<String, String>> listarray, Activity a) {
        //
        //  list = (ListView) a.findViewById(R.id.listView2);
        if (this.getTag().equals("NYSE")) {
            mylist_nyse = listarray;
            // System.out.println("datalist:" + mylist_nyse);
            arrayAdapter=new CustomeAdapter2(getActivity(),R.layout.market_row,mylist_nyse);

           /* adapter = new SimpleAdapter(a, mylist_nyse, R.layout.unusualstocksrow,
                    new String[] { "name", "price", "volume","change" }, new int[] {
                    R.id.nametxt, R.id.pricetxt, R.id.volumetxt ,R.id.changetxt});*/
        }
        if (this.getTag().equals("NASDAQ")) {
            mylist_nasdaq = listarray;
            // System.out.println("datalist:" + mylist_nasdaq);
            arrayAdapter=new CustomeAdapter2(getActivity(),R.layout.market_row,mylist_nasdaq);

           /* adapter = new SimpleAdapter(a, mylist_nasdaq, R.layout.unusualstocksrow,
                    new String[] { "name", "price", "volume","change" }, new int[] {
                    R.id.nametxt, R.id.pricetxt, R.id.volumetxt,R.id.changetxt});*/
        }
        if (this.getTag().equals("AMEX")) {
            mylist_amex = listarray;
            //   System.out.println("datalist:" + mylist_amex);
            arrayAdapter=new CustomeAdapter2(getActivity(),R.layout.market_row,mylist_amex);

            /*adapter = new SimpleAdapter(a, mylist_amex, R.layout.unusualstocksrow,
                    new String[] { "name", "price", "volume","change" }, new int[] {
                    R.id.nametxt, R.id.pricetxt, R.id.volumetxt,R.id.changetxt });*/
        }

        list.setAdapter(arrayAdapter);
        this.arrayAdapter.notifyDataSetChanged();

    }
}
