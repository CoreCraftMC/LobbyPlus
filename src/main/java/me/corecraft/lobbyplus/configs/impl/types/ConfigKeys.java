package me.corecraft.lobbyplus.configs.impl.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigKeys implements SettingsHolder {

    @Comment("The prefix used in commands")
    public static final Property<String> command_prefix = newProperty("root.prefix", "<gradient:red:yellow>CoreCraft <reset>");

    @Comment("This will take into consideration, what the client's locale is set to, on join and when they change it, if this is set to true.")
    public static final Property<Boolean> per_player_locale = newProperty("root.per-player-locale", false);

    @Comment({
            "The default locale file, to display to players if the above option is set to false.",
            "",
            "A list of available localization:",
            " ⤷ en_US (English America)",
            " ⤷ de_DE (German)",
            ""
    })
    public static final Property<String> default_locale_file = newProperty("root.default-locale-file", "en_US");

}