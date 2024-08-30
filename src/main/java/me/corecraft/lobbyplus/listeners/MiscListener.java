package me.corecraft.lobbyplus.listeners;

import ch.jalu.configme.SettingsManager;
import me.corecraft.lobbyplus.configs.ConfigManager;
import me.corecraft.lobbyplus.configs.impl.types.ProtectionKeys;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class MiscListener implements Listener {

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onWeatherChange(WeatherChangeEvent event) {
        if (!this.config.getProperty(ProtectionKeys.event_prevent_weather_change)) return;

        event.setCancelled(event.toWeatherState());
    }
}