# Plan: Phase 1B - EMC Registry Port

**Proyecto**: Equivalent Legacy 26.2 / NeoForge 26.2.0.37-beta  
**Objetivo**: Port del sistema EMC Registry desde Equivox 26.1.2  
**Scope**: ~30 archivos Java, 5 subsistemas principales  
**Prioridad**: Bloqueador para v1.2.0-RELEASE

---

## Current State Analysis

**Already exists in Equivalent Legacy**:
- ✅ `FixedValues.java` — base class (estructura correcta)
- ✅ `NormalizedSimpleStack` (NSS) — item normalization framework
- ✅ `NSSItem`, `NSSTag`, `NSSDataComponentHolder` — NSS implementations
- ✅ `EMCNetwork`, `EMCNetworkData` — global EMC storage
- ✅ `EMCHelper.java` — utility methods
- ✅ `RecipeMapper.java` — basic recipe-based mapping
- ✅ `ConversionGroup`, `CustomConversion` — custom conversion framework

**Missing / Incomplete**:
- ❌ `EMCMappingHandler` — central coordinator (arquitectura falta)
- ❌ `DataComponentMapper` + 26 processors (enchantments, books, containers, etc.)
- ❌ Special mappers (OreBlacklist, RawMaterials, Waxable, Oxidization, etc.)
- ❌ Brewing mapper
- ❌ Graph-based arithmetic (BigFraction handling for complex recipes)
- ❌ Hardcoded EMC values for Phase 1-2 items (200+ values)
- ❌ Capabilities API (IEmcStorage, IKnowledgeProvider, IEmcProvider)

---

## Work Breakdown (5 Subsystems)

### 1. **Hardcoded EMC Values** (Subsystem A)
**Files to create**: `src/main/java/com/skd/equivalentlegacy/emc/EmcValues.java`

**What it does**:
- Initialize `FixedValues` with 200+ hardcoded EMC values
- Phase 1 (vanilla basics): dirt=0, oak_log=2, oak_wood=2, stone=1, coal=32, iron_ingot=256, diamond=2048, etc.
- Phase 2 (ProcessE): emerald=2048, redstone=32, lapis_lazuli=36, glowstone=40, etc.
- Phase 3-4 (ProjectE gems): transmutation_stone=10000, etc.
- Phase 5 (Equivalent Legacy specific): klein_star_mk1=1000000, etc.

**Method signature**:
```java
public static FixedValues initializeDefaultValues()
```

**Reference**: Check `temp/Equivox-main/.../* pregenerated/PregeneratedEMC.java` for value ranges

**Definition of Done**:
- [ ] FixedValues initialized with ≥200 values
- [ ] Oak log = 2 EMC (test: `EmcValues.initializeDefaultValues().setValueBefore(NSSItem.of(Items.OAK_LOG)) == 2`)
- [ ] Diamond = 2048 EMC
- [ ] Compiles without errors

---

### 2. **EMCMappingHandler** (Subsystem B)
**Files to create**: 
- `src/main/java/com/skd/equivalentlegacy/emc/mapper/EMCMappingHandler.java` (main coordinator)
- `src/main/java/com/skd/equivalentlegacy/emc/mapper/IMappingCollector.java` (if not exists)
- `src/main/java/com/skd/equivalentlegacy/emc/mapper/MappingResult.java` (result wrapper)

**What it does**:
- Central coordinator that manages all mappers
- Caches EMC values to avoid recalculation
- Handles priority resolution (when multiple mappers provide values for same item)
- Public entry point: `getEMCValue(ItemStack)` → `long`
- Detects and prevents circular dependencies

**Key methods**:
```java
public long getEMCValue(ItemStack stack) { ... }
public long getEMCValue(Fluid fluid) { ... }
public long getEMCValue(TagKey<?> tag) { ... }
public void registerMapper(IEMCMapper mapper) { ... }
public void invalidateCache() { ... }
```

**Architecture**:
- Initialize with `FixedValues` (highest priority)
- Register mappers in order of priority:
  1. FixedValues
  2. RecipeMapper (crafting, smithing, brewing)
  3. DataComponentMapper (enchantments, containers, etc.)
  4. SpecialMappers (ore blacklist, oxidization, etc.)
  5. Fallback/graph-based (lowest priority)

**Reference**: `temp/Equivox-main/.../emc/EMCMappingHandler.java`

**Definition of Done**:
- [ ] Compiles without errors
- [ ] `getEMCValue(oak_log)` returns 2
- [ ] `getEMCValue(diamond)` returns 2048
- [ ] Cache invalidation works
- [ ] No circular dependency resolution needed for Phase 1B (just detect & error)

---

### 3. **DataComponentMapper + Processors** (Subsystem C)
**Files to create**:
- `src/main/java/com/skd/equivalentlegacy/emc/components/DataComponentMapper.java` (main orchestrator)
- `src/main/java/com/skd/equivalentlegacy/emc/components/processor/IComponentProcessor.java` (base interface)
- Processors (at minimum 5-10 critical ones):
  - `EnchantmentProcessor.java` — enchanted items
  - `DamageProcessor.java` — damaged items
  - `SimpleContainerProcessor.java` — items with containers (barrels, chests, etc.)
  - `PersistentComponentProcessor.java` — generic NBT
  - `WrittenBookProcessor.java` — books with content
  - (Optional: ArmorTrimProcessor, BannerProcessor, DecoratedPotProcessor, etc. — add if time)

**What it does**:
- Adds EMC for data components (enchantments, damage, container content, etc.)
- Each processor handles a specific component type
- Used by EMCMappingHandler to enhance base item values

**Processor interface**:
```java
public interface IComponentProcessor {
    long calculateComponentEMC(ItemStack stack, EMCMappingHandler handler);
}
```

**Reference**: `temp/Equivox-main/.../emc/components/processor/*.java`

**Definition of Done**:
- [ ] Compiles without errors
- [ ] EnchantmentProcessor works: enchanted_diamond_sword = EMC(diamond_sword) + EMC(sharpness_v, etc.)
- [ ] SimpleContainerProcessor works: shulker_box_with_diamonds = EMC(shulker) + EMC(diamonds_inside)
- [ ] DamageProcessor works: damaged items reduce EMC proportionally

---

### 4. **Special Mappers** (Subsystem D)
**Files to create**:
- `src/main/java/com/skd/equivalentlegacy/emc/mappers/OreBlacklistMapper.java` — exclude certain ores
- `src/main/java/com/skd/equivalentlegacy/emc/mappers/RawMaterialsBlacklistMapper.java` — exclude raw materials
- `src/main/java/com/skd/equivalentlegacy/emc/mappers/TagMapper.java` — tag-based mappings
- `src/main/java/com/skd/equivalentlegacy/emc/mappers/CustomConversionMapper.java` — custom conversions

**What they do**:
- OreBlacklistMapper: Mark certain ores (copper, iron, etc.) as "no EMC" (prevents circular loops)
- RawMaterialsBlacklistMapper: Exclude raw materials (raw_iron, raw_copper, etc.)
- TagMapper: Items with same tag = same EMC (oak wood = spruce wood)
- CustomConversionMapper: Player-defined conversions

**Reference**: `temp/Equivox-main/.../emc/mappers/*.java`

**Definition of Done**:
- [ ] Compiles without errors
- [ ] OreBlacklistMapper prevents circular dependencies
- [ ] TagMapper correctly groups wood types

---

### 5. **Capabilities & Public API** (Subsystem E)
**Files to create**:
- `src/main/java/com/skd/equivalentlegacy/emc/capability/IEmcStorage.java` — store/extract EMC
- `src/main/java/com/skd/equivalentlegacy/emc/capability/IKnowledgeProvider.java` — known items per player
- `src/main/java/com/skd/equivalentlegacy/emc/capability/IEmcProvider.java` — custom EMC providers
- `src/main/java/com/skd/equivalentlegacy/emc/EquivalentLegacyEMCAPI.java` — public entry point

**What they do**:
- IEmcStorage: Block entities can store EMC (for Collectors, Condensers, Klein Stars)
- IKnowledgeProvider: Track which items player has "discovered" (for transmutation)
- IEmcProvider: Allow mods to provide custom EMC values
- API: Public methods for addons to query/modify EMC

**Reference**: `temp/Equivox-main/.../api/...capability/*.java`

**Definition of Done**:
- [ ] Compiles without errors
- [ ] EMCMappingHandler is accessible via API
- [ ] Capabilities can be attached to block entities
- [ ] Public API is usable from external code (no internal-only exports)

---

## Integration Checkpoints

**Before Phase 1B handoff**:
1. All 5 subsystems compile cleanly
2. `EMCMappingHandler.getEMCValue(ItemStack)` works for common items (oak_log, diamond, etc.)
3. Collector GUI can display EMC values without NPE
4. TransmutationContainer can request EMC values without errors

**Testing**: Compile + smoke test in-game (Collector opening, EMC display, no crashes)

---

## Constraints & Notes

- **Keep NeoForge 26.2.0.37-beta** — do NOT upgrade
- **Namespace**: All classes in `com.skd.equivalentlegacy.emc.*`
- **No external APIs yet**: Focus on internal API (public Capability interfaces only)
- **Graph arithmetic**: For Phase 1B, use simple long arithmetic (no BigFraction). Save graph-based resolution for Phase 2.
- **Circular dependency**: Detect (throw error) but don't resolve yet
- **Reference mapping**: Mirror structure from `temp/Equivox-main/.../emc/` but adapt to EL namespace

---

## Deliverables

- 5 git commits (one per subsystem)
- All files compile without errors
- EMCMappingHandler passes basic queries (oak_log=2, diamond=2048)
- Capabilities ready for use by other systems (Collectors, Transmutation, etc.)
- No circular dependencies in hardcoded values
