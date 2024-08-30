package me.corecraft.lobbyplus.commands.subs.staff;

import ch.jalu.configme.SettingsManager;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import me.corecraft.lobbyplus.api.cache.objects.User;
import me.corecraft.lobbyplus.api.enums.other.Messages;
import me.corecraft.lobbyplus.api.enums.types.BypassType;
import me.corecraft.lobbyplus.commands.subs.BaseCommand;
import me.corecraft.lobbyplus.configs.impl.messages.commands.ToggleKeys;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;

public class CommandBypass extends BaseCommand {

    @Command("bypass")
    @Permission(value = "lobbyplus.bypass", def = PermissionDefault.OP, description = "Access to /lobbyplus bypass")
    public void bypass(final Player player, @Suggestion("bypass_type") final String bypass_type) {
        final BypassType type = BypassType.getBypassType(bypass_type);

        final User user = this.userManager.getUser(player);

        final SettingsManager locale = user.getLocale();

        if (user.activeBypassTypes.contains(type.getName())) {
            user.activeBypassTypes.remove(type.getName());

            Messages.bypass_toggle.sendMessage(player, new HashMap<>() {{
                put("{state}", type.getPrettyName());
                put("{status}", locale.getProperty(ToggleKeys.toggle_disabled));
            }});

            return;
        }

        user.activeBypassTypes.add(type.getName());

        Messages.bypass_toggle.sendMessage(player, new HashMap<>() {{
            put("{state}", type.getPrettyName());
            put("{status}", locale.getProperty(ToggleKeys.toggle_enabled));
        }});
    }
}