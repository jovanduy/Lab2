package com.mobileproto.jovanduy.photofeed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Fragment for searching
 */
public class SearchFragment extends Fragment {
    public View view;
    public EditText editText;
    public Button button;
    public ArrayList<String> links = new ArrayList<>();;
    public DbAccessor dbAccessor;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        editText = (EditText)view.findViewById(R.id.editText);
        button = (Button)view.findViewById(R.id.search_button);

        dbAccessor = new DbAccessor(getContext());
        if (!links.isEmpty()) {
            GridView gridView = (GridView) view.findViewById(R.id.gridView);
            gridView.setAdapter(new ImageAdapter(getContext(), links));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openAdder(links.get(position)).show();
                }
            });
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editText.getText().toString();
                makeRequestWithCallback(searchText);
            }
        });
        return view;
    }

    /**
     * Make the http get request to search
     * @param search the String being searched for
     */
    public void makeRequestWithCallback(String search) {
        HttpHandler handler = new HttpHandler(getContext());
        handler.searchImages(search, new Callback() {
            @Override
            public void callback(boolean success, final ArrayList<String> images) {
                GridView gridView = (GridView) view.findViewById(R.id.gridView);
                gridView.setAdapter(new ImageAdapter(getContext(), images));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openAdder(images.get(position)).show();
                    }
                });
                links = images;
            }
        });

    }

    public AlertDialog openAdder(final String url) {
        AlertDialog.Builder addBuilder = new AlertDialog.Builder(getContext());
        addBuilder.setTitle(R.string.add_question);

        addBuilder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbAccessor.writeToDb(url);
            }
        });

        addBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        return addBuilder.create();
    }

}
