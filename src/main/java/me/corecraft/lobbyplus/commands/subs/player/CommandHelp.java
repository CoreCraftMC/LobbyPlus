package me.corecraft.lobbyplus.commands.subs.player;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import me.corecraft.lobbyplus.commands.subs.BaseCommand;
import me.corecraft.lobbyplus.configs.impl.messages.MiscKeys;
import me.corecraft.lobbyplus.utils.MsgUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import java.util.List;
import java.util.Map;

public class CommandHelp extends BaseCommand {

    @Command("help")
    @Permission(value = "lobbyplus.help", def = PermissionDefault.TRUE, description = "Access to /lobbyplus help")
    public void help(final CommandSender sender, @Suggestion("pages") final int page) {
        final Map<String, List<String>> help = this.userManager.getUser(sender).getLocale().getProperty(MiscKeys.help).getEntry();

        help.get(String.valueOf(page)).forEach(line -> MsgUtils.sendMessage(sender, line, "{max}", String.valueOf(help.size())));
    }
}