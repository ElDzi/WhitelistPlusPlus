package pl.eldzi.whitelistplusplus.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends Command
{
    public PlayerCommand(final String name, final String desc, final String usage, final String permission, final String... aliases) {
        super(name, desc, usage, permission, aliases);
    }
    
    @Override
    public boolean runCommand(final CommandSender sender, final String[] args) {
        if (!(sender instanceof Player)) {
            return Util.sendMsg(sender, "&cYou must be a player to run that command!");
        }
        return this.playerCommand((Player)sender, args);
    }
    
    public abstract boolean playerCommand(final Player p, final String[] args);
}
