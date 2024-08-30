package me.corecraft.lobbyplus.listeners.players;

import ch.jalu.configme.SettingsManager;
import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.api.cache.UserManager;
import me.corecraft.lobbyplus.api.cache.objects.User;
import me.corecraft.lobbyplus.api.enums.other.Permissions;
import me.corecraft.lobbyplus.api.enums.types.BypassType;
import me.corecraft.lobbyplus.configs.ConfigManager;
import me.corecraft.lobbyplus.configs.impl.types.ProtectionKeys;
import me.corecraft.lobbyplus.utils.MiscUtils;
import net.kyori.adventure.sound.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class InteractionListener implements Listener {

    private final LobbyPlus plugin = LobbyPlus.get();

    private final UserManager userManager = this.plugin.getUserManager();

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();

        final Block block = event.getBlock();

        if (block.getType().isAir()) return;

        if (!this.config.getProperty(ProtectionKeys.event_prevent_block_interact) || Permissions.event_block_interact.hasPermission(player)) return;

        final User user = this.userManager.getUser(player);

        if (user != null) {
            if (user.activeBypassTypes.contains(BypassType.allow_block_interact.getName())) {
                return;
            }
        }

        final Block block = event.getBlock();

        MiscUtils.play(player, block.getLocation(), this.config.getProperty(ProtectionKeys.protection_sound), Sound.Source.PLAYER);

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        final Block block = event.getBlock();

        if (block.getType().isAir()) return;

        if (!this.config.getProperty(ProtectionKeys.event_prevent_block_interact) || Permissions.event_block_interact.hasPermission(player)) return;

        final User user = this.userManager.getUser(player);

        if (user != null) {
            if (user.activeBypassTypes.contains(BypassType.allow_block_interact.getName())) {
                return;
            }
        }

        final Block block = event.getBlock();
        final Player player = event.getPlayer();

        final Block block = event.getClickedBlock();

        if (block == null || block.getType().isAir()) return;

        MiscUtils.play(player, block.getLocation(), this.config.getProperty(ProtectionKeys.protection_sound), Sound.Source.PLAYER);

        event.setCancelled(true);
    }
}