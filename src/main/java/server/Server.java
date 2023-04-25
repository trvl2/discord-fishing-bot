package server;

import events.CatchingEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Properties;

public class Server {

    private JDA server;

    public Server() {
        try {
            Properties properties = new Properties();

            server = JDABuilder.createDefault("your token")
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .setActivity(Activity.watching("you sinking"))
                    .build();

            server.addEventListener(new CatchingEvent());

            server.updateCommands().addCommands(
                    Commands.slash("!catch", "Catch a fish.")
            ).queue();

        } catch(Exception e) {
            System.out.println("Error occurred. See logs for details. Shutting down...");
        }
    }
}
