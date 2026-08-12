package dev.walshy.sfmobdrops;

import dev.walshy.sfmobdrops.drops.Drop;
import dev.walshy.sfmobdrops.drops.MobDrop;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

public class Config {

    private final SfMobDrops instance;

    public Config(SfMobDrops instance) {
        this.instance = instance;
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    public Set<MobDrop> loadConfig() {
        final Set<MobDrop> mobDrops = new LinkedHashSet<>();

        final List<Map<String, Object>> list = (List<Map<String, Object>>) instance.getConfig().getList("drops");
        if (list == null || list.isEmpty()) {
            return mobDrops;
        }

        for (Map<String, Object> map : list) {
            final String entity = (String) map.get("entity");
            if (entity == null) {
                logSkipMsg("'entity' is not defined");
                continue;
            }

            EntityType dropsFrom = null;
            boolean allMobs = false;

            if (!Constants.CONSTANT.asMatchPredicate().test(entity)) {
                logSkipMsg("'entity' should be in SCREAMING_SNAKE_CASE");
                continue;
            } else if (entity.equals("ALL")) {
                allMobs = true;
            } else {
                try {
                    dropsFrom = EntityType.valueOf(entity);
                } catch (IllegalArgumentException ex) {
                    logSkipMsg("Invalid entity type: " + entity);
                    continue;
                }
            }

            final String configuredName = (String) map.get("name");
            final String entityName = configuredName == null
                ? null
                : ChatColor.translateAlternateColorCodes('&', configuredName);

            NamespacedKey entityNbtTag = null;
            final String nbtTag = (String) map.get("nbtTag");
            if (nbtTag != null) {
                if (!Constants.NAMESPACE.asMatchPredicate().test(nbtTag)) {
                    logSkipMsg("'nbtTag' should be a valid namespace - e.g. 'some_plugin:custom_mob'");
                    continue;
                }

                entityNbtTag = NamespacedKey.fromString(nbtTag);
                if (entityNbtTag == null) {
                    logSkipMsg("Invalid nbtTag: " + nbtTag);
                    continue;
                }
            }

            final List<Map<String, Object>> dropsMap = (List<Map<String, Object>>) map.get("drops");
            final Set<Drop> drops = dropsMap != null ? loadDrop(dropsMap) : loadLegacyDrop(map);
            if (drops == null || drops.isEmpty()) {
                continue;
            }

            mobDrops.add(new MobDrop(dropsFrom, allMobs, entityName, entityNbtTag, drops));
        }

        return mobDrops;
    }

    @Nullable
    private Set<Drop> loadDrop(@Nonnull List<Map<String, Object>> map) {
        final Set<Drop> drops = new LinkedHashSet<>();

        for (Map<String, Object> dropMap : map) {
            final Drop drop = loadSingleDrop(dropMap);
            if (drop == null) {
                return null;
            }
            drops.add(drop);
        }

        return drops;
    }

    @Nullable
    private Set<Drop> loadLegacyDrop(@Nonnull Map<String, Object> map) {
        instance.getLogger().warning(
            "Loading legacy drop for " + map.get("entity") + ". Please update to the current drops: format."
        );

        final Drop drop = loadSingleDrop(map);
        return drop == null ? null : Set.of(drop);
    }

    @Nullable
    private Drop loadSingleDrop(@Nonnull Map<String, Object> map) {
        final String slimefunId = (String) map.get("slimefunItem");
        if (slimefunId == null || slimefunId.isBlank()) {
            logSkipMsg("'slimefunItem' is not defined");
            return null;
        }

        final Number chanceNumber = asNumber(map.get("chance"));
        if (chanceNumber == null) {
            logSkipMsg("'chance' must be a number");
            return null;
        }

        final double chance = chanceNumber.doubleValue();
        if (chance < 0.0D || chance > 100.0D) {
            logSkipMsg("'chance' must be between 0 and 100");
            return null;
        }

        final Object amountObject = map.get("amount");
        final Number amountNumber = amountObject == null ? Integer.valueOf(1) : asNumber(amountObject);
        if (amountNumber == null) {
            logSkipMsg("'amount' must be a number");
            return null;
        }

        final int amount = amountNumber.intValue();
        if (amount < 1 || amount > 64) {
            logSkipMsg("'amount' must be between 1 and 64");
            return null;
        }

        return new Drop(slimefunId, chance, amount);
    }

    @Nullable
    private Number asNumber(@Nullable Object value) {
        return value instanceof Number ? (Number) value : null;
    }

    private void logSkipMsg(@Nonnull String reason) {
        instance.getLogger().warning(reason + ". Skipping invalid drop");
    }
}
