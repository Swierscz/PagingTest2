package com.example.pagingtest2;

public class NetworkCache {
    private static NetworkCache INSTANCE;

    public static NetworkCache getInstance(){
        if(INSTANCE == null){
            synchronized (NetworkCache.class){
                if(INSTANCE == null){
                    INSTANCE = new NetworkCache();
                }
            }
        }
        return INSTANCE;
    }


    public static boolean isLoading = false;
    public static int offset = 1;


}
