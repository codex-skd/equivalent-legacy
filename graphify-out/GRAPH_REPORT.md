# Graph Report - 26.2  (2026-08-02)

## Corpus Check
- 518 files · ~30,807 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 1151 nodes · 2041 edges · 93 communities (86 shown, 7 thin omitted)
- Extraction: 97% EXTRACTED · 3% INFERRED · 0% AMBIGUOUS · INFERRED: 58 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `f65f5e85`
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
- AlchemicalBag.java
- KnowledgeSyncPayload.java
- FixedValues
- EquivalentLegacyItems
- NormalizedSimpleStack
- PhilosophersStone.java
- TransmutationTableBlock.java
- SimpleGraphMapper
- EMCHelper
- TransmutationTablet.java
- .syncKnowledgeDataIfChanged
- NSSTag
- MatterMaterials
- CurioItem.java
- IHasConversions
- TransmuteRequestPayload.java
- EquivalentLegacyTags
- EquivalentLegacyAttachments.java
- PacketHandler.java

## God Nodes (most connected - your core abstractions)
1. `NormalizedSimpleStack` - 31 edges
2. `EquivalentLegacyItems` - 29 edges
3. `Changelog — Equivalent Legacy` - 28 edges
4. `PlayerKnowledge` - 27 edges
5. `MatterFurnaceBlockEntity` - 26 edges
6. `NSSItem` - 21 edges
7. `PlayerKnowledgeAttachment` - 21 edges
8. `CustomConversion` - 19 edges
9. `FixedValues` - 19 edges
10. `KleinStar` - 19 edges

## Surprising Connections (you probably didn't know these)
- `Publish Public Script` ----> `Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton`  [EXTRACTED]
  .gitlab-ci.yml → CHANGELOG.md
- `Publish Public Script` ----> `Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.`  [EXTRACTED]
  .gitlab-ci.yml → README.md
- `AlchemicalChestBlockEntity` --inherits--> `BaseMachineBlockEntity`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/block/entity/AlchemicalChestBlockEntity.java → src/main/java/com/skd/equivalentlegacy/block/entity/BaseMachineBlockEntity.java
- `EquivalentLegacyBlockEntities` --references--> `AlchemicalChestBlockEntity`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/block/entity/EquivalentLegacyBlockEntities.java → src/main/java/com/skd/equivalentlegacy/block/entity/AlchemicalChestBlockEntity.java
- `CollectorBlockEntity` --inherits--> `BaseMachineBlockEntity`  [EXTRACTED]
  src/main/java/com/skd/equivalentlegacy/block/entity/CollectorBlockEntity.java → src/main/java/com/skd/equivalentlegacy/block/entity/BaseMachineBlockEntity.java

## Import Cycles
- None detected.

## Communities (93 total, 7 thin omitted)

### Community 0 - "Equivalent Legacy Mod"
Cohesion: 0.27
Nodes (7): FMLCommonSetupEvent, EquivalentLegacy, Identifier, IEventBus, Logger, Mod, ModContainer

### Community 1 - "Mod Configuration"
Cohesion: 0.29
Nodes (7): CommandDispatcher, CommandSourceStack, RegisterCommandsEvent, EventBusSubscriber, ServerPlayer, SubscribeEvent, ModCommands

### Community 2 - "Client Setup"
Cohesion: 0.09
Nodes (22): AbstractContainerScreen, CollectorScreen, Component, GuiGraphicsExtractor, Inventory, Override, CondenserScreen, Component (+14 more)

### Community 3 - "Gradle Build Script"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 4 - "Public Script Publish"
Cohesion: 0.67
Nodes (3): Publish Public Script, Initial Scaffold from the codex-docs/mod_template/26.2-26.2.0.32-beta NeoForge MDK skeleton, Equivalent Legacy is an EMC/transmutation mod for Minecraft 26.2 (NeoForge), bringing the classic Equivalent Exchange-style gameplay to modern Minecraft.

### Community 5 - "Event Bus"
Cohesion: 0.17
Nodes (4): Object2IntSortedMap, CustomConversion, Object2IntMap, Override

### Community 6 - "Server Start"
Cohesion: 0.12
Nodes (10): AbstractDataComponentHolderNSSTag, DataComponentPatch, Identifier, Override, AbstractNSSTag, Identifier, Override, Registry (+2 more)

### Community 7 - "Creative Mode Tab"
Cohesion: 0.06
Nodes (13): IExtendedMappingCollector, Object2IntMap, IMappingCollector, Object2IntMap, Provider, IValueArithmetic, Override, LongArithmetic (+5 more)

### Community 8 - "Mod Notes"
Cohesion: 0.22
Nodes (8): CurseForge — Variables del proyecto, Dependencia requerida, Nota, Proyecto, Rama, Tag, Tokens, Variables para script (lectura automática)

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
Cohesion: 0.07
Nodes (28): 0.0.0-beta.1, 0.0.0-beta.10, 0.0.0-beta.11, 0.0.0-beta.12, 0.0.0-beta.13, 0.0.0-beta.14, 0.0.0-beta.15, 0.0.0-beta.16 (+20 more)

### Community 16 - "Flujo de trabajo — Equivalent Legacy (NeoForge)"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Equivalent Legacy (NeoForge), Flujo por tarea, Idioma (+4 more)

### Community 19 - "IMappingCollector"
Cohesion: 0.08
Nodes (30): AbstractFurnaceMenu, AbstractFurnaceScreen, BaseContainerBlockEntity, CachedCheck, Container, Items, NonNullList, ServerLevel (+22 more)

### Community 20 - "LongArithmetic"
Cohesion: 0.07
Nodes (34): ChargableItem, Component, InteractionHand, InteractionResult, ItemStack, Level, Override, Player (+26 more)

### Community 21 - "SimpleGraphMapper"
Cohesion: 0.05
Nodes (36): CreativeModeTab, DeferredBlock, CollectorTier, MK1, MK2, MK3, CondenserTier, MK1 (+28 more)

### Community 22 - "EquivalentLegacyConfig"
Cohesion: 0.47
Nodes (3): EquivalentLegacyConfig, Logger, ModContainer

### Community 23 - "NSSTag"
Cohesion: 0.14
Nodes (7): Item, AeternalisFuel, AlchemicalCoal, CovalenceDust, DarkMatter, MobiusFuel, RedMatter

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
Cohesion: 0.33
Nodes (7): CustomPacketPayload, ByteBuf, IPayloadContext, Override, StreamCodec, Type, KnowledgeDataPayload

### Community 32 - "AbstractNSSTag"
Cohesion: 0.21
Nodes (9): MouseButtonEvent, ItemEntry, Component, GuiGraphicsExtractor, Identifier, Inventory, ItemStack, Override (+1 more)

### Community 34 - "KnowledgeSyncChangePayload.java"
Cohesion: 0.12
Nodes (18): Component, InteractionHand, InteractionResult, ItemStack, Level, Override, Player, TooltipContext (+10 more)

### Community 38 - "Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex)"
Cohesion: 0.25
Nodes (7): Contexto — Equivalent Legacy (para usar directamente con OpenCode/Codex), Lo que falta (siguientes betas), Lo ya implementado (fases 1-13, todo commiteado, compilando y publicado en CurseForge), Notas sobre el entorno (por qué esto existe), Origen / atribución (obligatorio mantener), Qué es este mod, Reglas de naming y estilo (obligatorias)

### Community 40 - "PlayerEvents"
Cohesion: 0.26
Nodes (10): ItemLike, DataComponentPatch, Identifier, Item, ItemStack, MapCodec, Override, Registry (+2 more)

### Community 43 - "CondenserBlockEntity"
Cohesion: 0.10
Nodes (15): CondenserBlockEntity, BlockEntityTicker, BlockPos, BlockState, Component, Level, MenuProvider, Override (+7 more)

### Community 44 - "CollectorBlockEntity"
Cohesion: 0.06
Nodes (27): SavedData, SavedDataType, ServerStartingEvent, ServerStoppingEvent, CollectorBlockEntity, BlockEntityTicker, BlockPos, BlockState (+19 more)

### Community 45 - "BaseMachineBlock.java"
Cohesion: 0.08
Nodes (39): Block, EntityBlock, BaseMachineBlock, BlockEntity, BlockEntityTicker, BlockEntityType, BlockHitResult, BlockPlaceContext (+31 more)

### Community 46 - "BaseMachineBlockEntity"
Cohesion: 0.08
Nodes (26): FMLClientSetupEvent, RegisterMenuScreensEvent, AlchemicalChestBlockEntity, BlockPos, BlockState, Component, MenuProvider, Override (+18 more)

### Community 47 - "TransmutationContainer"
Cohesion: 0.26
Nodes (6): Inventory, ItemStack, Override, Player, SimpleContainer, TransmutationContainer

### Community 48 - "PlayerKnowledge"
Cohesion: 0.24
Nodes (4): Identifier, Player, ServerPlayer, PlayerKnowledge

### Community 49 - "PlayerEvents"
Cohesion: 0.25
Nodes (6): PlayerChangedDimensionEvent, PlayerLoggedInEvent, PlayerRespawnEvent, EventBusSubscriber, SubscribeEvent, PlayerEvents

### Community 50 - ".syncKnowledgeDataIfChanged"
Cohesion: 0.10
Nodes (21): AbstractContainerMenu, IItemHandler, BagMenu, InteractionHand, Inventory, ItemStack, ItemStackHandler, Override (+13 more)

### Community 51 - "MatterMaterials"
Cohesion: 0.10
Nodes (20): CurioItem, InteractionResult, Override, UseOnContext, Type, BLACK_HOLE_BAND, BODY_STONE, EVERTIDE_AMULET (+12 more)

### Community 55 - "CurioEvents.java"
Cohesion: 0.14
Nodes (14): Post, CurioEvents, EventBusSubscriber, ItemStack, Level, Player, SubscribeEvent, InteractionHand (+6 more)

### Community 56 - ".use"
Cohesion: 0.25
Nodes (6): InteractionHand, InteractionResult, Level, Override, Player, Tome

### Community 57 - "CurioItem"
Cohesion: 0.15
Nodes (13): Blocks, InteractionResult, Override, UseOnContext, MiscToolItem, Type, CATALYTIC_LENS, DESTRUCTION_CATALYST (+5 more)

### Community 58 - ".syncKnowledgeDataIfChanged"
Cohesion: 0.09
Nodes (17): BlockEntity, BaseMachineBlockEntity, BlockEntityType, BlockPos, BlockState, Component, MenuProvider, Player (+9 more)

### Community 59 - "KnowledgeSyncChangePayload.java"
Cohesion: 0.53
Nodes (4): DeferredHolder, DeferredRegister, IngredientType, ModIngredientTypes

### Community 63 - "AlchemicalBag.java"
Cohesion: 0.23
Nodes (8): AlchemicalBag, InteractionHand, InteractionResult, ItemStack, ItemStackHandler, Level, Override, Player

### Community 64 - "KnowledgeSyncPayload.java"
Cohesion: 0.33
Nodes (6): ByteBuf, IPayloadContext, Override, StreamCodec, Type, KnowledgeSyncPayload

### Community 68 - "FixedValues"
Cohesion: 0.25
Nodes (3): Object2LongSortedMap, FixedValues, Override

### Community 69 - "EquivalentLegacyItems"
Cohesion: 0.30
Nodes (8): AxeItem, BlockItem, DeferredItem, HoeItem, ShearsItem, ShovelItem, EquivalentLegacyItems, Item

### Community 70 - "NormalizedSimpleStack"
Cohesion: 0.23
Nodes (3): ConversionGroup, MapCodec, NormalizedSimpleStack

### Community 72 - "TransmutationTableBlock.java"
Cohesion: 0.29
Nodes (8): BlockHitResult, BlockPos, BlockState, InteractionResult, Level, Override, Player, TransmutationTableBlock

### Community 73 - "SimpleGraphMapper"
Cohesion: 0.22
Nodes (4): IValueGenerator, Logger, Override, SimpleGraphMapper

### Community 75 - "TransmutationTablet.java"
Cohesion: 0.33
Nodes (6): InteractionHand, InteractionResult, Level, Override, Player, TransmutationTablet

### Community 78 - "MatterMaterials"
Cohesion: 0.13
Nodes (16): ArmorMaterial, DataComponentType, EquipmentAsset, Holder, ICustomIngredient, FullKleinStarIngredient, IngredientType, Item (+8 more)

### Community 79 - "CurioItem.java"
Cohesion: 0.33
Nodes (6): ByteBuf, IPayloadContext, Override, StreamCodec, Type, KnowledgeSyncChangePayload

### Community 80 - "IHasConversions"
Cohesion: 0.33
Nodes (6): ByteBuf, IPayloadContext, Override, StreamCodec, Type, KnowledgeSyncEmcPayload

### Community 86 - "TransmuteRequestPayload.java"
Cohesion: 0.33
Nodes (6): ByteBuf, IPayloadContext, Override, StreamCodec, Type, TransmuteRequestPayload

### Community 87 - "EquivalentLegacyTags"
Cohesion: 0.60
Nodes (3): EquivalentLegacyTags, Item, TagKey

### Community 88 - "EquivalentLegacyAttachments.java"
Cohesion: 0.60
Nodes (3): AttachmentType, EquivalentLegacyAttachments, DeferredRegister

## Knowledge Gaps
- **96 isolated node(s):** `MK1`, `MK2`, `MK3`, `MK1`, `MK2` (+91 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NSSItem` connect `PlayerEvents` to `AbstractNSSTag`, `Server Start`, `Common Setup`, `CondenserBlockEntity`, `.syncKnowledgeDataIfChanged`, `TransmutationContainer`, `PlayerKnowledge`, `CurioEvents.java`, `CurioItem`?**
  _High betweenness centrality (0.105) - this node is a cross-community bridge._
- **Why does `NormalizedSimpleStack` connect `NormalizedSimpleStack` to `FixedValues`, `Event Bus`, `Server Start`, `PlayerEvents`, `EMCHelper`, `.syncKnowledgeDataIfChanged`, `NSSTag`, `CurioEvents.java`?**
  _High betweenness centrality (0.090) - this node is a cross-community bridge._
- **Why does `EquivalentLegacyItems` connect `EquivalentLegacyItems` to `KnowledgeSyncChangePayload.java`, `TransmutationTablet.java`, `MatterMaterials`, `IMappingCollector`, `LongArithmetic`, `CurioEvents.java`, `NSSTag`, `.use`, `CurioItem`, `AlchemicalBag.java`?**
  _High betweenness centrality (0.074) - this node is a cross-community bridge._
- **What connects `MK1`, `MK2`, `MK3` to the rest of the system?**
  _96 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Client Setup` be split into smaller, more focused modules?**
  _Cohesion score 0.08846153846153847 - nodes in this community are weakly interconnected._
- **Should `Server Start` be split into smaller, more focused modules?**
  _Cohesion score 0.1225071225071225 - nodes in this community are weakly interconnected._
- **Should `Creative Mode Tab` be split into smaller, more focused modules?**
  _Cohesion score 0.055152394775036286 - nodes in this community are weakly interconnected._