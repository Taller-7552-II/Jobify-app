package com.fiuba.tallerii.jobify;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

public class FieldValidatorTest
{
    FieldValidator mFieldValidator;

    public FieldValidatorTest()
    {
        mFieldValidator = new FieldValidator();
    }

    // Error testing patterns - look for solution - see Robolectric
    /*@Test
    public void validEmailTest()
    {
        String email = "email_string@hotmail.com.ar";
        assertTrue(mFieldValidator.isEmailValid(email));
    }*/

    @Test
    public void validNameTest()
    {
        String name = "Jorge";
        assertTrue(mFieldValidator.isNameValid(name));
    }

    @Test
    public void invalidNameTest()
    {
        String name = "Jorge$";
        assertTrue(!mFieldValidator.isNameValid(name));
    }

    @Test
    public void validPasswordTest()
    {
        String password = "Password123...";
        assertTrue(mFieldValidator.isPasswordValid(password));
    }
    @Test
    public void invalidPasswordTest()
    {
        String password = "ds!";
        assertTrue(!mFieldValidator.isPasswordValid(password));
    }

}