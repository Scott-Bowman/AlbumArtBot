package com.albumartbot;

import com.albumartbot.config.TralfbotConfig;
import com.omertron.fanarttvapi.FanartTvException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException
    {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(TralfbotConfig.class);
//        context.refresh();
//
//        Tralfbot bot = context.getBean(Tralfbot.class);
//        bot.startBot();

        Tralfbot tralfbot = new Tralfbot();
        tralfbot.startBot();
    }

}
