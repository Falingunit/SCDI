package net.falingunit.skibidicertifieddiscordintegrator;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.falingunit.skibidicertifieddiscordintegrator.discordevents.OnMessageEventListener;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Objects;

public class SCDI implements ModInitializer {
	public static final String MOD_ID = "skibidicertifieddiscordintegrator";
    public static final Logger LOGGER = LoggerFactory.getLogger("skibidicertifieddiscordintegrator");

	public static String BOT_TOKEN = "token";
	public static String CHAT_CHANNEL_ID = "1237380886354333707";
	public static String CONSOLE_CHANNEL_ID = "1236314145960361994";

	public SCDIBot bot;

	@Override
	public void onInitialize() {
		registerEvents();

		LOGGER.info("Attempting to login...");

		bot = SCDIBot.getInstance();
    }

	public void registerEvents(){
		ServerLifecycleEvents.SERVER_STARTED.register(this::onServerOnline);
		ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
		ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
		ServerMessageEvents.CHAT_MESSAGE.register(this::onChatMessage);
		ServerMessageEvents.GAME_MESSAGE.register(this::onGameMessage);
		ServerMessageEvents.COMMAND_MESSAGE.register(this::onCommandMessage);
	}

	private void onCommandMessage(SignedMessage signedMessage, ServerCommandSource serverCommandSource, MessageType.Parameters parameters) {
		LOGGER.info(signedMessage.toString());
	}

	private void onServerStopping(MinecraftServer minecraftServer) {
		//Get chat channel and send offline embed
		TextChannel channel = (TextChannel) bot.jda.getChannelById(Channel.class, SCDI.CHAT_CHANNEL_ID);
        assert channel != null;
        channel.sendMessageEmbeds(new EmbedBuilder().setTitle("Server is offline...").setColor(Color.BLACK).build()).queue();
	}

	private void onServerStarting(MinecraftServer minecraftServer) {
		//Get chat channel and send starting embed
		TextChannel channel = (TextChannel) bot.jda.getChannelById(Channel.class, SCDI.CHAT_CHANNEL_ID);
        assert channel != null;
        channel.sendMessageEmbeds(new EmbedBuilder().setTitle("Server is starting...").setColor(Color.DARK_GRAY).build()).queue();
	}

	private void onServerOnline(MinecraftServer minecraftServer) {
		//Get chat channel and send online embed
		TextChannel channel = bot.jda.getChannelById(TextChannel.class, CHAT_CHANNEL_ID);
        assert channel != null;
        channel.sendMessageEmbeds(new EmbedBuilder().setTitle("Server is online...").setColor(Color.WHITE).build()).queue();
        bot.jda.addEventListener(new OnMessageEventListener(minecraftServer));
	}

	private void onGameMessage(MinecraftServer minecraftServer, Text message, boolean overlay) {
		if (message.getContent() instanceof TranslatableTextContent){
			switch (((TranslatableTextContent) message.getContent()).getKey()){
				case "chat.type.advancement.task":
					Objects.requireNonNull(bot.jda.getChannelById(TextChannel.class, CHAT_CHANNEL_ID)).sendMessageEmbeds(new EmbedBuilder().setTitle(message.getString()).setColor(new Color(34, 255, 0)).build()).queue();
					break;
				case "chat.type.advancement.challenge":
					Objects.requireNonNull(bot.jda.getChannelById(TextChannel.class, CHAT_CHANNEL_ID)).sendMessageEmbeds(new EmbedBuilder().setTitle(message.getString()).setColor(new Color(121, 50, 168)).build()).queue();
					break;
				case "multiplayer.player.joined":
					Objects.requireNonNull(bot.jda.getChannelById(TextChannel.class, CHAT_CHANNEL_ID)).sendMessageEmbeds(new EmbedBuilder().setTitle(message.getString()).setColor(new Color(0, 255, 229)).build()).queue();
					break;
				case "multiplayer.player.left":
					Objects.requireNonNull(bot.jda.getChannelById(TextChannel.class, CHAT_CHANNEL_ID)).sendMessageEmbeds(new EmbedBuilder().setTitle(message.getString()).setColor(new Color(255, 235, 15)).build()).queue();
					break;
				default:
					if (((TranslatableTextContent) message.getContent()).getKey().startsWith("death.")){
						Objects.requireNonNull(bot.jda.getChannelById(TextChannel.class, CHAT_CHANNEL_ID)).sendMessageEmbeds(new EmbedBuilder().setTitle(message.getString()).setColor(new Color(255, 87, 15)).build()).queue();
					}
					else {
						LOGGER.info(((TranslatableTextContent) message.getContent()).getKey());
					}
					break;
			}
		}
	}

	public void onChatMessage(SignedMessage message, ServerPlayerEntity sender, MessageType.Parameters params){
		String playerUsername = sender.getName().getLiteralString();
		String messageContent = message.getContent().getLiteralString();

		Objects.requireNonNull(bot.jda.getChannelById(TextChannel.class, CHAT_CHANNEL_ID)).sendMessage("**[" + playerUsername + "]**  " + messageContent).queue();
	}
}