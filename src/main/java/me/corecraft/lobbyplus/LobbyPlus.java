package me.corecraft.lobbyplus;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.settings.PacketEventsSettings;
import com.ryderbelserion.vital.common.api.interfaces.IPlugin;
import com.ryderbelserion.vital.common.managers.PluginManager;
import com.ryderbelserion.vital.paper.Vital;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import me.corecraft.lobbyplus.api.cache.UserManager;
import me.corecraft.lobbyplus.api.cache.listeners.CacheListener;
import me.corecraft.lobbyplus.api.enums.other.Permissions;
import me.corecraft.lobbyplus.commands.CommandManager;
import me.corecraft.lobbyplus.configs.ConfigManager;
import me.corecraft.lobbyplus.listeners.MobListener;
import me.corecraft.lobbyplus.listeners.ProtectionListener;
import me.corecraft.lobbyplus.listeners.players.InteractionListener;
import me.corecraft.lobbyplus.listeners.players.PlayerListener;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Locale;

public class LobbyPlus extends Vital {

    public static LobbyPlus get() {
        return JavaPlugin.getPlugin(LobbyPlus.class);
    }

    private final long startTime;

    public LobbyPlus() {
        this.startTime = System.nanoTime();
    }

    @Override
    public void onLoad() {
        if (PluginManager.isEnabled("packetevents")) {
            PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this, new PacketEventsSettings().checkForUpdates(false)));
            PacketEvents.getAPI().load();
        }
    }

    private UserManager userManager;

    @Override
    public void onEnable() {
        ConfigManager.load(getDataFolder());

        this.userManager = new UserManager(this);

        final org.bukkit.plugin.@NotNull PluginManager server = getServer().getPluginManager();

        for (final Permissions value : Permissions.values()) {
            if (!value.shouldRegister()) continue;

            final String node = value.getNode();

            if (server.getPermission(node) == null) {
                final String description = value.getDescription();
                final PermissionDefault permissionDefault = value.isDefault();

                final Permission permission = new Permission(node, description, permissionDefault);

                server.addPermission(permission);

                if (isVerbose()) {
                    getComponentLogger().info("Successfully added permission {} to the server! Default: {}", node, permissionDefault);
                }
            } else {
                if (isVerbose()) {
                    getComponentLogger().warn("The permission {} is already added to the server!", node);
                }
            }
        }

        List.of(
                new CacheListener(),

                new InteractionListener(),
                new ProtectionListener(),
                new PlayerListener(),
                new MobListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        CommandManager.load();

        getComponentLogger().info("Done ({})!", String.format(Locale.ROOT, "%.3fs", (double) (System.nanoTime() - this.startTime) / 1.0E9D));
    }

    @Override
    public void onDisable() {
        final IPlugin packets = PluginManager.getPlugin("packetevents");

        if (packets != null) {
            packets.stop();
        }

        final org.bukkit.plugin.@NotNull PluginManager server = getServer().getPluginManager();

        // this is to account for plugman oddities
        for (final Permissions value : Permissions.values()) {
            final String node = value.getNode();

            if (server.getPermission(node) != null) {
                server.removePermission(node);

                if (isVerbose()) {
                    getComponentLogger().info("Successfully removed permission {} from the server, since we are disabling.", node);
                }
            } else {
                if (isVerbose()) {
                    getComponentLogger().warn("The permission {} was not removed from the server since it was not found!", node);
                }
            }
        }

        getFileManager().purge();
    }

    public final UserManager getUserManager() {
        return this.userManager;
    }

    @Override
    public final boolean isLegacy() {
        return false;
    }
}