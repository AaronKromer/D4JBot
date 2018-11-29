package hera.events.commands;

import hera.enums.BotSettings;
import hera.events.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Vote extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Vote.class);

    private static Vote instance;

    public static Vote getInstance() {
        if (instance == null) {
            instance = new Vote();
        }
        return instance;
    }

    private hera.eventSupplements.MessageSender ms;
    private hera.eventSupplements.VoteManager vm;

    // default constructor
    public Vote() {
        super(null, 1, true);
        this.ms = hera.eventSupplements.MessageSender.getInstance();
        this.vm = hera.eventSupplements.VoteManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Vote.execute");
        if (!vm.isVoteActive()) {
                String topic = params[0];

                vm.startVote(topic, e.getAuthor());

                ms.sendMessage(e.getChannel(),
                        "Vote started!", "Topic: " + topic + "\nYes ("+ BotSettings.BOT_PREFIX.getPropertyValue()+"yes) or No ("+BotSettings.BOT_PREFIX.getPropertyValue()+"no).\n\nType $end to end the vote.");
                LOG.info(e.getAuthor() + " started a vote with the topic " + topic);
        }
        LOG.debug("End of: Vote.execute");
    }
}
