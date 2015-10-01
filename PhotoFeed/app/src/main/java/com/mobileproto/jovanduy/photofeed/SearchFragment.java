package com.mobileproto.jovanduy.photofeed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    public Button loadMoreButton; // load more images
    public ArrayList<String> links = new ArrayList<>();
    public DbAccessor dbAccessor;
    public String searchText;
    public int index; // start google query at this result index

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        editText = (EditText)view.findViewById(R.id.editText);
        button = (Button)view.findViewById(R.id.search_button);
        loadMoreButton = (Button)view.findViewById(R.id.load_more_button);
        loadMoreButton.setVisibility(View.INVISIBLE);
        index = 1;

        dbAccessor = new DbAccessor(getContext());
        if (!links.isEmpty()) {
            GridView gridView = (GridView) view.findViewById(R.id.gridView);
            gridView.setAdapter(new ImageAdapter(getContext(), links));
            loadMoreButton.setVisibility(View.VISIBLE);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openAdder(links.get(position)).show();
                }
            });
            loadMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadMore();
                }
            });
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); // hide keyboard
                searchText = editText.getText().toString();
                if (searchText != null && !searchText.isEmpty()) {
                    index = 1;
                    links.clear();
                    makeRequestWithCallback(searchText);
                }
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
        handler.searchImages(search, index, new Callback() {
            @Override
            public void callback(boolean success, final ArrayList<String> images) {
                GridView gridView = (GridView) view.findViewById(R.id.gridView);
                links.addAll(images);
                gridView.setAdapter(new ImageAdapter(getContext(), links));
                loadMoreButton.setVisibility(View.VISIBLE);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        openAdder(images.get(position)).show();
                    }
                });
                loadMoreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadMore();
                    }
                });

            }
        });

    }

    /**
     * loads next 9 results from google
     */
    public void loadMore() {
        index +=9;
        makeRequestWithCallback(searchText);
    }

    /**
     * opens AlertDialog to ask if user wants to add selected image to feed
     * @param url URL of image clicked
     * @return AlertDialog to add image to feed
     */
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
