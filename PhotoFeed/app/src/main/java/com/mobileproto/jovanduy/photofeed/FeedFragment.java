package com.mobileproto.jovanduy.photofeed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Jordan on 9/30/15.
 */
public class FeedFragment extends Fragment {

    public View view;
    public DbAccessor dbAccessor;
    public ArrayList<String> images;
    public ImageAdapter imageAdapter;
    public GridView gridView;

    public FeedFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        dbAccessor = new DbAccessor(getContext());
        images = dbAccessor.readFromDb();
        imageAdapter = new ImageAdapter(getContext(), images);
        gridView = (GridView) view.findViewById(R.id.feed_grid);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDeleter(position).show();
            }
        });
        return view;
    }

    public AlertDialog openDeleter(final int position) {
        AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(getContext());
        deleteBuilder.setTitle(R.string.delete_question);

        deleteBuilder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbAccessor.deleteFromDb(position);
                images.remove(position);
                imageAdapter = new ImageAdapter(getContext(), images);
                gridView.setAdapter(imageAdapter);
            }
        });

        deleteBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        return deleteBuilder.create();
    }
}
