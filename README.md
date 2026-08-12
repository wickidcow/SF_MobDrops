<div align="center">

# 🧟 SFMobDrops Legacy 💀

### Turn Minecraft mobs into configurable sources of Slimefun loot.

**🧟 Mob** → **⚔️ Defeated** → **💀 RIP** → **⚙️✨ Slimefun Drops!**

Maintained for **Slimefun Legacy**, modern **Paper 26.2**, and the AlbionMC ecosystem.

</div>

---

## 💚 A Tribute to WalshyDev

**SFMobDrops was originally created by WalshyDev.**

This Legacy fork exists because the original addon had a great, simple idea: let server owners decide which Slimefun items mobs can drop without turning the feature into an unnecessarily complicated system.

The goal of this fork is to **preserve WalshyDev's original work and design**, keep the addon recognizable, and make it usable again on current Minecraft and Slimefun Legacy servers.

Original authorship and credit remain with **WalshyDev**. This repository is a compatibility and maintenance fork, not a replacement for the original project.

---

## 🧪 What does SFMobDrops do?

SFMobDrops lets a server owner add **Slimefun items to normal mob death drops** through configuration.

You can use it to make mobs drop things such as:

- ⚙️ Slimefun machine components
- ✨ Magical items and materials
- 🧪 Dusts, resources, and crafting ingredients
- 💎 Rare Slimefun loot
- 🧟 Special drops from specific mob types
- 👑 Special drops from custom-named mobs
- 🏷️ Drops restricted by persistent-data tags
- 🌍 Global drops that can come from **all mobs**

Each configured drop can have its own **chance** and **amount**, and a mob can have **multiple possible Slimefun drops**.

---

## 🎯 Example

A zombie can have a 5% chance to drop Magical Zombie Pills and a separate 1% chance to drop Iron Dust:

```yaml
drops:
  - entity: ZOMBIE
    drops:
      - slimefunItem: MAGICAL_ZOMBIE_PILLS
        chance: 5
        amount: 1
      - slimefunItem: IRON_DUST
        chance: 1
        amount: 1
```

You can also create a drop that applies to every mob:

```yaml
drops:
  - entity: ALL
    drops:
      - slimefunItem: COPPER_DUST
        chance: 1
        amount: 1
```

Optional `name` and `nbtTag` filters allow much more specific drop rules.

---

## 🛠️ Slimefun Legacy Edition

This fork modernizes the original addon for the current Slimefun Legacy stack while trying to keep its behavior familiar.

### Current target

- **Minecraft / Paper 26.2**
- **Java 25** build environment
- **Java 21** plugin bytecode
- **Slimefun Legacy-compatible Slimefun4 API**
- English configuration and command output

### Legacy improvements

- Updated from the old **Spigot 1.17 / Slimefun RC-30** build environment.
- Removed the obsolete Blob Builds self-updater so a Legacy build cannot silently replace itself with an incompatible upstream build.
- Updated the bundled bStats dependency.
- Removed Lombok and annotation-processor reliance from the plugin's small data models for a more dependable modern Java build.
- Preserved the existing configuration format, including the older single-drop format.
- Multiple matching drop definitions can now contribute drops to the same mob instead of stopping at the first matching rule.
- Fixed custom-name matching so a rule requiring a mob name does not accidentally match an unnamed mob.
- Hardened drop chance and amount parsing.
- Protected the mob-drop GUI from invalid inventory sizes on modern Paper.
- Keeps Slimefun itself as a provided dependency rather than bundling the Slimefun core inside this addon.

---

## 📦 Builds

GitHub Actions builds a directly usable file named:

```text
SF_MobDrops_Legacy_v1.0.0.jar
```

The Actions workflow uses **direct artifact mode**, so the downloadable build is the actual `.jar` file — **not** a `.jar.zip` wrapper that has to be extracted first.

Tagged releases also attach the same raw JAR directly to the GitHub Release.

---

## 🎮 Commands

| Command | Purpose |
| --- | --- |
| `/mobdrops reload` | Reload the mob-drop configuration |
| `/mobdrops list` | Open the configured mob-drop list |
| `/sfmobdrops` | Alias for `/mobdrops` |

Administrative commands require `sfmobdrops.admin`.

---

## 💀 The basic idea

```text
        🧟 Creeper / Zombie / Skeleton / Custom Mob
                         │
                         │  player defeats mob
                         ▼
                        💀
                         │
             configured chance is rolled
                         │
                 ┌───────┴───────┐
                 ▼               ▼
              no drop       ⚙️ Slimefun loot!
```

Simple, configurable, and still very much **SFMobDrops**.

---

## ❤️ Credits

- **WalshyDev** — original creator of SFMobDrops and the original addon concept/codebase.
- **Slimefun contributors** — for the Slimefun platform and API this addon builds upon.
- **Slimefun Legacy** — modern compatibility and preservation target for this maintenance fork.

Please preserve the original attribution when redistributing modified builds.
