package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.core.music.HeraAudioManager;
import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

public class Pause {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		return pausePlayer(guild).flatMap(m -> channel.createMessage(spec -> spec.setEmbed(embed -> {
				embed.setColor(Color.ORANGE);
				embed.setDescription(m);
			}))
		)
		.then();
	}

	private static Mono<String> pausePlayer(Guild guild) {
		Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_PAUSED, guild);
		String message = local.getValue();
		if (!HeraAudioManager.getPlayer(guild).isPaused()) {
			HeraAudioManager.getPlayer(guild).setPaused(true);
		} else {
			message = HeraUtil.getLocalisation(LocalisationKey.COMMAND_PAUSED_ERROR, guild).getValue();
		}

		return Mono.just(message);
	}
}
