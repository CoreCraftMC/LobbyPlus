package me.corecraft.lobbyplus.commands.subs;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.api.cache.UserManager;
import me.corecraft.lobbyplus.configs.ConfigManager;
import me.corecraft.lobbyplus.configs.impl.messages.MiscKeys;
import me.corecraft.lobbyplus.utils.MsgUtils;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import java.util.List;
import java.util.Map;

@Command(value = "lobbyplus")
public class BaseCommand {

    protected final LobbyPlus plugin = LobbyPlus.get();
    protected final Server server = this.plugin.getServer();
    protected final UserManager userManager = this.plugin.getUserManager();

    @Command
    @Permission(value = "lobbyplus.use", def = PermissionDefault.TRUE, description = "Access to /lobbyplus")
    public void root(final CommandSender sender) {
        final Map<String, List<String>> help = ConfigManager.getLocale(MsgUtils.getLocale(sender)).getProperty(MiscKeys.help).getEntry();

        help.get("1").forEach(line -> MsgUtils.sendMessage(sender, line, "{max}", String.valueOf(help.size())));
    }
}