# Phase 5.2 — Alchemical Chests (Storage & EMC Sync)

## Objetivo
Port del sistema de Alchemical Chests desde Equivox a Equivalent Legacy. Los Alchemical Chests son contenedores de almacenamiento masivo (104 slots) que pueden sincronizar EMC entre chests conectados y soportar items especiales con lógica personalizada.

## Scope Completo

### 1. Block Entity Base
**Archivos a revisar/adaptar**:
- Equivox tiene `EmcChestBlockEntity` (base) y `AlchBlockEntityChest` (alchemical variant)
- En Equivalent Legacy, revisar si existe base para chests EMC, o crear desde cero

**New**: `com.skd.equivalentlegacy.block.entity.AlchemicalChestBlockEntity`
- Extends BaseMachineBlockEntity o EmcBlockEntity
- 104 slots (8x13 grid)
- EMC sync capability (IEmcStorage)
- Lid animation state (openness)
- Client tick: animate lid
- Server tick: handle inventory changes, EMC sync between connected chests

### 2. Block
**New**: `com.skd.equivalentlegacy.block.AlchemicalChestBlock`
- Extends Block + EntityBlock
- FACING property (direction where player opens)
- WATERLOGGED support (for placement in water)
- VoxelShape (1-15 on X/Z, 0-14 on Y)
- Custom collision/render shapes
- Lid animation rendering

### 3. Container/Menu
**New**: `com.skd.equivalentlegacy.gui.AlchemicalChestContainer`
- Extends PEContainer or AbstractContainerMenu
- 104 chest slots (8 rows x 13 columns) + player inventory
- Layout: chest grid at top, player inv at bottom
- Sync openness state for lid animation
- No special validation (can hold any items)

### 4. Screen/GUI
**New**: `com.skd.equivalentlegacy.client.gui.screen.AlchemicalChestScreen`
- Extends AbstractContainerScreen<AlchemicalChestContainer>
- Texture: assets/equivalent_legacy/textures/gui/alchemical_chest_gui.png
- 8x13 slot grid + player inventory display
- Optional: sort/organization buttons (Phase 6)
- Localized title: "Alchemical Chest"

### 5. EMC Sync Network (Optional for Phase 5.2)
**Deferred to Phase 6** unless simple:
- Detection of adjacent Alchemical Chests
- Synchronized EMC pool (shared EMC storage between connected chests)
- Single "master" chest UI shows network status
- For Phase 5.2: basic structure, Phase 6 adds active sync logic

### 6. Registration
**Update**:
- `com.skd.equivalentlegacy.block.EquivalentLegacyBlocks` — add ALCHEMICAL_CHEST
- `com.skd.equivalentlegacy.block.entity.EquivalentLegacyBlockEntities` — add ALCHEMICAL_CHEST
- `com.skd.equivalentlegacy.gui.ModMenuTypes` — add ALCHEMICAL_CHEST_MENU

### 7. Client Setup
**Update**:
- `com.skd.equivalentlegacy.EquivalentLegacyClient` — registerScreenFactory for AlchemicalChestScreen

### 8. Data Files (JSON)
**New in `src/main/resources/data/equivalent_legacy/`**:
- `recipe/alchemical_chest_crafting.json` — recipe (Dark Matter + Chest vanilla + other materials)
- `loot_table/blocks/alchemical_chest.json` — drops when destroyed
- `blockstate/alchemical_chest.json` — facing + waterlogged states
- `model/block/alchemical_chest.json` — block model (simple box like vanilla chest)

### 9. Localization
**Update** all 19 lang files with:
- `block.equivalent_legacy.alchemical_chest`
- `container.equivalent_legacy.alchemical_chest`
- `gui.equivalent_legacy.alchemical_chest` (title)
- Optional future keys: `gui.equivalent_legacy.chest.sort`, `gui.equivalent_legacy.chest.network_status`

## Integration Points

### EMC Storage
- Chest extends or has IEmcStorage capability
- Can store up to 10,000,000 EMC (configurable)
- Other machines (collectors, condensers) can query/sync EMC

### Pedestal Integration
- Red Matter Pedestal above chest can auto-extract items and convert to EMC (Phase 4 logic)
- Dark Matter Pedestal can manage chest inventory

### Future (Phase 6)
- Network sync between multiple chests
- Sorting/organization UI
- Filter slots (whitelist/blacklist items)
- Auto-condensing (convert stored items to EMC)

## Criteria de Éxito

- [ ] Compila sin errores
- [ ] Alchemical Chest placeable en mundo
- [ ] Menu abre correctamente con 104 slots
- [ ] Screen renders correctly con grid de slots
- [ ] Items almacenable en todos los 104 slots
- [ ] Lid animation funciona (open/close visual)
- [ ] WATERLOGGED support funciona (placeable en agua)
- [ ] FACING property respeta dirección
- [ ] Recipe crafteable
- [ ] Loot table funciona al destruir
- [ ] Localization en 19 idiomas
- [ ] Build SUCCESS
- [ ] Commit con phase completada

## Archivos a crear/modificar

**Nuevos (6)**:
1. AlchemicalChestBlockEntity.java
2. AlchemicalChestBlock.java
3. AlchemicalChestContainer.java
4. AlchemicalChestScreen.java
5. 4 JSON data files (recipe, loot, blockstate, model)

**Modificar (3)**:
- EquivalentLegacyBlocks.java
- EquivalentLegacyBlockEntities.java
- ModMenuTypes.java
- EquivalentLegacyClient.java

**Localization (19 archivos)**:
- +4 claves por idioma

## Notas Arquitectura

### Diferencias vs Equivox
- Equivox usa `BlockDirection` (custom base); usar vanilla `Block` + state property FACING
- Equivox usa `StackHandler` (custom item handler); usar vanilla `ItemStackHandler` o `SimpleContainer`
- Equivalent Legacy ya tiene `PEContainer`; usar ese como base si es posible

### Simpl ificaciones para 5.2
- **No EMC sync network** (solo single-chest storage, no multi-chest linking)
- **No IAlchChestItem capability** (special item behavior deferred)
- **No sorting UI** (simple inventory grid, player sorts manually)
- **Basic animation** (lid opening/closing, no fancy smooth animation)

## Estimado
- OpenCode: 1 sesión (todas las clases + data files)
- Después: commit + push + CurseForge (beta.4)

## Post-Phase 5.2
- Phase 6: Advanced storage features (network sync, filters, auto-condensing)
- Phase 7: Testing & stabilization → v1.3.0-RELEASE
