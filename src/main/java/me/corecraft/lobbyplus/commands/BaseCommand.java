package me.corecraft.lobbyplus.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.corecraft.lobbyplus.api.enums.other.Permissions;
import me.corecraft.lobbyplus.configs.impl.messages.MiscKeys;
import me.corecraft.lobbyplus.utils.MsgUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Map;

public class BaseCommand extends AbstractCommand {

    @Override
    public void execute(final PaperCommandInfo info) {
        final CommandSender sender = info.getCommandSender();

        final Map<String, List<String>> help = this.userManager.getUser(sender).getLocale().getProperty(MiscKeys.help).getEntry();

        help.get("1").forEach(line -> MsgUtils.sendMessage(sender, line, "{max}", String.valueOf(help.size())));
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.use.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        return Commands.literal("lobbyplus")
                .requires(source -> source.getSender().hasPermission(getPermission()))
                .executes(context -> {
                    execute(new PaperCommandInfo(context));

                    return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                }).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.use.registerPermission();

        return this;
    }
}