package tech.fxdev.discord.SinkingShipsAutomator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.fxdev.discord.SinkingShipsAutomator.server.Server;

@SpringBootApplication
public class SinkingShipsAutomatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SinkingShipsAutomatorApplication.class, args);

		Server server = new Server();
	}

}
