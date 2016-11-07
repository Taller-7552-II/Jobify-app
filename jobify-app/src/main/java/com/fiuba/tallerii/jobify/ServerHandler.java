package com.fiuba.tallerii.jobify;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Singleton to handle connections with app server
 */
public class ServerHandler
{
    private final static String USER_AGENT = "Mozilla/5.0";

    private static ServerHandler sServerHandler;

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
        String newCredential = user.getEmail() + ":" + user.getPassword();
        mDummyCredentials.add(newCredential);
    }

    /***************************************************/
    public static ServerHandler get(Context context)
    {
        if (sServerHandler == null)
        {
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


    /*******************************************
     * ASYNC Methods
     *******************************************/

    public String GET(String urlSpec)
    {
        try
        {
            URL url = new URL(urlSpec);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //add header
            connection.setRequestProperty("User-Agent", USER_AGENT);
            //set request as GET
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                Log.i("HTTP GET", "Successful connection to " + urlSpec);
                InputStream responseBody = connection.getInputStream();
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                BufferedReader streamReader = new BufferedReader(responseBodyReader);
                StringBuilder responseStrBuilder = new StringBuilder();

                //get JSON String
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                connection.disconnect();
                return responseStrBuilder.toString();
            } else
            {
                connection.disconnect();
                Log.d("HTTP GET", "Error connecting to " + urlSpec);
                return "";
            }

        } catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public String POST(String urlSpec, String parameters)
    {
        try {
            URL url = new URL(urlSpec);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //add header
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            connection.setRequestProperty("content-type", "application/json");
            //set request as GET
            connection.setRequestMethod("POST");

            //Create the data
            connection.setDoOutput(true);
            connection.getOutputStream().write(parameters.getBytes());

            //get response
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("HTTP POST", "Succesful connection to " + urlSpec);
                // Get InputStream
                InputStream is = connection.getInputStream();
                // Convert the InputStream into a string
                String charset = "UTF-8";
                BufferedReader r = new BufferedReader(new InputStreamReader(is, charset));
                StringBuilder total = new StringBuilder();
                String line;

                while ((line = r.readLine()) != null) {
                    total.append(line);
                }

                byte[] bytes = total.toString().getBytes();
                connection.disconnect();
                return new String(bytes, Charset.forName(charset));

            } else {
                // Server returned HTTP error code.
                connection.disconnect();
                Log.d("HTTP POST", "Error connecting to " + urlSpec);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }




}
