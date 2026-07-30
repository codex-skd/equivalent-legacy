# Graph Report - 26.2  (2026-07-31)

## Corpus Check
- 43 files · ~7,057 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 279 nodes · 403 edges · 31 communities (27 shown, 4 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 7 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `c83ea1e0`
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
- IMappingCollector
- LongArithmetic
- SimpleGraphMapper
- EquivalentLegacyConfig
- NSSTag
- ServerConfig
- ClientConfig
- CommonConfig
- MappingConfig

## God Nodes (most connected - your core abstractions)
1. `NormalizedSimpleStack` - 31 edges
2. `CustomConversion` - 19 edges
3. `FixedValues` - 19 edges
4. `IValueArithmetic` - 14 edges
5. `LongArithmetic` - 13 edges
6. `EMCHelper` - 12 edges
7. `AbstractNSSTag` - 12 edges
8. `MappingCollector` - 11 edges
9. `AbstractDataComponentHolderNSSTag` - 10 edges
10. `NSSItem` - 10 edges

## Surprising Connections (you probably didn't know these)
- `Publish Public Script` ----> `Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton`  [EXTRACTED]
  .gitlab-ci.yml → CHANGELOG.md
- `Publish Public Script` ----> `Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.`  [EXTRACTED]
  .gitlab-ci.yml → README.md
- `FixedValues` --references--> `CustomConversion`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/emc/FixedValues.java → src/main/java/com/skd/equivalentlegacy/emc/CustomConversion.java
- `EMCHelper` --references--> `NormalizedSimpleStack`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/emc/EMCHelper.java → src/main/java/com/skd/equivalentlegacy/emc/nss/NormalizedSimpleStack.java
- `FixedValues` --references--> `NormalizedSimpleStack`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/emc/FixedValues.java → src/main/java/com/skd/equivalentlegacy/emc/nss/NormalizedSimpleStack.java

## Import Cycles
- None detected.

## Communities (31 total, 4 thin omitted)

### Community 0 - "Equivalent Legacy Mod"
Cohesion: 0.14
Nodes (13): Blocks, CreativeModeTab, DeferredHolder, DeferredRegister, FMLCommonSetupEvent, IEventBus, EquivalentLegacyBlocks, EquivalentLegacy (+5 more)

### Community 1 - "Mod Configuration"
Cohesion: 0.26
Nodes (10): ItemLike, ResourceKey, DataComponentPatch, Identifier, Item, ItemStack, MapCodec, Override (+2 more)

### Community 2 - "Client Setup"
Cohesion: 0.36
Nodes (6): EventBusSubscriber, FMLClientSetupEvent, EquivalentLegacyClient, Mod, ModContainer, SubscribeEvent

### Community 3 - "Gradle Build Script"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 4 - "Public Script Publish"
Cohesion: 0.67
Nodes (3): Publish Public Script, Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton, Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.

### Community 5 - "Event Bus"
Cohesion: 0.10
Nodes (9): Object2IntSortedMap, ConversionGroup, CustomConversion, Object2IntMap, Override, MapCodec, NormalizedSimpleStack, Override (+1 more)

### Community 6 - "Server Start"
Cohesion: 0.12
Nodes (10): AbstractDataComponentHolderNSSTag, DataComponentPatch, Identifier, Override, AbstractNSSTag, Identifier, Override, Registry (+2 more)

### Community 7 - "Creative Mode Tab"
Cohesion: 0.11
Nodes (6): IValueArithmetic, Conversion, Object2IntMap, Override, Provider, MappingCollector

### Community 8 - "Mod Notes"
Cohesion: 0.25
Nodes (7): CurseForge — Variables del proyecto, Nota, Proyecto, Rama, Tag, Tokens, Variables para script (lectura automática)

### Community 10 - "Common Setup"
Cohesion: 0.16
Nodes (4): Object2LongSortedMap, FixedValues, Override, IHasConversions

### Community 13 - "Equivalent Legacy"
Cohesion: 0.33
Nodes (5): Equivalent Legacy, Installation, License, Requirements, Status

### Community 14 - "CLAUDE.md — equivalent_legacy (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — equivalent_legacy (26.2), Paso 0 obligatorio, Prioridad de instrucciones

### Community 15 - "Changelog — Equivalent Legacy"
Cohesion: 0.40
Nodes (4): 0.0.0-beta.1, 0.0.0-beta.2, 0.0.0-beta.3, Changelog — Equivalent Legacy

### Community 19 - "IMappingCollector"
Cohesion: 0.18
Nodes (5): IExtendedMappingCollector, Object2IntMap, IMappingCollector, Object2IntMap, Provider

### Community 21 - "SimpleGraphMapper"
Cohesion: 0.22
Nodes (4): IValueGenerator, Logger, Override, SimpleGraphMapper

### Community 22 - "EquivalentLegacyConfig"
Cohesion: 0.47
Nodes (3): EquivalentLegacyConfig, Logger, ModContainer

### Community 23 - "NSSTag"
Cohesion: 0.12
Nodes (8): DeferredItem, Item, Items, EMCHelper, Logger, EquivalentLegacyItems, ItemStack, PhilosophersStone

### Community 24 - "ServerConfig"
Cohesion: 0.50
Nodes (4): IntValue, Builder, ModConfigSpec, ServerConfig

### Community 25 - "ClientConfig"
Cohesion: 0.50
Nodes (4): ClientConfig, BooleanValue, Builder, ModConfigSpec

### Community 26 - "CommonConfig"
Cohesion: 0.50
Nodes (4): CommonConfig, BooleanValue, Builder, ModConfigSpec

### Community 27 - "MappingConfig"
Cohesion: 0.50
Nodes (4): BooleanValue, Builder, ModConfigSpec, MappingConfig

## Knowledge Gaps
- **20 isolated node(s):** `Paso 0 obligatorio`, `Prioridad de instrucciones`, `0.0.0-beta.3`, `0.0.0-beta.2`, `0.0.0-beta.1` (+15 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NormalizedSimpleStack` connect `Event Bus` to `Mod Configuration`, `Common Setup`, `Server Start`, `NSSTag`?**
  _High betweenness centrality (0.256) - this node is a cross-community bridge._
- **Why does `SimpleGraphMapper` connect `SimpleGraphMapper` to `Creative Mode Tab`?**
  _High betweenness centrality (0.129) - this node is a cross-community bridge._
- **Why does `MappingCollector` connect `Creative Mode Tab` to `IMappingCollector`, `SimpleGraphMapper`?**
  _High betweenness centrality (0.123) - this node is a cross-community bridge._
- **What connects `Paso 0 obligatorio`, `Prioridad de instrucciones`, `0.0.0-beta.3` to the rest of the system?**
  _20 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Equivalent Legacy Mod` be split into smaller, more focused modules?**
  _Cohesion score 0.14285714285714285 - nodes in this community are weakly interconnected._
- **Should `Event Bus` be split into smaller, more focused modules?**
  _Cohesion score 0.10227272727272728 - nodes in this community are weakly interconnected._
- **Should `Server Start` be split into smaller, more focused modules?**
  _Cohesion score 0.1225071225071225 - nodes in this community are weakly interconnected._