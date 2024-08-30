package me.corecraft.lobbyplus.listeners.players;

import ch.jalu.configme.SettingsManager;
import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.api.cache.UserManager;
import me.corecraft.lobbyplus.api.cache.objects.User;
import me.corecraft.lobbyplus.api.enums.other.Permissions;
import me.corecraft.lobbyplus.configs.ConfigManager;
import me.corecraft.lobbyplus.configs.impl.types.ProtectionKeys;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class DamageListener implements Listener {

    private final LobbyPlus plugin = LobbyPlus.get();

    private final UserManager userManager = this.plugin.getUserManager();

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player receiver) || !(event.getDamager() instanceof Player sender)) return;

        if (!Permissions.event_player_pvp.hasPermission(sender) || !Permissions.event_player_pvp.hasPermission(receiver)) return;

        final User target = this.userManager.getUser(receiver);
        final User damager = this.userManager.getUser(sender);

        if (target.isPvpEnabled && damager.isPvpEnabled) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        EntityDamageEvent.DamageCause damageCause = event.getCause();

        switch (damageCause) {
            case FALL -> {
                if (this.config.getProperty(ProtectionKeys.event_prevent_fall_damage)) {
                    event.setCancelled(true);
                }
            }

            case FIRE -> {
                if (this.config.getProperty(ProtectionKeys.event_prevent_fire_damage)) {
                    event.setCancelled(true);
                }
            }

            case VOID -> {
                if (this.config.getProperty(ProtectionKeys.event_prevent_void_damage)) {
                    player.setFallDistance(0.0F);

                    this.plugin.getScheduler().runDelayedTask(consumer -> player.teleport(player.getWorld().getSpawnLocation().toCenterLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN), 3L);

                    event.setCancelled(true);
                }
            }
        }
    }
}