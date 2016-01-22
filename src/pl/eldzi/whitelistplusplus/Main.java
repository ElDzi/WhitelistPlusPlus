package pl.eldzi.whitelistplusplus;

import org.bukkit.plugin.java.JavaPlugin;

import pl.eldzi.whitelistplusplus.cmds.WhitelistCommand;
import pl.eldzi.whitelistplusplus.events.JoinEvent;
import pl.eldzi.whitelistplusplus.utils.CommandManager;

public class Main extends JavaPlugin {
    private static Main pl;

    public static Main getPlugin() {
        return pl;
    }

    public void onEnable() {
        pl = this;
        Config.loadConfig();
        CommandManager.register(new WhitelistCommand());
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);

    }

    @Override
    public void onDisable() {
        Config.saveConfig();
    }

}
