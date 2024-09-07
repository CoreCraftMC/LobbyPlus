package me.corecraft.lobbyplus.commands.staff;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.corecraft.lobbyplus.api.cache.objects.User;
import me.corecraft.lobbyplus.api.enums.other.Messages;
import me.corecraft.lobbyplus.api.enums.other.Permissions;
import me.corecraft.lobbyplus.commands.AbstractCommand;
import me.corecraft.lobbyplus.configs.ConfigManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandReload extends AbstractCommand {

    @Override
    public void execute(final PaperCommandInfo info) {
        this.plugin.getFileManager().reloadFiles();

        ConfigManager.refresh();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);

        for (final Player player : this.server.getOnlinePlayers()) {
            final User user = this.userManager.getUser(player);

            user.hideBossBar();
        }

        Messages.plugin_reload.sendMessage(info.getCommandSender());
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.reload_plugin.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        return Commands.literal("reload")
                .requires(source -> source.getSender().hasPermission(getPermission()))
                .executes(context -> {
                    execute(new PaperCommandInfo(context));

                    return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                }).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.reload_plugin.registerPermission();

        return this;
    }
}