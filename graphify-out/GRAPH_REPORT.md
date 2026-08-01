# Graph Report - 26.2  (2026-08-01)

## Corpus Check
- 417 files · ~26,102 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 907 nodes · 1592 edges · 68 communities (63 shown, 5 thin omitted)
- Extraction: 97% EXTRACTED · 3% INFERRED · 0% AMBIGUOUS · INFERRED: 52 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `877c52df`
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
- CurioEvents.java
- .use
- CurioItem
- .syncKnowledgeDataIfChanged
- KnowledgeSyncChangePayload.java
- KnowledgeSyncEmcPayload.java
- KnowledgeSyncPayload.java
- TransmuteRequestPayload.java
- EquivalentLegacyAttachments.java
- PacketHandler.java

## God Nodes (most connected - your core abstractions)
1. `NormalizedSimpleStack` - 31 edges
2. `PlayerKnowledge` - 26 edges
3. `EquivalentLegacyItems` - 24 edges
4. `PlayerKnowledgeAttachment` - 21 edges
5. `NSSItem` - 20 edges
6. `CustomConversion` - 19 edges
7. `FixedValues` - 19 edges
8. `KleinStar` - 19 edges
9. `TransmutationContainer` - 18 edges
10. `BaseMachineBlockEntity` - 17 edges

## Surprising Connections (you probably didn't know these)
- `Publish Public Script` ----> `Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton`  [EXTRACTED]
  .gitlab-ci.yml → CHANGELOG.md
- `Publish Public Script` ----> `Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.`  [EXTRACTED]
  .gitlab-ci.yml → README.md
- `AlchemicalChestBlockEntity` --inherits--> `BaseMachineBlockEntity`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/block/entity/AlchemicalChestBlockEntity.java → src/main/java/com/skd/equivalentlegacy/block/entity/BaseMachineBlockEntity.java
- `CondenserBlockEntity` --inherits--> `BaseMachineBlockEntity`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/block/entity/CondenserBlockEntity.java → src/main/java/com/skd/equivalentlegacy/block/entity/BaseMachineBlockEntity.java
- `RelayBlockEntity` --inherits--> `BaseMachineBlockEntity`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/block/entity/RelayBlockEntity.java → src/main/java/com/skd/equivalentlegacy/block/entity/BaseMachineBlockEntity.java

## Import Cycles
- None detected.

## Communities (68 total, 5 thin omitted)

### Community 0 - "Equivalent Legacy Mod"
Cohesion: 0.08
Nodes (25): ArmorMaterial, CreativeModeTab, DataComponentType, DeferredBlock, EquipmentAsset, FMLCommonSetupEvent, EquivalentLegacyBlocks, Block (+17 more)

### Community 1 - "Mod Configuration"
Cohesion: 0.29
Nodes (7): CommandDispatcher, CommandSourceStack, RegisterCommandsEvent, EventBusSubscriber, ServerPlayer, SubscribeEvent, ModCommands

### Community 2 - "Client Setup"
Cohesion: 0.06
Nodes (32): FMLClientSetupEvent, RegisterMenuScreensEvent, EquivalentLegacyClient, EventBusSubscriber, Mod, ModContainer, SubscribeEvent, CollectorScreen (+24 more)

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
Cohesion: 0.11
Nodes (17): 0.0.0-beta.1, 0.0.0-beta.10, 0.0.0-beta.11, 0.0.0-beta.12, 0.0.0-beta.13, 0.0.0-beta.14, 0.0.0-beta.15, 0.0.0-beta.16 (+9 more)

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
Cohesion: 0.05
Nodes (39): AxeItem, BlockItem, DeferredItem, HoeItem, Item, Items, LivingEntity, ShearsItem (+31 more)

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
Cohesion: 0.36
Nodes (6): CustomPacketPayload, ByteBuf, IPayloadContext, Override, StreamCodec, KnowledgeDataPayload

### Community 32 - "AbstractNSSTag"
Cohesion: 0.13
Nodes (16): AbstractContainerScreen, MouseButtonEvent, Component, GuiGraphicsExtractor, Identifier, Inventory, Override, StorageScreen (+8 more)

### Community 34 - "KnowledgeSyncChangePayload.java"
Cohesion: 0.12
Nodes (18): Component, InteractionHand, InteractionResult, ItemStack, Level, Override, Player, KleinStar (+10 more)

### Community 38 - "Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex)"
Cohesion: 0.25
Nodes (7): Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex), Lo que falta (siguientes betas), Lo ya implementado (fases 1-13, todo commiteado, compilando y publicado en CurseForge), Notas sobre el entorno (por qué esto existe), Origen / atribución (obligatorio mantener), Qué es este mod, Reglas de naming y estilo (obligatorias)

### Community 40 - "PlayerEvents"
Cohesion: 0.26
Nodes (10): ItemLike, DataComponentPatch, Identifier, Item, ItemStack, MapCodec, Override, Registry (+2 more)

### Community 43 - "CondenserBlockEntity"
Cohesion: 0.09
Nodes (18): CondenserBlockEntity, BlockEntityTicker, BlockPos, BlockState, Component, Level, MenuProvider, Override (+10 more)

### Community 44 - "CollectorBlockEntity"
Cohesion: 0.06
Nodes (24): BlockEntity, BaseMachineBlockEntity, BlockEntityType, BlockPos, BlockState, Component, MenuProvider, Player (+16 more)

### Community 45 - "BaseMachineBlock.java"
Cohesion: 0.20
Nodes (16): Block, BlockHitResult, BlockPlaceContext, EntityBlock, RenderShape, BaseMachineBlock, BlockEntity, BlockEntityTicker (+8 more)

### Community 46 - "BaseMachineBlockEntity"
Cohesion: 0.11
Nodes (19): AlchemicalChestBlockEntity, BlockPos, BlockState, Component, MenuProvider, Override, EquivalentLegacyBlockEntities, BlockEntityType (+11 more)

### Community 47 - "TransmutationContainer"
Cohesion: 0.24
Nodes (6): Inventory, ItemStack, Override, Player, SimpleContainer, TransmutationContainer

### Community 48 - "PlayerKnowledge"
Cohesion: 0.24
Nodes (4): Identifier, Player, ServerPlayer, PlayerKnowledge

### Community 49 - "PlayerEvents"
Cohesion: 0.25
Nodes (6): PlayerChangedDimensionEvent, PlayerLoggedInEvent, PlayerRespawnEvent, EventBusSubscriber, SubscribeEvent, PlayerEvents

### Community 50 - ".syncKnowledgeDataIfChanged"
Cohesion: 0.14
Nodes (15): AbstractContainerMenu, IItemHandler, BagMenu, InteractionHand, Inventory, ItemStack, ItemStackHandler, Override (+7 more)

### Community 51 - "MatterMaterials"
Cohesion: 0.12
Nodes (16): Type, BLACK_HOLE_BAND, BODY_STONE, EVERTIDE_AMULET, GEM_OF_ETERNAL_DENSITY, HARVEST_GODDESS_BAND, IGNITION_RING, LIFE_STONE (+8 more)

### Community 55 - "CurioEvents.java"
Cohesion: 0.30
Nodes (7): Post, CurioEvents, EventBusSubscriber, ItemStack, Level, Player, SubscribeEvent

### Community 56 - ".use"
Cohesion: 0.25
Nodes (6): InteractionHand, InteractionResult, Level, Override, Player, Tome

### Community 57 - "CurioItem"
Cohesion: 0.28
Nodes (5): Blocks, CurioItem, InteractionResult, Override, UseOnContext

### Community 59 - "KnowledgeSyncChangePayload.java"
Cohesion: 0.36
Nodes (5): ByteBuf, IPayloadContext, Override, StreamCodec, KnowledgeSyncChangePayload

### Community 60 - "KnowledgeSyncEmcPayload.java"
Cohesion: 0.36
Nodes (5): ByteBuf, IPayloadContext, Override, StreamCodec, KnowledgeSyncEmcPayload

### Community 61 - "KnowledgeSyncPayload.java"
Cohesion: 0.36
Nodes (5): ByteBuf, IPayloadContext, Override, StreamCodec, KnowledgeSyncPayload

### Community 62 - "TransmuteRequestPayload.java"
Cohesion: 0.36
Nodes (5): ByteBuf, IPayloadContext, Override, StreamCodec, TransmuteRequestPayload

### Community 63 - "EquivalentLegacyAttachments.java"
Cohesion: 0.60
Nodes (3): AttachmentType, EquivalentLegacyAttachments, DeferredRegister

## Knowledge Gaps
- **77 isolated node(s):** `MK1`, `MK2`, `MK3`, `MK1`, `MK2` (+72 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NormalizedSimpleStack` connect `Event Bus` to `Server Start`, `PlayerEvents`, `CondenserBlockEntity`, `TransmutationContainer`, `.syncKnowledgeDataIfChanged`?**
  _High betweenness centrality (0.098) - this node is a cross-community bridge._
- **Why does `NSSItem` connect `PlayerEvents` to `AbstractNSSTag`, `Server Start`, `Common Setup`, `CondenserBlockEntity`, `TransmutationContainer`, `PlayerKnowledge`, `NSSTag`, `CurioEvents.java`, `.syncKnowledgeDataIfChanged`?**
  _High betweenness centrality (0.091) - this node is a cross-community bridge._
- **Why does `KleinStar` connect `KnowledgeSyncChangePayload.java` to `CondenserBlockEntity`, `CollectorBlockEntity`, `SimpleGraphMapper`, `NSSTag`?**
  _High betweenness centrality (0.085) - this node is a cross-community bridge._
- **What connects `MK1`, `MK2`, `MK3` to the rest of the system?**
  _77 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Equivalent Legacy Mod` be split into smaller, more focused modules?**
  _Cohesion score 0.07682926829268293 - nodes in this community are weakly interconnected._
- **Should `Client Setup` be split into smaller, more focused modules?**
  _Cohesion score 0.06233766233766234 - nodes in this community are weakly interconnected._
- **Should `Event Bus` be split into smaller, more focused modules?**
  _Cohesion score 0.059562841530054644 - nodes in this community are weakly interconnected._