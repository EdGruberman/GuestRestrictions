package edgruberman.bukkit.guestrestrictions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.PluginManager;

import edgruberman.bukkit.messagemanager.MessageLevel;

public class EntityListener extends org.bukkit.event.entity.EntityListener {
    
    public EntityListener(Main plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvent(Event.Type.ENTITY_DAMAGE, this, Event.Priority.Normal, plugin);
        pluginManager.registerEvent(Event.Type.ENTITY_TARGET, this, Event.Priority.Normal, plugin);
    }
    
    @Override
    public void onEntityDamage(final EntityDamageEvent event) {
        if (event.isCancelled()) return;
        
        if (!(event instanceof EntityDamageByEntityEvent)) return;

        EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) event;
        if (!(edbee.getDamager() instanceof Player)) return;
        
        Player player = (Player) edbee.getDamager();
        if (!Main.isGuest(player)) return;
        
        String target = "a " + Main.className(edbee.getEntity().getClass()).substring("Craft".length());
        if (edbee.getEntity() instanceof Player) target = ((Player) edbee.getEntity()).getName();
        
        event.setCancelled(true);
        Main.messageManager.log("Cancelled " + player.getName() + " inflicting damage on " + target, MessageLevel.FINE);
        Main.messageManager.send(player, "Guests are not allowed to inflict damage.", MessageLevel.SEVERE, false);
    }
    
    @Override
    public void onEntityTarget(final EntityTargetEvent event) {
        if (event.isCancelled()) return;
        
        if (!(event.getTarget() instanceof Player)) return;
        
        Player player = (Player) event.getTarget();
        if (!Main.isGuest(player)) return;
        
        event.setCancelled(true);
        
        String entity = Main.className(event.getEntity().getClass()).substring("Craft".length());
        Main.messageManager.log("Cancelled " + entity + " targeting " + player.getName(), MessageLevel.FINER);
    }
}