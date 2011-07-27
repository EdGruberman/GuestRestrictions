package edgruberman.bukkit.guestrestrictions;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

public class ZombeDisabler extends org.bukkit.event.player.PlayerListener {
    
    public ZombeDisabler(Main plugin) {
        plugin.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, this, Event.Priority.Monitor, plugin);
    }
    
    @Override
    public void onPlayerJoin(final PlayerJoinEvent event) {      
        String disableFly = "§f §f §1 §0 §2 §4";   // Hidden no-z-fly equivalent
        String disableCheat = "§f §f §2 §0 §4 §8"; // Hidden no-z-cheat equivalent
        
        String disableAll = disableFly + " " + disableCheat;
        
        event.getPlayer().sendMessage(disableAll);
    }
}