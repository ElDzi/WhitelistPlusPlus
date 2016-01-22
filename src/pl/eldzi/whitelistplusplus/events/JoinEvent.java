package pl.eldzi.whitelistplusplus.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import pl.eldzi.whitelistplusplus.Config;
import pl.eldzi.whitelistplusplus.utils.Util;

public class JoinEvent implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("whitelist.bypass")) {
            return;
        }

        if (!Config.WHITELIST_ENABLE) {
            return;
        }

        if (Config.WHITELIST_PLAYERS.contains(p.getName())) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : Config.MESSAGES_KICK$WHITELIST) {
            sb.append(s + "\n");
        }

        e.disallow(Result.KICK_OTHER, Util.fixColor(sb.toString()));
    }
}
