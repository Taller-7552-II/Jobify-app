package com.fiuba.tallerii.jobify;

import android.util.Patterns;

public class FieldValidator
{
    public boolean isNameValid(String name)
    {
        //probably faster than return name.matches("[a-zA-Z]+");
        char[] chars = name.toCharArray();
        for (char c : chars)
        {
            if(!Character.isLetter(c))
            {
                return false;
            }
        }
        return true;
    }

    public boolean isEmailValid(String email)
    {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isPasswordValid(String password)
    {
        return (password.length() > 4);
    }
}
