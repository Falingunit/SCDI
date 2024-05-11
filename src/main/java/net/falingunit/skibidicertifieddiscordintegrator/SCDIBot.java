package net.falingunit.skibidicertifieddiscordintegrator;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.falingunit.skibidicertifieddiscordintegrator.discordevents.ReadyEventListener;

import javax.security.auth.login.LoginException;

public class SCDIBot {

    private static SCDIBot instance;

    public JDA jda;

    public SCDIBot() throws LoginException {
        JDABuilder jdaBuilder = JDABuilder.createDefault(SCDI.BOT_TOKEN);

        jda = jdaBuilder
                .enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .addEventListeners(new ReadyEventListener())
                .build();

        instance = this;
    }

    public static SCDIBot getInstance() {
        if (instance == null){
            try {
                instance = new SCDIBot();
            } catch (Exception ignored){

            }
        }
        return instance;
    }
}
