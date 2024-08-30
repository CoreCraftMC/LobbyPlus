package me.corecraft.lobbyplus.listeners.players;

import ch.jalu.configme.SettingsManager;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.api.cache.UserManager;
import me.corecraft.lobbyplus.api.cache.objects.User;
import me.corecraft.lobbyplus.api.enums.other.Permissions;
import me.corecraft.lobbyplus.api.enums.types.BypassType;
import me.corecraft.lobbyplus.configs.ConfigManager;
import me.corecraft.lobbyplus.configs.impl.types.ProtectionKeys;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerListener implements Listener {

    private final LobbyPlus plugin = LobbyPlus.get();

    private final UserManager userManager = this.plugin.getUserManager();

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerPickupEvent(PlayerPickItemEvent event) {
        final Player player = event.getPlayer();

        if (!this.config.getProperty(ProtectionKeys.event_prevent_item_pickup) || Permissions.event_item_pickup.hasPermission(player)) return;

        final User user = this.userManager.getUser(player);

        if (user != null) {
            if (user.activeBypassTypes.contains(BypassType.allow_item_pickup.getName())) {
                return;
            }
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerFoodChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (!this.config.getProperty(ProtectionKeys.event_prevent_hunger_change)) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDropEvent(PlayerDropItemEvent event) {
        final Player player = event.getPlayer();

        if (!this.config.getProperty(ProtectionKeys.event_prevent_item_drop) || Permissions.event_item_drop.hasPermission(player)) return;

        final User user = this.userManager.getUser(player);

        if (user != null) {
            if (user.activeBypassTypes.contains(BypassType.allow_item_drop.getName())) {
                return;
            }
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        //todo() add toggle?

        event.deathMessage(null);
    }
}