package com.fiuba.tallerii.jobify;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * Singleton to handle connections with app server
 */
public class ServerHandler
{
    private static ServerHandler sServerHandler;

    private static final int BUFFER_SIZE = 1024;

    private String mServerIP;
    private boolean mAuthenticated;

    /*****************************************************************
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private ArrayList<String> mDummyCredentials;

    public ArrayList<String> getCredentials()
    {
        return mDummyCredentials;
    }
    public void addUser(User user)
    {
        String newCredential = new String(user.getEmail() + ":" + user.getPassword());
        mDummyCredentials.add(newCredential);
    }
    /***************************************************/
    public static ServerHandler get(Context context) {
        if (sServerHandler == null) {
            sServerHandler = new ServerHandler(context);
        }
        return sServerHandler;
    }

    /**
     * private constructor
     * initialize class members
     */
    private ServerHandler(Context context)
    {
        mServerIP = "127.0.0.1";
        mAuthenticated = false;
        mDummyCredentials = new ArrayList<String>();
    }

    /**
     *
     * Temporary method - learning http
     */
    public byte[] getUrlBytes(String urlSpec) throws IOException
    {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            //Read bytes and write them in buffer
            int bytesRead = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = in.read(buffer)) > 0)
            {
                out.write(buffer, 0, bytesRead);
            }
            out.close();

            return out.toByteArray();
        }
        finally
        {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException
    {
        return new String(getUrlBytes(urlSpec));
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
