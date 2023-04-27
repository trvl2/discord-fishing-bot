package events;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class CatchingEvent extends ListenerAdapter  {

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if(event.isFromType(ChannelType.PRIVATE) || event.getAuthor().isBot())
        {
            return;
        }

        if(event.getMessage().getContentRaw().equalsIgnoreCase("!catch"))
        {
            System.out.printf("[%s][] %s: %s\n", event.getGuild().getName(),
                    Objects.requireNonNull(event.getMember()).getEffectiveName(),
                    event.getMessage().getContentRaw());

            event.getChannel().sendMessage("Wow! " + event.getAuthor().getName() + " now trying to catch something..").queue();
            event.getChannel().sendMessage("Great! " + event.getAuthor().getName() + " caught " + catchRandomFish()).queue();
        }
    }

    private String catchRandomFish() {
        String[] fishes = {"Splashtail", "Pondie", "Islehopper",
                        "Plentifin", "Wildsplash", "Ancientscale",
                        "Devilfish", "Wrecker", "Stormfish", "Battlegil"};

        return fishes[(int) (Math.random() * fishes.length)];
    }
}
