package net.falingunit.skibidicertifieddiscordintegrator.discordevents;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.falingunit.skibidicertifieddiscordintegrator.SCDI;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class ReadyEventListener implements EventListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(SCDI.MOD_ID+"/ReadyEventListener");

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (event instanceof ReadyEvent){
            LOGGER.info("Discord bot is ready...");
        }
    }
}
