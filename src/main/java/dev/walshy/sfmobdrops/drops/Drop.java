package dev.walshy.sfmobdrops.drops;

import java.util.Objects;
import javax.annotation.Nonnull;

public final class Drop {

    @Nonnull
    private final String slimefunItem;
    private final double chance;
    private final int amount;

    public Drop(@Nonnull String slimefunItem, double chance, int amount) {
        this.slimefunItem = Objects.requireNonNull(slimefunItem, "slimefunItem");
        this.chance = chance;
        this.amount = amount;
    }

    @Nonnull
    public String getSlimefunItem() {
        return slimefunItem;
    }

    public double getChance() {
        return chance;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Drop other)) {
            return false;
        }
        return Double.compare(chance, other.chance) == 0
            && amount == other.amount
            && slimefunItem.equals(other.slimefunItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slimefunItem, chance, amount);
    }
}
