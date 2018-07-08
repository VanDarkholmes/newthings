package com.example.machenike.ok13.adpter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by byy on 2018/6/27.
 */

public class SelectAdapter extends BaseAdapter {

    private List<View> allViews;

    public SelectAdapter(List<View> allViews) {
        this.allViews = allViews;

    }

    public SelectAdapter() {
    }

    @Override
    public int getCount() {
        return allViews.size();
    }

    @Override
    public Object getItem(int i) {
        return allViews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return allViews.get(i);
    }
}
