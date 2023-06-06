package ru.plus_command;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class command_class extends BukkitCommand implements CommandExecutor {
	String commandName;
	Plugin plugin;
	ColorCodesPause ccp = new ColorCodesPause();
	
    protected command_class(String name, Plugin p) {
		super(name);
		this.plugin = p;
		this.commandName = name;
		// TODO Auto-generated constructor stub
	}
    
    public int StringToInt(String s) {
    	String strNum = s.replaceAll("[^\\d]", "");
        if (0 == strNum.length()) {
            return 0;
        }
		return Integer.parseInt(strNum); 
    }
	
	@Override
    public boolean execute(CommandSender sender, String command, String[] args) { // Config.set("MultiCommandSeparatorSymbol", "; ");
		YamlConfiguration Config = Main.getYamlConfiguration();
		
		if (sender instanceof ConsoleCommandSender) {
			String str = Config.getString("Command."+command+".RunCommand");
			boolean colorCodesPause = Config.getBoolean("ColorCodesPause");
		    for(String arrayS0 : str.split(Config.getString("MultiCommandSeparatorSymbol"))) {
		        String[] array0 = arrayS0.split(Config.getString("CommandSeparatorSymbol")); 
		        
		        if (StringToInt(Config.getString("Command."+command+".MinArgument")) <= args.length && args.length <= StringToInt(Config.getString("Command."+command+".MaxArgument"))) {
		        	if (array0[0].equalsIgnoreCase("Console")) {
		            	String commandS = array0[1];
		            	for (int i = 0; i < args.length; i++) {
		            		commandS = commandS.replace("{"+i+"}", args[i]);
		            	}
		            	if (colorCodesPause) {
		            		commandS = ccp.FormatPause(commandS);
		            	}
		            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), commandS);  			
		            }
		        	
		            if (array0[0].equalsIgnoreCase("Player")) {
		            	Bukkit.getServer().getLogger().info(ccp.FormatPause(Config.getString("ErrorCommandRun")));
		            }
		        }else {
		        	if (sender instanceof Player) {
		        		sender.sendMessage(ccp.FormatPause(Config.getString("Command."+command+".Usenge")));
		        		return true;
		        	}
		        	
		        	if (sender instanceof ConsoleCommandSender) {
		        		Bukkit.getServer().getLogger().info(ccp.FormatPause(Config.getString("Command."+command+".Usenge")));
		        		return true;
		        	}
		        }
		    }
    	}
		
		if (sender instanceof Player) {
    		if (sender.hasPermission(Config.getString("Command."+command+".Permission")) || Config.getBoolean("Command."+command+".Defaultpermission")) {
    			
    			String str = Config.getString("Command."+command+".RunCommand");
    			boolean colorCodesPause = Config.getBoolean("ColorCodesPause");
    		    for(String arrayS0 : str.split(Config.getString("MultiCommandSeparatorSymbol"))) {
    		        String[] array0 = arrayS0.split(Config.getString("CommandSeparatorSymbol")); 
    		        
    		        if (StringToInt(Config.getString("Command."+command+".MinArgument")) <= args.length && args.length <= StringToInt(Config.getString("Command."+command+".MaxArgument"))) {
    		        	if (array0[0].equalsIgnoreCase("Console")) {
    		            	String commandS = array0[1];
    		            	commandS = commandS.replace("{player}", sender.getName());
    		            	for (int i = 0; i < args.length; i++) {
    		            		commandS = commandS.replace("{"+i+"}", args[i]);
    		            	}
    		            	if (colorCodesPause) {
    		            		commandS = ccp.FormatPause(commandS);
    		            	}
    		            	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), commandS);  			
    		            }
    		        	
    		            if (array0[0].equalsIgnoreCase("Player")) {
    		            	String commandS = array0[1];
		            	    commandS = commandS.replace("{player}", sender.getName());
		            	    commandS = commandS.replace("{world}", ((Player) sender).getWorld().getName());
		            	    for (int i = 0; i < args.length; i++) {
		            	    	commandS = commandS.replace("{"+i+"}", args[i]);
		            	    }
		            	    if (colorCodesPause) {
			            		commandS = ccp.FormatPause(commandS);
			            	}
		            	    Bukkit.getServer().dispatchCommand(sender, commandS);
    		            }
    		        }else {
    		        	if (sender instanceof Player) {
    		        		sender.sendMessage(ccp.FormatPause(Config.getString("Command."+command+".Usenge")));
    		        		return true;
    		        	}
    		        	
    		        	if (sender instanceof ConsoleCommandSender) {
    		        		Bukkit.getServer().getLogger().info(ccp.FormatPause(Config.getString("Command."+command+".Usenge")));
    		        		return true;
    		        	}
    		        }
    		    }
    		}else {
    			sender.sendMessage(ccp.FormatPause(Config.getString("Command."+command+".PermissionMessange")));
    		}
    	}
        return false;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		// TODO Автоматически созданная заглушка метода
		sender.sendMessage(">> " + arg1 + " " + arg2 + " " + arg3);
		return false;
	}
}
