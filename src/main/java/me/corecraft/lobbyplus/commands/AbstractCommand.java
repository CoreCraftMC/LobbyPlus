package me.corecraft.lobbyplus.commands;

import com.ryderbelserion.vital.paper.commands.PaperCommand;
import me.corecraft.lobbyplus.LobbyPlus;
import me.corecraft.lobbyplus.api.cache.UserManager;
import org.bukkit.Server;

public abstract class AbstractCommand extends PaperCommand {

    protected final LobbyPlus plugin = LobbyPlus.get();
    protected final UserManager userManager = this.plugin.getUserManager();
    protected final Server server = this.plugin.getServer();

}