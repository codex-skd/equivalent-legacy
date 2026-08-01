# Graph Report - 26.2  (2026-08-01)

## Corpus Check
- 278 files · ~22,016 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 785 nodes · 1367 edges · 53 communities (49 shown, 4 thin omitted)
- Extraction: 96% EXTRACTED · 4% INFERRED · 0% AMBIGUOUS · INFERRED: 52 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `16a5f0d2`
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
- CondenserBlockEntity
- CollectorBlockEntity
- BaseMachineBlock.java
- BaseMachineBlockEntity
- TransmutationContainer
- PlayerKnowledge
- PlayerEvents
- .syncKnowledgeDataIfChanged
- MatterMaterials

## God Nodes (most connected - your core abstractions)
1. `NormalizedSimpleStack` - 31 edges
2. `PlayerKnowledge` - 24 edges
3. `PlayerKnowledgeAttachment` - 21 edges
4. `CustomConversion` - 19 edges
5. `FixedValues` - 19 edges
6. `NSSItem` - 19 edges
7. `KleinStar` - 19 edges
8. `TransmutationContainer` - 18 edges
9. `EquivalentLegacyItems` - 18 edges
10. `CondenserBlockEntity` - 17 edges

## Surprising Connections (you probably didn't know these)
- `Publish Public Script` ----> `Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton`  [EXTRACTED]
  .gitlab-ci.yml → CHANGELOG.md
- `Publish Public Script` ----> `Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.`  [EXTRACTED]
  .gitlab-ci.yml → README.md
- `CollectorBlockEntity` --inherits--> `BaseMachineBlockEntity`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/block/entity/CollectorBlockEntity.java → src/main/java/com/skd/equivalentlegacy/block/entity/BaseMachineBlockEntity.java
- `CondenserBlockEntity` --inherits--> `BaseMachineBlockEntity`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/block/entity/CondenserBlockEntity.java → src/main/java/com/skd/equivalentlegacy/block/entity/BaseMachineBlockEntity.java
- `RelayBlockEntity` --inherits--> `BaseMachineBlockEntity`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/block/entity/RelayBlockEntity.java → src/main/java/com/skd/equivalentlegacy/block/entity/BaseMachineBlockEntity.java

## Import Cycles
- None detected.

## Communities (53 total, 4 thin omitted)

### Community 0 - "Equivalent Legacy Mod"
Cohesion: 0.08
Nodes (25): Blocks, CreativeModeTab, DataComponentType, DeferredBlock, FMLCommonSetupEvent, EquivalentLegacyBlockEntities, BlockEntityType, DeferredHolder (+17 more)

### Community 1 - "Mod Configuration"
Cohesion: 0.29
Nodes (7): CommandDispatcher, CommandSourceStack, RegisterCommandsEvent, EventBusSubscriber, ServerPlayer, SubscribeEvent, ModCommands

### Community 2 - "Client Setup"
Cohesion: 0.07
Nodes (29): AbstractContainerScreen, FMLClientSetupEvent, RegisterMenuScreensEvent, EquivalentLegacyClient, EventBusSubscriber, Mod, ModContainer, SubscribeEvent (+21 more)

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
Cohesion: 0.08
Nodes (10): IValueArithmetic, IValueGenerator, Conversion, Object2IntMap, Override, Provider, MappingCollector, Logger (+2 more)

### Community 8 - "Mod Notes"
Cohesion: 0.25
Nodes (7): CurseForge — Variables del proyecto, Nota, Proyecto, Rama, Tag, Tokens, Variables para script (lectura automática)

### Community 10 - "Common Setup"
Cohesion: 0.13
Nodes (4): ByteBuf, MapCodec, StreamCodec, PlayerKnowledgeAttachment

### Community 13 - "Equivalent Legacy"
Cohesion: 0.33
Nodes (5): Equivalent Legacy, Installation, License, Requirements, Status

### Community 14 - "CLAUDE.md — equivalent_legacy (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — equivalent_legacy (26.2), Prioridad de instrucciones, Workflow del mod

### Community 15 - "Changelog — Equivalent Legacy"
Cohesion: 0.15
Nodes (12): 0.0.0-beta.1, 0.0.0-beta.10, 0.0.0-beta.11, 0.0.0-beta.2, 0.0.0-beta.3, 0.0.0-beta.4, 0.0.0-beta.5, 0.0.0-beta.6 (+4 more)

### Community 16 - "Flujo de trabajo — Equivalent Legacy (NeoForge)"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Equivalent Legacy (NeoForge), Flujo por tarea, Idioma (+4 more)

### Community 19 - "IMappingCollector"
Cohesion: 0.18
Nodes (5): IExtendedMappingCollector, Object2IntMap, IMappingCollector, Object2IntMap, Provider

### Community 21 - "SimpleGraphMapper"
Cohesion: 0.07
Nodes (26): CollectorTier, MK1, MK2, MK3, Block, MachineTiers, of(), RelayTier (+18 more)

### Community 22 - "EquivalentLegacyConfig"
Cohesion: 0.47
Nodes (3): EquivalentLegacyConfig, Logger, ModContainer

### Community 23 - "NSSTag"
Cohesion: 0.07
Nodes (30): AxeItem, BlockItem, DeferredItem, HoeItem, Item, Items, LivingEntity, ShearsItem (+22 more)

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
Cohesion: 0.21
Nodes (9): MouseButtonEvent, ItemEntry, Component, GuiGraphicsExtractor, Identifier, Inventory, ItemStack, Override (+1 more)

### Community 34 - "KnowledgeSyncChangePayload.java"
Cohesion: 0.12
Nodes (18): Component, InteractionHand, InteractionResult, ItemStack, Level, Override, Player, KleinStar (+10 more)

### Community 38 - "Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex)"
Cohesion: 0.25
Nodes (7): Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex), Lo que falta (beta.11 en adelante), Lo ya implementado (fases 1-10, todo commiteado y compilando), Notas sobre el entorno (por qué esto existe), Origen / atribución (obligatorio mantener), Qué es este mod, Reglas de naming y estilo (obligatorias)

### Community 40 - "PlayerEvents"
Cohesion: 0.26
Nodes (10): ItemLike, DataComponentPatch, Identifier, Item, ItemStack, MapCodec, Override, Registry (+2 more)

### Community 43 - "CondenserBlockEntity"
Cohesion: 0.08
Nodes (22): CondenserBlockEntity, BlockEntityTicker, BlockPos, BlockState, Component, Level, MenuProvider, Override (+14 more)

### Community 44 - "CollectorBlockEntity"
Cohesion: 0.08
Nodes (20): AbstractContainerMenu, BlockEntity, CollectorBlockEntity, BlockEntityTicker, BlockPos, BlockState, Component, Level (+12 more)

### Community 45 - "BaseMachineBlock.java"
Cohesion: 0.20
Nodes (16): Block, BlockHitResult, BlockPlaceContext, EntityBlock, RenderShape, BaseMachineBlock, BlockEntity, BlockEntityTicker (+8 more)

### Community 46 - "BaseMachineBlockEntity"
Cohesion: 0.16
Nodes (8): BaseMachineBlockEntity, BlockEntityType, BlockPos, BlockState, Component, MenuProvider, Player, SimpleContainer

### Community 47 - "TransmutationContainer"
Cohesion: 0.26
Nodes (6): Inventory, ItemStack, Override, Player, SimpleContainer, TransmutationContainer

### Community 48 - "PlayerKnowledge"
Cohesion: 0.27
Nodes (4): Identifier, Player, ServerPlayer, PlayerKnowledge

### Community 49 - "PlayerEvents"
Cohesion: 0.25
Nodes (6): PlayerChangedDimensionEvent, PlayerLoggedInEvent, PlayerRespawnEvent, EventBusSubscriber, SubscribeEvent, PlayerEvents

### Community 51 - "MatterMaterials"
Cohesion: 0.43
Nodes (5): ArmorMaterial, EquipmentAsset, ResourceKey, MatterMaterials, ToolMaterial

## Knowledge Gaps
- **57 isolated node(s):** `MK1`, `MK2`, `MK3`, `MK1`, `MK2` (+52 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NormalizedSimpleStack` connect `Event Bus` to `PlayerEvents`, `.syncKnowledgeDataIfChanged`, `Server Start`, `NSSTag`?**
  _High betweenness centrality (0.111) - this node is a cross-community bridge._
- **Why does `NSSItem` connect `PlayerEvents` to `AbstractNSSTag`, `Server Start`, `Common Setup`, `CondenserBlockEntity`, `TransmutationContainer`, `PlayerKnowledge`, `.syncKnowledgeDataIfChanged`, `NSSTag`, `KnowledgeSyncPayload.java`?**
  _High betweenness centrality (0.106) - this node is a cross-community bridge._
- **Why does `KleinStar` connect `KnowledgeSyncChangePayload.java` to `CondenserBlockEntity`, `CollectorBlockEntity`, `SimpleGraphMapper`, `NSSTag`?**
  _High betweenness centrality (0.090) - this node is a cross-community bridge._
- **What connects `MK1`, `MK2`, `MK3` to the rest of the system?**
  _57 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Equivalent Legacy Mod` be split into smaller, more focused modules?**
  _Cohesion score 0.07804878048780488 - nodes in this community are weakly interconnected._
- **Should `Client Setup` be split into smaller, more focused modules?**
  _Cohesion score 0.06980392156862746 - nodes in this community are weakly interconnected._
- **Should `Event Bus` be split into smaller, more focused modules?**
  _Cohesion score 0.058699101004759384 - nodes in this community are weakly interconnected._