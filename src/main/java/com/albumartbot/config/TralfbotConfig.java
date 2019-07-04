package com.albumartbot.config;

import com.albumartbot.AlbumArt;
import com.albumartbot.FanartTvAlbumArt;
import com.albumartbot.ItunesAlbumArt;
import com.albumartbot.Tralfbot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.albumartbot")
public class TralfbotConfig
{
  @Bean
  public Tralfbot tralfbot() { return new Tralfbot(); }

  @Bean(name = "fanartTv")
  public AlbumArt fanartTv() { return new FanartTvAlbumArt(); }

  @Bean(name = "itunes")
  public AlbumArt itunes() { return new ItunesAlbumArt(); }

}
