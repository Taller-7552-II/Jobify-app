.. java:import:: android.os.AsyncTask;

.. java:import:: android.content.Context;
.. java:import:: android.util.Log;

.. java:import:: java.io.BufferedReader;
.. java:import:: java.io.IOException;
.. java:import:: java.io.InputStream;
.. java:import:: java.io.InputStreamReader;
.. java:import:: java.net.HttpURLConnection;
.. java:import:: java.net.URL;
.. java:import:: java.nio.charset.Charset;

ServerHandler
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class ServerHandler

   Permite realizar operaciones HTTP a través de una API Rest, utilizando un token de authenticación.
   Es importante mencionar, que estas operaciones NO deben realizarse en la UI thread de Android, sino que deben ser desplazadas a otra thread. En esta aplicación, se utilizan AsyncTasks para ejecutar las operaciones del ServerHandler en la Background thread.

Fields
------
mServerIP
^^^^^^^^

.. java:field:: private String mServerIP
   :outertype: ServerHandler

   IP del server con puerto incluido, el cual utilizarña para conectarse al servidor. Ejemplo: 192.168.0.19:8000


mUsername
^^^^^^^^

.. java:field:: private String mUsername;
   :outertype: ServerHandler

   Nombre de usuario con el cual se ha establecido la conexión

mConnectionToken
^^^^^^^^

.. java:field:: private String mConnectionToken;
   :outertype: ServerHandler

   Token provista para el usuario actual.
   

Methods
-------
get
^^^^^^^^^^^^^^^^^^

.. java:method::  public static ServerHandler get(Context context)
   :outertype: ServerHandler

   Devuelve la instancia actual del Singleton ServerHandler.

setConnectionToken
^^^^^^^^^^^^^^^^^^

.. java:method::  public void setConnectionToken(String connectionToken)
   :outertype: ServerHandler

   Permite utilizar el token provisto en los headers de los mensajes HTTP realizados.

   :param connectionToken: token de authenticación provisto


GET
^^^^^^^^

.. java:method:: public String GET(String urlSpec)
   :outertype: ServerHandler

   Realiza un GET request a la dirección especificada por el parámetro urlSpec.

   :param urlSpec: URL a en la que se establecerá la conexión

   :return: Respuesta del server

POST
^^^^^^^^

.. java:method:: public String POST(String urlSpec, String parameters)
   :outertype: ServerHandler

   Realiza un POST request a la dirección especificada por el parámetro urlSpec.

   :param urlSpec: URL a en la que se establecerá la conexión
   :param parameters: parametros del POST en formato json 

   :return: Respuesta del server

PUT
^^^^^^^^

.. java:method:: public String PUT(String urlSpec, String parameters)
   :outertype: ServerHandler

   Realiza un PUT request a la dirección especificada por el parámetro urlSpec.

   :param urlSpec: URL a en la que se establecerá la conexión
   :param parameters: parametros del PUT en formato json 

   :return: Respuesta del server

DELETE
^^^^^^^^

.. java:method:: public String DELETE(String urlSpec, String parameters)
   :outertype: ServerHandler

   Realiza un DELETE request a la dirección especificada por el parámetro urlSpec.

   :param urlSpec: URL a en la que se establecerá la conexión
   :param parameters: parametros del DELETE en formato json 

   :return: Respuesta del server


