package com.astock.androidstockapp;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

import com.astock.webservice.WebServiceCall;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    // Refresh menu item
    private MenuItem refreshMenuItem;
    final Activity a=this;
    private Fragment portfolio_fr;
    private Fragment unusualstocks_fr;
    private Fragment market;
    private Fragment options_fr;
    private ArrayList<HashMap<String,String>> tickerList=new ArrayList<HashMap<String, String>>();
    private WebServiceCall ws=new WebServiceCall();;
    private CustomeAutoCompleteTextView autoComplete;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ws=new WebServiceCall();
       // tickerList=new ArrayList<HashMap<String, String>>();
        tickerList=ws.invokeWSforTickerList(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (number) {
            case 1:
                mTitle = "Market";
                if(market==null)
                    market = new Market();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, market)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                mTitle = getString(R.string.title_section1);
                if(unusualstocks_fr==null)
                    unusualstocks_fr = new UnusualStockFragment();
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, unusualstocks_fr)
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                if(options_fr==null)
                options_fr=new OptionsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, options_fr)
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                mTitle = getString(R.string.title_section3);
                if(tickerList.isEmpty())
                    tickerList=ws.invokeWSforTickerList(this);
                System.out.println("Duplicate entry ::"+tickerList);
                if(portfolio_fr==null)
                portfolio_fr=new PortfolioFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,portfolio_fr )
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.list_divider)));
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            View v=(View)menu.findItem(R.id.action_add).getActionView();
            MenuItem item = menu.findItem(R.id.action_add);
            restoreActionBar();

            if (mTitle.equals("Stocks"))
                if (item != null)
                    item.setVisible(false);
            if (mTitle.equals("Options"))
                if (item != null)
                    item.setVisible(false);
            if (mTitle.equals("My Portfolio")) {
                if (item != null)
                    item.setVisible(true);
                if (portfolio_fr != null) {
                    // Keys used in Hashmap
                    String[] from = {"sym", "name"};
                    // Ids of views in listview_layout
                    int[] to = {R.id.sym, R.id.nme};
                    // Instantiating an adapter to store each items
                    // R.layout.listview_layout defines the layout of each item
                   SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), tickerList, R.layout.list_item, from, to);


                    // Getting a reference to CustomAutoCompleteTextView of activity_main.xml layout file
                     autoComplete = (CustomeAutoCompleteTextView) v.findViewById(R.id.autocomplete_stock);
                    /** Defining an itemclick event listener for the autocompletetextview */
                    OnItemClickListener itemClickListener = new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                            /** Each item in the adapter is a HashMap object.
                             *  So this statement creates the currently clicked hashmap object
                             * */
                            HashMap<String, String> hm = (HashMap<String, String>) arg0.getAdapter().getItem(position);
                            String symbol = hm.get("sym");
                            ws.callData(symbol, a, (PortfolioFragment) portfolio_fr);
                            autoComplete.setText("");


                        }
                    };
                    autoComplete.setThreshold(1);
                    /** Setting the adapter to the listView */
                    autoComplete.setAdapter(adapter);
                    //System.out.println("check symbol:" + tickerList);

                    /** Setting the itemclick event listener */
                    autoComplete.setOnItemClickListener(itemClickListener);


                }
            }

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.action_refresh:
                // refresh
                refreshMenuItem = item;
                // load the data from server
                new SyncData().execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    public void refreshData(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        unusualstocks_fr = new UnusualStockFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, unusualstocks_fr)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Async task to load the data from server
     * **/
    private class SyncData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // set the progress bar view
            refreshMenuItem.setActionView(R.layout.action_progressbar);

            refreshMenuItem.expandActionView();
        }

        @Override
        protected String doInBackground(String... params) {
            // not making real request in this demo
            // for now we use a timer to wait for sometime
            try {
                Thread.sleep(3000);
                refreshData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            refreshMenuItem.collapseActionView();
            // remove the progress bar view
            refreshMenuItem.setActionView(null);
        }
    };


}
