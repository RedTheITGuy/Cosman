package me.RedTheITGuy.Cosman;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	// Fired when the plugin is first enabled
	@Override
	public void onEnable() {
		// Registers the event listener
		Bukkit.getServer().getPluginManager().registerEvents(new EventListener(), this);
		// Registers the commands
		this.getCommand("nick").setExecutor(new CommandNick());
	}
}
