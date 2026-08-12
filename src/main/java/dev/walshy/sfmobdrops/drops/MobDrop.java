package dev.walshy.sfmobdrops.drops;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

public final class MobDrop {

    @Nullable
    private final EntityType dropsFrom;
    private final boolean allMobs;
    @Nullable
    private final String entityName;
    @Nullable
    private final NamespacedKey entityNbtTag;
    @Nonnull
    private final Set<Drop> drops;

    public MobDrop(
        @Nullable EntityType dropsFrom,
        boolean allMobs,
        @Nullable String entityName,
        @Nullable NamespacedKey entityNbtTag,
        @Nonnull Set<Drop> drops
    ) {
        this.dropsFrom = dropsFrom;
        this.allMobs = allMobs;
        this.entityName = entityName;
        this.entityNbtTag = entityNbtTag;
        this.drops = Collections.unmodifiableSet(new LinkedHashSet<>(Objects.requireNonNull(drops, "drops")));
    }

    @Nullable
    public EntityType getDropsFrom() {
        return dropsFrom;
    }

    public boolean isAllMobs() {
        return allMobs;
    }

    @Nullable
    public String getEntityName() {
        return entityName;
    }

    @Nullable
    public NamespacedKey getEntityNbtTag() {
        return entityNbtTag;
    }

    @Nonnull
    public Set<Drop> getDrops() {
        return drops;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MobDrop other)) {
            return false;
        }
        return allMobs == other.allMobs
            && dropsFrom == other.dropsFrom
            && Objects.equals(entityName, other.entityName)
            && Objects.equals(entityNbtTag, other.entityNbtTag)
            && drops.equals(other.drops);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dropsFrom, allMobs, entityName, entityNbtTag, drops);
    }
}
