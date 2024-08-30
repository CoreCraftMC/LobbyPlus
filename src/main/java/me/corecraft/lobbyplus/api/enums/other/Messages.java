package me.corecraft.lobbyplus.api.enums.other;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.vital.common.utils.StringUtil;
import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.api.cache.UserManager;
import me.corecraft.lobbyplus.api.cache.objects.User;
import me.corecraft.lobbyplus.configs.impl.messages.ErrorKeys;
import me.corecraft.lobbyplus.configs.impl.messages.MiscKeys;
import me.corecraft.lobbyplus.configs.impl.messages.PlayerKeys;
import me.corecraft.lobbyplus.configs.ConfigManager;
import me.corecraft.lobbyplus.configs.impl.types.ConfigKeys;
import me.corecraft.lobbyplus.utils.MsgUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Messages {

    feature_disabled(MiscKeys.feature_disabled),
    unknown_command(MiscKeys.unknown_command),
    correct_usage(MiscKeys.correct_usage),
    plugin_reload(MiscKeys.plugin_reload),

    internal_error(ErrorKeys.internal_error),
    message_empty(ErrorKeys.message_empty),

    player_not_found(PlayerKeys.not_online),
    no_permission(PlayerKeys.no_permission),
    same_player(PlayerKeys.same_player),

    must_be_a_player(PlayerKeys.must_be_a_player),
    must_be_console_sender(PlayerKeys.must_be_console_sender),
    inventory_not_empty(PlayerKeys.inventory_not_empty);

    private Property<String> property;

    private Property<List<String>> properties;
    private boolean isList = false;

    Messages(@NotNull final Property<String> property) {
        this.property = property;
    }

    Messages(@NotNull final Property<List<String>> properties, final boolean isList) {
        this.properties = properties;
        this.isList = isList;
    }

    private final LobbyPlus plugin = LobbyPlus.get();

    private final SettingsManager config = ConfigManager.getConfig();

    private final UserManager userManager = this.plugin.getUserManager();

    private boolean isList() {
        return this.isList;
    }

    public String getString(final CommandSender sender) {
        final User user = this.userManager.getUser(sender);

        return ConfigManager.getLocale(user.locale).getProperty(this.property);
    }

    public List<String> getList(final CommandSender sender) {
        final User user = this.userManager.getUser(sender);

        return ConfigManager.getLocale(user.locale).getProperty(this.properties);
    }

    public String getMessage(@NotNull final CommandSender sender) {
        return getMessage(sender, new HashMap<>());
    }

    public String getMessage(@NotNull final CommandSender sender, @NotNull final String placeholder, @NotNull final String replacement) {
        Map<String, String> placeholders = new HashMap<>() {{
            put(placeholder, replacement);
        }};

        return getMessage(sender, placeholders);
    }

    public String getMessage(@NotNull final CommandSender sender, @NotNull final Map<String, String> placeholders) {
        return parse(sender, placeholders).replaceAll("\\{prefix}", this.config.getProperty(ConfigKeys.command_prefix));
    }

    public void sendMessage(final CommandSender sender, final String placeholder, final String replacement) {
        sender.sendRichMessage(getMessage(sender, placeholder, replacement));
    }

    public void sendMessage(final CommandSender sender, final Map<String, String> placeholders) {
        sender.sendRichMessage(getMessage(sender, placeholders));
    }

    public void sendMessage(final CommandSender sender) {
        sender.sendRichMessage(getMessage(sender));
    }

    private @NotNull String parse(@NotNull final CommandSender sender, @Nullable final Map<String, String> placeholders) {
        String message;

        if (isList()) {
            message = StringUtil.chomp(StringUtil.convertList(getList(sender)));
        } else {
            message = getString(sender);
        }

        return MsgUtils.getMessage(sender, message, placeholders);
    }

    public void broadcast() {
        broadcast(null);
    }

    public void broadcast(@Nullable final Map<String, String> placeholders) {
        sendMessage(this.plugin.getServer().getConsoleSender(), placeholders);

        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            sendMessage(player, placeholders);
        }
    }
}