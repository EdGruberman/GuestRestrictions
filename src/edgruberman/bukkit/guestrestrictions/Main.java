package edgruberman.bukkit.guestrestrictions;

import org.bukkit.entity.Player;

import edgruberman.bukkit.accesscontrol.AccountManager;
import edgruberman.bukkit.messagemanager.MessageManager;

public final class Main extends org.bukkit.plugin.java.JavaPlugin {
    
    static ConfigurationFile configurationFile;
    static MessageManager messageManager;
    
    public void onLoad() {
        Main.configurationFile = new ConfigurationFile(this);
        Main.configurationFile.load();
        
        Main.messageManager = new MessageManager(this);
        Main.messageManager.log("Version " + this.getDescription().getVersion());
    }
	
    public void onEnable() {
        new PlayerListener(this);
        new EntityListener(this);
        new VehicleListener(this);
        new CheatDisabler(this);
        
        Main.messageManager.log("Plugin Enabled");
    }
    
    public void onDisable() {
        Main.messageManager.log("Plugin Disabled");
    }
    
    static boolean isGuest(final Player player) {
        return AccountManager.getAccount(player.getName()).getMemberships().size() == 0;
    }
    
    static String className(final Class<?> classOf) {
        String[] name = classOf.getName().split("\\.");
        return name[name.length - 1];
    }
}