.. java:import:: android.graphics.Bitmap;

.. java:import:: java.util.ArrayList;
.. java:import:: java.util.List;

Contact
=======

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class Contact

   Clase que contiene información acerca de un contacto.

Contact
------------
Notification
^^^^^^^

.. java:constructor:: public Contact(String name, String email, Bitmap pictureBitmap)
   :outertype: Contact

   Constructor. Inicializa los parametros ingresados.

   :param name: nombre completo del contacto. Incluye apellido
   :param email: email del contacto
   :param pictureBitmap: foto de perfil del contacto


Methods
-------

Getters y Setters de los atributos.

addSkill
^^^^^^^^^^^^^^^^^^

.. java:method::  public void addSkill(Skill skillToAdd)
   :outertype: Contact

   Agrega una destreza ignorando duplicados.

   :param skillToAdd: destreza a añadir.

addJob
^^^^^^^^^^^^^^^^^^

.. java:method::  public void addJob(Job jobToAdd)
   :outertype: Contact

   Agrega una experiencia laboral ignorando duplicados.

   :param jobToAdd: experiencia laboral a añadir.



