package com.aluxian.drizzle.utils;

public interface Function<T> {

    /**
     * Apply the function with the given argument.
     *
     * @param arg An argument.
     */
    public void apply(T arg);

}
