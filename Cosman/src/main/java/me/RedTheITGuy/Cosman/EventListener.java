package me.RedTheITGuy.Cosman;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;

public class EventListener implements Listener {
	// Runs when a player is has joined the game
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
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
			return;
		}
		
		// Gets the player in the Bukkit API way
		Player player = event.getPlayer();
		// Loads the player data from LuckPerms
		User user = api.getUserManager().getUser(player.getUniqueId());
		// Gets the user's meta data
		CachedMetaData metaData = user.getCachedData().getMetaData(api.getContextManager().getQueryOptions(player));
		
		// Tries to get the player's nick
		String playerNick = metaData.getMetaValue("nick");
		// Exits if the player doesn't have a nick
		if (playerNick == null) return;
		
		// Gets the name changer class
		NameChanger nameChanger = new NameChanger();
		// Changes the player's name
		nameChanger.ChangeName(player, playerNick);
	}
}
