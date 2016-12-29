package com.mronlinecoder;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.mronlinecoder.commands.GalaxyCommand;
import com.mronlinecoder.commands.RageQuitCommand;

public class AndromedaCommands extends JavaPlugin implements CommandExecutor{
	
	HashMap<String, GalaxyCommand> commands = new HashMap<>();
	
	public void onEnable() {
		registerCommands();
	}
	
	public void registerCommands() {
		commands.put("ragequit", new RageQuitCommand());
		
		getCommand("ragequit").setExecutor(this);
	}
	
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		getLogger().info("Label: "+label);
		if (commands.get(label) != null) {
			GalaxyCommand gcmd = commands.get(label);
			if (args.length < gcmd.minArgs) {
				sender.sendMessage(ChatColor.GRAY+"Usage: "+gcmd.usage);
				return false;
			}
			
			gcmd.run(getServer(), sender, args);
		} else {
			sender.sendMessage(ChatColor.GRAY+"Unknown command!");
		}
		
		return false;
	}
}
