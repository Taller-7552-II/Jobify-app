
.. java:import:: android.graphics.Bitmap;

.. java:import:: java.util.ArrayList;
.. java:import:: java.util.List;

InformationHolder
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class InformationHolder

   Permite realizar operaciones HTTP a través de una API Rest, utilizando un token de authenticación.
   Es importante mencionar, que estas operaciones NO deben realizarse en la UI thread de Android, sino que deben ser desplazadas a otra thread. En esta aplicación, se utilizan AsyncTasks para ejecutar las operaciones del InformationHolder en la Background thread.

Fields
------
mName
^^^^^^^^

.. java:field:: private String mName
   :outertype: InformationHolder

   Nombre del usuario actualmente conectado.


mResume
^^^^^^^^

.. java:field:: private String mResume;
   :outertype: InformationHolder

   Resumen del usuario actual

mMail
^^^^^^^^

.. java:field:: private String mMail;
   :outertype: InformationHolder

   Email del usuario actual

mProfilePicture
^^^^^^^^

.. java:field:: private Bitmap mProfilePicture;
   :outertype: InformationHolder

   Bitmap correspondiente a la foto de perfil del usuario actual.

mContacts
^^^^^^^^

.. java:field:: private List<Contact> mContacts;
   :outertype: InformationHolder

   Lista de contactos del usuario actual

mJobs
^^^^^^^^

.. java:field:: private List<Job> mJobs;
   :outertype: InformationHolder

   Lista de experiencias laborales del usuario actual

mSkills
^^^^^^^^

.. java:field:: private List<Skill> mSkills;
   :outertype: InformationHolder

   Lista de destrezas del usuario actual

mNotifications
^^^^^^^^

.. java:field:: private List<Notification> mNotifications;
   :outertype: InformationHolder

   Lista de notificaciones del usuario actual



Methods
-------

Getters y Setters de los atributos anteriores.

get
^^^^^^^^^^^^^^^^^^

.. java:method::  public static InformationHolder get()
   :outertype: InformationHolder

   Devuelve la instancia actual del Singleton InformationHolder.
