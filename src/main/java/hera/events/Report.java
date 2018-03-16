package d4jbot.events;

import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Report implements Command {

	private static Report instance;

	public static Report getInstance() {
		if (instance == null) {
			instance = new Report();
		}
		return instance;
	}

	private MessageSender ms;

	// constructor
	private Report() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		String[] args = e.getMessage().getContent().split(" ");

		if (args.length > 2 && args[1].startsWith("<@")) {
			String reportedUser = args[1];
			String message = "";
			for (int i = 2; i < args.length; i++) {
				message += args[i] + " ";
			}

			ms.sendMessage(BoundChannel.REPORT.getBoundChannel(), "Reporter:	 " + e.getAuthor().mention()
					+ "\nRecipient:	" + reportedUser + "\n\nReport message:\n" + message);
			ms.sendMessage(e.getChannel(), "Reported " + reportedUser);
		} else {
			ms.sendMessage(e.getChannel(), "Invalid report! \nReport example: $report <@userToReport> <reportMessage>");
		}
	}
}