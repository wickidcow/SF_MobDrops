package dev.walshy.sfmobdrops;

import dev.walshy.sfmobdrops.drops.Drop;
import dev.walshy.sfmobdrops.drops.MobDrop;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class Guis implements Listener {

    private static final String TITLE = ChatColor.DARK_PURPLE + "Mob Drops";
    private static final int MAX_GUI_SIZE = 54;

    protected Guis() {}

    public static void openMobDropList(Player player) {
        final Set<MobDrop> drops = SfMobDrops.getInstance().getMobDrops();
        final int requestedSize = Math.max(9, ((drops.size() + 8) / 9) * 9);
        final int size = Math.min(MAX_GUI_SIZE, requestedSize);

        final Inventory inv = Bukkit.createInventory(null, size, TITLE);
        int added = 0;

        for (MobDrop mobDrop : drops) {
            if (added >= size) {
                break;
            }

            final ItemStack itemStack = new ItemStack(
                mobDrop.isAllMobs() ? Material.SPAWNER : getMaterialForMob(mobDrop.getDropsFrom())
            );
            final ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(
                mobDrop.getEntityName() != null
                    ? mobDrop.getEntityName()
                    : mobDrop.isAllMobs()
                        ? ChatColor.GOLD + "All Mobs"
                        : getEntity(mobDrop.getDropsFrom())
            );

            final List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Drops:");
            lore.add("");
            for (Drop drop : mobDrop.getDrops()) {
                lore.add(ChatColor.GRAY + "" + drop.getAmount() + "x " + ChatColor.GOLD + drop.getSlimefunItem());
                lore.add(ChatColor.LIGHT_PURPLE + "" + drop.getChance() + "% " + ChatColor.GRAY + "chance");
                lore.add("");
            }

            if (mobDrop.getEntityName() != null) {
                lore.add(ChatColor.GRAY + "Requires name: " + mobDrop.getEntityName());
            }
            if (mobDrop.getEntityNbtTag() != null) {
                lore.add(ChatColor.GRAY + "Requires tag: " + ChatColor.LIGHT_PURPLE + mobDrop.getEntityNbtTag());
            }

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inv.addItem(itemStack);
            added++;
        }

        if (drops.size() > size) {
            player.sendMessage(
                ChatColor.YELLOW + "Showing the first " + size + " mob drop definitions. GUI paging is not available yet."
            );
        }

        player.openInventory(inv);
    }

    private static Material getMaterialForMob(@Nonnull EntityType type) {
        if (type == EntityType.ENDER_DRAGON) {
            return Material.DRAGON_HEAD;
        } else if (type == EntityType.ZOMBIE) {
            return Material.ZOMBIE_HEAD;
        } else if (type == EntityType.CREEPER) {
            return Material.CREEPER_HEAD;
        }

        final Material material = Material.getMaterial(type + "_SPAWN_EGG");
        return material != null ? material : Material.SPAWNER;
    }

    private static String getEntity(@Nonnull EntityType type) {
        return ChatColor.LIGHT_PURPLE
            + capitalise((type.name().charAt(0) + type.name().substring(1)).replace('_', ' ').toLowerCase(Locale.ROOT));
    }

    private static String capitalise(String str) {
        if (str.isEmpty()) {
            return str;
        }

        final char[] chars = str.toCharArray();
        boolean capitalizeNext = true;
        for (int i = 0; i < chars.length; i++) {
            final char ch = chars[i];
            if (ch == ' ') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                chars[i] = Character.toTitleCase(ch);
                capitalizeNext = false;
            }
        }
        return new String(chars);
    }

    @EventHandler
    public void onInvClick(@Nonnull InventoryClickEvent event) {
        if (event.getView().getTitle().equals(TITLE)) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }
}
