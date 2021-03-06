package com.mronlinecoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mronlinecoder.commands.AddPlayerCommand;
import com.mronlinecoder.commands.BanCommand;
import com.mronlinecoder.commands.BanXCommand;
import com.mronlinecoder.commands.BringCommand;
import com.mronlinecoder.commands.GalaxyCommand;
import com.mronlinecoder.commands.GenCommand;
import com.mronlinecoder.commands.InfoCommand;
import com.mronlinecoder.commands.JoinCommand;
import com.mronlinecoder.commands.KickCommand;
import com.mronlinecoder.commands.RageQuitCommand;
import com.mronlinecoder.commands.RankCommand;
import com.mronlinecoder.commands.ReviewCommand;
import com.mronlinecoder.commands.SecretCommand;
import com.mronlinecoder.commands.UnbanCommand;
import com.mronlinecoder.commands.UndoXCommand;
import com.mronlinecoder.commands.WBuildCommand;

public class AndromedaCommands extends JavaPlugin implements CommandExecutor, Listener {
	
	HashMap<String, GalaxyCommand> commands = new HashMap<>();
	Rank rank = new Rank();
	ArrayList<String> cannotBuild = new ArrayList<>();
	public Logger logger;
	
	//To save server HDD/SSD lifetime and perfomance speed, we use this "queue" to apply changes to PlayerDB every 1 minute.
	HashMap<String, Integer> chatQueue = new HashMap<>();
	
	
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
				
				for (Map.Entry<String, Integer> entry : chatQueue.entrySet()) {
				    String key = entry.getKey();
				    int value = entry.getValue();
					GalaxyPlayer gp = GalaxyPlayer.load(key);
					gp.setMessages(value);
					gp.save();
					chatQueue.remove(key);
				}
			}
		}, 20, 1200);
		getServer().getPluginManager().registerEvents(this, this);
		registerCommands();
		logger = Logger.getLogger("AndromedaCore");
        Logger.getLogger("AndromedaCore").setUseParentHandlers(false);
		FileHandler fh;
		 try {  
		        fh = new FileHandler("plugins/Andromeda/andromeda_log.txt", true);  
		        fh.setFormatter( new LogFormatter());  
		        Logger.getLogger("AndromedaCore").addHandler(fh);

		        Logger.getLogger("AndromedaCore").info("--- Server start up ---");  

		    } catch (SecurityException e) {  
		        e.printStackTrace();  
		    } catch (IOException e) {  
		        e.printStackTrace();  
		 }  

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
		commands.put("addplayer", new AddPlayerCommand());
		commands.put("banx", new BanXCommand(getConfig()));
		commands.put("undox", new UndoXCommand());
		commands.put("gen", new GenCommand(this));
		commands.put("wbuild", new WBuildCommand());
		commands.put("secret", new SecretCommand());
		
		
		getCommand("join").setExecutor(this);
		getCommand("ragequit").setExecutor(this);
		getCommand("info").setExecutor(this);
		getCommand("ban").setExecutor(this);
		getCommand("rank").setExecutor(this);
		getCommand("kick").setExecutor(this);
		getCommand("review").setExecutor(this);
		getCommand("unban").setExecutor(this);
		getCommand("bring").setExecutor(this);
		getCommand("addplayer").setExecutor(this);
		getCommand("banx").setExecutor(this);
		getCommand("undox").setExecutor(this);
		getCommand("gen").setExecutor(this);
		getCommand("wbuild").setExecutor(this);
		getCommand("secret").setExecutor(this);
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
			
			logger.info("User "+sender.getName()+" issued a command: /"+label);
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
	
	/*@EventHandler
	public void onCmd(PlayerCommandPreprocessEvent e) {
		if (e.isCancelled()) return;
		if (e.getMessage().startsWith("//") && !e.getMessage().toLowerCase().startsWith("//limit")) {
			e.getPlayer().chat("//limit "+getConfig().getInt("ranks."+rank.getRankName(e.getPlayer().getName())+".drawLimit", 100));
		}
	}*/
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.isCancelled()) {
			return;
		}
		
		Player p = e.getPlayer();
		
		if (cannotBuild.contains(p.getName())) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED+"You cannot build in this world!");
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.isCancelled()) {
			return;
		}
		
		Player p = e.getPlayer();
		
		if (cannotBuild.contains(p.getName())) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED+"You cannot build in this world!");
		}
	}
	
	@EventHandler
	public void onWorld(PlayerChangedWorldEvent e) {
		Player pl = e.getPlayer();
		String world = pl.getWorld().getName();
		String requiredRank = getConfig().getString("worlds."+world+".build", "Guest");
		getLogger().info("Build rank for world "+world+" is "+requiredRank);
		if (!rank.hasRank(pl.getName(), requiredRank)) {
			pl.sendMessage(ChatColor.GRAY+"Note: you cannot build in this world.");
			cannotBuild.add(pl.getName());
		} else {
			if (cannotBuild.contains(pl.getName())) {
				cannotBuild.remove(pl.getName());
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (e.isCancelled()) {
			return;
		}
		if (e.getMessage().startsWith("/")) {
			return;
		}
		
		if (e.getMessage().equalsIgnoreCase("kick me")) {
			getServer().dispatchCommand(getServer().getConsoleSender(), "kick "+e.getPlayer().getName()+" No problem.");
		}
		
		if (chatQueue.get(e.getPlayer().getName()) == null) {
			chatQueue.put(e.getPlayer().getName(), 1);
		} else {
			chatQueue.put(e.getPlayer().getName(), chatQueue.get(e.getPlayer().getName())+1);
		}
		
	}
}

