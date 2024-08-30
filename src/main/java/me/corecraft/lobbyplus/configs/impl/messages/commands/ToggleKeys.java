package me.corecraft.lobbyplus.configs.impl.messages.commands;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ToggleKeys implements SettingsHolder {

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "All messages related to lobbyplus bypass.",
        };

        conf.setComment("commands.toggle", header);
    }

    @Comment({
            "A list of available placeholders:",
            "",
            "{state} which returns whatever argument was supplied in /lobbyplus bypass <state>",
            "",
            "{status} returns enabled or disabled, which can be configured below."
    })
    public static final Property<String> bypass_toggle = newProperty("commands.bypass.message", "{prefix}<green>{state} has been {status}");

    public static final Property<String> toggle_enabled = newProperty("commands.bypass.enabled", "<green>enabled");

    public static final Property<String> toggle_disabled = newProperty("commands.bypass.disabled", "<red>disabled");
}