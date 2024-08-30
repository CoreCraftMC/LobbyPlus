package me.corecraft.lobbyplus.api.enums.types;

import org.apache.commons.lang.WordUtils;

public enum BypassType {

    allow_block_interact("allow_block_interact"),
    allow_item_drop("allow_item_drop"),
    allow_item_pickup("allow_item_pickup"),
    no_bypass("no_bypass");

    private final String chat;

    BypassType(final String chat) {
        this.chat = chat;
    }

    public final String getPrettyName() {
        return WordUtils.capitalize(getName().replace("_", " "));
    }

    public final String getName() {
        return this.chat;
    }

    public static BypassType getBypassType(final String value) {
        BypassType type = BypassType.no_bypass;

        if (value.isEmpty()) {
            return type;
        }

        for (final BypassType key : BypassType.values()) {
            if (key.getName().equals(value)) {
                type = key;

                break;
            }
        }

        return type;
    }
}