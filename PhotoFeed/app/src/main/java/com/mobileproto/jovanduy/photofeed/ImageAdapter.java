package com.mobileproto.jovanduy.photofeed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Custom Adapter for Images that has an ArrayList to hold images
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<String> links;

    public ImageAdapter(Context c, ArrayList<String> images) {
        this.context = c;
        this.links = images;
    }

    /**
     * returns length of ImageAdapter's ArrayList
     * @return length of links
     */
    @Override
    public int getCount() {
        return links.size();
    }

    /**
     * returns the item at a position in the ArrayList
     * @param position of item
     * @return an element of links
     */
    @Override
    public Object getItem(int position) {
        return links.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    /**
     * create a new ImageView for each item referenced by the Adapter
     * @param position index of item in ArrayList
     * @param convertView
     * @param parent
     * @return ImageView of item
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
        } else {
            imageView = (ImageView) convertView;
        }

        // third party tool for loading images at a url into ImageView
        Picasso.with(context).load(links.get(position)).into(imageView);
        return imageView;
    }

}
