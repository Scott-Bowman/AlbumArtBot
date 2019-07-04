package com.albumartbot;

public class AlbumArtFactory
{
    public static AlbumArt getAlbumArtImpl(String name)
    {
        if (name.equalsIgnoreCase("itunes"))
            return new ItunesAlbumArt();
        else if (name.equalsIgnoreCase("fanartTv"))
            return new FanartTvAlbumArt();

        return null;
    }
}
