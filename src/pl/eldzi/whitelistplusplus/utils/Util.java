
package pl.eldzi.whitelistplusplus.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public final class Util {
    private static final SimpleDateFormat dateFormat;
    private static final SimpleDateFormat timeFormat;
    private static final SimpleDateFormat backupFormat;
    private static final Pattern DOUBLE_PATTERN;
    private static final LinkedHashMap<Integer, String> values;

    public static String fixColor(final String s) {
        if (s == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private static Pattern VALID_IPV4_PATTERN = null;
    private static Pattern VALID_IPV6_PATTERN = null;
    private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

    public static boolean isIpAddress(String ipAddress) {

        Matcher m1 = VALID_IPV4_PATTERN.matcher(ipAddress);
        if (m1.matches()) {
            return true;
        }
        Matcher m2 = VALID_IPV6_PATTERN.matcher(ipAddress);
        return m2.matches();
    }

    public static Collection<String> fixColor(final Collection<String> collection) {
        final Collection<String> local = new ArrayList<String>();
        for (final String s : collection) {
            local.add(fixColor(s));
        }
        return local;
    }

    public static String[] fixColor(final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = fixColor(array[i]);
        }
        return array;
    }

    public static Player searchPlayerByRealName(String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public static boolean sendMsg(final CommandSender sender, final String message, final String permission) {
        if (sender instanceof ConsoleCommandSender) {
            sendMsg(sender, message);
        }
        return permission != null && permission != "" && sender.hasPermission(permission) && sendMsg(sender, message);
    }

    public static boolean sendMsg(final CommandSender sender, final String message) {
        if (sender instanceof Player) {
            if (message != null || message != "") {
                sender.sendMessage(fixColor(message));
            }
        } else {
            sender.sendMessage(ChatColor.stripColor(fixColor(message)));
        }
        return true;
    }

    public static boolean reloadPlugin(Plugin p) {
        if (p != null) {
            p.onDisable();
            p.onEnable();
            return true;
        }
        return false;
    }

    public static boolean sendMsg(final Player[] collection, final String message) {
        for (final CommandSender cs : collection) {
            sendMsg(cs, message);
        }
        return true;
    }

    public static boolean sendMsg(final Player[] collection, final String message, final String permission) {
        for (final CommandSender cs : collection) {
            sendMsg(cs, message, permission);
        }
        return true;
    }

    public static boolean containsIgnoreCase(final String[] array, final String element) {
        for (final String s : array) {
            if (s.equalsIgnoreCase(element)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static Material getMaterial(final String idOrName) {
        if (isInteger(idOrName)) {
            return Material.getMaterial(Integer.parseInt(idOrName));
        }
        for (final Material m : Material.values()) {
            if (m.name().replace("_", "").equalsIgnoreCase(idOrName)) {
                return m;
            }
        }
        return null;
    }

    public static ItemStack getItemStack(final Material m, final short data, final int amount,
            final HashMap<Enchantment, Integer> enchants) {
        ItemStack item = null;
        int a = 64;
        if (amount >= 1) {
            a = amount;
        }
        if (data > 0) {
            item = new ItemStack(m, a);
        } else {
            item = new ItemStack(m, a, data);
        }
        if (enchants != null) {
            item.addUnsafeEnchantments(enchants);
        }
        return item;
    }

    public static void giveItems(final Player p, final ItemStack... items) {
        final Inventory i = (Inventory) p.getInventory();
        final HashMap<Integer, ItemStack> notStored = (HashMap<Integer, ItemStack>) i.addItem(items);
        for (final Map.Entry<Integer, ItemStack> e : notStored.entrySet()) {
            p.getWorld().dropItemNaturally(p.getLocation(), (ItemStack) e.getValue());
        }
    }

    public static Player getDamager(final EntityDamageByEntityEvent e) {
        final Entity damager = e.getDamager();
        if (damager instanceof Player) {
            return (Player) damager;
        }
        if (damager instanceof Projectile) {
            final Projectile p = (Projectile) damager;
            if (p.getShooter() instanceof Player) {
                return (Player) p.getShooter();
            }
        }
        return null;
    }

    public static String secondsToString(int seconds) {
        final StringBuilder sb = new StringBuilder();
        for (final Map.Entry<Integer, String> e : Util.values.entrySet()) {
            final int iDiv = seconds / e.getKey();
            if (iDiv >= 1) {
                final int x = (int) Math.floor(iDiv);
                sb.append(x + e.getValue()).append(" ");
                seconds -= x * e.getKey();
            }
        }
        return sb.toString();
    }

    public static boolean isAlphaNumeric(final String s) {
        return s.matches("^[a-zA-Z0-9_]*$");
    }

    public static boolean isFloat(final String string) {
        return Pattern.matches("([0-9]*)\\.([0-9]*)", string);
    }

    public static boolean isInteger(final String string) {
        return Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
    }

    public static String getDate(final long time) {
        return Util.dateFormat.format(new Date(time));
    }

    public static String getDate(Date date) {
        return Util.backupFormat.format(date);
    }

    public static String getTime(final long time) {
        return Util.timeFormat.format(new Date(time));
    }

    public static ItemStack getItemStackFromString(final String itemstack) {
        final String[] splits = itemstack.split("@");
        final String type = splits[0];
        final String data = (splits.length == 2) ? splits[1] : null;
        if (data == null) {
            return new ItemStack(Material.getMaterial(type), 1);
        }
        return new ItemStack(Material.getMaterial(type), 1, (short) Integer.parseInt(data));
    }

    public static String toString(Location l, boolean other) {
        StringBuilder sb = new StringBuilder();
        String separator = "/";
        sb.append(l.getBlockX() + separator).append(l.getBlockY() + separator).append(l.getBlockZ() + separator)
                .append(l.getWorld().getName());
        if (other) {
            sb.append("/" + l.getYaw() + "/" + l.getPitch());
        }
        return sb.toString();
    }

    public static boolean isDouble(String s) {
        return DOUBLE_PATTERN.matcher(s).matches();
    }

    public static Double getDouble(String s) {
        if (isDouble(s)) {
            return Double.parseDouble(s);
        }
        return Double.MIN_VALUE;
    }

    public static Location toLocation(String s) {
        if (s == "") {
            return null;
        }
        String[] t = s.split("/");
        if (t.length == 4) {
            return new Location(Bukkit.getWorld(t[3]), getDouble(t[0]), getDouble(t[1]), getDouble(t[2]));
        } else if (t.length == 6) {
            return new Location(Bukkit.getWorld(t[3]), getDouble(t[0]), getDouble(t[1]), getDouble(t[2]),
                    Float.parseFloat(t[4]), Float.parseFloat(t[5]));
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static String getStringFromItemstack(final ItemStack itemstack) {
        if (itemstack.getData().getData() > 0) {
            return itemstack.getType().toString() + "@" + itemstack.getData().getData();
        }
        return itemstack.getType().toString();
    }

    public static long parseDateDiff(final String time) {
        try {
            final Pattern timePattern = Pattern.compile(
                    "(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?",
                    2);
            final Matcher m = timePattern.matcher(time);
            int years = 0;
            int months = 0;
            int weeks = 0;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            boolean found = false;
            while (m.find()) {
                if (m.group() != null && !m.group().isEmpty()) {
                    for (int i = 0; i < m.groupCount(); ++i) {
                        if (m.group(i) != null && !m.group(i).isEmpty()) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        continue;
                    }
                    if (m.group(1) != null && !m.group(1).isEmpty()) {
                        years = Integer.parseInt(m.group(1));
                    }
                    if (m.group(2) != null && !m.group(2).isEmpty()) {
                        months = Integer.parseInt(m.group(2));
                    }
                    if (m.group(3) != null && !m.group(3).isEmpty()) {
                        weeks = Integer.parseInt(m.group(3));
                    }
                    if (m.group(4) != null && !m.group(4).isEmpty()) {
                        days = Integer.parseInt(m.group(4));
                    }
                    if (m.group(5) != null && !m.group(5).isEmpty()) {
                        hours = Integer.parseInt(m.group(5));
                    }
                    if (m.group(6) != null && !m.group(6).isEmpty()) {
                        minutes = Integer.parseInt(m.group(6));
                    }
                    if (m.group(7) == null) {
                        break;
                    }
                    if (m.group(7).isEmpty()) {
                        break;
                    }
                    seconds = Integer.parseInt(m.group(7));
                    break;
                }
            }
            if (!found) {
                return -1L;
            }
            final Calendar c = new GregorianCalendar();
            if (years > 0) {
                c.add(1, years);
            }
            if (months > 0) {
                c.add(2, months);
            }
            if (weeks > 0) {
                c.add(3, weeks);
            }
            if (days > 0) {
                c.add(5, days);
            }
            if (hours > 0) {
                c.add(11, hours);
            }
            if (minutes > 0) {
                c.add(12, minutes);
            }
            if (seconds > 0) {
                c.add(13, seconds);
            }
            final Calendar max = new GregorianCalendar();
            max.add(1, 10);
            if (c.after(max)) {
                return max.getTimeInMillis();
            }
            return c.getTimeInMillis();
        } catch (Exception e) {
            return -1L;
        }
    }

    static {
        VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
        VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern, Pattern.CASE_INSENSITIVE);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
        backupFormat = new SimpleDateFormat("dd-mm-yyyy-HH-mm-ss");
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        DOUBLE_PATTERN = Pattern.compile("[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)"
                + "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|"
                + "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))"
                + "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");
        (values = new LinkedHashMap<Integer, String>(6)).put(31104000, "y");
        Util.values.put(2592000, "msc");
        Util.values.put(86400, "d");
        Util.values.put(3600, "h");
        Util.values.put(60, "min");
        Util.values.put(1, "s");
    }
}
