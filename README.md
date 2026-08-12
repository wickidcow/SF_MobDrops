<h1 align="center">SFMobDrops Legacy</h1>

<h3 align="center">Configurable Slimefun item drops for modern Paper / Slimefun Legacy servers.</h3>

## About this fork

This repository preserves WalshyDev's original **SFMobDrops** plugin while updating it for the modern Slimefun Legacy stack used by AlbionMC.

Original project and design credit belongs to **WalshyDev**. This fork focuses on maintenance and compatibility rather than replacing the original work.

## Current target

- Paper / Minecraft **26.2**
- Java **25** build toolchain
- Java **21** plugin bytecode
- Slimefun Legacy-compatible Slimefun4 API
- English configuration and command output

## Legacy build changes

- Updated the build from the old Spigot 1.17 / Slimefun RC-30 toolchain.
- Removed the obsolete Blob Builds self-updater so this fork cannot silently replace itself with an incompatible upstream build.
- Updated bStats and removed Lombok/annotation-processor dependence from the plugin data models.
- Preserved existing mob-drop configuration support, including the older single-drop format.
- Multiple matching drop definitions for the same mob can now apply instead of stopping at the first match.
- Fixed named-mob matching so a configured custom name does not also match unnamed mobs.
- Hardened numeric configuration parsing and GUI sizing for modern Paper.

## Building

GitHub Actions builds the plugin as:

`SF_MobDrops_Legacy_v1.0.0.jar`

The workflow uses the direct artifact mode, so the Actions output is the raw `.jar` file instead of a `.jar.zip` wrapper. Tagged builds also attach the same raw JAR directly to the GitHub Release.

## Configuration

SFMobDrops can add one or more Slimefun items to mob death drops. Existing configuration files remain supported.

Example:

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

  - entity: ALL
    drops:
      - slimefunItem: COPPER_DUST
        chance: 1
```

Optional `name` and `nbtTag` filters can further restrict which mobs qualify.

## Credits

SFMobDrops was created by **WalshyDev**. Please preserve original attribution when redistributing modified builds.
