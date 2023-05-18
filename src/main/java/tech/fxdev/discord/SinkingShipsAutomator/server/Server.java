package tech.fxdev.discord.SinkingShipsAutomator.server;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import tech.fxdev.discord.SinkingShipsAutomator.core.PropertyReader;
import tech.fxdev.discord.SinkingShipsAutomator.embeds.SessionPlanner;
import tech.fxdev.discord.SinkingShipsAutomator.threads.ThreadCreator;

public class Server {

    private JDA server;

    public Server() {

        try {
            PropertyReader pr = new PropertyReader();

            server = JDABuilder.createDefault(pr.getProperty("discord.token"))
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .setActivity(Activity.watching("you sinking"))
                    .build();

            server.addEventListener(new SessionPlanner());
            server.addEventListener(new ThreadCreator(pr.getProperty("discord.channel.art")));

        } catch(Exception e) {
            System.out.println("Error occurred. See logs for details. Shutting down...");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
