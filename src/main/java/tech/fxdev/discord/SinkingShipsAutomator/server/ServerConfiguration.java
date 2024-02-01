package tech.fxdev.discord.SinkingShipsAutomator.server;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties
public class ServerConfiguration {

    @Value("${discord.server.token}")
    private String serverToken;

    @Value("${discord.server.activityContent}")
    private String activityContent;

    @Value("${discord.server.intents")
    private List<GatewayIntent> intents;

}
