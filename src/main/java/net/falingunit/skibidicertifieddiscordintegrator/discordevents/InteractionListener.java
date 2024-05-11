package net.falingunit.skibidicertifieddiscordintegrator.discordevents;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.falingunit.skibidicertifieddiscordintegrator.SCDI;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InteractionListener extends ListenerAdapter {

    private final MinecraftServer server;

    public InteractionListener(MinecraftServer server){
        this.server = server;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        switch (event.getName()){
            case "execute-cmd":
                if (Objects.equals(event.getChannelId(), SCDI.CHAT_CHANNEL_ID)){
                    if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR)) {
                        String command = Objects.requireNonNull(event.getOption("execute-cmd")).getAsString();
                        server.getCommandManager().executeWithPrefix(server.getCommandSource(), command);
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + event.getName());
        }

    }
}
