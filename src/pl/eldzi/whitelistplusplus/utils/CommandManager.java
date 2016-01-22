
package pl.eldzi.whitelistplusplus.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import pl.eldzi.whitelistplusplus.utils.Reflection.FieldAccessor;

public class CommandManager {
    private static final HashMap<String, Command> commands;
    private static final FieldAccessor<SimpleCommandMap> f;
    private static CommandMap cmdMap;

    public static void register(final Command cmd) {
        if (cmdMap == null) {
            cmdMap = f.get(Bukkit.getServer().getPluginManager());
        }
        cmdMap.register(cmd.getName(), cmd);
        commands.put(cmd.getName(), cmd);
        Logger.info("Registed " + cmd.getName() + " command!");
    }

    static {
        commands = new HashMap<String, Command>();
        f = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class);
        cmdMap = f.get(Bukkit.getServer().getPluginManager());
    }
}
