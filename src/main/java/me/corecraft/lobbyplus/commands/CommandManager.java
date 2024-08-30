package me.corecraft.lobbyplus.commands;

import com.ryderbelserion.vital.paper.api.builders.PlayerBuilder;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.core.suggestion.SuggestionKey;
import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.api.cache.UserManager;
import me.corecraft.lobbyplus.api.enums.types.BypassType;
import me.corecraft.lobbyplus.commands.relations.ArgumentRelations;
import me.corecraft.lobbyplus.commands.subs.BaseCommand;
import me.corecraft.lobbyplus.commands.subs.player.CommandHelp;
import me.corecraft.lobbyplus.commands.subs.staff.CommandBypass;
import me.corecraft.lobbyplus.commands.subs.staff.CommandReload;
import me.corecraft.lobbyplus.configs.impl.messages.MiscKeys;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    private final static @NotNull LobbyPlus plugin = LobbyPlus.get();

    private final static @NotNull UserManager userManager = plugin.getUserManager();

    private final static @NotNull BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(plugin);

    /**
     * Loads commands.
     */
    public static void load() {
        new ArgumentRelations().build();

        commandManager.registerSuggestion(SuggestionKey.of("pages"), (sender, context) -> {
            final List<String> numbers = new ArrayList<>();

            userManager.getUser(sender).getLocale().getProperty(MiscKeys.help).getEntry().keySet().forEach(value -> numbers.add(String.valueOf(Integer.parseInt(value))));

            return numbers;
        });

        commandManager.registerSuggestion(SuggestionKey.of("bypass_type"), (sender, context) -> Arrays.stream(BypassType.values()).map(BypassType::getName).toList());

        // default
        commandManager.registerSuggestion(SuggestionKey.of("players"), (sender, context) -> plugin.getServer().getOnlinePlayers().stream().map(Player::getName).toList());

        commandManager.registerSuggestion(SuggestionKey.of("numbers"), (sender, context) -> {
            final List<String> numbers = new ArrayList<>();

            for (int i = 1; i <= 64; i++) numbers.add(String.valueOf(i));

            return numbers;
        });

        // default
        commandManager.registerArgument(PlayerBuilder.class, (sender, context) -> new PlayerBuilder(context));

        List.of(
                new BaseCommand(),

                new CommandReload(),
                new CommandBypass(),
                new CommandHelp()
        ).forEach(commandManager::registerCommand);
    }

    public static @NotNull BukkitCommandManager<CommandSender> getCommandManager() {
        return commandManager;
    }
}