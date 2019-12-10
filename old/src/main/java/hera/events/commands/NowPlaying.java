package hera.events.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import hera.enums.BoundChannel;
import hera.events.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class NowPlaying extends AbstractCommand {

    private static final Logger LOG = LoggerFactory.getLogger(NowPlaying.class);

    private MessageSender ms;
    private GuildAudioPlayerManager gapm;

    // constructor
    NowPlaying() {
        super(null, 0, false);
        this.ms = MessageSender.getInstance();
        this.gapm = GuildAudioPlayerManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: NowPlaying.execute");
        AudioTrack track = gapm.getGuildAudioPlayer(e.getGuild()).player.getPlayingTrack();
        if (track != null) {
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Now playing:", track.getInfo().title + " by "
                    + track.getInfo().author + " | " + getFormattedTime(track.getDuration()));
            LOG.info(e.getAuthor() + " used commands np, receiving the following information: Now playing: " + track.getInfo().title + " by "
                    + track.getInfo().author + " | " + getFormattedTime(track.getDuration()));
        }
        else {
            ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "No song is playing right now");
            LOG.debug(e.getAuthor() + " used commands np although there was no song playing");
        }
        LOG.debug("End of: NowPlaying.execute");
    }

    private String getFormattedTime(long milliseconds) {
        LOG.debug("Start of: NowPlaying.getFormattedTime");
        LOG.debug("End of: NowPlaying.getFormattedTime");
        return String.format("%02d:%02d:%02d", (milliseconds / (1000 * 60 * 60)) % 24, (milliseconds / (1000 * 60)) % 60, (milliseconds / 1000) % 60);
    }
}