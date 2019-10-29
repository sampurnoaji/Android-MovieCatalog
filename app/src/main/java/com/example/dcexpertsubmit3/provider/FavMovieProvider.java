package com.example.dcexpertsubmit3.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;

import com.example.dcexpertsubmit3.database.DataAccessObject;
import com.example.dcexpertsubmit3.database.FilmDatabase;

import java.util.Objects;

public class FavMovieProvider extends ContentProvider {
    private FilmDatabase database;
    private DataAccessObject dao;
    private static final String DATABASE_NAME = "film_database";
    private static final String TABLE_NAME = "Movie";
    private static final String AUTHORITY = "com.petersam.moviecatalog";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CODE_FAV_DIR = 1;
    private static final int CODE_FAV_ITEM = 2;

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, CODE_FAV_DIR);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", CODE_FAV_ITEM);
    }

    public FavMovieProvider() {
    }

    @Override
    public boolean onCreate() {
        database = Room.databaseBuilder(Objects.requireNonNull(getContext()), FilmDatabase.class, DATABASE_NAME).build();
        dao = database.dao();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int code = uriMatcher.match(uri);
        if (code == CODE_FAV_DIR || code == CODE_FAV_ITEM){
            final Context context = getContext();
            if (context == null){
                return null;
            }

            final Cursor cursor;
            if (code == CODE_FAV_DIR){
                cursor = dao.selectAll();
            } else {
                cursor = dao.selectById(ContentUris.parseId(uri));
            }

            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
}
