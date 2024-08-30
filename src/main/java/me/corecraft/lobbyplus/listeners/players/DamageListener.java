package me.corecraft.lobbyplus.listeners.players;

import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.api.cache.UserManager;
import me.corecraft.lobbyplus.api.cache.objects.User;
import me.corecraft.lobbyplus.api.enums.other.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private final LobbyPlus plugin = LobbyPlus.get();

    private final UserManager userManager = this.plugin.getUserManager();

    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player receiver) || !(event.getDamager() instanceof Player sender)) return;

        if (!Permissions.event_player_pvp.hasPermission(sender) || !Permissions.event_player_pvp.hasPermission(receiver)) return;

        final User target = this.userManager.getUser(receiver);
        final User damager = this.userManager.getUser(sender);

        if (target.isPvpEnabled && damager.isPvpEnabled) return;

        event.setCancelled(true);
    }
}