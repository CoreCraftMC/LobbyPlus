package me.corecraft.lobbyplus.api.cache.listeners;

import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.api.cache.UserManager;
import me.corecraft.lobbyplus.api.cache.objects.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.Locale;

public class CacheListener implements Listener {

    private final LobbyPlus plugin = LobbyPlus.get();

    private final UserManager userManager = this.plugin.getUserManager();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(PlayerLoginEvent event) {
        this.userManager.addUser(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLocaleChange(PlayerLocaleChangeEvent event) {
        final Locale locale = event.locale();

        final User user = this.userManager.getUser(event.getPlayer());

        if (user == null) return;

        user.updateLocale(locale);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.userManager.removeUser(event.getPlayer());
    }
}