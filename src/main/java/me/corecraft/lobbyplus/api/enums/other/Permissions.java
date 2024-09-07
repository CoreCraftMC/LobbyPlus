package me.corecraft.lobbyplus.api.enums.other;

import me.corecraft.lobbyplus.LobbyPlus;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import java.util.HashMap;
import java.util.Map;

public enum Permissions {

    reload_plugin("reload", "Access to /lobbyplus reload", PermissionDefault.OP, false),
    help("help", "Access to /lobbyplus help", PermissionDefault.TRUE, false),
    bypass("bypass", "Access to /lobbyplus bypass", PermissionDefault.OP, false),
    use("use", "Access to /lobbyplus", PermissionDefault.TRUE, false),

    event_block_interact("event.block.interact", "Ability to place/break blocks", PermissionDefault.OP, true),
    event_item_drop("event.item.drop", "Ability to drop items", PermissionDefault.OP, true),
    event_item_pickup("event.item.pickup", "Ability to pick up items", PermissionDefault.OP, true),

    event_player_pvp("event.player.pvp", "Allows the player to pvp", PermissionDefault.TRUE, false);

    private final String node;
    private final String description;
    private final PermissionDefault isDefault;
    private final Map<String, Boolean> children;

    private final boolean register;

    private final PluginManager manager = LobbyPlus.get().getServer().getPluginManager();

    Permissions(String node, String description, PermissionDefault isDefault, Map<String, Boolean> children, boolean register) {
        this.node = node;
        this.description = description;

        this.isDefault = isDefault;

        this.children = children;
        this.register = register;
    }

    Permissions(String node, String description, PermissionDefault isDefault, boolean register) {
        this.node = node;
        this.description = description;

        this.isDefault = isDefault;
        this.children = new HashMap<>();
        this.register = register;
    }

    public final String getNode() {
        return "lobbyplus." + this.node;
    }

    public final boolean shouldRegister() {
        return this.register;
    }

    public final String getDescription() {
        return this.description;
    }

    public final PermissionDefault isDefault() {
        return this.isDefault;
    }

    public final Map<String, Boolean> getChildren() {
        return this.children;
    }

    public final boolean hasPermission(final Player player) {
        return player.hasPermission(getNode());
    }

    public final boolean isValid() {
        return this.manager.getPermission(getNode()) != null;
    }

    public final Permission getPermission() {
        return new Permission(getNode(), getDescription(), isDefault());
    }

    public void registerPermission() {
        if (isValid()) return;

        this.manager.addPermission(getPermission());
    }

    public void unregisterPermission() {
        if (!isValid()) return;

        this.manager.removePermission(getNode());
    }
}