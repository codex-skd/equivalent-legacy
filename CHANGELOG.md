# Changelog - Equivalent Legacy 26.2


## [1.5.0] - 2026-08-19

### Feature

- **Espadas, herramientas y armaduras PE ahora se pueden encantar**: `PESword`, `PETool` (pico/hacha/pala/azada), `PEShears` y `PEArmor` tenían `isPrimaryItemFor`/`supportsEnchantment` fijados a `false` a propósito (igual que en el port de ProjectE 1.21.1 de referencia), lo que bloqueaba el encantado vanilla por completo. Quitados esos bloqueos para que apliquen las reglas de tags `minecraft:enchantable/*` estándar, y añadido un valor de encantabilidad (`DataComponents.ENCHANTABLE`) por tier de materia (Dark Matter 18, Red Matter 22, armadura Gem 24). El equipo dm_/rm_/gem_ estaba además explícitamente excluido (`"remove"`) de esas tags — se ha vuelto a incluir. El sistema de carga/EMC no se ve afectado, ambos bonus se acumulan de forma independiente.
- `EnumMatterType#getEnchantmentValue()` devolvía siempre `1` (nivel de encantabilidad de madera) desde que existe — nunca se había conectado a nada real hasta ahora.

### Fix

- **Pérdida de inventario al romper Collector/Pedestal/Matter Furnace (el fix de 1.4.3 no funcionaba realmente)**: `Block#onBlockStateChange` se dispara desde `Level#markAndNotifyBlock`, que se ejecuta **después** de que `LevelChunk#setBlockState` ya ha eliminado el block entity del nivel — cualquier drop de inventario intentado desde ese hook busca un block entity que ya no existe y pierde el contenido en silencio. Esto explica por qué el fix del Collector en 1.4.3 (commit `87f9b65`) nunca llegó a funcionar en pruebas reales. Movido el volcado de inventario a `BlockEntity#preRemoveSideEffects`, que se ejecuta justo antes de que el block entity se elimine: Collector (input+aux), Pedestal (ítem sujeto) y Matter Furnace (input/output/fuel).

## [1.4.4] - 2026-08-18

### Change

- **Actualización de NeoForge**: actualizado de 26.2.0.37-beta a 26.2.0.45-beta.
- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `equivalent_legacy-26.2-neoforge-26.2.0.45-beta-1.4.4.jar`.
- **Documentación del workflow**: actualizada `docs/WORKFLOW_EQUIVALENT_LEGACY_26-2.md` para reflejar la nueva rama de trabajo.

## [1.4.3] - 2026-08-18

### Fix

- **Icono de "barra llena" del colector saliéndose del panel (MK1)**: `collector1/2/3.png` empaquetan el sprite de referencia de las barras de sol/combustible pegado al borde del ancho declarado de cada tier. En el MK1 ese sprite empezaba unos píxeles antes del borde, así que parte de él caía dentro del recorte del fondo y se veía como un icono suelto pegado a la esquina del panel. Movido 20px más adentro del atlas en las tres texturas.
- **Pérdida del ítem en carga al romper un colector**: el volcado de inventario al romper el bloque reutilizaba el handler de automatización, que bloquea a propósito la extracción del slot que se está cargando (para que un hopper no lo robe) — esa misma restricción hacía que el ítem desapareciera al minar el bloque. Ahora se vuelca directamente desde los handlers propios del block entity, sin esa restricción.
- **Icono de vista previa de transmutación pegado sobre cualquier GUI**: `TransmutationRenderingOverlay` solo comprobaba si el HUD estaba oculto (F1), no si había una pantalla abierta — el icono de previsualización del bloque de destino de la Piedra Filosofal podía quedarse pegado encima de cualquier inventario (ej. un colector) abierto justo después de apuntar con la piedra.
- **Valores de EMC por encima del coste de su receta más barata**: `minecraft:name_tag` (192), `minecraft:andesite` (16) y `minecraft:moss_block` (12) tenían un valor fijo en `defaults.json` superior a lo que cuesta craftearlos de verdad (papel+placa=46; diorita+adoquín→2 andesita=17 total; 9 surface_moss→moss_block=9), permitiendo generar EMC gratis crafteando y descomponiendo. Quitados los valores fijos para que el propio sistema anti-exploit del mod los recalcule.

### Chore

- **Eliminado el chequeo de UUIDs contra el GitHub de ProjectE** (`ThreadCheckUUID`): hacía una petición HTTP a `sinkillerj/ProjectE` en cada arranque del servidor para una lista de "High Alchemist" heredada, generando errores en el log cuando GitHub devolvía 503 y sin sentido para este fork.

### Técnico

- Renombrada la clase de entrada `@Mod` de `PECore` a `ELCore` (sin cambios de comportamiento).

## [1.4.2] - 2026-08-13

### Fix

- **El servidor podía crashear al detectar datos de block entity corruptos en un chunk**: `WorldHelper#getBlockEntity(BlockGetter, BlockPos)` llamaba `level.getBlockEntity(pos)` sin protección. Cuando un chunk conserva datos NBT de un block entity que ya no coincide con el bloque actual en esa posición (ej. una mesa de encantar rota y sustituida por verrugas del Nether sin limpiar su block entity), el chequeo de sanidad de vanilla (`BlockEntity#validateBlockState`) lanza `IllegalStateException`. Ese error se propagaba desde `getBlockEntitiesWithinAABB` (usado por `StellarCondenserEvents#onLivingDeath`) hasta el bus de eventos, tumbando el servidor entero en cualquier tick que escaneara esa posición. Capturada la excepción en el único punto de paso — el método ya está documentado y tipado `@Nullable` para devolver `null` cuando no hay block entity, así que una posición corrupta se trata igual en vez de crashear.
- Subido a CurseForge vía `curseforge-upload.ps1` (file ID `8641239`).

## [1.4.0] - 2026-08-11

### ✨ Release estable — consolida la línea 1.3.0-beta

Reescritura interna completa, re-porteada mecánicamente desde la referencia Equivox 26.2 actualizada (el fork en el que se basa este mod), llevando todos los sistemas a paridad 1:1, más traducción completa al español y varios fixes descubiertos durante las pruebas.

- **Sistema EMC**: sustituido el sistema simplificado anterior por el motor completo de mapeo/aritmética de la referencia (cálculo basado en BigFraction).
- **Block entities y GUIs**: Collector, Condenser, Furnaces, Alchemical Chest, Pedestales y Mesa de Transmutación ahora replican la mecánica y el renderizado de la referencia, incluyendo barras de progreso que faltaban.
- **Networking**: capa de paquetes reconstruida para coincidir con la estructura de la referencia.
- **Integraciones JEI/WTHIT**: categorías de receta y tooltips portados y adaptados a la versión de JEI de este proyecto.
- **Traducción al español (es_es)**: traducción completa de las 718 claves de idioma.

### 🐛 Bug Fixes
- **Crash al abrir un Collector o Relay** (`ArrayIndexOutOfBoundsException`) por tamaños de inventario desalineados en tiers superiores.
- **Pérdida de ítems en la mesa de transmutación** al cerrar el GUI con ítems en los slots de input/output.
- **Nombre del mod sin espacio** ("EquivalentLegacy") en la pestaña de creativo y varios tooltips/pantallas de configuración — ahora "Equivalent Legacy".
- **Cálculo de EMC completamente roto en juego** (`NoClassDefFoundError: org/apache/commons/math3/fraction/BigFraction`) — la librería requerida ahora se empaqueta correctamente dentro del jar del mod.
- **Texturas de horno encendido faltantes** para el Horno de Materia Oscura y de Materia Roja (bug preexistente en el proyecto de referencia).

### ⚙️ Técnico
- `PECore` es ahora el punto de entrada `@Mod` del mod, sustituyendo al antiguo `EquivalentLegacy.java`.
- Añadida y empaquetada (jar-in-jar) la dependencia `commons-math3`, requerida por el sistema de aritmética EMC.
- Añadido el Access Transformer de la referencia (`accesstransformer.cfg`).
- Integraciones CraftTweaker, Jade, TheOneProbe y EMI excluidas de compilación (sin dependencia configurada) — JEI y WTHIT siguen soportadas.

## [1.3.0-beta.14] - 2026-08-11

### ✨ Nuevo
- **Traducción al español (es_es)**: traducción completa de las 718 claves de idioma — el mod nunca había tenido traducción al español.

### 🐛 Bug Fixes
- **Nombre del mod sin espacio** ("EquivalentLegacy") en la pestaña de creativo y varios tooltips/pantallas de configuración en todos los idiomas — ahora "Equivalent Legacy".

## [1.3.0-beta.13] - 2026-08-10

### ✨ Reescritura completa (re-port desde la referencia Equivox)

Re-port mecánico completo del mod desde `lib_ext/Equivox-26.2-port` (el fork de referencia actualizado a NeoForge 26.2), sustituyendo la implementación parcial anterior por una réplica 1:1 adaptada al namespace `equivalent_legacy`.

- **Sistema EMC**: sustituido el sistema simplificado anterior por el motor completo de mapeo/aritmética de la referencia (cálculo basado en BigFraction).
- **Block entities y GUIs**: Collector, Condenser, Furnaces, Alchemical Chest, Pedestales y Mesa de Transmutación ahora replican la mecánica y el renderizado de la referencia, incluyendo barras de progreso que faltaban.
- **Networking**: capa de paquetes reconstruida para coincidir con la estructura de la referencia.
- **Integraciones JEI/WTHIT**: categorías de receta y tooltips portados y adaptados a la versión de JEI de este proyecto.

### 🐛 Bug Fixes
- **Crash al abrir un Collector o Relay** (`ArrayIndexOutOfBoundsException`) por tamaños de inventario desalineados en tiers superiores.
- **Pérdida de ítems en la mesa de transmutación** al cerrar el GUI con ítems en los slots de input/output.
- **Nombre del mod sin espacio** ("EquivalentLegacy") en la pestaña de creativo y varios tooltips — ahora "Equivalent Legacy".

### ⚙️ Técnico
- `PECore` es ahora el punto de entrada `@Mod` del mod, sustituyendo al antiguo `EquivalentLegacy.java`.
- Añadida dependencia `commons-math3` (requerida por el sistema de aritmética EMC).
- Añadido el Access Transformer de la referencia (`accesstransformer.cfg`).
- Integraciones CraftTweaker, Jade, TheOneProbe y EMI excluidas de compilación (sin dependencia configurada) — JEI y WTHIT siguen soportadas.
- Corregidos ~15 desajustes de API entre la versión de JEI que usa la referencia (29.5.0.24) y la de este proyecto (30.15.0.121).

## [1.3.0-beta.12] - 2026-08-08

### 🐛 Bug Fixes
- **Fixed 9 missing item models (MC 26.2 item model system)** — Added missing item definition files under `assets/equivalent_legacy/items/` for pedestal, dm_pedestal, rm_pedestal, destruction_catalyst_block, transmutation_stone, pe_axe, pe_pickaxe, pe_saw, and spawner_control_wand, resolving "Missing item model for location" warnings at load

### ⚙️ Technical
- New-format item definitions (`assets/equivalent_legacy/items/*.json`) required by Minecraft 26.2: blocks reference `equivalent_legacy:block/<id>`, items reference `equivalent_legacy:item/<id>`

## [1.3.0-beta.11] - 2026-08-08

### 🐛 Bug Fixes
- **Fixed missing item models** — Added explicit `particle` textures to all block models extending `base_chest` (pedestal, dm_pedestal, rm_pedestal, alchemical_chest, condenser_mk1, condenser_mk2), resolving "Missing item model" warnings

### ⚙️ Technical
- Moved `particle` texture from `base_chest.json` to individual child models to fix NeoForge model validation

### 🐛 Bug Fixes
- **Fixed transmutation_table blockstate** — Added `FACING` property to `TransmutationTableBlock` to match blockstate JSON
- **Fixed destruction_catalyst blockstate** — Renamed `destruction_catalyst_block.json` to `destruction_catalyst.json` to match registry name
- **Fixed missing particle textures** — Added `particle` entry to `base_chest.json`, fixing warnings on alchemical_chest, pedestal, condensers
- **Fixed arcana_ring item model** — Added missing `parent` and `textures` to base model
- **Fixed spawner_control_wand model** — Corrected parent from `item/handheld` to `minecraft:item/handheld`
- **Fixed IndexOutOfBoundsException on GUIs** — Removed duplicate `addDataSlot()` calls from `PEContainer.broadcastPE()` that were incrementing slot count every tick
- **Fixed missing item textures** — Created placeholder textures for pe_axe, pe_pickaxe, pe_saw, transmutation_stone, spawner_control_wand
- **Removed empty test_recipe.json** causing data parse errors

### ⚙️ Technical
- `TransmutationTableBlock`: added `EnumProperty<Direction> FACING` with `createBlockStateDefinition()`
- `ChestMenu`: added manual `addDataSlot(networkEmc.highSlot/lowSlot)` in constructor
- Deleted `test_recipe.json` (empty file causing JSON parse errors)

## [1.3.0-beta.9] - 2026-08-08

### 🐛 Bug Fixes
- **Fixed missing item model JSONs** — pedestals (base, dm, rm), destruction_catalyst_block, and spawner_control_wand now have proper item model definitions, resolving magenta texture glitches in inventory
- **Fixed missing block texture references** — pedestal variants and destruction_catalyst_block now use existing PNG textures (alchemical_chest, dark_matter_block, red_matter_block) instead of non-existent files

### ⚙️ Technical
- Added `models/item/{pedestal,rm_pedestal,destruction_catalyst_block,spawner_control_wand}.json` with proper parent model inheritance
- Updated `models/block/{pedestal,dm_pedestal,rm_pedestal,destruction_catalyst_block}.json` texture mappings to reference existing assets

## [1.3.0-beta.8] - 2026-08-07

### 🐛 Bug Fixes
- **Fixed incomplete block models** — condenser_mk1, condenser_mk2, alchemical_chest, pedestal, dm_pedestal, rm_pedestal now render with proper geometry instead of magenta/black missing textures
- **Fixed translation namespace mismatch** — 6,813+ translation keys updated from `projecte` to `equivalent_legacy` namespace across 14 language files (all items/blocks now display correct names in tooltips)

### ⚙️ Technical
- Applied parent block reference (`base_chest`) to chest-like machines for proper model rendering
- Verified all translation files (19 total) for namespace consistency and JSON validity
- All supported languages: en_us, es_es, da_dk, de_de, fi_fi, fr_fr, it_it, ja_jp, ko_kr, nl_nl, no_no, pl_pl, pt_br, pt_pt, ru_ru, sv_se, tr_tr, zh_cn, zh_tw

## [1.3.0-beta.7] - 2026-08-07

### ✨ Features Complete
- All Phases 1-6 implemented and ready for testing
- Phase 1: EMC Registry + Container Infrastructure (235+ values, PEContainer)
- Phase 2: Data Files + Localization (142+ JSON, 19 languages)
- Phase 3: Transmutation System (GUI, cost visualization)
- Phase 4: Mob Farming System (spawner detection, pedestal integration)
- Phase 5: Matter Furnace + Alchemical Chests (Dark/Red Matter, 104-slot storage)
- Phase 6: Alchemical Chest Network (multi-chest linking, shared EMC pool)

## [1.2.0-RELEASE] - 2026-08-07

### ✨ Major Features

#### Phase 1A: Container Infrastructure Rewrite
- **New Base Class: PEContainer** — Abstract container extending MachineMenu with built-in EMC synchronization
  - Manages `BoxedLong` fields for 64-bit EMC values (supports > Integer.MAX_VALUE)
  - Inherits `addPlayerInventory()` for consistent player inventory handling
  - Thread-safe `broadcastChanges()` with `broadcastPE()` hook for EMC sync
  
- **5 Menus Rewritten**:
  - **CollectorMenu**: 1 validated slot for Klein Star, syncs network EMC + sun level + charge progress
  - **CondenserMenu**: 3 slots (TARGET, KLEIN, OUTPUT), syncs EMC cost calculation
  - **RelayMenu**: 1 validated slot for Klein Star, syncs relay power
  - **BagMenu**: 13 item slots (7+6), simplified with PEContainer inheritance
  - **ChestMenu**: 13 storage slots (7+6), syncs openness state

- **Slot Infrastructure**:
  - `ValidatedSlot` — slot validation with `Predicate<ItemStack>` for IItemHandler-based containers
  - `ValidatedContainerSlot` — variant for SimpleContainer (non-NeoForge) containers
  - `SlotGhost` — display-only ghost slots (count=1, no pickup/placement, auto-set count=1)
  - `ISlotGhost` interface — marks ghost slot behavior
  - `SlotPredicates` — reusable validators:
    - `ALWAYS_FALSE` — block all placements
    - `HAS_EMC` — accept any non-empty item
    - `COLLECTOR_INV` — collector-specific validation
    - `CONDENSER_LOCK` — condenser lock slot validation
    - `RELAY_INV` — relay inventory validation

#### Phase 1B: EMC Registry System (Complete Port from Equivox 26.1.2)

The EMC Registry is the "heart" of Equivalent Legacy. It handles all value lookups, calculations, and conversions.

##### Subsystem 1: Hardcoded EMC Values (235+ items)
- **File**: `EmcValues.java`
- **Categories**:
  - Raw Materials: dirt, logs, wood, stone, coal, etc.
  - Ores: iron, gold, diamond, emerald, etc.
  - Gems: diamond (2048), emerald (2048), nether star, etc.
  - Ingots: iron (256), gold (32), copper (8), netherite (8192), etc.
  - Blocks: furnaces, crafting tables, etc.
  - Food: apples, wheat, etc.
  - Dyes: all 16 colors
  - Tools & Armor: pickaxes, swords, chestplates, etc.
  - Mob Drops: bones, string, etc.
  - Equivalent Legacy specific: Klein Stars, upgrades, etc.

**Key Values**:
- `oak_log` = 2
- `stone` = 1
- `iron_ingot` = 256
- `gold_ingot` = 32
- `diamond` = 2048
- `emerald` = 2048
- `netherite_ingot` = 8192

##### Subsystem 2: EMCMappingHandler (Central Coordinator)
- **File**: `EMCMappingHandler.java`
- **Purpose**: Resolve EMC values from all sources with priority ordering
- **Architecture**:
  1. **FixedValues** (highest priority) — hardcoded values
  2. **Recipe Mappers** — derive values from crafting, smelting, brewing
  3. **Data Component Processors** — add EMC for enchantments, damage, container contents
  4. **Special Mappers** — handle edge cases (ore blacklist, tag grouping, custom conversions)
  5. **Graph Mapper** (fallback) — derive from complex recipe chains

- **Public API**:
  - `getEMCValue(ItemStack)` — get EMC with data component bonuses
  - `getBaseEMCValue(NormalizedSimpleStack)` — get base EMC (no components)
  - `getEMCValue(Fluid)` — get fluid EMC (0 in Phase 1B)
  - `getEMCValue(TagKey)` — get lowest EMC in tag
  - `hasEMCValue(ItemStack)` — check if item has EMC
  - `invalidateCache()` — force recomputation
  - `registerMapper(IEMCMapper)` — add new mapper
  - `registerComponentEnhancer(IComponentEnhancer)` — add component processor

- **Thread-safe**: Uses `synchronized` blocks and volatile fields for concurrent access
- **Singleton**: `EMCMappingHandler.INSTANCE`

##### Subsystem 3: DataComponentMapper (8 Component Processors)
Adds EMC for item data components (NBT in 1.20+):

1. **EnchantmentProcessor** — enchanted items get bonus EMC per enchantment level
2. **DamageProcessor** — damaged items lose EMC proportional to damage
3. **ContainerProcessor** — containers (chests, barrels, shulkers) include contents EMC
4. **SimpleContainerProcessor** — generic container contents
5. **PersistentComponentProcessor** — custom NBT data
6. **ArmorTrimProcessor** — armor trim variations
7. **BundleProcessor** — bundle contents
8. **WrittenBookProcessor** — book content (text adds EMC)

Each processor implements `IComponentProcessor`:
```java
long calculateComponentEMC(ItemStack stack, EMCMappingHandler handler)
```

##### Subsystem 4: Special Mappers (4 Mappers)
- **OreBlacklistMapper** — marks all ores as 0 EMC (prevents circular loops between ore → ingot recipes)
- **RawMaterialsBlacklistMapper** — marks raw materials (raw_iron, raw_copper, etc.) as 0 EMC
- **CustomConversionMapper** — player-defined custom conversions (public static API for mods)
- **TagMapper** — placeholder for Phase 2 (full tag-to-tag grouping deferred due to API limitations)

##### Subsystem 5: Capabilities & Public API
- **IEmcStorage** — capability for EMC storage (Klein Stars, Collectors, Condensers, Relays)
  - `getStoredEmc()`, `setStoredEmc()`, `getMaxEmc()`
  - `addEmc()`, `removeEmc()`

- **IKnowledgeProvider** — capability for player transmutation knowledge
  - `knows()`, `learn()`, `getKnownItems()`, `reset()`

- **IEmcProvider** — capability for custom EMC providers (addon API)
  - `getEmc(NormalizedSimpleStack)` → `Optional<Long>`
  - `getName()`

- **EquivalentLegacyEMCAPI** — public entry point
  - `initialize(EMCMappingHandler)` — set up at mod load
  - `getEMCValue(ItemStack)` → long
  - `getBaseEMCValue(NormalizedSimpleStack)` → long
  - `getHandler()` → EMCMappingHandler
  - `invalidateCache()` → void

#### Phase 2: Data Files & Localization (Complete Port) ✅
- **Data Files** (142 JSON files):
  - Recipes: collectors (MK1-MK3), condenser, relay, furnace, transmutation, bags, chests
  - Loot tables: block drops for all machines
  - Tags: item/block grouping for transmutation targets
  - Advancements: unlock tree for progression
  - World generation: custom feature registration
  - Curios: Klein Star slot integration

- **Localization** (19 languages):
  - `en_us.json`: 729 keys (primary language, complete coverage)
  - 18 translations: de_de, es_es, fr_fr, it_it, ja_jp, ko_kr, nl_nl, pl_pl, pt_br, pt_pt, ru_ru, sv_se, tr_tr, zh_cn, zh_tw, no_no, fi_fi, da_dk
  - UTF-8 encoding (BOM removed for proper JSON parsing)
  - Keys cover: item/block names, tooltips, GUI strings, messages, advancements

- **Fixed Issues**:
  - UTF-8 BOM in all language files (was preventing JSON parsing)
  - Language coverage: 18 → 19 files (added pt_pt, zh_tw, sv_se, no_no, fi_fi, da_dk; removed non-planned: cs_cz, en_au, en_gb, en_ud, uk_ua)

### 🐛 Bug Fixes
- Fixed texture loading issue (restored `items/` folder with BlockItem definitions)
- Fixed namespace references (projecte → equivalent_legacy)
- Fixed type mismatches in containers (ItemStack handling in PEContainer)
- Fixed UTF-8 BOM in language files (JSON parsing errors)

### 📋 Technical Details

#### Normalized Simple Stack (NSS)
All items are represented as `NormalizedSimpleStack` for consistent comparison:
- `NSSItem` — vanilla/mod items
- `NSSTag` — item tags (oak_wood, spruce_wood grouped together)
- `NSSFluid` — fluids (Phase 2)
- Custom NSS for special items

#### BoxedLong Pattern
For values > `Integer.MAX_VALUE`, EMC uses 64-bit split into high/low ints:
```
64-bit EMC = (high_int << 32) | (low_int & 0xFFFFFFFF)
```
Synchronized via two `DataSlot` fields for network sync.

#### Priority Resolution
When multiple mappers provide values, highest-priority source wins:
1. FixedValues (configured first)
2. RecipeMappers
3. ComponentEnhancers
4. SpecialMappers
5. GraphMapper (fallback only)

### ⚙️ Architecture Improvements
- **Separation of Concerns**: Containers (UI) vs EMC (values) cleanly separated
- **Extensibility**: Public API allows addons to hook into EMC system without modifying core
- **Thread Safety**: EMCMappingHandler uses sync + volatile for concurrent queries
- **Performance**: Caching + lazy remapping minimizes recalculation
- **Testability**: NSS normalization enables unit tests without full game bootstrap

### 🚀 Ready for Phase 2
- ✅ Data files generation (recipes, loot tables, lang files)
- ✅ Missing items + block definitions
- ✅ Graph-based EMC resolution (for complex recipe chains)
- ✅ Fluid EMC support
- ✅ Full tag-to-tag grouping (TagMapper)
- ✅ Client-side knowledge sync (player transmutation progress)

### 📝 Known Limitations (Phase 1B)
- **No Fluid EMC** — fluids return 0 (deferred to Phase 2)
- **No graph-based resolution** — circular dependencies detected as errors (Phase 2 will resolve via graph)
- **No BigFraction arithmetic** — uses simple `long` math only
- **No datagen integration** — hardcoded values only (Phase 2 adds dynamic mapping)
- **TagMapper placeholder** — full tag iteration deferred to Phase 2

### 📦 Files Changed
- **Phase 1A (Containers)**:
  - `PEContainer.java` (new)
  - `BoxedLong.java` (new)
  - `CollectorMenu.java` (rewritten)
  - `CondenserMenu.java` (rewritten)
  - `RelayMenu.java` (rewritten)
  - `BagMenu.java` (rewritten)
  - `ChestMenu.java` (rewritten)
  - `slots/ValidatedSlot.java` (new)
  - `slots/ValidatedContainerSlot.java` (new)
  - `slots/SlotGhost.java` (new)
  - `slots/ISlotGhost.java` (new)
  - `slots/SlotPredicates.java` (new)

- **Phase 1B (EMC Registry)**:
  - `EmcValues.java` (new, 235+ values)
  - `mapper/EMCMappingHandler.java` (new)
  - `mapper/IEMCMapper.java` (new)
  - `mapper/MappingResult.java` (new)
  - `components/DataComponentMapper.java` (new)
  - `components/processor/IComponentProcessor.java` (new)
  - `components/processor/*Processor.java` (8 new files)
  - `mappers/OreBlacklistMapper.java` (new)
  - `mappers/RawMaterialsBlacklistMapper.java` (new)
  - `mappers/CustomConversionMapper.java` (new)
  - `mappers/TagMapper.java` (new)
  - `capability/IEmcStorage.java` (new)
  - `capability/IKnowledgeProvider.java` (new)
  - `capability/IEmcProvider.java` (new)
  - `EquivalentLegacyEMCAPI.java` (new)

### 🔗 Commits
- `a541117` — feat: add base slot infrastructure
- `a472328` — Phase 1A: Rewrite CollectorMenu
- `38ccf68` — Phase 1A: Rewrite CondenserMenu
- `fd09f40` — Phase 1A: Rewrite RelayMenu
- `6275031` — Phase 1A: Rewrite BagMenu
- `0f75836` — Phase 1A: Rewrite ChestMenu
- `8cfd338` — Phase 1B: Add hardcoded EMC values registry
- `36714fc` — Phase 1B: Add EMCMappingHandler
- `be39842` — Phase 1B: Add DataComponentMapper + processors
- `1395e36` — Phase 1B: Add Capabilities & Public API

---

## [1.4.1] - 2026-08-12

### Change

- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `equivalent_legacy-26.2-neoforge-26.2.0.37-beta-1.4.1.jar` (se añade la versión de cargador/NeoForge al nombre del archivo). Empaquetado y documentación; sin cambios de funcionalidad.


## [1.2.0-beta.10] - Previous Release
(See git history for earlier versions)
