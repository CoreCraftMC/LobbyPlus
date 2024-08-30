package me.corecraft.lobbyplus.configs.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntryProperty {

    private Map<String, List<String>> entry = new HashMap<>();

    public final EntryProperty populate() {
        this.entry.put("1", List.of(
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ LobbyPlus Help ━━━━━━━━━━━━━━━━━━━</gold></bold>",
                " ⤷ <red>/lobbyplus bypass - <white>Allows you to build in spawn.",
                " ⤷ <red>/lobbyplus help - <white>Opens this help menu",
                " ⤷ <red>/lobbyplus reload - <white>Reloads the plugin.",
                "<bold><gold>━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━</gold></bold>"
        ));

        return this;
    }

    public void setEntry(Map<String, List<String>> help) {
        this.entry = help;
    }

    public Map<String, List<String>> getEntry() {
        return this.entry;
    }
}