package com.example.twidy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

public class DefaultInternetChecker implements InternetChecker {
    private Context context;

    @Inject
    public DefaultInternetChecker(Context context){
        this.context = context;
    }

    //проверка активного подключения к интернету
    @Override
    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
