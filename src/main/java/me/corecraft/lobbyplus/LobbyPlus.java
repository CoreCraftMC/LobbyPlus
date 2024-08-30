package me.corecraft.lobbyplus;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.settings.PacketEventsSettings;
import com.ryderbelserion.vital.common.api.interfaces.IPlugin;
import com.ryderbelserion.vital.common.managers.PluginManager;
import com.ryderbelserion.vital.paper.Vital;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import me.corecraft.lobbyplus.api.cache.UserManager;
import me.corecraft.lobbyplus.api.cache.listeners.CacheListener;
import me.corecraft.lobbyplus.commands.CommandManager;
import me.corecraft.lobbyplus.configs.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;
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
        this.userManager = new UserManager(this);

        ConfigManager.load(getDataFolder());

        List.of(
                new CacheListener()
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