# Phase 5.1 — Matter Furnace (Dark & Red Matter)

## Objetivo
Port del sistema de Matter Furnace desde Equivox a Equivalent Legacy. Los Matter Furnace son hornos especiales que usan EMC en lugar de combustible tradicional para smeltar items.

## Scope Completo

### 1. Enum y Tipos
**Archivo nuevo**: `com.skd.equivalentlegacy.gameObjs.EnumMatterType`
- Dark Matter: tier 0, efficiency=14, attack=3, charge_modifier=12
- Red Matter: tier 1, efficiency=16, attack=4, charge_modifier=14
- Métodos: getSerializedName(), getChargeModifier(), getSpeed(), getAttackDamageBonus(), getMatterTier()

### 2. Blocks
**Archivos nuevos**:
- `com.skd.equivalentlegacy.gameObjs.blocks.MatterFurnace` — extends AbstractFurnaceBlock + IMatterBlock
  - Dos variantes: DARK_MATTER_FURNACE, RED_MATTER_FURNACE
  - openContainer() abre el menu del player
  - onBlockStateChange() dropa inventario si se destruye
  - Redstone signal output basado en contenido del inventory
  
### 3. Block Entities
**Archivos nuevos**:
- `com.skd.equivalentlegacy.gameObjs.block_entities.DMFurnaceBlockEntity` — extends EmcBlockEntity + MenuProvider
  - Properties: litTime, litDuration, cookingProgress, cookingTotalTime
  - Slots: fuel (usar EMC de Klein Star), input (1 slot principal + 8 de storage), output (9 slots)
  - Capacidades: IItemHandler (entrada, salida, fuel)
  - Tick logic: consume EMC del fuel slot, smelting logic basada en recipes de smelting vanilla
  - Sync a cliente: progress, emc consumption rate
  
- `com.skd.equivalentlegacy.gameObjs.block_entities.RMFurnaceBlockEntity` — extends DMFurnaceBlockEntity
  - Misma lógica pero diferente EMC cost modifier (Red Matter es más eficiente)

### 4. Containers/Menus
**Archivos nuevos**:
- `com.skd.equivalentlegacy.gameObjs.container.DMFurnaceContainer` — extends PEContainer
  - Constructor: windowId, playerInv, furnace BlockEntity
  - initSlots(): 
    - Fuel slot (Klein Star, con validación HAS_EMC)
    - Input slot (1 principal + 8 de storage)
    - Output slots (1 principal + 8 de storage)
  - DataSlots: litTime, litDuration, cookingProgress, cookingTotalTime

- `com.skd.equivalentlegacy.gameObjs.container.RMFurnaceContainer` — extends DMFurnaceContainer (si hay diferencias)

### 5. Screens/GUIs
**Archivos nuevos**:
- `com.skd.equivalentlegacy.client.gui.screen.DMFurnaceScreen` — extends AbstractContainerScreen<DMFurnaceContainer>
  - Texture: assets/equivalent_legacy/textures/gui/dark_matter_furnace_gui.png
  - Progress bar (arrow) mostrando cooking progress
  - EMC consumption rate indicator
  - Labels: "Fuel EMC", "Input", "Output" (localized)

- `com.skd.equivalentlegacy.client.gui.screen.RMFurnaceScreen` — extends DMFurnaceScreen (con texture roja)

### 6. Registration
**Actualizar**:
- `com.skd.equivalentlegacy.gameObjs.registries.BlockRegistry` — agregar DARK_MATTER_FURNACE, RED_MATTER_FURNACE
- `com.skd.equivalentlegacy.gameObjs.registries.BlockEntityRegistry` — agregar DM_FURNACE_BE, RM_FURNACE_BE
- `com.skd.equivalentlegacy.gameObjs.registries.MenuRegistry` — agregar DM_FURNACE_MENU, RM_FURNACE_MENU

### 7. Client Setup
**Actualizar**:
- `com.skd.equivalentlegacy.client.ClientSetup` — registerScreenFactory() para ambos furnaces

### 8. Data Files (JSON)
**Nuevos archivos** en `src/main/resources/data/equivalent_legacy/`:
- `recipe/dark_matter_furnace_crafting.json` — recipe para craftar Dark Matter Furnace (usa Dark Matter Ingots + Furnace vanilla)
- `recipe/red_matter_furnace_crafting.json` — recipe para Red Matter Furnace (usa Red Matter Ingots + Furnace)
- `loot_table/blocks/dark_matter_furnace.json` — loot cuando se destruye
- `loot_table/blocks/red_matter_furnace.json`
- `tag/item/furnace_fuel.json` — pequeño tag que incluya Klein Stars para reconocimiento

### 9. Localization
**Actualizar** todas las 19 lang files con claves:
- `block.equivalent_legacy.dark_matter_furnace`
- `block.equivalent_legacy.red_matter_furnace`
- `gui.equivalent_legacy.dark_matter_furnace` (para el título de GUI)
- `gui.equivalent_legacy.red_matter_furnace`
- `gui.equivalent_legacy.furnace.fuel_emc` (EMC disponible en fuel slot)
- `gui.equivalent_legacy.furnace.burn_time` (tiempo restante)

## Integración con Phase 1 & 4

- **EMC Consumption**: Los furnaces consultan EMCMappingHandler.getEMCValue() para saber el costo de cada smelting
  - Dark Matter: costo normal
  - Red Matter: costo -20% (más eficiente)
- **Klein Star fuel**: Usa IItemEmcHolder capability para extraer EMC del Klein Star en fuel slot
- **Block registration**: Los blocks aparecen en creative mode bajo tab "Equivalent Legacy"

## Arquitectura clave

### EMC Cost Calculation
```
recipe_output EMC value = sum(recipe_input EMC values) + smelting_fuel_cost
Matter Furnace cost = recipe_output * fuel_multiplier (0.8 para Red Matter)
```

### Smelting Logic (tick)
1. Si hay item en input slot y Klein Star en fuel slot:
2. Obtener recipe de SmeltingRecipeType
3. Calcular EMC cost
4. Si furnace tiene suficiente EMC en fuel slot (vía capability):
5. Consumir EMC, incrementar cookingProgress
6. Cuando cookingProgress >= cookingTotalTime: mover item a output, resetear progress

### Redstone Output
- Varía de 0 a 15 según cantidad de items en output slots
- ItemHandlerHelper.calcRedstoneFromInventory() estándar

## Criterios de Éxito

- [ ] Compila sin errores
- [ ] Ambos furnace types (Dark y Red) placeable en mundo
- [ ] Menu abre correctamente
- [ ] Klein Star en fuel slot reconocido
- [ ] Smelting consume EMC correctamente
- [ ] Output slot recibe items smelteados
- [ ] Redstone output funciona
- [ ] Textures + GUIs renderean
- [ ] Localization funciona en 19 idiomas
- [ ] Recipes crafteable
- [ ] Build SUCCESS
- [ ] Commit con phase completada

## Archivos a modificar

**Nuevos (11)**:
1. EnumMatterType.java
2. MatterFurnace.java (block)
3. DMFurnaceBlockEntity.java
4. RMFurnaceBlockEntity.java
5. DMFurnaceContainer.java
6. RMFurnaceContainer.java
7. DMFurnaceScreen.java
8. RMFurnaceScreen.java
9. BlockRegistry.java (actualizar)
10. BlockEntityRegistry.java (actualizar)
11. MenuRegistry.java (actualizar)
12. ClientSetup.java (actualizar)

**Data Files (6 JSON)**:
- 2 recipes (DM + RM furnace crafting)
- 2 loot tables
- 1 tag

**Localization (19 archivos)**:
- +6 claves por idioma

## Estimado
- OpenCode: 1 sesión (todas las clases + data files + localization)
- Después: commit + push + CurseForge (beta.3)
