package com.albumartbot;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

public class DiscogsApi
{
    public String getRelaseInfo(String artist, String album) throws URISyntaxException
    {
        Client client = ClientBuilder.newClient();

        URIBuilder uriBuilder = new URIBuilder("https://api.discogs.com")
                                        .setPath("/database/search")
                                        .addParameter("type", "master")
                                        .addParameter("artist", artist)
                                        .addParameter("release_title", album);

        String dKey = TralfbotProperties.getDiscogsKey();
        String dSecret = TralfbotProperties.getDiscogsSecret();

        String authStr = "Discogs key=" + dKey + ", " + "secret=" + dSecret ;

        Response response = client.target(uriBuilder.build())
                                        .request(MediaType.TEXT_PLAIN_TYPE)
                                        .header(HttpHeaders.AUTHORIZATION, authStr)
                                        .get();

        Response.Status status = Response.Status.fromStatusCode(response.getStatus());

        if (status != Response.Status.OK)
            return null;

        JSONObject json = new JSONObject(response.readEntity(String.class));

        if (! json.has("results"))
            return null;

        String masterURL = json.getJSONArray("results").getJSONObject(0).getString("master_url");

        URIBuilder uriBuilder2 = new URIBuilder("https://api.discogs.com/marketplace/search")
                                                .addParameter("release_id", "814103");

        response = client.target(uriBuilder2.build())
                                        .request(MediaType.TEXT_PLAIN_TYPE)
                                        .header(HttpHeaders.AUTHORIZATION, authStr)
                                        .get();

        String str2 =  response.readEntity(String.class);

        System.out.println(str2);

        client.close();

        return masterURL;
    }
}
