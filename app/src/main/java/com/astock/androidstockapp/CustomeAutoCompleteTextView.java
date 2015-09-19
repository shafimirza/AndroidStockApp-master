package com.astock.androidstockapp;

import android.widget.AutoCompleteTextView;
import java.util.HashMap;

import android.content.Context;
import android.util.AttributeSet;
/**
 * Created by root on 3/28/15.
 */
public class CustomeAutoCompleteTextView extends AutoCompleteTextView {
    public CustomeAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /** Returns the symbol corresponding to the selected item */
    @Override
    protected CharSequence convertSelectionToString(Object selectedItem) {
        /** Each item in the autocompetetextview suggestion list is a hashmap object */
        HashMap<String, String> hm = (HashMap<String, String>) selectedItem;
            return hm.get("sym");

        }
}

