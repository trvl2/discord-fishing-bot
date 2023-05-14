package tech.fxdev.discord.SinkingShipsAutomator.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tech.fxdev.discord.SinkingShipsAutomator.core.PropertyReader;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.*;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;

public class SessionPlanner extends ListenerAdapter {
    private static final String CHANNEL_ID = new PropertyReader().getProperty("discord.channel.sessionplanner");
    private static final String EB_GAME_DEFAULT = "Unknown game";
    private static final String EB_BANNER_DEFAULT = "https://fxdev.tech/api/discord/bb_unk.jpg";
    private static final Color EB_COLOR_DEFAULT = Color.ORANGE;

    private static final Map<String, GameInfo> GAMES = Map.of(
        "sot", new GameInfo("Sea of Thieves", "https://fxdev.tech/api/discord/bb_sot.jpg", new Color(52, 152, 219)),
        "pup", new GameInfo("PlateUp", "https://fxdev.tech/api/discord/bb_pu.jpg", new Color(231, 76, 60)),
        "trr", new GameInfo("Terraria", "https://fxdev.tech/api/discord/bb_tr.jpg", new Color(26, 188, 156)),
        "stv", new GameInfo("Stardew Valley", "https://fxdev.tech/api/discord/bb_sv.jpg", new Color(243, 156, 18)),
        "ccs", new GameInfo("Carcassonne", "https://fxdev.tech/api/discord/bb_cc.jpg", new Color(211, 84, 0)),
        "ttr", new GameInfo("Ticket to Ride", "https://fxdev.tech/api/discord/bb_tt.jpg", new Color(52, 73, 94))
    );

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.isFromType(ChannelType.PRIVATE) || event.getAuthor().isBot() || !event.getChannel().getId().equals(CHANNEL_ID)) {
            return;
        }

        String[] message = event.getMessage().getContentRaw().split(" ");
        if (!message[0].equalsIgnoreCase("!plan") || message.length < 4) {
            // Deleting wrong pattern messages
            event.getMessage().delete().queue();
            return;
        }

        // Retrieving game by parsing gamecode in user message
        String gameCode = message[1].toLowerCase();
        GameInfo gameInfo = GAMES.getOrDefault(gameCode, new GameInfo(EB_GAME_DEFAULT, EB_BANNER_DEFAULT, EB_COLOR_DEFAULT));

        String formattedTime = "";
        try {
            // Retrieving time by parsing hours and minutes in user message
            LocalTime time = LocalTime.parse(message[2], DateTimeFormatter.ofPattern("HH:mm"));
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), time);
            long unixTime = localDateTime.toEpochSecond(ZoneOffset.UTC);
            formattedTime = "<t:" + unixTime + ">";
        } catch (DateTimeParseException e) {
            event.getMessage().delete().queue();
            return;
        }


        // Building embed
        EmbedBuilder eb = new EmbedBuilder()
            .setTitle("Session in " + gameInfo.getName())
            .setColor(gameInfo.getColor())
            .addField("Initiator", Objects.requireNonNull(event.getMember()).getAsMention(), true)
            .addField("Game", gameInfo.getName(), true)
            .addField("Date", formattedTime, true)
            .addField("Description", buildDescription(message), false)
            .setImage(gameInfo.getBanner());

        // Deleting user message
        event.getMessage().delete().queue();

        // Posting embed
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    // Building description of all messages except !plan [gameCode] [time]
    private String buildDescription(String[] message) {
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i < message.length; i++) {
            sb.append(message[i]).append(" ");
        }
        return sb.toString();
    }

    private static class GameInfo {
        private final String name;
        private final String banner;
        private final Color color;

        public GameInfo(String name, String banner, Color color) {
            this.name = name;
            this.banner = banner;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public String getBanner() {
            return banner;
        }

        public Color getColor() {
            return color;
        }
    }
}
