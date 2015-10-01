package com.mobileproto.jovanduy.photofeed;

import android.provider.BaseColumns;

/**
 * Created by Jordan on 9/27/15.
 */
public final class FeedReaderContract {

    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Feed";
        public static final String COLUMN_NAME_URL = "url";
    }

}
