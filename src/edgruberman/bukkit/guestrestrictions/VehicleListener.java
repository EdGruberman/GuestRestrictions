package edgruberman.bukkit.guestrestrictions;

import org.bukkit.entity.Player;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.event.Event;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.plugin.PluginManager;

import edgruberman.bukkit.messagemanager.MessageLevel;

public class VehicleListener extends org.bukkit.event.vehicle.VehicleListener {
    
    public VehicleListener(Main plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        
        pluginManager.registerEvent(Event.Type.VEHICLE_DAMAGE, this, Event.Priority.Low, plugin);
    }
    
    @Override
    public void onVehicleDamage(final VehicleDamageEvent event) {
        if (event.isCancelled()) return;
        
        if (!(event.getAttacker() instanceof Player)) return;
        
        Player player = (Player) event.getAttacker();
        if (!(Main.isGuest(player))) return;
        
        if (!(event.getVehicle() instanceof PoweredMinecart) && !(event.getVehicle() instanceof StorageMinecart)) return;
        
        event.setCancelled(true);
        Main.messageManager.log(
                "Cancelled " + player.getName() + " interacting with " + Main.className(event.getVehicle().getClass())
                    + " at x:" + event.getVehicle().getLocation().getBlockX()
                    + " y:" + event.getVehicle().getLocation().getBlockY()
                    + " z:" + event.getVehicle().getLocation().getBlockZ()
                , MessageLevel.FINE
        );
        Main.messageManager.send(player, "Guests are not allowed to inflict damage on " + Main.className(event.getVehicle().getClass()).substring("Craft".length()) + "s", MessageLevel.SEVERE, false);
    }
}