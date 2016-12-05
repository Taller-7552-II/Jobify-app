.. java:import:: android.util.Patterns;

FieldValidator
==================

.. java:package:: com.fiuba.tallerii.jobify
   :noindex:

.. java:type:: public class FieldValidator

   Clase que permite verificar si un campo es válido o no


Methods
-------
isNameValid
^^^^^^^^^^^^^^^^^^

.. java:method::  public boolean isNameValid(String name)
   :outertype: FieldValidator

   Devuelve true si la cadena ingresada contiene solo letras del alfabeto. Devuelve false en otro caso.

   :param name: cadena a verificar

isEmailValid
^^^^^^^^^^^^^^^^^^

.. java:method::  public boolean isEmailValid(String email)
   :outertype: FieldValidator

   Devuelve true si la cadena ingresada posee el formato de una dirección mail. Devuelve false en otro caso.

   :param email: cadena a verificar


isPasswordValid
^^^^^^^^

.. java:method:: public boolean isPasswordValid(String password)
   :outertype: FieldValidator

   Devuelve true si la cadena ingresada posee 4 o más caracteres. Devuelve false en otro caso.

   :param password: cadena a verificar


