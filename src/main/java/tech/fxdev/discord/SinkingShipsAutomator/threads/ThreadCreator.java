package tech.fxdev.discord.SinkingShipsAutomator.threads;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

import static net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel.AutoArchiveDuration.TIME_1_WEEK;

public class ThreadCreator extends ListenerAdapter {
    private final String channelId;
    private static final Color EB_COLOR_DEFAULT = new Color(243, 156, 18);
    private static final int THREAD_TITLE_MAX = 100;
    private static final int THREAD_DESCRIPTION_MAX = 999;

    public ThreadCreator(String channel_id) {
        this.channelId = channel_id;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE) || event.getAuthor().isBot() || !event.getChannel().getId().equals(channelId)) {
            return;
        }

        String[] message = event.getMessage().getContentRaw().split(" ");
        if (!isCreateThreadCommand(message)) {
            event.getMessage().delete().queue();
            return;
        }

        String threadTitle = parseText(message, THREAD_TITLE_MAX);
        String threadType = message[1];
        String threadPlace = ":flag_ea:" + " " + message[3] + ", " + message[4];
        String threadDescription = parseText(message, THREAD_DESCRIPTION_MAX);

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(threadTitle)
                .setColor(EB_COLOR_DEFAULT)
                .addField("Place", threadPlace, true)
                .addField("Description", threadDescription, true);

        //event.getMessage().delete().queue();

        TextChannel channel = event.getChannel().asTextChannel();

        channel.sendMessageEmbeds(embed.build()).queue(embedMessage -> {
            embedMessage.createThreadChannel(threadTitle).setAutoArchiveDuration(TIME_1_WEEK).queue();
        });
    }

    private boolean isCreateThreadCommand(String[] message) {
        return message.length >= 5 && message[0].equalsIgnoreCase("!createthread");
    }

    private String parseText(String[] message, int length) {
        if (message == null || message.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 5; i < message.length; i++) {
            sb.append(message[i]).append(" ");
        }

        return truncateText(sb.toString(), length);
    }

    private String truncateText(String text, int length) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        if (text.length() >= length) {
            return text.substring(0, length - 3) + "...";
        } else {
            return text;
        }
    }
}
