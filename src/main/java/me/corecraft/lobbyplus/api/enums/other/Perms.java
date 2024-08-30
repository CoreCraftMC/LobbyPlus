package me.corecraft.lobbyplus.api.enums.other;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;
import java.util.Map;

public enum Perms {

    reload_plugin("reload", "Access to /lobbyplus reload", PermissionDefault.OP),
    help("help", "Access to /lobbyplus help", PermissionDefault.TRUE),
    use("use", "Access to /lobbyplus", PermissionDefault.TRUE);

    private final String node;
    private final String description;
    private final PermissionDefault isDefault;
    private final Map<String, Boolean> children;

    Perms(String node, String description, PermissionDefault isDefault, Map<String, Boolean> children) {
        this.node = node;
        this.description = description;

        this.isDefault = isDefault;

        this.children = children;
    }

    Perms(String node, String description, PermissionDefault isDefault) {
        this.node = node;
        this.description = description;

        this.isDefault = isDefault;
        this.children = new HashMap<>();
    }

    public final String getNode() {
        return "lobbyplus." + this.node;
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
}