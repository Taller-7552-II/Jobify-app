package com.fiuba.tallerii.jobify;

/*public class TestParaT
{
    private final static String USER_AGENT = "Mozilla/5.0";
    private static String user="matias";
    private static String letras="";
    private static boolean next=false;

    public static void main(String[] args) throws Exception
    {

        String url ="http://localhost:3000/db";
        url="http://jobify-professional.herokuapp.com/db";
        sendGet(url);
        url="http://jobify-professional.herokuapp.com/id";
        //url ="http://localhost:3000/id";
        for(int i =1;i<5;i++)
            sendPost(url,i+"","s");
        //http.sendPost("matias","matias");

    }

    // HTTP GET request
    private static void sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP POST request
    private static void sendPost(String url,String user,String pw) throws Exception {



        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        ((HttpURLConnection) con).setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        con.setRequestProperty("content-type", "application/json");

        String urlParameters = "{\"id\" : \""+user+"\", \"password\" : \""+pw+"\"}";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = ((HttpURLConnection) con).getResponseCode();
        //System.out.println("\nSending 'POST' request to URL : " + url);
        // System.out.println("Post parameters : " + urlParameters);
//  System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            System.out.println(pw);

            response.append(inputLine);
            next=true;
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }
}*/
