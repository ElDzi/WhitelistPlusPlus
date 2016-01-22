
package pl.eldzi.whitelistplusplus.utils;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import pl.eldzi.whitelistplusplus.Config;

public abstract class Command extends org.bukkit.command.Command {
    private final String name;
    private final String usage;
    private final String desc;
    private final String permission;

    public Command(final String name, final String desc, final String usage, final String permission,
            final String... aliases) {
        super(name, desc, usage, Arrays.asList(aliases));
        this.name = name;
        this.usage = usage;
        this.desc = desc;
        this.permission = permission;

    }

    public boolean execute(final CommandSender sender, final String label, final String[] args) {
        if ((this.permission != null || this.permission != "") && !sender.hasPermission(this.permission)) {
            return Util.sendMsg(sender, Config.MESSAGES_CMD$NO$PERMISSION);
        }
        return this.runCommand(sender, args);
    }

    public abstract boolean runCommand(final CommandSender sender, final String[] args);

    public String getName() {
        return this.name;
    }

    public String getUsage() {
        return this.usage;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getPermission() {
        return this.permission;
    }
}
