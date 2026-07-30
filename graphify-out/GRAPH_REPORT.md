# Graph Report - 26.2  (2026-07-30)

## Corpus Check
- 13 files · ~4,823 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 77 nodes · 89 edges · 19 communities (11 shown, 8 thin omitted)
- Extraction: 100% EXTRACTED · 0% INFERRED · 0% AMBIGUOUS
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `ee2e25af`
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
- Equivalent Legacy
- CLAUDE.md — equivalent_legacy (26.2)
- Changelog — Equivalent Legacy
- Flujo de trabajo — Equivalent Legacy (NeoForge)
- Variables del proyecto

## God Nodes (most connected - your core abstractions)
1. `EquivalentLegacy` - 17 edges
2. `Config` - 7 edges
3. `CurseForge — Variables del proyecto` - 7 edges
4. `EquivalentLegacyClient` - 5 edges
5. `Equivalent Legacy` - 5 edges
6. `CLAUDE.md — equivalent_legacy (26.2)` - 3 edges
7. `Changelog — Equivalent Legacy` - 2 edges
8. `Flujo de trabajo — Equivalent Legacy (NeoForge)` - 2 edges
9. `Publish Public Script` - 2 edges
10. `Paso 0 obligatorio` - 1 edges

## Surprising Connections (you probably didn't know these)
- `Publish Public Script` ----> `Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton`  [EXTRACTED]
  .gitlab-ci.yml → CHANGELOG.md
- `Publish Public Script` ----> `Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.`  [EXTRACTED]
  .gitlab-ci.yml → README.md

## Import Cycles
- None detected.

## Communities (19 total, 8 thin omitted)

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

### Community 8 - "Mod Notes"
Cohesion: 0.25
Nodes (7): CurseForge — Variables del proyecto, Nota, Proyecto, Rama, Tag, Tokens, Variables para script (lectura automática)

### Community 13 - "Equivalent Legacy"
Cohesion: 0.33
Nodes (5): Equivalent Legacy, Installation, License, Requirements, Status

### Community 14 - "CLAUDE.md — equivalent_legacy (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — equivalent_legacy (26.2), Paso 0 obligatorio, Prioridad de instrucciones

## Knowledge Gaps
- **18 isolated node(s):** `Paso 0 obligatorio`, `Prioridad de instrucciones`, `0.0.0-beta.1`, `Status`, `Requirements` (+13 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **8 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `EquivalentLegacy` connect `Equivalent Legacy Mod` to `Mod Configuration`, `Event Bus`, `Server Start`, `Creative Mode Tab`, `Common Setup`?**
  _High betweenness centrality (0.099) - this node is a cross-community bridge._
- **What connects `Paso 0 obligatorio`, `Prioridad de instrucciones`, `0.0.0-beta.1` to the rest of the system?**
  _18 weakly-connected nodes found - possible documentation gaps or missing edges._