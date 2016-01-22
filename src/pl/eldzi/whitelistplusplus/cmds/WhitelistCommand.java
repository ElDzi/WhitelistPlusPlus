package pl.eldzi.whitelistplusplus.cmds;

import org.bukkit.command.CommandSender;

import pl.eldzi.whitelistplusplus.Config;
import pl.eldzi.whitelistplusplus.utils.Command;
import pl.eldzi.whitelistplusplus.utils.Util;

public class WhitelistCommand extends Command {
    public WhitelistCommand() {
        super("whitelist", "whitelist manager", "/whitelist <add/remove/list/on/off>", "whitelist.use", "wl");
    }

    public boolean runCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            return Util.sendMsg(sender, Config.MESSAGES_CMD$INCORRECT$USE.replace("%correct%", getUsage()));
        }
        String a = args[0];
        switch (a) {
            case "add": {
                if (args.length == 2) {
                    String n = args[1];
                    if (Config.WHITELIST_PLAYERS.contains(n)) {
                        return Util.sendMsg(sender, Config.MESSAGES_CMD$CONTAIN$WHITELIST.replace("%nickname%", n));
                    }
                    Config.WHITELIST_PLAYERS.add(n);
                    Config.saveConfig();
                    return Util.sendMsg(sender, Config.MESSAGES_CMD$ADDED$PLAYER.replace("%nickname%", n));
                } else {
                    return Util.sendMsg(sender,
                            Config.MESSAGES_CMD$INCORRECT$USE.replace("%correct%", "/whitelist add <player>"));
                }
            }
            case "remove": {
                if (args.length == 2) {
                    String n = args[1];
                    if (!Config.WHITELIST_PLAYERS.contains(n)) {
                        return Util.sendMsg(sender, Config.MESSAGES_CMD$NOT$CONTAIN$WHITELIST.replace("%nickname%", n));
                    }
                    if (Config.WHITELIST_PLAYERS.size() == 1) {
                        Config.WHITELIST_PLAYERS.clear();
                    } else {
                        Config.WHITELIST_PLAYERS.remove(n);
                    }
                    Config.saveConfig();
                    return Util.sendMsg(sender, Config.MESSAGES_CMD$REMOVED$PLAYER.replace("%nickname%", n));
                } else {
                    return Util.sendMsg(sender,
                            Config.MESSAGES_CMD$INCORRECT$USE.replace("%correct%", "/whitelist remove <player>"));
                }
            }
            case "list": {
                if (args.length == 1) {
                    if (Config.WHITELIST_PLAYERS.isEmpty()) {
                        return Util.sendMsg(sender, Config.MESSAGES_CMD$EMPTY$LIST);
                    }
                    Util.sendMsg(sender, Config.MESSAGES_CMD$HEADER);
                    for (int k = 0; k < Config.WHITELIST_PLAYERS.size(); k++) {
                        Util.sendMsg(sender, Config.MESSAGES_CMD$LIST.replace("%number%", String.valueOf(k + 1))
                                .replace("%nickname%", Config.WHITELIST_PLAYERS.get(k)));
                    }
                    return Util.sendMsg(sender, Config.MESSAGES_CMD$FOOTER);
                } else {
                    return Util.sendMsg(sender,
                            Config.MESSAGES_CMD$INCORRECT$USE.replace("%correct%", "/whitelist list"));
                }
            }
            case "off": {
                if (args.length == 1) {
                    Config.WHITELIST_ENABLE = false;
                    Config.saveConfig();
                    return Util.sendMsg(sender, Config.MESSAGES_CMD$DISABLED$WHITELIST);
                } else {
                    return Util.sendMsg(sender,
                            Config.MESSAGES_CMD$INCORRECT$USE.replace("%correct%", "/whitelist off"));
                }
            }
            case "on": {
                if (args.length == 1) {
                    Config.WHITELIST_ENABLE = true;
                    Config.saveConfig();
                    return Util.sendMsg(sender, Config.MESSAGES_CMD$ENABLED$WHITELIST);
                } else {
                    return Util.sendMsg(sender,
                            Config.MESSAGES_CMD$INCORRECT$USE.replace("%correct%", "/whitelist on"));

                }
            }
        }
        return Util.sendMsg(sender, Config.MESSAGES_CMD$INCORRECT$USE.replace("%correct%", getUsage()));
    }
}
