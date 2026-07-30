# Changelog — Equivalent Legacy

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
