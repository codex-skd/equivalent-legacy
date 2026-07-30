# Graph Report - 26.2  (2026-07-31)

## Corpus Check
- 52 files · ~8,137 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 368 nodes · 563 edges · 34 communities (30 shown, 4 thin omitted)
- Extraction: 96% EXTRACTED · 4% INFERRED · 0% AMBIGUOUS · INFERRED: 23 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `f1049809`
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
- KnowledgeSyncPayload.java
- AbstractNSSTag

## God Nodes (most connected - your core abstractions)
1. `NormalizedSimpleStack` - 31 edges
2. `PlayerKnowledgeAttachment` - 21 edges
3. `CustomConversion` - 19 edges
4. `FixedValues` - 19 edges
5. `PlayerKnowledge` - 18 edges
6. `NSSItem` - 16 edges
7. `IValueArithmetic` - 14 edges
8. `LongArithmetic` - 13 edges
9. `EMCHelper` - 12 edges
10. `AbstractNSSTag` - 12 edges

## Surprising Connections (you probably didn't know these)
- `Publish Public Script` ----> `Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton`  [EXTRACTED]
  .gitlab-ci.yml → CHANGELOG.md
- `Publish Public Script` ----> `Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.`  [EXTRACTED]
  .gitlab-ci.yml → README.md
- `MappingCollector` --implements--> `IExtendedMappingCollector`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/emc/mapper/MappingCollector.java → src/main/java/com/skd/equivalentlegacy/emc/mapper/IExtendedMappingCollector.java
- `LongArithmetic` --implements--> `IValueArithmetic`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/emc/mapper/LongArithmetic.java → src/main/java/com/skd/equivalentlegacy/emc/mapper/IValueArithmetic.java
- `SimpleGraphMapper` --inherits--> `MappingCollector`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/emc/mapper/SimpleGraphMapper.java → src/main/java/com/skd/equivalentlegacy/emc/mapper/MappingCollector.java

## Import Cycles
- None detected.

## Communities (34 total, 4 thin omitted)

### Community 0 - "Equivalent Legacy Mod"
Cohesion: 0.14
Nodes (13): Blocks, CreativeModeTab, DeferredHolder, FMLCommonSetupEvent, EquivalentLegacyBlocks, EquivalentLegacy, Identifier, IEventBus (+5 more)

### Community 1 - "Mod Configuration"
Cohesion: 0.22
Nodes (10): ItemLike, ResourceKey, DataComponentPatch, Identifier, Item, ItemStack, MapCodec, Override (+2 more)

### Community 2 - "Client Setup"
Cohesion: 0.36
Nodes (6): FMLClientSetupEvent, EquivalentLegacyClient, EventBusSubscriber, Mod, ModContainer, SubscribeEvent

### Community 3 - "Gradle Build Script"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 4 - "Public Script Publish"
Cohesion: 0.67
Nodes (3): Publish Public Script, Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton, Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.

### Community 5 - "Event Bus"
Cohesion: 0.07
Nodes (13): Object2IntSortedMap, Object2LongSortedMap, ConversionGroup, CustomConversion, Object2IntMap, Override, EMCHelper, Logger (+5 more)

### Community 6 - "Server Start"
Cohesion: 0.23
Nodes (6): AbstractDataComponentHolderNSSTag, DataComponentPatch, Identifier, Override, DataComponentPatch, NSSDataComponentHolder

### Community 7 - "Creative Mode Tab"
Cohesion: 0.11
Nodes (6): IValueArithmetic, Conversion, Object2IntMap, Override, Provider, MappingCollector

### Community 8 - "Mod Notes"
Cohesion: 0.25
Nodes (7): CurseForge — Variables del proyecto, Nota, Proyecto, Rama, Tag, Tokens, Variables para script (lectura automática)

### Community 10 - "Common Setup"
Cohesion: 0.07
Nodes (14): Player, PlayerChangedDimensionEvent, PlayerLoggedInEvent, PlayerRespawnEvent, ServerPlayer, EventBusSubscriber, SubscribeEvent, PlayerEvents (+6 more)

### Community 13 - "Equivalent Legacy"
Cohesion: 0.33
Nodes (5): Equivalent Legacy, Installation, License, Requirements, Status

### Community 14 - "CLAUDE.md — equivalent_legacy (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — equivalent_legacy (26.2), Paso 0 obligatorio, Prioridad de instrucciones

### Community 15 - "Changelog — Equivalent Legacy"
Cohesion: 0.33
Nodes (5): 0.0.0-beta.1, 0.0.0-beta.2, 0.0.0-beta.3, 0.0.0-beta.4, Changelog — Equivalent Legacy

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
Cohesion: 0.23
Nodes (6): DeferredItem, Item, Items, EquivalentLegacyItems, ItemStack, PhilosophersStone

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

### Community 31 - "KnowledgeSyncPayload.java"
Cohesion: 0.08
Nodes (24): AttachmentType, CustomPacketPayload, IEventBus, PacketHandler, ByteBuf, IPayloadContext, Override, StreamCodec (+16 more)

### Community 32 - "AbstractNSSTag"
Cohesion: 0.16
Nodes (6): AbstractNSSTag, Identifier, Override, Registry, Override, NSSTag

## Knowledge Gaps
- **21 isolated node(s):** `Paso 0 obligatorio`, `Prioridad de instrucciones`, `0.0.0-beta.4`, `0.0.0-beta.3`, `0.0.0-beta.2` (+16 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NSSItem` connect `Mod Configuration` to `Common Setup`, `Server Start`, `NSSTag`?**
  _High betweenness centrality (0.293) - this node is a cross-community bridge._
- **Why does `NormalizedSimpleStack` connect `Event Bus` to `AbstractNSSTag`, `Mod Configuration`, `NSSTag`?**
  _High betweenness centrality (0.204) - this node is a cross-community bridge._
- **Why does `SimpleGraphMapper` connect `SimpleGraphMapper` to `Creative Mode Tab`?**
  _High betweenness centrality (0.116) - this node is a cross-community bridge._
- **What connects `Paso 0 obligatorio`, `Prioridad de instrucciones`, `0.0.0-beta.4` to the rest of the system?**
  _21 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Equivalent Legacy Mod` be split into smaller, more focused modules?**
  _Cohesion score 0.1380952380952381 - nodes in this community are weakly interconnected._
- **Should `Event Bus` be split into smaller, more focused modules?**
  _Cohesion score 0.06516290726817042 - nodes in this community are weakly interconnected._
- **Should `Creative Mode Tab` be split into smaller, more focused modules?**
  _Cohesion score 0.11384615384615385 - nodes in this community are weakly interconnected._