package pl.eldzi.whitelistplusplus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static List<String> WHITELIST_PLAYERS;
    public static boolean WHITELIST_ENABLE;
    public static List<String> MESSAGES_KICK$WHITELIST;
    public static String MESSAGES_CMD$NO$PERMISSION, MESSAGES_CMD$CONTAIN$WHITELIST, MESSAGES_CMD$NOT$CONTAIN$WHITELIST,
            MESSAGES_CMD$ADDED$PLAYER, MESSAGES_CMD$REMOVED$PLAYER, MESSAGES_CMD$INCORRECT$USE, MESSAGES_CMD$HEADER,
            MESSAGES_CMD$LIST, MESSAGES_CMD$FOOTER, MESSAGES_CMD$DISABLED$WHITELIST, MESSAGES_CMD$ENABLED$WHITELIST,
            MESSAGES_CMD$EMPTY$LIST;

    static {
        WHITELIST_ENABLE = true;
        WHITELIST_PLAYERS = Arrays.asList("TOREMOVE");
        MESSAGES_KICK$WHITELIST = Arrays.asList("&cYou aren't be on whitelist!", "F**K U");
        MESSAGES_CMD$ADDED$PLAYER = "&cPlayer %nickname% was been added!";
        MESSAGES_CMD$CONTAIN$WHITELIST = "&cPlayer is containing whitelist!";
        MESSAGES_CMD$DISABLED$WHITELIST = "&cWhitelist is disabled";
        MESSAGES_CMD$ENABLED$WHITELIST = "&cWhitelist is enabled!";
        MESSAGES_CMD$FOOTER = "&c====== END OF LIST ======";
        MESSAGES_CMD$HEADER = "&c===== LIST =====";
        MESSAGES_CMD$INCORRECT$USE = "&cIncorrect use: %correct%";
        MESSAGES_CMD$LIST = " %number%. %nickname%";
        MESSAGES_CMD$NO$PERMISSION = "&dYou dont have permission!";
        MESSAGES_CMD$NOT$CONTAIN$WHITELIST = "&cPlayer isn't on whitelist!";
        MESSAGES_CMD$REMOVED$PLAYER = "&cPlayer %nickname% was been removed from whitelist!";
        MESSAGES_CMD$EMPTY$LIST = "&cOops.. Your list is empty !";
    }

    public static void loadConfig() {
        try {
            Main.getPlugin().saveDefaultConfig();
            final FileConfiguration c = Main.getPlugin().getConfig();
            for (final Field f : Config.class.getFields()) {
                if (c.isSet("config." + f.getName().toLowerCase().replace("_", ".").replace("$", "_"))) {
                    f.set(null, c.get("config." + f.getName().toLowerCase().replace("_", ".").replace("$", "_")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            final FileConfiguration c = Main.getPlugin().getConfig();
            for (final Field f : Config.class.getFields()) {
                c.set("config." + f.getName().toLowerCase().replace("_", ".").replace("$", "_"), f.get(null));
            }
            Main.getPlugin().saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig() {
        Main.getPlugin().reloadConfig();
        loadConfig();
        saveConfig();
    }
}
