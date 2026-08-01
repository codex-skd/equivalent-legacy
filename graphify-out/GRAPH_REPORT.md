# Graph Report - 26.2  (2026-08-01)

## Corpus Check
- 137 files · ~13,789 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 538 nodes · 893 edges · 42 communities (39 shown, 3 thin omitted)
- Extraction: 96% EXTRACTED · 4% INFERRED · 0% AMBIGUOUS · INFERRED: 36 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `064c5eb1`
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
- Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex)
- PlayerEvents

## God Nodes (most connected - your core abstractions)
1. `NormalizedSimpleStack` - 31 edges
2. `PlayerKnowledge` - 24 edges
3. `PlayerKnowledgeAttachment` - 21 edges
4. `CustomConversion` - 19 edges
5. `FixedValues` - 19 edges
6. `NSSItem` - 18 edges
7. `TransmutationContainer` - 18 edges
8. `KleinStar` - 16 edges
9. `IValueArithmetic` - 14 edges
10. `TransmutationScreen` - 14 edges

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

## Communities (42 total, 3 thin omitted)

### Community 0 - "Equivalent Legacy Mod"
Cohesion: 0.11
Nodes (18): Block, Blocks, CreativeModeTab, DataComponentType, DeferredBlock, FMLCommonSetupEvent, EquivalentLegacyBlocks, EquivalentLegacy (+10 more)

### Community 1 - "Mod Configuration"
Cohesion: 0.29
Nodes (7): CommandDispatcher, CommandSourceStack, RegisterCommandsEvent, EventBusSubscriber, ServerPlayer, SubscribeEvent, ModCommands

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
Cohesion: 0.05
Nodes (26): AbstractContainerMenu, ItemLike, ResourceKey, SimpleContainer, DataComponentPatch, Identifier, Item, ItemStack (+18 more)

### Community 13 - "Equivalent Legacy"
Cohesion: 0.33
Nodes (5): Equivalent Legacy, Installation, License, Requirements, Status

### Community 14 - "CLAUDE.md — equivalent_legacy (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — equivalent_legacy (26.2), Prioridad de instrucciones, Workflow del mod

### Community 15 - "Changelog — Equivalent Legacy"
Cohesion: 0.18
Nodes (10): 0.0.0-beta.1, 0.0.0-beta.2, 0.0.0-beta.3, 0.0.0-beta.4, 0.0.0-beta.5, 0.0.0-beta.6, 0.0.0-beta.7, 0.0.0-beta.8 (+2 more)

### Community 16 - "Flujo de trabajo — Equivalent Legacy (NeoForge)"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Equivalent Legacy (NeoForge), Flujo por tarea, Idioma (+4 more)

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
Cohesion: 0.09
Nodes (17): BlockItem, DeferredItem, Item, Items, AeternalisFuel, AlchemicalCoal, DarkMatter, EquivalentLegacyItems (+9 more)

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
Cohesion: 0.06
Nodes (36): AttachmentType, CustomPacketPayload, IEventBus, PacketHandler, ByteBuf, IPayloadContext, Override, StreamCodec (+28 more)

### Community 32 - "AbstractNSSTag"
Cohesion: 0.20
Nodes (10): AbstractContainerScreen, GuiGraphicsExtractor, MouseButtonEvent, ItemEntry, Component, Identifier, Inventory, ItemStack (+2 more)

### Community 34 - "KnowledgeSyncChangePayload.java"
Cohesion: 0.12
Nodes (18): Component, InteractionHand, InteractionResult, ItemStack, Level, Override, Player, KleinStar (+10 more)

### Community 38 - "Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex)"
Cohesion: 0.25
Nodes (7): Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex), Lo que falta (fase 6 en adelante — quedó a medias, no arrancó), Lo ya implementado (fases 1-5, todo commiteado y compilando), Notas sobre el entorno (por qué esto existe), Origen / atribución (obligatorio mantener), Qué es este mod, Reglas de naming y estilo (obligatorias)

### Community 40 - "PlayerEvents"
Cohesion: 0.25
Nodes (6): PlayerChangedDimensionEvent, PlayerLoggedInEvent, PlayerRespawnEvent, EventBusSubscriber, SubscribeEvent, PlayerEvents

## Knowledge Gaps
- **47 isolated node(s):** `EIN`, `ZWEI`, `DREI`, `VIER`, `SPHERE` (+42 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NormalizedSimpleStack` connect `Event Bus` to `Common Setup`, `Server Start`, `NSSTag`?**
  _High betweenness centrality (0.148) - this node is a cross-community bridge._
- **Why does `PlayerKnowledge` connect `Common Setup` to `PlayerEvents`, `Mod Configuration`, `KnowledgeSyncChangePayload.java`?**
  _High betweenness centrality (0.143) - this node is a cross-community bridge._
- **Why does `NSSItem` connect `Common Setup` to `AbstractNSSTag`, `Server Start`, `NSSTag`?**
  _High betweenness centrality (0.115) - this node is a cross-community bridge._
- **What connects `EIN`, `ZWEI`, `DREI` to the rest of the system?**
  _47 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Equivalent Legacy Mod` be split into smaller, more focused modules?**
  _Cohesion score 0.10591133004926108 - nodes in this community are weakly interconnected._
- **Should `Event Bus` be split into smaller, more focused modules?**
  _Cohesion score 0.058699101004759384 - nodes in this community are weakly interconnected._
- **Should `Server Start` be split into smaller, more focused modules?**
  _Cohesion score 0.1225071225071225 - nodes in this community are weakly interconnected._