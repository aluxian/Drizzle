package com.aluxian.drizzle.utils;

import android.text.format.DateUtils;

public class Config {

    /** There have to be at least this many unloaded mItems in a grid, otherwise more mItems are loaded. */
    public static final int LOAD_ITEMS_THRESHOLD = 6;

    /** The number of results to load at a time when making requests to the Dribbble API. */
    public static final int RESULTS_PER_PAGE = 50;

    /** The endpoint where Dribbble API requests are made. */
    public static final String API_ENDPOINT = "https://api.dribbble.com/v1";

    /** App ID used for user authorization. */
    public static final String API_CLIENT_ID = "a2e36216a48709e24ab07c74e275fd127be662f706da238c6179c8dce8c20b8b";

    /** App secret used for user authorization. */
    public static final String API_CLIENT_SECRET = "7080f16ec3ee01626b7e043edde715d8dd2c0e1f8c397a28a78094c256b1dcaa";

    /** A token used for read-only API requests. */
    public static final String API_CLIENT_TOKEN = "ff549b889305c04600bab572bc1e9f90fd61272c5eb803bcd5ad5d68b4afa120";

    /** The maximum cache size for API requests. */
    public static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    /** Cached responses older than this are declared stale and won't be used. */
    public static final long CACHE_TIMEOUT = DateUtils.HOUR_IN_MILLIS;

}
