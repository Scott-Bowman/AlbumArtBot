package com.albumartbot;

import org.json.JSONObject;
import org.musicbrainz.controller.Artist;
import org.musicbrainz.controller.ReleaseGroup;
import org.musicbrainz.model.entity.ArtistWs2;
import org.musicbrainz.model.entity.ReleaseGroupWs2;
import org.musicbrainz.model.searchresult.ArtistResultWs2;
import org.musicbrainz.model.searchresult.ReleaseGroupResultWs2;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class FanartTvAlbumArt implements AlbumArt
{
    private static final String apiKey = TralfbotProperties.getFanartTvApiKey();

    @Override
    public String getAlbumArt(String artistSearchStr, String albumSearchStr)
    {
        Client client = ClientBuilder.newClient();

        //String mbArtistid = getMusicbrainzArtistId(artistSearchStr);
        String mbReleaseGroupId = getReleaseGroupId(artistSearchStr, albumSearchStr);

        if (mbReleaseGroupId == null)
            return null;

        String url = "http://webservice.fanart.tv/v3/music/albums/"+ mbReleaseGroupId +  "?api_key=" + apiKey;

        Response response = client.target(url)
                                        .request(MediaType.TEXT_PLAIN_TYPE)
                                        .get();

        System.out.println("status: " + response.getStatus());
        System.out.println("headers: " + response.getHeaders());



        JSONObject json = new JSONObject(response.readEntity(String.class));

        if (json.has("status") && json.getString("status").equals("error"))
            return null;

        return json
                .getJSONObject("albums")
                .getJSONObject(mbReleaseGroupId)
                .getJSONArray("albumcover")
                .getJSONObject(0)
                .getString("url");

    }

    private String getMusicbrainzArtistId(String artistSearchStr)
    {
        Artist artist = new Artist();
        artist.search(artistSearchStr);

        List<ArtistResultWs2> results  =  artist.getFullSearchResultList();

        if (results.isEmpty()) return null;

        ArtistWs2 result = results.get(0).getArtist();

        System.out.println("MBID: " + result.getIdUri());
        System.out.println("Artist: " + result.getName());

        return result.getId();
    }

    private String getReleaseGroupId(String artistSearchStr, String albumSearchStr)
    {
        ReleaseGroup releaseGroup = new ReleaseGroup();

        // Looking for exacting matches
        long perPage = 100L;

        releaseGroup.getSearchFilter().setLimit(10L);
        releaseGroup.getSearchFilter().setMinScore(100L);

        releaseGroup.search("artist:" + artistSearchStr + " AND release:" + albumSearchStr);

        // getting the list.
        List<ReleaseGroupResultWs2> results  =  releaseGroup.getFullSearchResultList();

        if (results.isEmpty()) return null;

        ReleaseGroupWs2 relGrp = results.get(0).getReleaseGroup();

        return relGrp.getId();
    }
}
