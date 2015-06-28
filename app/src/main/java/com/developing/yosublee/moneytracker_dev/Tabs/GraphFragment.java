package com.developing.yosublee.moneytracker_dev.Tabs;

import com.developing.yosublee.moneytracker_dev.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developing.yosublee.moneytracker_dev.R;

/**
 * Created by yosublee on 6/23/15.
 */
public class GraphFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graphs, container, false);

        return view;
    }
}
