# Graph Report - .  (2026-07-30)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 54 nodes · 72 edges · 13 communities (7 shown, 6 thin omitted)
- Extraction: 100% EXTRACTED · 0% INFERRED · 0% AMBIGUOUS
- Token cost: 367 input · 121 output

## Graph Freshness
- Built from commit: `8ef3202f`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Equivalent Legacy Mod
- Mod Configuration
- Client Setup
- Gradle Build Script
- Public Script Publish
- Event Bus
- Server Start
- Creative Mode Tab
- Mod Notes
- Project Overview
- Common Setup

## God Nodes (most connected - your core abstractions)
1. `EquivalentLegacy` - 17 edges
2. `Config` - 7 edges
3. `EquivalentLegacyClient` - 5 edges
4. `Publish Public Script` - 2 edges
5. `Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton` - 1 edges
6. `Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.` - 1 edges
7. `Paso 0 obligatorio` - 1 edges
8. `Notas específicas de este mod` - 1 edges
9. `Overview` - 1 edges
10. `Variables del proyecto` - 1 edges

## Surprising Connections (you probably didn't know these)
- `Publish Public Script` ----> `Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton`  [EXTRACTED]
  .gitlab-ci.yml → CHANGELOG.md
- `Publish Public Script` ----> `Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.`  [EXTRACTED]
  .gitlab-ci.yml → README.md
- `Paso 0 obligatorio` ----> `Notas específicas de este mod`  [EXTRACTED]
  .claude/CLAUDE.md → docs/WORKFLOW_EQUIVALENT_LEGACY_26-2.md
- `Overview` ----> `Variables del proyecto`  [EXTRACTED]
  docs/curseforge/project_description.md → docs/curseforge/project_vars.md

## Import Cycles
- None detected.

## Communities (13 total, 6 thin omitted)

### Community 0 - "Equivalent Legacy Mod"
Cohesion: 0.28
Nodes (12): Block, BlockItem, Blocks, CreativeModeTab, DeferredBlock, DeferredHolder, DeferredItem, DeferredRegister (+4 more)

### Community 1 - "Mod Configuration"
Cohesion: 0.25
Nodes (7): BooleanValue, Builder, ConfigValue, IntValue, Item, ModConfigSpec, Config

### Community 2 - "Client Setup"
Cohesion: 0.36
Nodes (6): EventBusSubscriber, FMLClientSetupEvent, EquivalentLegacyClient, Mod, ModContainer, SubscribeEvent

### Community 3 - "Gradle Build Script"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 4 - "Public Script Publish"
Cohesion: 0.67
Nodes (3): Publish Public Script, Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton, Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.

## Knowledge Gaps
- **6 isolated node(s):** `Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton`, `Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.`, `Paso 0 obligatorio`, `Notas específicas de este mod`, `Overview` (+1 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **6 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `EquivalentLegacy` connect `Equivalent Legacy Mod` to `Mod Configuration`, `Event Bus`, `Server Start`, `Creative Mode Tab`, `Common Setup`?**
  _High betweenness centrality (0.205) - this node is a cross-community bridge._
- **What connects `Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton`, `Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.`, `Paso 0 obligatorio` to the rest of the system?**
  _6 weakly-connected nodes found - possible documentation gaps or missing edges._