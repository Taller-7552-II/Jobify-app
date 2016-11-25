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

/**
 * Singleton to handle connections with app server
 */
public class ServerHandler
{
    private final static String USER_AGENT = "Mozilla/5.0";

    private static ServerHandler sServerHandler;

    private String mServerIP = "";

    private String mUsername;
    private String mConnectionToken;
    private boolean mAuthenticated;

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
        mUsername = "none";
        mServerIP = "127.0.0.1";
        mAuthenticated = false;
    }

    public void setConnectionToken(String connectionToken)
    {
        mConnectionToken = connectionToken;
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

    public String getUsername()
    {
        return mUsername;
    }

    public void setUsername(String username)
    {
        mUsername = username;
    }

    /*******************************************
     * ASYNC Methods
     *******************************************/

    /*

        GET

     */
    public String GET(String urlSpec)
    {
        try
        {
            URL url = new URL(urlSpec);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //add header
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("conn_token", mConnectionToken);
            //set request as GET
            connection.setRequestMethod("GET");

            Log.d("Jobify", "Connecting to " + urlSpec + "...");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                Log.i("Jobify", "Successful connection to " + urlSpec);
                InputStream responseBody = connection.getInputStream();
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                BufferedReader streamReader = new BufferedReader(responseBodyReader);
                StringBuilder responseStrBuilder = new StringBuilder();

                //get JSON String
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                connection.disconnect();

                Log.d("Jobify", "Get Response: " +  responseStrBuilder.toString());
                return responseStrBuilder.toString();
            } else
            {
                connection.disconnect();
                Log.d("Jobify", "Error connecting to " + urlSpec);
                return "";
            }

        } catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /*

    POST

 */
    public String POST(String urlSpec, String parameters)
    {
        try {
            URL url = new URL(urlSpec);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            Log.d("Jobify", "Connecting to " + urlSpec + "...");

            //add header
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            connection.setRequestProperty("content-type", "application/json");
            connection.setRequestProperty("conn_token", mConnectionToken);
            //set request as POST
            connection.setRequestMethod("POST");

            //Create the data
            connection.setDoOutput(true);
            connection.getOutputStream().write(parameters.getBytes());

            //get response
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("Jobify", "Succesful connection to " + urlSpec);
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

                Log.d("Jobify", "POST Response: " +  new String(bytes, Charset.forName(charset)));
                return new String(bytes, Charset.forName(charset));

            } else {
                // Server returned HTTP error code.
                connection.disconnect();
                Log.d("Jobify", "Error connecting to " + urlSpec);
            }
        } catch (IOException e) {
            Log.e("Jobify", "Error in POST request");
            e.printStackTrace();
        }
        return "";

    }
    public String PUT(String urlSpec, String parameters)
    {
        try {
            URL url = new URL(urlSpec);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            Log.d("Jobify", "Connecting to " + urlSpec + "...");

            //add header
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            connection.setRequestProperty("content-type", "application/json");
            connection.setRequestProperty("conn_token", mConnectionToken);
            //set request as POST
            connection.setRequestMethod("PUT");

            //Create the data
            connection.setDoOutput(true);
            connection.getOutputStream().write(parameters.getBytes());

            //get response
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("Jobify", "Succesful connection to " + urlSpec);
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

                Log.d("Jobify", "PUT Response: " +  new String(bytes, Charset.forName(charset)));
                return new String(bytes, Charset.forName(charset));

            } else {
                // Server returned HTTP error code.
                connection.disconnect();
                Log.d("Jobify", "Error connecting to " + urlSpec);
            }
        } catch (IOException e) {
            Log.e("Jobify", "Error in PUT request");
            e.printStackTrace();
        }
        return "";

    }

}
