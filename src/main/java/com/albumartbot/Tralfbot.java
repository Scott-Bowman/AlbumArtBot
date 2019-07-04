package com.albumartbot;

import com.omertron.fanarttvapi.FanartTvApi;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Tralfbot extends ListenerAdapter
{
    public void startBot() throws LoginException
    {
        new TralfbotProperties();

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = TralfbotProperties.getDiscordToken();

        builder.setToken(token);
        builder.addEventListener(new Tralfbot());
        builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
        {
            return;
        }

        String msg = event.getMessage().getContentRaw();

        if (msg.startsWith(".albumart "))
        {
            long slashCount = msg.codePoints().filter(c -> c == '/').count();

            // Make sure the message is formatted properly.
            if (slashCount > 2L || slashCount < 1L)
            {
                send(event, "Format: <artist> / <album> / <source> (source optional)\n"
                              + "sources: itunes or fanartTV. fanartTV is default.");
                return;
            }

            List<String> input = Arrays.stream(msg.substring(".albumart ".length()).split("/"))
                                       .map(String::trim)
                                       .collect(Collectors.toList());

            // Fanart.tv is the default image service. The user can specify itunes instead if they wish.
            AlbumArt aa = AlbumArtFactory.getAlbumArtImpl(input.size() > 2 ? input.get(2) : "fanartTv");

            String url = getAlbumArt(input, aa);

            send(event, url);
        }
        else if (msg.startsWith(".howmuch "))
        {
            List<String> input = Arrays.stream(msg.substring(".howmuch ".length()).split("/"))
                    .map(String::trim)
                    .collect(Collectors.toList());

            // TODO Discogs features Under Construction

            try
            {
                new DiscogsApi().getRelaseInfo(input.get(0), input.get(1));
            }
            catch (URISyntaxException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String getAlbumArt(List<String> input, AlbumArt albumArt)
    {
        String artist = input.get(0);
        String album = input.get(1);

        System.out.println("Artist:" + artist);

        return albumArt.getAlbumArt(artist, album);
    }

    private void send(MessageReceivedEvent event, String msg)
    {
        event.getChannel().sendMessage(msg != null ? msg : "Album Art not found.").queue();
    }
}
