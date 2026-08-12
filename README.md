<div align="center">

# 🧟💀 SFMobDrops — Slimefun Legacy

**Turn Minecraft mobs into configurable sources of Slimefun loot.**

**🧟 Mob → ⚔️ Defeated → 💀 RIP → ⚙️✨ Slimefun Drops!**

![Slimefun Legacy](https://img.shields.io/badge/Slimefun-Legacy-6bd425?style=for-the-badge)
![Paper 26.2](https://img.shields.io/badge/Paper-26.2-blue?style=for-the-badge)
![License: MIT](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)
![Maintained for AlbionMC.com](https://img.shields.io/badge/Maintained%20for-albionmc.com-7b68ee?style=for-the-badge)

</div>

> [!IMPORTANT]
> This is an **unofficial Slimefun Legacy maintenance fork** developed for use on **albionmc.com**. The original SFMobDrops concept and authorship remain with its original creator.

## 💚 A tribute to WalshyDev

**SFMobDrops was originally created by WalshyDev (Daniel Walsh).**

The original addon had a wonderfully simple idea: let server owners decide which Slimefun items mobs can drop without turning the feature into an unnecessarily complicated system. This fork exists to **preserve that idea, its configuration style, and WalshyDev's work** while keeping it usable on current Slimefun Legacy servers.

Original authorship is not replaced by this fork. The maintenance work is a continuation of the project, not a claim to have created it.

## 🎯 What does SFMobDrops do?

SFMobDrops lets a server owner add **Slimefun items to mob death drops** through configuration.

You can configure:

- specific mob types;
- global drops that may come from all mobs;
- multiple possible Slimefun drops per mob;
- independent chance and amount values;
- custom-name filters;
- persistent-data/tag filters for specialized rules.

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
```

## 🧪 Slimefun Legacy maintenance

- modern Paper 26.2 / Slimefun Legacy build target;
- modern Java build tooling;
- English configuration and command output;
- removal of the obsolete external self-updater;
- preservation of the established configuration format, including older single-drop definitions;
- multiple matching drop definitions can contribute to the same mob;
- corrected custom-name matching;
- hardened chance/amount parsing;
- safer GUI sizing on modern Paper;
- Slimefun remains a provided dependency instead of being bundled into the addon.

### Commands

| Command | Purpose |
|---|---|
| `/mobdrops reload` | Reload the mob-drop configuration |
| `/mobdrops list` | Open the configured mob-drop list |
| `/sfmobdrops` | Alias for `/mobdrops` |

Administrative commands require `sfmobdrops.admin`.

## ❤️ Credits & project lineage

- **WalshyDev / Daniel Walsh** — original creator of SFMobDrops and the original addon concept/codebase.
- **SFMobDrops community users and contributors** — configuration examples, testing, and continued use of the addon.
- **Slimefun developers and contributors** — for the Slimefun platform and API.
- **wickidcow / Slimefun Legacy** — current compatibility and preservation maintenance for modern servers and albionmc.com.

Please preserve the original copyright and attribution when redistributing or creating additional forks.

## 📜 License

SFMobDrops is licensed under the **MIT License**, not GNU GPLv3. The repository's `LICENSE` retains the **2020 Daniel Walsh** copyright notice.

When copying or distributing copies or substantial portions of the software, preserve the copyright and permission notice required by the MIT License. The software is provided **“AS IS”**, without warranty, as stated in that license.

> [!NOTE]
> Many Slimefun addons are GPLv3, but **SFMobDrops is MIT-licensed**. This maintenance fork intentionally keeps the actual upstream license rather than applying a different license just for consistency.

## ⚖️ Independence & trademark notice

**NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

SFMobDrops, Slimefun Legacy, and this maintenance fork are independent community projects. They are not sponsored, endorsed, approved, or operated by Mojang Studios or Microsoft. Minecraft-related names, brands, and assets remain the property of their respective rights holders.

This repository is also not represented as an official release of WalshyDev, the original Slimefun developers, or any other upstream party unless explicitly stated by them.

---

<div align="center">

**🧟 Defeat mob → 💀 roll chance → ⚙️ Slimefun loot. Simple and classic.**

</div>
