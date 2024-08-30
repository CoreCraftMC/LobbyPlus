package me.corecraft.lobbyplus.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.vital.common.utils.FileUtil;
import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.configs.impl.messages.ErrorKeys;
import me.corecraft.lobbyplus.configs.impl.messages.MiscKeys;
import me.corecraft.lobbyplus.configs.impl.messages.PlayerKeys;
import me.corecraft.lobbyplus.configs.impl.messages.commands.ToggleKeys;
import me.corecraft.lobbyplus.configs.impl.types.ConfigKeys;
import me.corecraft.lobbyplus.configs.impl.types.ProtectionKeys;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private static final Map<String, SettingsManager> locales = new HashMap<>();

    private static SettingsManager config;

    /**
     * Loads configuration files.
     */
    public static void load(final File dataFolder) {
        final File localeFolder = new File(dataFolder, "locale");

        YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).charset(StandardCharsets.UTF_8).build();

        config = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class, ProtectionKeys.class)
                .create();

        FileUtil.extracts(LobbyPlus.class, "/locale/", dataFolder.toPath().resolve("locale"), false);

        final List<String> files = FileUtil.getFiles(dataFolder, "locale", ".yml");

        locales.put("en_US", SettingsManagerBuilder
                .withYamlFile(new File(localeFolder, "en_US.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(MiscKeys.class, PlayerKeys.class, ErrorKeys.class, ToggleKeys.class)
                .create());

        files.forEach(file -> {
            if (!locales.containsKey(file)) {
                final SettingsManager settings = SettingsManagerBuilder
                        .withYamlFile(new File(localeFolder, file + ".yml"), builder)
                        .useDefaultMigrationService()
                        .configurationData(MiscKeys.class, PlayerKeys.class, ErrorKeys.class, ToggleKeys.class)
                        .create();

                locales.put(file, settings);
            }
        });
    }

    /**
     * Refreshes configuration files.
     */
    public static void refresh() {
        config.reload();

        locales.values().forEach(SettingsManager::reload);
    }

    /**
     * @return gets config.yml
     */
    public static SettingsManager getConfig() {
        return config;
    }

    /**
     * Gets the locale for this identifier, or the default locale.
     *
     * @param locale the locale
     * @return {@link SettingsManager}
     */
    public static SettingsManager getLocale(final String locale) {
        if (config.getProperty(ConfigKeys.per_player_locale)) {
            return locales.getOrDefault(config.getProperty(ConfigKeys.default_locale_file), locales.get("en_US"));
        }

        return locales.getOrDefault(locale, locales.get("en_US"));
    }
}