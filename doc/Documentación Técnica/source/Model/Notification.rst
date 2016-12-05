

Notification
=======

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class Notification

   Clase que contiene información acerca de una notificación.

Constructor
------------
Notification
^^^^^^^

.. java:constructor:: public Notification(String content, int code)
   :outertype: Notification

   Constructor. Inicializa los parametros ingresados.

   :param content: contenido de la notificación.
   :param code: código interno que identifica de que tipo de notificación se trata.


Methods
-------

Getters y Setters de los atributos.

generateTitle
^^^^^^^^^^^^^^^^^^

.. java:method::  private String generateTitle(int code)
   :outertype: Notification

   Genera el título dependiendo dl código de la notificación.

   :param code: código que identifica el tipo de notificación

