package me.RedTheITGuy.Cosman;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.MetaNode;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class NameChanger {
	public void ChangeName(Player player, String name) {
		// Formats the name correctly
		name = "~" + ChatColor.translateAlternateColorCodes('&', name) + ChatColor.RESET;
		// Sets the player's display name
		player.setDisplayName(name);
		// Sets the player's name for their name tag and for death messages, etc.
		player.setPlayerListName(name);
		
		// Creates the message to be sent (Bungee component for allowing clicking)
		ComponentBuilder message = new ComponentBuilder("[NICK] ").color(ChatColor.GOLD).bold(true);
		// Adds the main info to the message
		message.append("You are using the nickname ").color(ChatColor.WHITE).append(name).append(".\n").color(ChatColor.WHITE);
		
		// Creates the component for the clickable section
		TextComponent clickableText = new TextComponent("You can disable you're nick with \"/nick reset\".");
		// Sets the hover event for that text
		clickableText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GOLD + "Click here to reset your nickname.")));
		// Sets the click event for the text
		clickableText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/nick reset"));
		// Adds the clickable text to the message
		message.append(clickableText).color(ChatColor.AQUA);
		
		// Sends the player the message
		player.spigot().sendMessage(message.create());
	}
	
	public int SetName(Player player, String name) {
		// Stores the name with the colour striped
		String nameNoColour = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name));
		// Exits if the name is invalid
		if (nameNoColour.isEmpty() || nameNoColour.length() < 3 || nameNoColour.length() > 16 || nameNoColour.matches("\\W")) return 1;
		
		// Gets the provider for LuckPerms
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		// Creates the variable to store the API
		LuckPerms api = null;
		// Gets the LuckPerms API is the provider was retrieved
		if (provider != null) api = provider.getProvider();
		// Runs if the API is still null
		if (api == null) {
			// Tells the console an error as occurred
			Bukkit.getLogger().warning("Could not load the LuckPerms API.");
			// Exits the method
			return 2;
		}
		
		// Exits if the player is offline
		if (!player.isOnline()) return 3;
		// Loads the player data from LuckPerms
		User user = api.getUserManager().getUser(player.getUniqueId());
		
		// Builds the node for editing the meta
		MetaNode node = MetaNode.builder("nick", name).build();
		// Sets the players metadata
		user.data().add(node);
		
		// Changes the players name
		ChangeName(player, name);
		
		// Exits telling the program the method run successfully
		return 0;
	}
	
	public void ResetName(Player player) {
		// Resets the players display name
		player.setDisplayName(player.getName());
		// Resets the player's list name
		player.setPlayerListName(null);
		
		// Tells the player there name has been reset
		player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[NICK] " + ChatColor.RESET + "You're nickname has been reset.");
	}
}
