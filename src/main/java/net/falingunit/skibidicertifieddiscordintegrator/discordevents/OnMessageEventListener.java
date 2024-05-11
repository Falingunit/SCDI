package net.falingunit.skibidicertifieddiscordintegrator.discordevents;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.falingunit.skibidicertifieddiscordintegrator.SCDI;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnMessageEventListener implements EventListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(SCDI.MOD_ID+"/OnMessageEventListener");

    public MinecraftServer server;

    public OnMessageEventListener(MinecraftServer server){
        this.server = server;
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof MessageReceivedEvent){
            Message message = ((MessageReceivedEvent) event).getMessage();

            if (message.getChannelId().equals(SCDI.CHAT_CHANNEL_ID)) {
                User author = ((MessageReceivedEvent) event).getAuthor();

                if (author.isBot()) return;

                String text = message.getContentDisplay();
                String authorName = author.getName();

                server.getCommandManager().executeWithPrefix(server.getCommandSource(), String.format("tellraw @a [\"\",{\"text\":\"[Discord]\",\"italic\":true},{\"text\":\"<%s>\"},{\"text\":\" %s\"}]", authorName, text));
            }

         }
    }
}
