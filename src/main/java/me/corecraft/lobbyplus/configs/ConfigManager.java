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

    private static File rootFolder;

    private static File localeFolder;

    /**
     * Loads configuration files.
     */
    public static void load(final File dataFolder) {
        rootFolder = dataFolder;
        localeFolder = new File(rootFolder, "locale");

        YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).charset(StandardCharsets.UTF_8).build();

        config = SettingsManagerBuilder
                .withYamlFile(new File(rootFolder, "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class, ProtectionKeys.class)
                .create();

        FileUtil.extracts(LobbyPlus.class, "/locale/", rootFolder.toPath().resolve("locale"), false);

        final List<String> files = FileUtil.getFiles(rootFolder, "locale", ".yml");

        locales.put("en-US", SettingsManagerBuilder
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
        return locales.getOrDefault(locale, locales.get("en_US"));
    }

    public static File getRootFolder() {
        return rootFolder;
    }

    public static File getLocaleFolder() {
        return localeFolder;
    }
}