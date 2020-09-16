package me.RedTheITGuy.Cosman;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNick implements CommandExecutor {
	// Called when the command is run
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Creates a boolean to store if the GUI should be brought up
		boolean useGUI = true;
		// Creates a variable to store the player's data
		Player player = null;
		// Creates a variable to store the new nick
		String nick = "";
		
		// Runs if there is one argument
		if (args.length == 1) {
			// Gets the player entity from the command sender
			player = (Player) sender;
			// Exits if the sender is not a player
			if (player == null) return false;
			// Runs if the player wants to reset their nick
			if (args[0].equalsIgnoreCase("reset")) {
				// Gets the name changer class
				NameChanger nameChanger = new NameChanger();
				// Resets the player's name
				nameChanger.ResetName(player);
				// Exits the command
				return true;
			}
			// Gets the nick name from the arguments
			nick = args[0];
			// Stops the use of the GUI
			useGUI = false;
		}
		else if (args.length == 2) {
			// Runs if the player does not have the permission to nick others
			if (!sender.hasPermission("cosman.nick.others")) {
				// Returns the no permission string
				sender.sendMessage(Bukkit.getPluginCommand("nick").getPermissionMessage());
				// Exits the command
				return true;
			}
			// Gets the player entity from the command sender
			player = Bukkit.getPlayer(args[0]);
			// Runs if the sender is not a player
			if (player == null) {
				// Tells the sender an online player could not be found
				sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[NICK] " + ChatColor.RESET + "Could not find player " + args[0] + ", are they online?");
				// Exits the command
				return false;
			}
			// Runs if the player wants to reset their nick
			if (args[1].equalsIgnoreCase("reset")) {
				// Gets the name changer class
				NameChanger nameChanger = new NameChanger();
				// Resets the player's name
				nameChanger.ResetName(player);
				// Tells the sender the player's nick was reset
				sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[NICK] " + ChatColor.RESET + "Successfully reset " + player.getName() + "'s nick.");
				// Exits the command
				return true;
			}
			// Gets the nick name from the arguments
			nick = args[1];
			// Stops the use of the GUI
			useGUI = false;
		}
		
		// Runs if the GUI does not need to be used
		if (!useGUI) {
			// Gets the name changer class
			NameChanger nameChanger = new NameChanger();
			// Runs the set name method, catching errors
			switch (nameChanger.SetName(player, nick)) {
				case 0:
					// Tells the sender the player was nicked if needed
					if (!player.equals((Player) sender)) sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[NICK] " + ChatColor.RESET + "Successfully nicked " + player.getName() + ".");
					break;
				case 1:
					// Tells the user of the error that occurred
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[NICK] " + ChatColor.RESET + "Invalid nickname, please only use nicknames 3-16 long using only letters, numbers and underscores.");
					break;
				case 2:
					// Tells the user of the error that occurred
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[NICK] " + ChatColor.RESET + "The plugin experianced a technical error, please try again later.");
					break;
				case 3:
					// Tells the user of the error that occurred
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[NICK] " + ChatColor.RESET + player + " is not online.");
					break;
				default:
					// Tells the user an error occurred
					sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[NICK] " + ChatColor.RESET + "An unknown error occured.");
			}
			// Exits the command
			return true;
		}
		
		// Tells the server the command was run successfully
		return true;
	}
}
