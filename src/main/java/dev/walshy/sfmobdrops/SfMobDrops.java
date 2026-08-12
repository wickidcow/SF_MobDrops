package dev.walshy.sfmobdrops;

import dev.walshy.sfmobdrops.drops.Drop;
import dev.walshy.sfmobdrops.drops.MobDrop;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.debug.Debug;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nonnull;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SfMobDrops extends JavaPlugin implements Listener {

    private static final String DEBUG = "sfmobdrops_debug";

    private static SfMobDrops instance;

    private final Set<MobDrop> mobDrops = new LinkedHashSet<>();
    private Config config;

    @Override
    public void onEnable() {
        setInstance(this);
        saveDefaultConfig();

        final Plugin slimefun = getServer().getPluginManager().getPlugin("Slimefun");
        if (slimefun != null) {
            getLogger().info("Connected to Slimefun runtime " + slimefun.getDescription().getVersion());
        }

        new Metrics(this, 11950);

        config = new Config(this);
        loadDrops();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Guis(), this);

        final PluginCommand command = getCommand("mobdrops");
        if (command == null) {
            getLogger().severe("The mobdrops command is missing from plugin.yml. Disabling SFMobDrops.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        command.setExecutor(new MobDropsCommand());

        getLogger().info("SFMobDrops Legacy is enabled for Paper 26.2 / Slimefun Legacy-compatible runtimes.");
    }

    @Override
    public void onDisable() {
        setInstance(null);
    }

    protected void loadDrops() {
        final Set<MobDrop> newSet = config.loadConfig();

        mobDrops.clear();
        mobDrops.addAll(newSet);
        getLogger().info("Loaded " + mobDrops.size() + " mob drop definitions.");
    }

    @EventHandler
    public void onMobDeath(@Nonnull EntityDeathEvent event) {
        final Set<Drop> drops = findDropsFromEntity(event.getEntity());
        if (drops.isEmpty()) {
            return;
        }

        Debug.log(DEBUG, "Found mob drop, has {} drops", drops.size());

        for (Drop drop : drops) {
            final double chance = ThreadLocalRandom.current().nextDouble(100.0D);

            Debug.log(DEBUG, "Evaluating {} - {} <= {}", drop.getSlimefunItem(), chance, drop.getChance());

            if (chance > drop.getChance()) {
                continue;
            }

            final SlimefunItem item = SlimefunItem.getById(drop.getSlimefunItem());
            if (item == null || item.isDisabledIn(event.getEntity().getWorld())) {
                continue;
            }

            final ItemStack dropping = item.getItem().clone();
            dropping.setAmount(drop.getAmount());

            Debug.log(DEBUG, "Dropping {}x {}", drop.getAmount(), drop.getSlimefunItem());
            event.getDrops().add(dropping);
        }
    }

    @Nonnull
    private Set<Drop> findDropsFromEntity(@Nonnull LivingEntity entity) {
        final Set<Drop> matchingDrops = new LinkedHashSet<>();

        for (MobDrop mobDrop : mobDrops) {
            if (!mobDrop.isAllMobs() && entity.getType() != mobDrop.getDropsFrom()) {
                continue;
            }

            if (mobDrop.getEntityName() != null) {
                final String customName = entity.getCustomName();
                if (customName == null || !mobDrop.getEntityName().equals(customName)) {
                    continue;
                }
            }

            if (mobDrop.getEntityNbtTag() != null
                && !entity.getPersistentDataContainer().getKeys().contains(mobDrop.getEntityNbtTag())) {
                continue;
            }

            matchingDrops.addAll(mobDrop.getDrops());
        }

        return matchingDrops;
    }

    @Nonnull
    public Set<MobDrop> getMobDrops() {
        return mobDrops;
    }

    @Nonnull
    public static SfMobDrops getInstance() {
        return instance;
    }

    private static void setInstance(SfMobDrops ins) {
        instance = ins;
    }
}
