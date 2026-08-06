# OpenCode Task 1B: EMC Registry Completo para Equivalent Legacy

**Proyecto**: Equivalent Legacy (Minecraft 26.2 / NeoForge 26.2.0.37-beta)  
**Objetivo**: Port del sistema de EMC Registry desde Equivox 26.1.2 a Equivalent Legacy 26.2  
**Prioridad**: 🔴 CRÍTICA (bloqueador para v1.2.0-RELEASE)  
**Duración estimada**: 2 semanas (PARALELA a Tarea 1A)  
**Modelo preferido**: deepseek-v4-flash (ó Nvidia si OpenCode Go se agota)

---

## 📋 RESUMEN

El sistema de EMC es el "corazón" de Equivalent Legacy. Sin él, NADA funciona:
- Collectors no saben EMC de items
- Condensers no pueden convertir
- Furnaces no pueden smeltar
- Transmutation no funciona

**Tarea**: Port de ~30 archivos Java desde Equivox + crear registry de valores EMC para MC 26.2.

**Referencia**: `temp/Equivox-main/Equivox-main/src/main/java/com/yaskulsky/equivox/`  
- API: `src/api/java/com/yaskulsky/equivox/`
- Main: `src/main/java/com/yaskulsky/equivox/gameObjs/emc/` + `src/main/java/com/yaskulsky/equivox/emc/`

---

## 🎯 ARCHITECTURE OVERVIEW

### Niveles de abstracción (de arriba hacia abajo):

```
Level 1: Public API (jugadores/addons usan esto)
├─ EquivoxAPI.getEMCValue(item) → long
├─ EquivoxAPI.startEMCDiscovery(item)
└─ EquivoxAPI.transmute(input, output)

Level 2: User-facing Registries
├─ EMCRegistries (acceso a valores)
├─ KnowledgeProvider (conocimiento de jugador)
└─ Capabilities (EMC storage, charging, etc.)

Level 3: Internal Mapping Engine
├─ EMCMappingHandler (coordinador central)
├─ Various Mappers (recipe-based, special items)
├─ Data Component Processors (enchantments, containers, etc.)
└─ Arithmetic engines (cálculo de valores)

Level 4: Data Loading
├─ Datagen-generated recipes/conversions
├─ Config files (hardcoded values)
└─ Custom conversions (player-added)
```

---

## 🔧 COMPONENTES PRINCIPALES

### 1. **Base Classes** (CREAR NUEVO)

#### A. `FixedValues.java` — Valores hardcodeados
```java
public class FixedValues {
    private final Map<NSS, Long> values = new ConcurrentHashMap<>();
    
    public void addValue(NSS nss, long emc) { ... }
    public long getValue(NSS nss) { ... }
    
    // Init methods (llamar en mod setup):
    // - addBaseValues() — vanilla items (dirt, stone, etc.)
    // - addPhase12() — ProjectE fase 1-2 items
    // - addPhase34() — ProjectE fase 3-4 items
    // - etc.
}
```

#### B. `NormalizedSimpleStack (NSS)` — Item normalization
```java
// Abstracción para comparar items con/sin NBT
- NSSItem — Items normales
- NSSTag — Items con tags
- NSSFluid — Fluids (si aplica)
- NSSFake — Items customizados
```

**Propósito**: Múltiples items pueden tener el mismo EMC (ej: oak wood = spruce wood).

### 2. **Mappers** — Sistemas que CALCULAN EMC

#### A. `IEMCMapper` — Interface base
```java
public interface IEMCMapper {
    void addMappings(IMappingCollector collector);
}
```

#### B. `RecipeTypeMapper` — Calcula EMC desde recipes
```java
Submappers:
- CraftingMapper: Crafting recipes
- SmithingRecipeMapper: Smithing
- BrewingMapper: Potion brewing
- OxidizationMapper: Copper oxidation
- FallbackRecipeTypeMapper: Último recurso
```

**Ejemplo**: 
```
Si diamond = 2 diamonds (crafting → shapeless),
  EMC(diamond) = EMC(2 * diamond)
```

#### C. `SpecialMappers` — Casos especiales
```
- OreBlacklistMapper: Excluir ores (no calcular EMC)
- OreToVanilla: Map modded ores to vanilla (copper, etc.)
- RawMaterialsBlacklistMapper: Excluir raw materials
```

#### D. `DataComponentMapper` — Items con componentes
```
Procesa NBT/DataComponents:
- Enchanted items (summa EMC de enchantments)
- Books (contenido = EMC adicional)
- Containers (contenido = EMC adicional)
- Banners, decorated pots, etc.

Submappers (26 tipos en Equivox):
- EnchantmentProcessor
- PersistentComponentProcessor
- SimpleContainerProcessor
- DamageProcessor
- MapScaleProcessor
- etc.
```

### 3. **Collection Strategy**

#### `IMappingCollector` — Interfaz para recopilar valores
```java
public interface IMappingCollector {
    void map(NSS nss, long emc);
    void setPriority(int priority);
}
```

**Propósito**: Diferentes estrategias para resolver conflictos (ej: 2 recipes para el mismo item).

### 4. **Main Coordinator**

#### `EMCMappingHandler` — El "maestro"
```java
Constructor:
  EMCMappingHandler(IValueArithmetic arithmetic)

Métodos principales:
  - computeAndFinalize() — Calcula EMC para todo
  - getComputedValue(NSS) → long
  - supportsEmcFor(NSS) → boolean
```

**Responsabilidades**:
1. Llamar a todos los mappers
2. Resolver conflictos
3. Cachear resultados
4. Validar circularidades (item → item → item)

---

## 📦 ARCHIVOS A PORTAR

### Del `api/`:

```
✨ api/EquivoxAPI.java (punto de entrada público)
✨ api/EquivoxRegistries.java (registries públicos)
✨ api/ItemInfo.java (información de item)
✨ api/PEDataComponents.java (registro de componentes)
✨ api/PESounds.java (sonidos)

✨ api/block_entity/*.java (interfaces de BE)
✨ api/capabilities/*.java (7+ capabilities)
✨ api/conversion/*.java (conversion groups, custom conversions)
✨ api/data/*.java (builders para datagen)
✨ api/codec/*.java (helpers para codec)
✨ api/components/*.java (data component processors)
✨ api/config/*.java (config interface)
```

### Del `main/java/gameObjs/emc/`:

```
🔄 gameObjs/emc/FixedValues.java (valores hardcoded)
🔄 gameObjs/emc/nss/*.java (NormalizedSimpleStack)
  - NSSItem
  - NSSTag
  - NSSFluid
  - etc.

🔄 gameObjs/emc/EmcBlockEntity.java (BE con EMC storage)
🔄 gameObjs/emc/EmcChestBlockEntity.java (BE con inventario)
```

### Del `main/java/emc/`:

```
🔄 emc/EMCMappingHandler.java (COORDINADOR CENTRAL)
🔄 emc/arithmetic/*.java (IValueArithmetic + impls)
  - FullBigFractionArithmetic (precisión completa)
  - HiddenBigFractionArithmetic (servidor-only)
  
🔄 emc/mapping/*.java (mappers)
  - IEMCMapper
  - RecipeTypeMapper
  - SpecialMappers (ore, raw materials)
  - DataComponentMapper
  
🔄 emc/collector/*.java (estrategias de colección)
  - IMappingCollector
  - MappingCollector
  - LongToBigFractionCollector
  - DumpToFileCollector (debug)
```

### Utilities:

```
✨ emc/EMCHelper.java (acceso rápido a EMC)
✨ emc/Conversion.java (grupo de conversión)
✨ emc/CustomConversion.java (conversión user-defined)
```

---

## 🎯 IMPLEMENTACIÓN PASO A PASO

### Fase 1: Base Framework (Semana 1)

1. **Crear FixedValues.java**
   - Map de NSS → long
   - Métodos init (addBaseValues, addPhase12, etc.)

2. **Crear NormalizedSimpleStack (NSS)**
   - Interfaces + implementaciones
   - Compara items correctamente

3. **Crear IEMCMapper + base implementations**
   - Interfaz
   - RecipeTypeMapper skeleton

4. **Crear IMappingCollector**
   - Collector interface
   - MappingCollector implementation

5. **Crear EMCMappingHandler**
   - Main coordinator
   - Llamar mappers en orden
   - Cachear resultados

### Fase 2: Specialized Mappers (Semana 1-2)

1. **RecipeTypeMapper completamente**
   - Crafting, Smithing, Brewing, etc.
   - Detectar y resol ver circularidades

2. **DataComponentMapper**
   - Procesar enchantments
   - Procesar containers
   - Procesar books

3. **SpecialMappers**
   - Ore exclusions
   - Raw materials

### Fase 3: Capabilities & API (Semana 2)

1. **Portar Capabilities**
   - IEmcStorage (emit/extract EMC)
   - IKnowledgeProvider (learning)
   - IAlchBagProvider (bags)
   - IItemEmcHolder (items that hold EMC)
   - IModeChanger (ring modes)
   - etc.

2. **Crear EquivoxAPI**
   - getEMCValue(item)
   - discoverEMC(item)
   - Métodos públicos

### Fase 4: Integration & Testing (Semana 2)

1. **Hook into Equivalent Legacy**
   - Registrar mappers en mod load
   - Inicializar FixedValues
   - Crear collectors

2. **Test**
   - EMC values for common items
   - Transmutation can happen
   - No circular dependencies

---

## 📊 DATOS REQUERIDOS: FixedValues

### Base Vanilla Items (~150 items)
```
Tier 0 (value = 1):
- Dirt, Gravel, Sand, Netherrack

Tier 1 (value = 2-8):
- Logs, Planks, Sticks, Leaves, Wool

Tier 2 (value = 8-64):
- Stone, Cobblestone, Wood variants
- Dyes, Flowers

Tier 3 (value = 64-256):
- Iron ore (if available), Coal ore
- Common gems (Emerald region)

Tier 4 (value = 256+):
- Diamond-like items
- Obsidian, Netherite

Tier 5+ (ProjectE additions):
- Dark Matter, Red Matter
- Klein Star tiers
```

### ProjectE Items
- Philosopher's Stone: 139,264 EMC
- Klein Star Ein: 1,000,000 EMC
- Dark Matter: 500,000 EMC
- Red Matter: 1,000,000 EMC
- (Copiar valores de Equivox)

### Equivalent Legacy Specific
- Collectors, Furnaces, Relays: suma de componentes
- Custom items: valores balanceados

---

## 🔄 SINCRONIZACIÓN CON EL

Equivalent Legacy ya tiene:
- `EquivalentLegacyItems` — Registro de items
- `EquivalentLegacyBlocks` — Registro de bloques
- `EMCStorage` capability (parcial)
- `KnowledgeProvider` (parcial)

**Tarea**: Integrar sin romper existente.

---

## ✅ PRUEBAS REQUERIDAS

```
[ ] getEMCValue(oak_log) = 2
[ ] getEMCValue(diamond) = 2048
[ ] getEMCValue(philosopher_stone) = 139264
[ ] Transmutation de diamond → 2x diamond works
[ ] Collector almacena EMC correctamente
[ ] Furnace consume EMC para smeltar
[ ] Klein Star se carga con EMC
[ ] No circular dependencies (item A → B → A)
[ ] DataComponents (enchantments, books, containers) se suman correctamente
[ ] Custom conversions funcionan (via datagen)
```

---

## 🚀 INICIO

Mensaje para OpenCode:

> Port EMC Registry system from Equivox 26.1.2 to Equivalent Legacy 26.2.
> Includes: NormalizedSimpleStack, all Mappers, EMC values for 200+ items, Capabilities.
> Start with FixedValues + NSS + EMCMappingHandler, then add specialized mappers.
> Reference: temp/Equivox-main source. Target NeoForge 26.2 API.
> Build must compile + test: diamond = 2048 EMC, transmutation works.

---

**Contacto durante desarrollo**: Mensaje si hay ambigüedad en valores EMC o mapper logic.
