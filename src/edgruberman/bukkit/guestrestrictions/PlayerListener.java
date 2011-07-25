package edgruberman.bukkit.guestrestrictions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;

import edgruberman.bukkit.messagemanager.MessageLevel;

public class PlayerListener extends org.bukkit.event.player.PlayerListener {
    
    private static final Set<Material> DENIED_MATERIAL = new HashSet<Material>(Arrays.asList(
            Material.CHEST
          , Material.FURNACE
          , Material.DISPENSER
          , Material.STONE_BUTTON
          , Material.LEVER
          , Material.DIODE_BLOCK_OFF
          , Material.DIODE_BLOCK_ON
    ));
    
    public PlayerListener(Main plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        
        pluginManager.registerEvent(Event.Type.PLAYER_INTERACT, this, Event.Priority.Low, plugin);
        pluginManager.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, this, Event.Priority.Low, plugin);
    }
    
    @Override
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        
        if (!(Main.isGuest(event.getPlayer()))) return;
        
        if (!PlayerListener.DENIED_MATERIAL.contains(event.getClickedBlock().getType())) return;
        
        event.setCancelled(true);
        Main.messageManager.log(
                "Cancelled " + event.getPlayer().getName() + " interacting with " + event.getClickedBlock().getType().name()
                    + " at x:" + event.getClickedBlock().getX()
                    + " y:" + event.getClickedBlock().getY()
                    + " z:" + event.getClickedBlock().getZ()
                , MessageLevel.FINE
        );
        Main.messageManager.send(event.getPlayer(), "Guests are not allowed to interact with " + event.getClickedBlock().getType().name() + "s", MessageLevel.SEVERE, false);
    }
    
    @Override
    public void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
        if (event.isCancelled()) return;
        
        if (!(Main.isGuest(event.getPlayer()))) return;
        
        if (!(event.getRightClicked() instanceof PoweredMinecart) && !(event.getRightClicked() instanceof StorageMinecart)) return;
        
        event.setCancelled(true);
        Main.messageManager.log(
                "Cancelled " + event.getPlayer().getName() + " interacting with " + Main.className(event.getRightClicked().getClass())
                    + " at x:" + event.getRightClicked().getLocation().getBlockX()
                    + " y:" + event.getRightClicked().getLocation().getBlockY()
                    + " z:" + event.getRightClicked().getLocation().getBlockZ()
                , MessageLevel.FINE
        );
        Main.messageManager.send(event.getPlayer(), "Guests are not allowed to interact with " + Main.className(event.getRightClicked().getClass()).substring("Craft".length()) + "s", MessageLevel.SEVERE, false);
    }
}