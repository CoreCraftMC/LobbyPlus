package me.corecraft.lobbyplus.commands.subs.staff;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import me.corecraft.lobbyplus.api.cache.objects.User;
import me.corecraft.lobbyplus.api.enums.other.Messages;
import me.corecraft.lobbyplus.commands.subs.BaseCommand;
import me.corecraft.lobbyplus.configs.ConfigManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

public class CommandReload extends BaseCommand {

    @Command("reload")
    @Permission(value = "lobbyplus.reload", def = PermissionDefault.OP, description = "Access to /lobbyplus reload")
    public void reload(final CommandSender sender) {
        this.plugin.getFileManager().reloadFiles();

        ConfigManager.refresh();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);

        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            final User user = this.userManager.getUser(player);

            user.hideBossBar();
        }

        Messages.plugin_reload.sendMessage(sender);
    }
}