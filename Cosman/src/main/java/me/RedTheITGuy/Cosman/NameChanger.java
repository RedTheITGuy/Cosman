package me.RedTheITGuy.Cosman;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class NameChanger {
	public void ChangeName(Player player, String name) {
		// Formats the name correctly
		name = "~" + ChatColor.translateAlternateColorCodes('&', name);
		// Sets the player's display name
		player.setDisplayName(name);
		
		// Creates the message to be sent (Bungee component for allowing clicking)
		ComponentBuilder message = new ComponentBuilder("[NICK] ").color(ChatColor.GOLD).bold(true);
		// Adds the main info to the message
		message.append("You are using the nickname ").color(ChatColor.WHITE).append(name).append(".\n").color(ChatColor.WHITE);
		
		// Creates the component for the clickable section
		TextComponent clickableText = new TextComponent("You can disable you're nick with \"/nick off/reset\".");
		// Sets the hover event for that text
		clickableText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GOLD + "Click here to reset your nickname.")));
		// Sets the click event for the text
		clickableText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/nick off"));
		// Adds the clickable text to the message
		message.append(clickableText).color(ChatColor.AQUA);
		
		// Sends the player the message
		player.spigot().sendMessage(message.create());
	}
}
