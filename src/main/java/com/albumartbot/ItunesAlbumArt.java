package com.albumartbot;

import be.ceau.itunesapi.Search;
import be.ceau.itunesapi.request.Country;
import be.ceau.itunesapi.request.search.Media;
import be.ceau.itunesapi.response.Response;
import be.ceau.itunesapi.response.Result;
import org.springframework.stereotype.Component;

public class ItunesAlbumArt implements AlbumArt
{

    @Override
    public String getAlbumArt(String artistSearchStr, String albumSearchStr)
    {
        Search request = new Search()
        					.setTerm(artistSearchStr + " " + albumSearchStr)
        					.setCountry(Country.UNITED_STATES)
        					.setMedia(Media.MUSIC)
        					.setLimit(2);

        Response response = request.execute();

        Result result;

        if (response.getResultCount() > 0)
            result = response.getResults().get(0);
        else
            return null;


        String url = result.getArtworkUrl100();

        return url.replace("100x100bb.jpg", "100000x100000-999.jpg");
    }
}
