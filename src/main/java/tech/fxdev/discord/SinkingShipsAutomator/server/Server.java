package tech.fxdev.discord.SinkingShipsAutomator.server;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class Server {

    @Bean(destroyMethod = "shutdown")
    public JDA jda(List<EventListener> listenerList, ServerConfiguration serverConfiguration) {

        List<GatewayIntent> gatewayIntents = serverConfiguration.getIntents().stream().map(GatewayIntent::valueOf).toList();

        try {
            JDA server = JDABuilder.createDefault(serverConfiguration.getServerToken())
                    .enableIntents(gatewayIntents)
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
