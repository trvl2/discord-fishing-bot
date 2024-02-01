package tech.fxdev.discord.SinkingShipsAutomator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tech.fxdev.discord.SinkingShipsAutomator.server.ServerConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ServerConfiguration.class)
public class SinkingShipsAutomatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SinkingShipsAutomatorApplication.class, args);
	}

}
