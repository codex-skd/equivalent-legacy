# Changelog — Equivalent Legacy

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
