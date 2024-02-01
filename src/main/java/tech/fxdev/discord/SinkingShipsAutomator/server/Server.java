package tech.fxdev.discord.SinkingShipsAutomator.server;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class Server {

    @Bean(destroyMethod = "shutdown")
    public JDA jda(List<EventListener> listenerList, ServerConfiguration serverConfiguration) {

        try {
            JDA server = JDABuilder.createDefault(serverConfiguration.getServerToken())
                    .enableIntents(serverConfiguration.getIntents())
                    .setActivity(Activity.watching(serverConfiguration.getActivityContent()))
                    .build();

            listenerList.forEach(server::addEventListener);

            return server;
        } catch (Exception e) {
            log.error("Error occurred. See logs for details. Shutting down...");
            throw new RuntimeException(e);
        }
    }
}
