package com.albumartbot;

import org.musicbrainz.MBWS2Exception;

public interface AlbumArt
{
    String getAlbumArt(String artistSearchStr, String albumSearchStr);
}
