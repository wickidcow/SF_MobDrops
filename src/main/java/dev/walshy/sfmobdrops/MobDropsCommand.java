package dev.walshy.sfmobdrops;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class MobDropsCommand implements TabExecutor {

    private static final List<String> ARG_0 = List.of("reload", "list", "new", "delete");

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("sfmobdrops.admin")) {
            sender.sendMessage(Component.text("hmm, you can't do this.", NamedTextColor.RED));
            return true;
        }

        if (args.length == 0) {
            sendUsage(sender);
        } else if (args[0].equalsIgnoreCase("reload")) {
            SfMobDrops.getInstance().reloadConfig();
            SfMobDrops.getInstance().loadDrops();
            sender.sendMessage(Component.text("Reloaded config!", NamedTextColor.DARK_GREEN));
        } else if (args[0].equalsIgnoreCase("list")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Component.text("You need to be a player, sorry :/", NamedTextColor.RED));
                return true;
            }
            Guis.openMobDropList(player);
        } else if (args[0].equalsIgnoreCase("new") || args[0].equalsIgnoreCase("delete")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Component.text("You need to be a player, sorry :/", NamedTextColor.RED));
                return true;
            }
            sender.sendMessage(Component.text("I'll make this at some point", NamedTextColor.RED));
        } else {
            sendUsage(sender);
        }

        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], ARG_0, new ArrayList<>());
        }
        return Collections.emptyList();
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(
            Component.text("----------", NamedTextColor.GRAY)
                .append(Component.text("SFMobDrops", NamedTextColor.GOLD))
                .append(Component.text("----------", NamedTextColor.GRAY))
                .append(Component.newline())
                .append(Component.text("/mobdrops reload", NamedTextColor.GOLD))
                .append(Component.text(" - Reload the configuration", NamedTextColor.GRAY))
                .append(Component.newline())
                .append(Component.text("/mobdrops list", NamedTextColor.GOLD))
                .append(Component.text(" - Get a list of the mob drops", NamedTextColor.GRAY))
                .append(Component.newline())
                .append(Component.text("/mobdrops new", NamedTextColor.GOLD))
                .append(Component.text(" - Create a new mob drop", NamedTextColor.GRAY))
                .append(Component.newline())
                .append(Component.text("/mobdrops delete", NamedTextColor.GOLD))
                .append(Component.text(" - Delete a mob drop", NamedTextColor.GRAY))
        );
    }
}
