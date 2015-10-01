package com.mobileproto.jovanduy.photofeed;

import java.util.ArrayList;

/**
 * Created by Jordan on 9/29/15.
 */
public interface Callback {
    public void callback(boolean success, ArrayList<String> images);
}
