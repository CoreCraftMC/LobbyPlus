package me.corecraft.lobbyplus.utils;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.vital.paper.api.enums.Support;
import com.ryderbelserion.vital.paper.util.AdvUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.api.cache.objects.User;
import me.corecraft.lobbyplus.configs.ConfigManager;
import me.corecraft.lobbyplus.configs.impl.types.ConfigKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MsgUtils {

    private static final LobbyPlus plugin = LobbyPlus.get();

    private static final SettingsManager config = ConfigManager.getConfig();

    public static String getLocale(final CommandSender sender) {
        final User user = plugin.getUserManager().getUser(sender);

        String locale = "en_US";

        if (user != null) {
            locale = user.locale;
        }

        return locale;
    }

    public static void sendMessage(final CommandSender sender, final String message, @Nullable final Map<String, String> placeholders) {
        if (message.isEmpty()) return;

        final String msg = getMessage(sender, message, placeholders);

        sender.sendRichMessage(msg);
    }

    public static void sendActionBar(final CommandSender sender, final String message, @Nullable final Map<String, String> placeholders) {
        if (message.isEmpty()) return;

        sender.sendActionBar(AdvUtil.parse(getMessage(sender, message, placeholders)));
    }

    public static void sendTitle(final Player player, final String message, @Nullable final Map<String, String> placeholders) {
        if (message.isEmpty()) return;

        final Title.Times times = Title.Times.times(Duration.ofMillis(400), Duration.ofMillis(200), Duration.ofMillis(400));
        final Title title = Title.title(AdvUtil.parse(getMessage(player, message, placeholders)), Component.empty(), times);

        player.showTitle(title);
    }

    public static void sendMessage(final CommandSender sender, final String message, final String placeholder, final String replacement) {
        sendMessage(sender, message, new HashMap<>() {{
            put(placeholder, replacement);
        }});
    }

    public static void sendMessage(final CommandSender sender, final String message) {
        sendMessage(sender, message, null);
    }

    public static String getMessage(final CommandSender sender, final String message, @Nullable final Map<String, String> placeholders) {
        String msg = message;

        if (sender instanceof Player player) {
            if (Support.placeholder_api.isEnabled()) {
                msg = PlaceholderAPI.setPlaceholders(player, msg);
            }
        }

        if (placeholders != null && !placeholders.isEmpty()) {
            for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
                if (placeholder != null) {
                    final String key = placeholder.getKey();
                    final String value = placeholder.getValue();

                    if (key != null && value != null) {
                        msg = msg.replace(key, value).replace(key.toLowerCase(), value);
                    }
                }
            }
        }

        return msg;
    }

    /**
     * @return the {@link String}
     */
    public static @NotNull String getPrefix() {
        return config.getProperty(ConfigKeys.command_prefix);
    }
}