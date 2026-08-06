# Plan: Fase 2.4 - JEI/WTHIT Integration (Equivalent Legacy)

**Objetivo**: Integrar JEI (Just Enough Items) para mostrar recetas de transmutación y WTHIT/Jade para visualizar valores EMC en hover sobre bloques transmutables.

**Versión**: 1.2.0-beta.6

---

## 1. Dependencias en build.gradle

**Archivo**: `build.gradle`

**Agregar**:

```gradle
// JEI Integration (optional mod dependency)
compileOnly "mezz.jei:jei-${mc_version}-common-api:${jei_version}"
compileOnly "mezz.jei:jei-${mc_version}-neoforge-api:${jei_version}"
localRuntime "mezz.jei:jei-${mc_version}-neoforge:${jei_version}"

// WTHIT/Jade Data Provider (optional mod dependency)
compileOnly "mcp.mobius.waila:wthit-api:${wthit_version}"
localRuntime "mcp.mobius.waila:wthit:${wthit_version}"
```

**Agregar en gradle.properties**:

```properties
# Version variables
jei_version = 19.14.0  # Stable JEI for 26.2
wthit_version = 11.7.1  # Compatible WTHIT version
```

**Nota**: Usar `compileOnly` + `localRuntime` para que JEI/WTHIT sean dependencias opcionales (mod puede funcionar sin ellas).

---

## 2. JEI Integration

### 2.1 TransmutationRecipeDisplay (NUEVO)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/integration/jei/TransmutationRecipeDisplay.java`

**Propósito**: Representar una receta de transmutación para JEI

**Implementar**:
```java
public record TransmutationRecipeDisplay(
    ItemStack inputBlock,
    ItemStack outputBlock,
    long emcCost,
    @Nullable ItemStack philStone
) implements IRecipeDisplay {
    
    @Override
    public List<Ingredient> getInputs() {
        return List.of(Ingredient.of(inputBlock));
    }
    
    @Override
    public List<ItemStack> getOutputs() {
        return List.of(outputBlock);
    }
}
```

**Responsabilidades**:
- Almacenar entrada (bloque a transmutarse), salida (bloque destino), costo EMC
- Implementar `IRecipeDisplay` de JEI
- Renderizar: `bloque A [cost: 1024 EMC] → bloque B`

---

### 2.2 TransmutationRecipeCategory (NUEVO)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/integration/jei/TransmutationRecipeCategory.java`

**Propósito**: Definir cómo se renderiza la categoría de recetas de transmutación en JEI

**Implementar**:
```java
@OnlyIn(Dist.CLIENT)
public class TransmutationRecipeCategory implements IRecipeCategory<TransmutationRecipeDisplay> {
    
    private final IDrawable background;
    private final IDrawable icon;
    
    public TransmutationRecipeCategory(IGuiHelper guiHelper) {
        // Background: simple 2-slot layout
        // Icon: Philosopher's Stone
    }
    
    @Override
    public RecipeType<TransmutationRecipeDisplay> getRecipeType() {
        return EquivalentLegacyJei.TRANSMUTATION_TYPE;
    }
    
    @Override
    public void draw(TransmutationRecipeDisplay recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // Draw input block (left)
        // Draw arrow (center)
        // Draw cost EMC (center-bottom)
        // Draw output block (right)
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TransmutationRecipeDisplay recipe, IFocusGroup focuses) {
        // Registrar slots: input, output, y mostrar EMC cost
    }
}
```

**Responsabilidades**:
- Definir tamaño de la receta (típicamente 150x60 px)
- Renderizar inputs (bloque A), outputs (bloque B), y costo EMC
- Manejar tooltips en hover
- Arrow animation (opcional, por animación se puede usar frame por frame)

**Propiedades**:
- Background: textura simple con layout 2 slots + texto de costo
- Arrow: texture de flecha (puede ser vanilla)
- Tamaño de fuente: 8pt para costo EMC (pequeño pero legible)

---

### 2.3 EquivalentLegacyJeiPlugin (NUEVO)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/integration/jei/EquivalentLegacyJeiPlugin.java`

**Propósito**: Punto de entrada para la integración JEI

**Implementar**:
```java
@JeiPlugin
public class EquivalentLegacyJeiPlugin implements IModPlugin {
    
    public static final ResourceLocation ID = new ResourceLocation(EquivalentLegacy.MODID, "transmutation");
    public static final RecipeType<TransmutationRecipeDisplay> TRANSMUTATION_TYPE = 
        RecipeType.create(EquivalentLegacy.MODID, "transmutation", TransmutationRecipeDisplay.class);
    
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new TransmutationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }
    
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // Leer todas las recetas de transmutación desde WorldTransmutationManager
        List<TransmutationRecipeDisplay> recipes = loadTransmutationRecipes();
        registration.addRecipes(TRANSMUTATION_TYPE, recipes);
    }
    
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        // (Optional) Registrar handlers para GUIs del mod
    }
}
```

**Responsabilidades**:
- Registrar categoría `TransmutationRecipeCategory`
- Cargar todas las recetas de transmutación desde `WorldTransmutationManager`
- Registrar en JEI como tipo `transmutation`

**Carga de recetas**:
```java
private static List<TransmutationRecipeDisplay> loadTransmutationRecipes() {
    List<TransmutationRecipeDisplay> recipes = new ArrayList<>();
    for (var entry : WorldTransmutationManager.getTransmutationMap().entrySet()) {
        Block inputBlock = entry.getKey();
        TransmutationResult result = entry.getValue();
        recipes.add(new TransmutationRecipeDisplay(
            new ItemStack(inputBlock),
            new ItemStack(result.getResultBlock()),
            result.getEmcCost(),
            new ItemStack(EquivalentLegacyItems.PHILOSOPHERS_STONE.get())
        ));
    }
    return recipes;
}
```

---

## 3. WTHIT/Jade Integration

### 3.1 TransmutationDataProvider (NUEVO)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/integration/wthit/TransmutationDataProvider.java`

**Propósito**: Mostrar información EMC en hover (WTHIT/Jade)

**Implementar**:
```java
public class TransmutationDataProvider implements IDataProvider {
    
    @Override
    public void appendServerData(CompoundTag tag) {
        // Server-side: no hacer nada especial
    }
    
    @Override
    public void appendClientData(CompoundTag tag, IBlockAccessor block) {
        // Client-side: verificar si el bloque es transmutable
        Block currentBlock = block.getBlock();
        if (!WorldTransmutationManager.isTransmutable(currentBlock)) {
            return;
        }
        
        TransmutationResult result = WorldTransmutationManager.getTransmutation(currentBlock);
        if (result == null) return;
        
        // Agregar datos al tooltip
        tag.putString("transmutation_source", currentBlock.getName().getString());
        tag.putString("transmutation_target", result.getResultBlock().getName().getString());
        tag.putLong("transmutation_cost", result.getEmcCost());
    }
    
    @Override
    public void displayTooltip(CompoundTag tag, TooltipEvent event) {
        if (!tag.contains("transmutation_source")) return;
        
        String source = tag.getString("transmutation_source");
        String target = tag.getString("transmutation_target");
        long cost = tag.getLong("transmutation_cost");
        
        event.getTooltip().add(Component.literal("§6Transmutable:§r " + target));
        event.getTooltip().add(Component.literal("§cCost: " + cost + " EMC"));
    }
}
```

**Responsabilidades**:
- Detectar bloques transmutables en hover
- Agregar información de destino + costo EMC al tooltip de WTHIT
- Formato: `[Transmutable: Block] [Cost: 1024 EMC]`

---

### 3.2 Registro de DataProvider (EN EXISTING FILE)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/integration/wthit/EquivalentLegacyWthitPlugin.java` (NUEVO)

**Propósito**: Registrar el data provider en WTHIT

**Implementar**:
```java
public class EquivalentLegacyWthitPlugin implements IWthitPlugin {
    
    @Override
    public void register(IRegistrar registrar) {
        registrar.addBlockDataProvider(new TransmutationDataProvider());
    }
}
```

**Alternativa (si WTHIT no tiene IWthitPlugin)**:
```java
@Mod.EventBusSubscriber(modid = EquivalentLegacy.MODID, value = Dist.CLIENT)
public class WthitClientEvents {
    
    @SubscribeEvent
    static void onDataProviderRegistry(RegisterDataProvidersEvent event) {
        event.register(new TransmutationDataProvider());
    }
}
```

---

## 4. Estructura de Carpetas

```
src/main/java/com/skd/equivalentlegacy/
├── integration/
│   ├── jei/
│   │   ├── EquivalentLegacyJeiPlugin.java     (NEW)
│   │   ├── TransmutationRecipeCategory.java   (NEW)
│   │   └── TransmutationRecipeDisplay.java    (NEW)
│   └── wthit/
│       ├── EquivalentLegacyWthitPlugin.java   (NEW)
│       └── TransmutationDataProvider.java     (NEW)
```

---

## 5. Configuration y Assets

### 5.1 JEI Textures (Opcional, si se quiere customizar)

**Ubicación**: `src/main/resources/assets/equivalent_legacy/textures/gui/jei/`

**Archivos**:
- `transmutation_bg.png` — Background para categoría (150x60 px)
- `arrow.png` — Flecha de transmutación (20x10 px)

(Si no se crean assets custom, usar las texturas vanilla de JEI)

### 5.2 Idiomas

**Archivos existentes**: `src/main/resources/assets/equivalent_legacy/lang/`

**Agregar líneas a cada archivo .json**:
```json
"jei.equivalent_legacy.transmutation": "Transmutation",
"jei.equivalent_legacy.transmutation.cost": "Cost: %d EMC",
"tooltip.equivalent_legacy.transmutable": "§6Transmutable"
```

---

## 6. Integración con WorldTransmutationManager

### 6.1 Métodos Helper Necesarios

**Agregar a WorldTransmutationManager**:

```java
// Método para checkear si un bloque es transmutable
public static boolean isTransmutable(Block block) {
    return TRANSMUTATION_MAP.containsKey(block);
}

// Método para obtener la receta
public static TransmutationResult getTransmutation(Block block) {
    return TRANSMUTATION_MAP.get(block);
}

// Método para obtener todas las recetas (para JEI)
public static Map<Block, TransmutationResult> getTransmutationMap() {
    return Map.copyOf(TRANSMUTATION_MAP);
}
```

---

## 7. Comportamiento Esperado

### JEI
- Al abrir JEI, aparece nueva categoría "Transmutation"
- Hacer click en un bloque de piedra caliza → se muestra receta: `Limestone → Marble (1024 EMC)`
- Puede hacer recipe lookup inverso (click derecho en resultado)
- Mostrar costo EMC prominentemente en cada receta

### WTHIT
- Hacer hover sobre un bloque transmutable (ej. Limestone)
- Tooltip aparece con: `[Transmutable: Marble] [Cost: 1024 EMC]`
- En rojo si costo es muy alto, en verde si es asequible
- No rompe con otros tooltips de WTHIT (compatible)

---

## 8. Versioning y CurseForge

**Versión**: `1.2.0-beta.6`

**gradle.properties**:
```properties
mod_version = 1.2.0-beta.6
```

**CHANGELOG.md**:
- Agregar entrada para Fase 2.4
- Listar: JEI category, WTHIT/Jade support, transmutation recipes accessible

**CurseForge release notes** (`docs/curseforge/versions/1.2.0-beta.6.md`):
```markdown
## v1.2.0-beta.6 - JEI/WTHIT Integration Complete

### Added
- **JEI Integration**: New Transmutation category showing all block conversions and EMC costs
- **WTHIT/Jade Support**: Hover over transmutable blocks to see target and cost
- **Recipe Lookup**: Use JEI search to find transmutation recipes

### Technical Details
- RecipeType: `equivalent_legacy:transmutation`
- Data provider integration with WTHIT data display
- All 50+ transmutation recipes from Phase 2 accessible in JEI
```

---

## 9. Definition of Done

- [ ] Agregar dependencias JEI + WTHIT en build.gradle
- [ ] Crear `TransmutationRecipeDisplay` (record implementando IRecipeDisplay)
- [ ] Crear `TransmutationRecipeCategory` con renderización correcta
- [ ] Crear `EquivalentLegacyJeiPlugin` con carga de recetas
- [ ] Crear `TransmutationDataProvider` para WTHIT/Jade
- [ ] Crear `EquivalentLegacyWthitPlugin` con registro
- [ ] Agregar métodos helper en `WorldTransmutationManager` (isTransmutable, getTransmutation, getTransmutationMap)
- [ ] Agregar líneas de idioma en lang/*.json (JEI category name, tooltip labels)
- [ ] Compilación clean (sin errores/warnings)
- [ ] Probar en modpack:
  - [ ] JEI shows transmutation category
  - [ ] All 50+ recipes visible
  - [ ] Hovering over blocks shows EMC cost
  - [ ] Recipe lookup works (click to search)
  - [ ] No crashes with other mods
- [ ] gradle.properties: mod_version = 1.2.0-beta.6
- [ ] CHANGELOG.md actualizado
- [ ] CurseForge release notes creadas
- [ ] Commit message: `feat: add JEI/WTHIT integration (Phase 2.4, v1.2.0-beta.6)`

---

## 10. Restricciones Técnicas

### NeoForge 26.2
- JEI API 19.14+ (compatible con 26.2)
- WTHIT 11.7+ (compatible con 26.2)
- Usar `ResourceLocation` (no `ResourceKey` para JEI recipes)
- `IRecipeDisplay` interface de JEI

### Compatibility
- JEI y WTHIT son opcionales (mod funciona sin ellas)
- Data provider no debe crashear si WTHIT no está instalado
- Usar `@SubscribeEvent` con `@Mod.EventBusSubscriber` para inicialización segura

### Performance
- Cargar recetas en startup (una sola vez)
- No iterar `WorldTransmutationManager.getTransmutationMap()` cada frame
- Data provider debe ser eficiente (no raycasts costosos)

---

## 11. Notas de Implementación

1. **JEI es prioritario**: Mostrar recetas en JEI es más visible que WTHIT
2. **WTHIT es bonus**: Nice-to-have, proporciona contexto en el mundo
3. **Compatibilidad**: Ambas dependencias son opcionales; mod funciona sin ellas
4. **Lenguaje**: Usar `Component` de Minecraft, no strings raw
5. **Resourcelocation**: Usar `new ResourceLocation(MODID, "path")` para IDs
6. **Testing**: Verificar en modpack con JEI + WTHIT instalados y desinstalados

---

## 12. Próximas Fases

- **Fase 3**: GUI improvements (custom screen para transmutación)
- **Fase 4**: Mob farming system
- **Fase 5**: Dungeon integration

