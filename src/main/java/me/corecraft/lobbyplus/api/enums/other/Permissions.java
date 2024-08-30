package me.corecraft.lobbyplus.api.enums.other;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;

public enum Permissions {

    RELOAD("reload", "Reloads the plugin.", PermissionDefault.OP);

    private final String node;
    private final String description;
    private final PermissionDefault isDefault;
    private final HashMap<String, Boolean> children;

    Permissions(String node, String description, PermissionDefault isDefault, HashMap<String, Boolean> children) {
        this.node = node;
        this.description = description;

        this.isDefault = isDefault;

        this.children = children;
    }

    Permissions(String node, String description, PermissionDefault isDefault) {
        this.node = node;
        this.description = description;

        this.isDefault = isDefault;
        this.children = new HashMap<>();
    }

    public String getNode() {
        return "lobbyplus." + this.node;
    }

    public String getDescription() {
        return this.description;
    }

    public PermissionDefault isDefault() {
        return this.isDefault;
    }

    public HashMap<String, Boolean> getChildren() {
        return this.children;
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(getNode());
    }
}