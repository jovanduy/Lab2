package com.mobileproto.jovanduy.photofeed;

import java.util.ArrayList;

/**
 * Callback function interface
 */
public interface Callback {
    public void callback(boolean success, ArrayList<String> images);
}
