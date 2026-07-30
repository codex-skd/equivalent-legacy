# Graph Report - 26.2  (2026-07-31)

## Corpus Check
- 58 files · ~9,792 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 441 nodes · 727 edges · 37 communities (33 shown, 4 thin omitted)
- Extraction: 95% EXTRACTED · 5% INFERRED · 0% AMBIGUOUS · INFERRED: 33 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `40373950`
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
- KnowledgeSyncChangePayload.java
- PlayerEvents

## God Nodes (most connected - your core abstractions)
1. `NormalizedSimpleStack` - 31 edges
2. `PlayerKnowledge` - 22 edges
3. `PlayerKnowledgeAttachment` - 21 edges
4. `CustomConversion` - 19 edges
5. `FixedValues` - 19 edges
6. `NSSItem` - 18 edges
7. `TransmutationContainer` - 18 edges
8. `IValueArithmetic` - 14 edges
9. `TransmutationScreen` - 14 edges
10. `LongArithmetic` - 13 edges

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

## Communities (37 total, 4 thin omitted)

### Community 0 - "Equivalent Legacy Mod"
Cohesion: 0.14
Nodes (13): Blocks, CreativeModeTab, FMLCommonSetupEvent, EquivalentLegacyBlocks, EquivalentLegacy, Identifier, IEventBus, Logger (+5 more)

### Community 1 - "Mod Configuration"
Cohesion: 0.26
Nodes (10): ItemLike, ResourceKey, DataComponentPatch, Identifier, Item, ItemStack, MapCodec, Override (+2 more)

### Community 2 - "Client Setup"
Cohesion: 0.19
Nodes (11): FMLClientSetupEvent, MenuType, RegisterMenuScreensEvent, EquivalentLegacyClient, EventBusSubscriber, Mod, ModContainer, SubscribeEvent (+3 more)

### Community 3 - "Gradle Build Script"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 4 - "Public Script Publish"
Cohesion: 0.67
Nodes (3): Publish Public Script, Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton, Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.

### Community 5 - "Event Bus"
Cohesion: 0.06
Nodes (15): Object2IntSortedMap, Object2LongSortedMap, ConversionGroup, CustomConversion, Object2IntMap, Override, EMCHelper, Logger (+7 more)

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
Cohesion: 0.06
Nodes (16): AbstractContainerMenu, SimpleContainer, Inventory, ItemStack, Override, Player, ServerPlayer, TransmutationContainer (+8 more)

### Community 13 - "Equivalent Legacy"
Cohesion: 0.33
Nodes (5): Equivalent Legacy, Installation, License, Requirements, Status

### Community 14 - "CLAUDE.md — equivalent_legacy (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — equivalent_legacy (26.2), Paso 0 obligatorio, Prioridad de instrucciones

### Community 15 - "Changelog — Equivalent Legacy"
Cohesion: 0.29
Nodes (6): 0.0.0-beta.1, 0.0.0-beta.2, 0.0.0-beta.3, 0.0.0-beta.4, 0.0.0-beta.5, Changelog — Equivalent Legacy

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
Cohesion: 0.16
Nodes (11): DeferredItem, InteractionHand, InteractionResult, Item, Items, Level, EquivalentLegacyItems, ItemStack (+3 more)

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
Nodes (27): CustomPacketPayload, IEventBus, PacketHandler, ByteBuf, IPayloadContext, Override, StreamCodec, Type (+19 more)

### Community 32 - "AbstractNSSTag"
Cohesion: 0.20
Nodes (10): AbstractContainerScreen, Component, GuiGraphicsExtractor, MouseButtonEvent, ItemEntry, Identifier, Inventory, ItemStack (+2 more)

### Community 34 - "KnowledgeSyncChangePayload.java"
Cohesion: 0.21
Nodes (9): AttachmentType, ByteBuf, IPayloadContext, Override, StreamCodec, Type, KnowledgeSyncChangePayload, EquivalentLegacyAttachments (+1 more)

### Community 35 - "PlayerEvents"
Cohesion: 0.25
Nodes (6): PlayerChangedDimensionEvent, PlayerLoggedInEvent, PlayerRespawnEvent, EventBusSubscriber, SubscribeEvent, PlayerEvents

## Knowledge Gaps
- **22 isolated node(s):** `Paso 0 obligatorio`, `Prioridad de instrucciones`, `0.0.0-beta.5`, `0.0.0-beta.4`, `0.0.0-beta.3` (+17 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NormalizedSimpleStack` connect `Event Bus` to `Mod Configuration`, `Common Setup`, `Server Start`, `NSSTag`?**
  _High betweenness centrality (0.182) - this node is a cross-community bridge._
- **Why does `NSSItem` connect `Mod Configuration` to `AbstractNSSTag`, `Common Setup`, `Server Start`, `NSSTag`?**
  _High betweenness centrality (0.139) - this node is a cross-community bridge._
- **Why does `PlayerKnowledge` connect `Common Setup` to `PlayerEvents`?**
  _High betweenness centrality (0.114) - this node is a cross-community bridge._
- **What connects `Paso 0 obligatorio`, `Prioridad de instrucciones`, `0.0.0-beta.5` to the rest of the system?**
  _22 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Equivalent Legacy Mod` be split into smaller, more focused modules?**
  _Cohesion score 0.1380952380952381 - nodes in this community are weakly interconnected._
- **Should `Event Bus` be split into smaller, more focused modules?**
  _Cohesion score 0.058699101004759384 - nodes in this community are weakly interconnected._
- **Should `Server Start` be split into smaller, more focused modules?**
  _Cohesion score 0.1225071225071225 - nodes in this community are weakly interconnected._