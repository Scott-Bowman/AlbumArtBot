package com.albumartbot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TralfbotProperties
{
    private static Properties props;

    public TralfbotProperties()
    {
        props = new Properties();

        InputStream is = getClass().getClassLoader().getResourceAsStream("botProps.properties");

        try
        {
            props.load(is);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String getDiscordToken()
    {
        return props.getProperty("discordToken");
    }

    public static String getFanartTvApiKey()
    {
        return props.getProperty("fanartTvApiKey");
    }

    public static String getDiscogsKey() { return props.getProperty("discogsKey"); }

    public static String getDiscogsSecret() { return  props.getProperty("discogsSecret"); }
}
