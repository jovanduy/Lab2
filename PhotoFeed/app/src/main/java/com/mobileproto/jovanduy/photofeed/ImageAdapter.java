package com.mobileproto.jovanduy.photofeed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Jordan on 9/30/15.
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<String> links;

    public ImageAdapter(Context c, ArrayList<String> images) {
        this.context = c;
        this.links = images;
    }

    @Override
    public int getCount() {
        return links.size();
    }

    @Override
    public Object getItem(int position) {
        return links.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    /**
     * create a new WebView for each item referenced by the Adapter
     * @param position
     * @param convertView
     * @param parent
     * @return WebView of item
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        WebView webView;
        if (convertView == null) {
            webView = new WebView(context);
            webView.setInitialScale(35);
            webView.setLayoutParams(new GridView.LayoutParams(350, 350));
            webView.setPadding(3,3,3,3);
        } else {
            webView = (WebView) convertView;
        }

        webView.loadUrl(links.get(position));
        return webView;
    }

}
