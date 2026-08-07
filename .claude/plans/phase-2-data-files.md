# Plan: Phase 2 - Data Files & Localization

**Proyecto**: Equivalent Legacy 26.2 / NeoForge 26.2.0.37-beta  
**Objetivo**: Complete data files (recipes, loot tables, tags, advancement) + localization (19 languages)  
**Scope**: ~100+ JSON files + 19 lang files  
**Prioridad**: Media (content-heavy, not blocking gameplay but needed for completeness)

---

## Current State Analysis

**Data Files (in src/main/resources/data/)**:
- ❌ No recipes
- ❌ No loot tables
- ❌ No tags
- ❌ No advancements
- ❌ No world generation

**Localization (in src/main/resources/assets/equivalent_legacy/lang/)**:
- ❌ No en_us.json (English)
- ❌ No other 18 language files

---

## Work Breakdown (3 Subsystems)

### 1. **Data Files Generation** (Subsystem A)
**What it does**:
- Generate JSON files for Minecraft data loaders
- Include: recipes, loot tables, tags, advancements, block definitions

**Directory structure**:
```
src/main/resources/data/
├── equivalent_legacy/
│   ├── recipes/
│   │   ├── collector/
│   │   ├── condenser/
│   │   ├── transmutation/
│   │   └── furnace/
│   ├── loot_tables/
│   │   └── blocks/
│   ├── tags/
│   │   ├── blocks/
│   │   └── items/
│   ├── advancements/
│   │   └── root.json
│   └── worldgen/
└── minecraft/
    └── tags/
        └── blocks/
```

**Key files**:
1. **Recipes** (equivalent_legacy/recipes/):
   - Collector MK1/MK2/MK3 crafting
   - Condenser crafting
   - Relay crafting
   - Matter Furnace crafting
   - Transmutation Table crafting
   - Alchemical Bag crafting
   - Alchemical Chest crafting
   - Klein Star variants
   - Upgrade items

2. **Loot Tables** (equivalent_legacy/loot_tables/blocks/):
   - Collector drops
   - Condenser drops
   - Relay drops
   - Chest drops
   - Furnace drops

3. **Tags** (equivalent_legacy/tags/):
   - Item tags: emc_containers, transmutable_items, etc.
   - Block tags: emc_machines, storage_blocks, etc.

4. **Advancements** (equivalent_classic/advancements/):
   - Root advancement
   - Milestone achievements (craft first collector, transmute first item, etc.)

5. **World Generation** (equivalent_legacy/worldgen/):
   - Custom world features (if any: ore generation tweaks, etc.)

**Reference**: `temp/Equivox-main/.../src/datagen/generated/data/`

**Definition of Done**:
- [ ] Recipes compile without errors
- [ ] Loot tables parse correctly
- [ ] All tags reference existing items/blocks
- [ ] Advancements tree is valid
- [ ] No conflicting registrations

---

### 2. **Localization/Language Files** (Subsystem B)
**What it does**:
- Create `en_us.json` (English) as base
- Generate/translate 18 additional language files

**Languages (19 total)**:
1. en_us (English US)
2. es_es (Spanish)
3. fr_fr (French)
4. de_de (German)
5. it_it (Italian)
6. pt_br (Portuguese Brazil)
7. pt_pt (Portuguese Portugal)
8. ru_ru (Russian)
9. ja_jp (Japanese)
10. zh_cn (Chinese Simplified)
11. zh_tw (Chinese Traditional)
12. ko_kr (Korean)
13. pl_pl (Polish)
14. nl_nl (Dutch)
15. sv_se (Swedish)
16. no_no (Norwegian)
17. fi_fi (Finnish)
18. da_dk (Danish)
19. tr_tr (Turkish)

**Keys to translate** (in en_us.json):
- Item names: 50+ items (collectors, relays, klein stars, upgrades, etc.)
- Block names: 10+ blocks (collectors, condenser, relay, furnace, chests, etc.)
- Container names: "gui.equivalent_legacy.collector", etc.
- Tooltips: "tooltip.equivalent_legacy.klein_star_mk1", etc.
- Chat messages: EMC notifications, transmutation feedback, etc.
- Advancement names & descriptions: 10+ advancements

**Directory structure**:
```
src/main/resources/assets/equivalent_legacy/lang/
├── en_us.json
├── es_es.json
├── fr_fr.json
├── de_de.json
├── it_it.json
├── pt_br.json
├── pt_pt.json
├── ru_ru.json
├── ja_jp.json
├── zh_cn.json
├── zh_tw.json
├── ko_kr.json
├── pl_pl.json
├── nl_nl.json
├── sv_se.json
├── no_no.json
├── fi_fi.json
├── da_dk.json
└── tr_tr.json
```

**Translation approach**:
- **en_us.json**: Hand-written (source language)
- **Other 18 files**: Can be:
  - Machine-translated (via Graphify backend or similar)
  - Partially translated (high-priority strings only)
  - Placeholder (same as en_us, marked for translation later)

**Key translations to include**:
```json
{
  "block.equivalent_legacy.collector_mk1": "Collector MK1",
  "block.equivalent_legacy.condenser_mk1": "Condenser MK1",
  "block.equivalent_legacy.relay_mk1": "Relay MK1",
  "item.equivalent_legacy.klein_star_mk1": "Klein Star (MK1)",
  "item.equivalent_legacy.transmutation_stone": "Transmutation Stone",
  "gui.equivalent_legacy.collector": "Collector",
  "container.equivalent_legacy.collector": "Collector",
  "tooltip.equivalent_legacy.emc_storage": "EMC Storage: %s / %s",
  "advancement.equivalent_legacy.root": "Equivalent Legacy",
  "advancement.equivalent_legacy.first_collector": "First Collector"
}
```

**Definition of Done**:
- [ ] en_us.json complete with 100+ keys
- [ ] All 19 language files exist (even if identical to en_us.json)
- [ ] No missing keys across languages
- [ ] All JSON parses correctly
- [ ] No encoding errors (UTF-8)

---

### 3. **Item Registry & Block Definitions** (Subsystem C)
**What it does**:
- Create item/block registration for all Equivalent Legacy content
- Integrate with existing EMC Registry (Phase 1B)

**Items to define**:
- Klein Stars (MK1, MK2, MK3, MK4, MK5, MK6)
- Transmutation Stones
- Upgrade items (various)
- Fuel items
- Components

**Blocks to define**:
- Collector (MK1, MK2, MK3)
- Condenser (MK1, MK2, MK3)
- Relay (MK1, MK2, MK3)
- Matter Furnace (DM, RM variants)
- Transmutation Table
- Alchemical Chest
- Storage blocks

**Files to create/update**:
- `src/main/java/com/skd/equivalentlegacy/item/EquivalentLegacyItems.java` — item registry
- `src/main/java/com/skd/equivalentlegacy/block/EquivalentLegacyBlocks.java` — block registry
- `src/main/java/com/skd/equivalentlegacy/item/KleinStar.java` — Klein Star logic
- Item/Block classes for variants (MK1, MK2, etc.)

**Integration with Phase 1B**:
- Register items with EMC values in `EmcValues` registry
- Connect block entities to EMC system
- Ensure capabilities are attached (IEmcStorage, etc.)

**Definition of Done**:
- [ ] All items register without conflicts
- [ ] All blocks register without conflicts
- [ ] EMC values assigned to all items
- [ ] No missing model/texture references
- [ ] Compile without errors

---

## Integration Checkpoints

**Before Phase 2 handoff**:
1. All data files parse correctly (validate with Minecraft JSON schema)
2. All language files exist (even if partial translations)
3. All items/blocks compile and register
4. Build is clean: `./gradlew.bat build` — 0 errors
5. No missing block/item models or textures

**Testing**: In-game smoke test
- [ ] Crafting recipes work
- [ ] Items display correct names (all languages)
- [ ] Blocks place correctly
- [ ] Block entities sync EMC

---

## Known Limitations (Phase 2)

- **No world generation tuning** — default Minecraft spawn behavior
- **No custom loot tables** — basic vanilla drops only
- **Partial translations** — English primary, others placeholder
- **No advancement rewards** — just unlocks, no item grants

---

## Deliverables

- 3 git commits (one per subsystem)
- 100+ JSON files (recipes, loot, tags, advancements)
- 19 language files (en_us + 18 others)
- Item/block registry complete
- All files compile without errors
- No missing asset references

---

## Next Steps (After Phase 2)

- Phase 3: Missing items integration + GUI polish
- Phase 4: Testing + v1.2.0-RELEASE hotfixes
- Phase 5: Data-driven configuration (custom conversions, balance tweaks)
