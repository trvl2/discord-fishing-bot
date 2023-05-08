package tech.fxdev.discord.SinkingShipsAutomator.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private final Properties properties;

    public PropertyReader() {
        this.properties = new Properties();

        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
            this.properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getProperty(String propertyName) {
        return this.properties.getProperty(propertyName);
    }

}
