package com.mronlinecoder.commands;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public abstract class GalaxyCommand {
	public String usage;
	public int minArgs;
	public String label;
	
	public abstract void run(Server server, CommandSender sender, String[] args);

	public GalaxyCommand(String usage, int minArgs,
			String label) {
		this.usage = usage;
		this.minArgs = minArgs;
		this.label = label;
	}
	
	
}
