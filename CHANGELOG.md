# Changelog — Equivalent Legacy

## 1.2.0-beta.2

- **Fix**: Tome of Knowledge GUI now displays items correctly. The `setFullKnowledge()` method no longer clears the knowledge set.

## 1.2.0-beta.1

- **Added**: RecipeMapper implementation with 200+ vanilla item EMC values (ores, gems, blocks, food, tools, armor, mob drops).
- **Added**: EMC Vanilla Core foundation for transmutation system.

## 1.1.1

- **Fix**: Transmutation Table now initializes with base vanilla item EMC values (coal, stone, ores, gems, nether_star). The system can now learn from and transmute vanilla objects correctly. Full recipe-based EMC graph calculation infrastructure added for future expansion.

## 1.1.0

- **Localization**: added translations for Portuguese (Brazil), German, French, Russian, Simplified Chinese, Japanese, Korean, Italian, Polish and Dutch, alongside the existing English and Spanish. All 12 languages cover the full set of item, block, GUI, config and command strings.

## 1.0.2

- **Fix**: CurseForge dependency now targets `curios-api-updated` (project 1579340) instead of the original `curios` project.

## 1.0.1

- **CurseForge dependency**: Curios API is now declared as a required dependency on the CurseForge file, so the launcher installs it automatically.

## 1.0.0

- **First stable release.** Full port of the ProjectE / Equivalent Exchange 2 experience for Minecraft 26.2 / NeoForge.
- Complete EMC system (values, knowledge, transmutation) with Philosopher's Stone, Transmutation Table/Tablet and Tome of Knowledge.
- Alchemical fuels, Dark/Red Matter, full tool + armor toolchains with charge/AOE, Gem Armor and Full Star endgame recipes.
- Klein Stars Ein–Omega, the arcana curio set, misc toolchain items and the Interdiction Torch.
- Collectors/Relays/Condensers with a persistent per-dimension EMC network, Dark/Red Matter furnaces, Alchemical Chest and 16 colored Alchemical Bags.
- Requires Curios 15.0.0+.

## 0.0.0-beta.27

- **Fix**: removed the unused `NeoForge.EVENT_BUS.register(this)` from the main class, which crashed startup on NeoForge 26.2.0.37-beta (class has no `@SubscribeEvent` methods).

## 0.0.0-beta.26

- **Fix**: Curios dependency version range now accepts `15.0.0-beta.2+26.2` (the MC 26.2 build), fixing the load error.

## 0.0.0-beta.25

- **Full Star recipes**: custom recipe ingredient matching a maxed (16M) Klein Star Omega; alternative Gem Armor and Tome crafting routes (endgame).

## 0.0.0-beta.24

- **Curios required dependency**: arcana items and Klein Stars work while equipped in Curios slots (effects apply from slots, not only the inventory).
- **Critical packaging fix**: `META-INF/neoforge.mods.toml` is now included in the built JAR (previous releases shipped without mod metadata and would not load when installed).

## 0.0.0-beta.23

- **Persistent EMC network**: per-dimension pools stored via `SavedData` (loaded on start, saved on stop), surviving restarts.

## 0.0.0-beta.22

- **Dark Matter Furnace** (4x) and **Red Matter Furnace** (8x): fast smelting with vanilla recipes and a vanilla-style GUI.
- Completes the original mod's full block set.

## 0.0.0-beta.21

- **Gem Armor**: Gem Helmet/Chestplate/Leggings/Boots — the strongest armor set (Red Matter armor + arcana items + Klein Star Omega).

## 0.0.0-beta.20

- **World-level EMC network**: per-dimension `EMCNetwork` pool shared by all collectors/relays/condensers (replaces adjacent-block links).
- Machine GUIs show network-wide EMC.

## 0.0.0-beta.19

- **Tool charge system**: shift-right-click cycles 0-3 charge on swords, pickaxes, hammers (Charge tooltip).
- **Area mining**: hammers and pickaxes mine a face-aligned AOE scaled by charge.
- **Area attacks**: matter swords damage nearby mobs scaled by charge.

## 0.0.0-beta.18

- **Transmutation Table** (block) and **Transmutation Tablet** (item): open the transmutation GUI.
- **Interdiction Torch**: block entity that pushes mobs away.
- Core ProjectE feature set complete.

## 0.0.0-beta.17

- **Nova Catalyst / Cataclysm**: TNT + alchemical fuel crafting components.
- **Divining Rods I-III**: show total EMC in a growing area on right-click.
- **Destruction Catalyst**: clears 3x3x3 on right-click.
- **Hyperkinetic / Catalytic Lenses**: explosions on right-click.
- **Mercurial Eye**: shows player EMC. **Archangel's Smite**: bow component.

## 0.0.0-beta.16

- **Arcana set**: Iron Band, Black Hole Band, Gem of Eternal Density, Harvest Goddess Band, Ignition Ring, Swiftwolf's Rending Gale, Void Ring, Zero Ring, Arcana Ring, Body/Life/Mind/Soul Stones, Evertide/Volcanite Amulets, Repair Talisman, Watch of Flowing Time.
- Passive inventory effects (regen, fire resistance, water breathing, saturation, speed, haste, slow falling), magnet/void behaviors, right-click block effects, auto-repair.
- Curios tags for belt/necklace/ring slots.

## 0.0.0-beta.15

- **16 Alchemical Bags**: full color set (black, blue, brown, cyan, gray, green, light_blue, light_gray, lime, magenta, orange, pink, purple, red, white, yellow), each crafted with matching wool.

## 0.0.0-beta.14

- **Covalence Dusts**: Low/Medium/High (40 per craft) — crafting foundation.
- **Tome of Knowledge**: right-click unlocks all transmutation knowledge (consumed).
- Restored original Alchemical Chest/Bag recipes using covalence dusts.

## 0.0.0-beta.13

- **Curios support**: all six Klein Star tiers are equippable in a dedicated `klein_star` Curios slot (data-driven soft dependency, requires the Curios mod).

## 0.0.0-beta.12

- **Alchemical Bag**: portable 13-slot inventory persisted in the item's `DataComponents.CONTAINER`, opens on right-click.
- **Alchemical Chest**: 13-slot storage block with GUI, drops contents on break.
- Recipes adapted from the original (covalence dusts → alchemical fuels/vanilla).

## 0.0.0-beta.11

- **Collector MK1-3**: generates EMC from skylight (4/12/40 per second) and charges Klein Stars.
- **Relay MK1-3**: pulls EMC from adjacent collectors into a 100k–10M buffer and charges Klein Stars.
- **Condenser MK1-2**: learns a target item, drains EMC from Klein Stars and adjacent relays, produces the target.
- Block entities with GUIs (EMC/sun/progress via DataSlots), original ProjectE progression recipes.
- Simplified: neighbor-only EMC network, single-slot condenser output.

## 0.0.0-beta.10

- **Dark Matter toolchain**: Sword, Pickaxe, Axe, Shovel, Hoe, Shears, Hammer.
- **Red Matter toolchain**: Sword, Pickaxe, Axe, Shovel, Hoe, Shears, Hammer, Katar, Morning Star (upgrades from the Dark Matter tools).
- **Hammer** mines a 3x3 face-aligned area with proper drops and durability.
- **Armor**: full Dark and Red Matter sets with player-model rendering via equipment assets, netherite-tier stats.
- Simplified vs ProjectE: no charge/AOE sword attacks or EMC durability yet.

## 0.0.0-beta.9

- **Storage blocks**: Alchemical Coal, Mobius Fuel, Aeternalis Fuel (9 items), Dark Matter and Red Matter (4 items) blocks with construct/deconstruct recipes and EMC values.
- **Fix**: added the `items/<id>.json` model bindings required by Minecraft 26.2 — all mod items were rendering with the missing-model placeholder.
- Full blockstates, block models, loot tables; all blocks in the creative tab.

## 0.0.0-beta.8

- **Fuel chain**: Alchemical Coal (1,024 EMC), Mobius Fuel (3,072), Aeternalis Fuel (9,216) — each crafted from 4 of the previous tier plus the Philosopher's Stone.
- **Red Matter**: 417,792 EMC, second rung of the matter toolchain (8 Aeternalis Fuel around Dark Matter).
- **Original recipes restored**: Dark Matter and Klein Star Ein now use the alchemical fuels instead of the beta.7 vanilla substitutes.

## 0.0.0-beta.7

- **Higher Klein Star tiers**: Zwei, Drei, Vier, Sphere, Omega — full portable EMC battery line (1M–16M capacity), each crafted from 4 of the previous tier.
- **Crafting recipes**: Philosopher's Stone, Dark Matter and every Klein Star tier are now craftable (fuel ingredients adapted to vanilla until the alchemical fuels exist).
- **EMC commands**: `/equivalent_legacy emc` (your EMC) and `/equivalent_legacy emc give <player> <amount>` (operator-only), via Brigadier.

## 0.0.0-beta.6

- **Dark Matter**: high-EMC (139,264) intermediate crafting item, first rung above the Philosopher's Stone.
- **Klein Star Ein**: portable EMC battery. Right-click charges it from the player's EMC (up to 10k/click), shift-right-click discharges back to the player. Durability bar shows charge level, tooltip shows exact stored EMC.
- Both items have EMC values and appear in the creative tab.

## 0.0.0-beta.5

- **Philosopher's Stone transmutation GUI**: right-click opens a screen with a scrollable list of known items (sorted by EMC), an input slot to learn new items, an output slot, and the player's current EMC visible.
- Transmuting a known item spends EMC server-side and produces a copy in the output slot.
- Fix: EMC was being charged twice (once on transmute, again on picking up the output item) — now charged only once.

## 0.0.0-beta.4

- Persistent player EMC/knowledge data via NeoForge Data Attachments (`PlayerKnowledgeAttachment`, `PlayerKnowledge` API), carried over on death.
- Server→client sync networking (full sync, EMC-only, single-item-learned) on login, respawn, and dimension change.
- Still no transmutation logic or GUI — foundation for the next phase.

## 0.0.0-beta.3

- Item/block registration framework (`EquivalentLegacyItems`, `EquivalentLegacyBlocks`), wired into the mod's deferred registers.
- Added the mod's own creative mode tab.
- First functional item: **Philosopher's Stone**, hooked into the existing EMC system (`EMCHelper`/`NSSItem`) for value lookups. No transmutation logic yet.
- Fixed `archivesName` in `build.gradle` to follow the `<mod_id>-<mc-version>-neoforge` naming convention (previous betas produced a wrongly-named jar).

## 0.0.0-beta.2

- Ported the core EMC system from Equivox/ProjectE (fase 1):
  - Config framework: `CommonConfig`, `ClientConfig`, `ServerConfig`, `MappingConfig` via NeoForge `ModConfigSpec`.
  - NSS (NormalizedSimpleStack) item identification system, with data component support.
  - EMC mapper: `MappingCollector`, `SimpleGraphMapper`, `LongArithmetic`.
  - `FixedValues`/`CustomConversion` for EMC value overrides, `EMCHelper` runtime API.
- Removed the leftover skeleton example item/block and old `Config.java`.
- Still no items, blocks, commands, networking, or GUI — those are next.

## 0.0.0-beta.1

- Initial scaffold from the `codex-docs/mod_template/26.2-26.2.0.32-beta` NeoForge MDK skeleton.
- Project set up as a fork of [Equivox](https://github.com/Yaskulsky/projecte-26-port) (itself a fork of ProjectE), targeting Minecraft 26.2 / NeoForge 26.2.0.32-beta.
- Mod icon added (cropped from promotional artwork), wired via `logoFile` in `neoforge.mods.toml`.
- CurseForge project description expanded with the full planned feature set (EMC, transmutation, Philosopher's Stone toolchain, collectors/condensers, Klein Star).
- CurseForge project registered (`curseforge_project_id=1632317`).
