package com.example.dcexpertsubmit3.connectivity;

import com.example.dcexpertsubmit3.BuildConfig;

public class Connectivity {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static final String API_KEY = BuildConfig.TMBD_API_KEY;

    public static final String ENDPOINT_MOVIE = BASE_URL + "discover/movie?api_key=" + API_KEY + "&language=en-US";
    public static final String ENDPOINT_TVSHOW = BASE_URL + "discover/tv?api_key=" + API_KEY + "&language=en-US";

    public static final String ENDPOINT_SEARCH_MOVIE = BASE_URL + "search/movie?api_key=" + API_KEY + "&language=en-US&query=";
    public static final String ENDPOINT_SEARCH_TVSHOW = BASE_URL + "search/tv?api_key=" + API_KEY + "&language=en-US&query=";

    public static String TODAY_DATE = "";
    public static String ENDPOINT_RELEASE = BASE_URL + "discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + TODAY_DATE + "&primary_release_date.lte=" + TODAY_DATE;
}
