package com.mobileproto.jovanduy.photofeed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Jordan on 9/30/15.
 */
public class FeedFragment extends Fragment {

    public View view;
    public DbAccessor dbAccessor;
    public ArrayList<String> images;
    public int position;

    public FeedFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);
//        dbAccessor = new DbAccessor(getContext());
//        images = dbAccessor.readFromDb(position);
//        GridView gridView = (GridView) view.findViewById(R.id.feed_grid);
//        gridView.setAdapter(new ImageAdapter(getContext(), images));
        return view;
    }
}
