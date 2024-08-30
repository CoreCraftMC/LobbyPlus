package me.corecraft.lobbyplus.configs.impl.messages;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import me.corecraft.lobbyplus.configs.beans.EntryProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class MiscKeys implements SettingsHolder {

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "Github: https://github.com/CoreCraftMC",
                "",
                "Issues: https://github.com/CoreCraftMC/LobbyPlus/issues",
                "Features: https://github.com/CoreCraftMC/LobbyPlus/issues",
                "",
                "All messages allow the use of {prefix} unless stated otherwise.",
                ""
        };

        conf.setComment("misc", header);
    }

    @Comment("A list of available placeholders: {command}")
    public static final Property<String> unknown_command = newProperty("misc.unknown-command", "{prefix}<red>{command} is not a known command.");

    @Comment("A list of available placeholders: {usage}")
    public static final Property<String> correct_usage = newProperty("misc.correct-usage", "{prefix}<red>The correct usage for this command is <yellow>{usage}");

    public static final Property<String> plugin_reload = newProperty("misc.plugin-reload", "{prefix}<green>Plugin has been reloaded.");

    public static final Property<String> feature_disabled = newProperty("misc.feature-disabled", "{prefix}<red>This feature is disabled.");

    @Comment("This is the message, for the /lobbyplus help command!")
    public static final Property<EntryProperty> help = newBeanProperty(EntryProperty.class, "misc.command-help", new EntryProperty().populate());
}