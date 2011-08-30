package edgruberman.bukkit.guestrestrictions;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

public class CheatDisabler extends org.bukkit.event.player.PlayerListener {
    
    public CheatDisabler(Main plugin) {
        plugin.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, this, Event.Priority.Monitor, plugin);
    }
    
    @Override
    public void onPlayerJoin(final PlayerJoinEvent event) {
        // Zombe disable
        String zombeDisableFly = "§1 §0 §2 §4";   // Hidden no-z-fly equivalent
        String zombeDisableCheat = "§2 §0 §4 §8"; // Hidden no-z-cheat equivalent
        
        // CJB disable
        String cjbDisableAll = "§3 §9 §2 §0 §0 §0";
        // String nofly = "§3 §9 §2 §0 §0 §1";
        // String noxray = "§3 §9 §2 §0 §0 §2";
        // String noentity = "§3 §9 §2 §0 §0 §3";
        
        String disableAll = "§f §f " + zombeDisableFly + " " + zombeDisableCheat + " " + cjbDisableAll;
        
        event.getPlayer().sendMessage(disableAll);
    }
}