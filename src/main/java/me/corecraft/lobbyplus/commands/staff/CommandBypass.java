package me.corecraft.lobbyplus.commands.staff;

import ch.jalu.configme.SettingsManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.corecraft.lobbyplus.api.cache.objects.User;
import me.corecraft.lobbyplus.api.enums.other.Messages;
import me.corecraft.lobbyplus.api.enums.other.Permissions;
import me.corecraft.lobbyplus.api.enums.types.BypassType;
import me.corecraft.lobbyplus.commands.AbstractCommand;
import me.corecraft.lobbyplus.configs.impl.messages.commands.ToggleKeys;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandBypass extends AbstractCommand {

    @Override
    public void execute(final PaperCommandInfo info) {
        if (!info.isPlayer()) {
            Messages.must_be_a_player.sendMessage(info.getCommandSender());

            return;
        }

        final Player player = info.getPlayer();

        final BypassType type = BypassType.getBypassType(info.getStringArgument("bypass_type"));

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

    @Override
    public @NotNull final String getPermission() {
        return Permissions.bypass.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("bypass").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, String> arg1 = argument("bypass_type", StringArgumentType.string()).suggests((ctx, builder) -> {
            for (BypassType value : BypassType.values()) {
                builder.suggest(value.getName());
            }

            return builder.buildFuture();
        }).executes(context -> {
            execute(new PaperCommandInfo(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        });

        return root.then(arg1).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.bypass.registerPermission();

        return this;
    }
}