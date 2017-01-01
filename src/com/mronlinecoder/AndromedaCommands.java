package com.mronlinecoder;

import java.io.File;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mronlinecoder.commands.BanCommand;
import com.mronlinecoder.commands.BringCommand;
import com.mronlinecoder.commands.GalaxyCommand;
import com.mronlinecoder.commands.InfoCommand;
import com.mronlinecoder.commands.JoinCommand;
import com.mronlinecoder.commands.KickCommand;
import com.mronlinecoder.commands.RageQuitCommand;
import com.mronlinecoder.commands.RankCommand;
import com.mronlinecoder.commands.ReviewCommand;
import com.mronlinecoder.commands.UnbanCommand;

public class AndromedaCommands extends JavaPlugin implements CommandExecutor, Listener {
	
	HashMap<String, GalaxyCommand> commands = new HashMap<>();
	
	public void onEnable() {

		File folder = new File("plugins/Andromeda/players");
		if (!folder.exists()) {
			getLogger().info("/plugins/Andromeda folder is not found! Creating...");
			folder.mkdirs();
		}
		getServer().getScheduler().scheduleSyncRepeatingTask(this , new Runnable() {
			
			@Override
			public void run() {
				for (Player pl : getServer().getOnlinePlayers()) {
					GalaxyPlayer gpl = GalaxyPlayer.load(pl.getName());
					gpl.setTime(1);
					gpl.save();
				}
			}
		}, 20, 1200);
		getServer().getPluginManager().registerEvents(this, this);
		registerCommands();
	}
	
	public void registerCommands() {
		commands.put("ragequit", new RageQuitCommand());
		commands.put("info", new InfoCommand());
		commands.put("ban", new BanCommand(getConfig()));
		commands.put("kick", new KickCommand(getConfig()));
		commands.put("rank", new RankCommand(getConfig()));
		commands.put("review", new ReviewCommand());
		commands.put("join", new JoinCommand());
		commands.put("unban", new UnbanCommand());
		commands.put("bring", new BringCommand(getConfig()));
		
		
		getCommand("join").setExecutor(this);
		getCommand("ragequit").setExecutor(this);
		getCommand("info").setExecutor(this);
		getCommand("ban").setExecutor(this);
		getCommand("rank").setExecutor(this);
		getCommand("kick").setExecutor(this);
		getCommand("review").setExecutor(this);
		getCommand("unban").setExecutor(this);
		getCommand("bring").setExecutor(this);
	}
	
	public void onDisable() {
		saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
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
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		GalaxyPlayer pl = GalaxyPlayer.load(e.getPlayer().getName());
		pl.setLogins(1);
		pl.save();
	}
}
