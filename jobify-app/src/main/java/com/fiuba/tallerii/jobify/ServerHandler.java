package com.fiuba.tallerii.jobify;

import android.content.Context;

public class ServerHandler
{
    private static ServerHandler sServerHandler;

    private String mServerIP;
    private boolean mAuthenticated;

    public static ServerHandler get(Context context) {
        if (sServerHandler == null) {
            sServerHandler = new ServerHandler(context);
        }
        return sServerHandler;
    }

    private ServerHandler(Context context)
    {
        mServerIP = "127.0.0.1";
        mAuthenticated = false;
    }

    public boolean Authenticate(String email, String password)
    {
        return true;
    }

    public String getServerIP()
    {
        return mServerIP;
    }

    public void setServerIP(String serverIP)
    {
        mServerIP = serverIP;
    }
}
