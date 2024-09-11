package com.example.weatherappandroid_java.utils;

public abstract class NetworkResult<T> {
    private final T data;
    private final String message;

    // Constructor
    protected NetworkResult(T data, String message) {
        this.data = data;
        this.message = message;
    }

    // Getters
    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    // Success class
    public static class Success<T> extends NetworkResult<T> {
        public Success(T data) {
            super(data, null);
        }
    }

    // Error class
    public static class Error<T> extends NetworkResult<T> {
        public Error(String message, T data) {
            super(data, message);
        }

        // Overloaded constructor to handle the default value of data being null
        public Error(String message) {
            super(null, message);
        }
    }

    // Loading class
    public static class Loading<T> extends NetworkResult<T> {
        public Loading() {
            super(null, null);
        }
    }
}
