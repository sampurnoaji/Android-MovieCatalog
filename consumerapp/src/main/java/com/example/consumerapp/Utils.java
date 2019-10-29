package com.example.consumerapp;

import android.net.Uri;

class Utils {
    private static final String TABLE_NAME = "Movie";
    private static final String AUTHORITY = "com.petersam.moviecatalog";
    static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    static final String COLUMN_TITLE = "title";
    static final String COLUMN_OVERVIEW = "overview";
    static final String COLUMN_POSTER = "poster_path";
}
