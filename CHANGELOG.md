# Changelog - Equivalent Legacy 26.2

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

## [1.2.0-beta.10] - Previous Release
(See git history for earlier versions)
