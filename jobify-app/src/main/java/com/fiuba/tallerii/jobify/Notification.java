package com.fiuba.tallerii.jobify;

public class Notification
{
    public static final int NOTIFICATION_CODE_ADDFRIEND = 1;
    public static final int NOTIFICATION_CODE_CHAT = 2;

    private int mCode;
    private String mTitle;
    private String mContent;

    public Notification(String content, int code)
    {
        mCode = code;
        mTitle = generateTitle(mCode);
        mContent = content;
    }

    private String generateTitle(int code)
    {
        String tittle = "";
        switch (code)
        {
            case NOTIFICATION_CODE_ADDFRIEND:
                tittle = "Friend request";
                break;
            case NOTIFICATION_CODE_CHAT:
                tittle = "New message";
                break;
        }
        return tittle;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String title)
    {
        mTitle = title;
    }

    public String getContent()
    {
        return mContent;
    }

    public void setContent(String content)
    {
        mContent = content;
    }

    public int getCode()
    {
        return mCode;
    }
}
