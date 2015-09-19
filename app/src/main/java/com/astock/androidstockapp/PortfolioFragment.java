package com.astock.androidstockapp;
import com.astock.webservice.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.BaseAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;

import com.astock.androidstockapp.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PortfolioFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView list, list_head;
    LinearLayout layout;
    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> mylist_title;
    BaseAdapter adapter_title, adapter;
    HashMap<String, String> map;

    public HashMap<String, String> map1, map2 = new HashMap<String, String>();


    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static PortfolioFragment newInstance(String param1, String param2) {
        PortfolioFragment fragment = new PortfolioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PortfolioFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);


        list = (ListView) view.findViewById(R.id.list);
        list_head = (ListView) view.findViewById(R.id.list_head);
        layout = (LinearLayout) view.findViewById(R.id.resultview);


        // Set the adapter
      //  mListView = (AbsListVinkkkkew) view.findViewById(android.R.id.list);
        //'
        // ]?((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        list.setOnItemClickListener(this);
        showActivity();

        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
           // mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
        if(list!=null) {
            System.out.println("priyannka" + (HashMap<String,String>)list.getItemAtPosition(position));
            map=(HashMap<String,String>)list.getItemAtPosition(position);
          //  System.out.println("map symbol"+map.get("symbol"));
            Intent symbolIntent = new Intent(getActivity(), symbol.class);
            symbolIntent.putExtra("title",map.get("title"));
            symbolIntent.putExtra("open",map.get("open"));
            symbolIntent.putExtra("close",map.get("close"));
            symbolIntent.putExtra("low",map.get("low"));
            symbolIntent.putExtra("high",map.get("high"));
            symbolIntent.putExtra("volume",map.get("volume"));
            symbolIntent.putExtra("change",map.get("change"));
            symbolIntent.putExtra("price",map.get("price"));
            symbolIntent.putExtra("date",map.get("date"));
            symbolIntent.putExtra("symbol",map.get("symbol"));
            symbolIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(symbolIntent);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public void showActivity() {

        System.out.println("inside show Activity");
        // System.out.println(mylist);
        // System.out.println(adapter);

        mylist_title = new ArrayList<HashMap<String, String>>();
        /********** Display the headings ************/

        map1 = new HashMap<String, String>();

        map1.put("name", "Stock");
        map1.put("price", " Price");
        map1.put("volume", " Volume");
        mylist_title.add(map1);

        try {
            adapter_title = new SimpleAdapter(getActivity(), mylist_title,
                    R.layout.portfolio_row, new String[] { "name", "price", "volume" },
                    new int[] { R.id.name, R.id.price, R.id.volume });
            list_head.setAdapter(adapter_title);
            adapter_title.notifyDataSetChanged();

        } catch (Exception e) {

        }

        /********** Display the contents ************/

        try {

            adapter = new SimpleAdapter(getActivity(), mylist, R.layout.portfolio_row,
                    new String[] { "name", "price", "volume" }, new int[] {
                    R.id.name, R.id.price, R.id.volume });
            list.setAdapter(adapter);
            this.adapter.notifyDataSetChanged();


        } catch (Exception e) {

        }

        /********************************************************/

    }

    public HashMap<String, String> getMap2() {
        return map2;
    }

    public void setMap2(HashMap<String, String> map, Activity a) {
        this.map2 = map;
        int check=0;
        System.out.println("fragment map:" + map2);
        //System.out.println("datalist:" + mylist);

        if (!mylist.isEmpty()) {
            for (int i = 0; i < mylist.size(); i++) {
                if (mylist.get(i).get("name").toString()
                        .equals(map2.get("name"))) {
                    if (!mylist.get(i).get("price").toString()
                            .equals(map2.get("price"))) {
                        mylist.get(i).put("price", map2.get("price"));
                        mylist.get(i).put("volume", map2.get("volume"));

                    }
                    break;


                }
                check++;

            }
            if(check==mylist.size()){
                mylist.add(map2);
            }
        }
        if(mylist.isEmpty())
            mylist.add(map2);
        System.out.println("datalist:" + mylist);

        //list = (ListView) a.findViewById(R.id.listView2);
        adapter = new SimpleAdapter(a, mylist, R.layout.portfolio_row, new String[] {
                "name", "price", "volume" }, new int[] { R.id.name, R.id.price,
                R.id.volume });

        list.setAdapter(adapter);
        this.adapter.notifyDataSetChanged();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
