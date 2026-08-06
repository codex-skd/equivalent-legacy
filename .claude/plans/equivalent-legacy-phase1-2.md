# Plan: Completar Port de Equivox → Equivalent Legacy (Fase 1 + 2)

**Proyecto**: Equivalent Legacy (NeoForge 26.2)  
**Referencia**: equivox-1.0.0.jar (691 clases compiladas)  
**Estado actual**: 0.0.0-beta.1 (99 archivos .java, ~40-50% features)  
**Meta**: Feature parity con Equivox en 2 fases (beta.2 + beta.3)

---

## FASE 1: ITEMS + BLOQUES + HERRAMIENTAS → beta.2

### Objetivo
Completar todos los items, herramientas, armor y bloques que faltan, estableciendo base sólida para feature parity con Equivox.

### Archivos a Modificar / Crear

#### Items (Nuevos)

**Stones & Rings** — `src/main/java/com/skd/equivalentlegacy/item/`:
- `PhilosophersStone.java` — Piedra filósofo con modos: Normal, Transmutation, Tome, Collector, Condenser, Relay (reemplazar la clase existente si es incompleta)
- `BlackHoleBand.java` — Banda de agujero negro
- `BodyStone.java`, `LifeStone.java`, `MindStone.java`, `SoulStone.java` — Arcanas (4 piedras)
- `VoidRing.java` — Anillo del vacío
- `TransmutationStone.java` — Piedra de transmutación alternativa
- `CataliticLens.java` — Lente catalizadora

**Dark Matter Tools** — `src/main/java/com/skd/equivalentlegacy/item/`:
- `DarkMatterAxe.java`
- `DarkMatterSword.java` (si no existe completo)
- `DarkMatterShovel.java`
- `DarkMatterHoe.java`

**Red Matter Tools** — `src/main/java/com/skd/equivalentlegacy/item/`:
- `RedMatterPickaxe.java` (si no existe)
- `RedMatterAxe.java`
- `RedMatterShovel.java`
- `RedMatterHoe.java`

**Special Tools with AOE** — `src/main/java/com/skd/equivalentlegacy/item/`:
- `PEHammer.java` — Martillo con AOE
- `PEAxe.java` — Hacha con AOE mejorado
- `PEPickaxe.java` — Pico con AOE mejorado + modo
- `PESaw.java` — Sierra

**Armor Sets** — `src/main/java/com/skd/equivalentlegacy/item/`:
- `DarkMatterHelmet.java`, `DarkMatterChestplate.java`, `DarkMatterLeggings.java`, `DarkMatterBoots.java`
- `RedMatterHelmet.java`, `RedMatterChestplate.java`, `RedMatterLeggings.java`, `RedMatterBoots.java`

**Registry Updates**:
- `EquivalentLegacyItems.java` — Registrar todos los nuevos items, armor en DeferredRegisters

#### Bloques (Nuevos)

**Pedestals** — `src/main/java/com/skd/equivalentlegacy/block/`:
- `Pedestal.java` — Bloque pedestal base
- `DMPedestal.java` — Pedestal de dark matter
- `RMPedestal.java` — Pedestal de red matter

**Catalysts** — `src/main/java/com/skd/equivalentlegacy/block/`:
- `DestructionCatalyst.java` — Catalizador de destrucción

**Furnaces** — `src/main/java/com/skd/equivalentlegacy/block/`:
- `DMFurnace.java` — Furnace de dark matter
- `RMFurnace.java` — Furnace de red matter

**Block Entities** — `src/main/java/com/skd/equivalentlegacy/block/entity/`:
- `PedestalBlockEntity.java` — Entidad de bloque para pedestal
- `DMPedestalBlockEntity.java`
- `RMPedestalBlockEntity.java`
- `DestructionCatalystBlockEntity.java`
- `DMFurnaceBlockEntity.java`
- `RMFurnaceBlockEntity.java`

**Entities** — `src/main/java/com/skd/equivalentlegacy/entity/`:
- `EntityNovaCataclysmPrimed.java` — Entidad de nova cataclysm
- `EntityNovaCatalystPrimed.java` — Entidad de nova catalyst
- `EntityNovaPrimed.java` — Entidad de nova

**Registry Updates**:
- `EquivalentLegacyBlocks.java` — Registrar nuevos bloques
- `EquivalentLegacyBlockEntities.java` — Registrar nuevas entidades de bloque
- Crear `EquivalentLegacyEntities.java` si es necesario

#### Crafting & Recipes

**Data Generation** — `src/main/java/com/skd/equivalentlegacy/crafting/`:
- Crear generadores de recetas para todos los nuevos items
- Shapeless recipes para items combinados
- Shaped recipes para herramientas y armor
- Smelt recipes para Dark/Red Matter furnaces

#### EMC System

**Update** — `src/main/java/com/skd/equivalentlegacy/emc/`:
- Actualizar `EquivalentLegacy.java` → agregar `initEmcValues()` para todos los nuevos items
- Actualizar `FixedValues.java` con valores para: Stones, Rings, Tools, Armor
- Verificar que `EMCHelper.java` soporte nuevos valores

**Config** — `src/main/java/com/skd/equivalentlegacy/config/`:
- Extender `MappingConfig.java` o `ServerConfig.java` para valores custom por item

#### Asset Generation

**Resources** — `src/main/resources/assets/equivalent_legacy/`:
- Models JSON para todos los items (referencia: projecte retextures si están disponibles)
- Models JSON para bloques
- Texturas (PNG) — usar ProjectE retexture como base si está disponible
- Lang files actualizado (es_es.json, en_us.json) con nuevos items
- Blockstates JSON para bloques con variantes

### Constraints

- **Ninguna clase debe importar de `moze_intel.projecte` o `com.yaskulsky.equivox`** — son solo referencias
- Mantener convención de nomenclatura: snake_case para mod_id, PascalCase para clases
- Todas las clases de items deben heredar de patrones existentes en el proyecto
- Armor debe ser compatible con Curios slot donde sea aplicable
- Todos los bloques deben tener BlockEntity si manejan datos

### Definition of Done (Fase 1)

- [ ] Todos los items nuevos compilados y registrados sin errores
- [ ] Todos los armor sets compilados y wearable
- [ ] Todos los bloques compilados y placeable
- [ ] EMC values asignados a todos los nuevos items
- [ ] Recetas generadas y funcionales (testear crafting)
- [ ] Assets (modelos, texturas, lang) generados sin errores de recurso
- [ ] Build gradle completa sin warnings
- [ ] Teste en cliente: item visible en creative tab
- [ ] Teste en cliente: bloques visibles y placeable
- [ ] Commit message formato: `feat: add phase 1 items/blocks (v0.0.0-beta.2)`

---

## FASE 2: WORLD TRANSMUTATION + RENDERING + APIS → beta.3

### Objetivo
Implementar transmutación de bloques en el mundo, rendering personalizado, y APIs públicas para integraciones.

### Archivos a Modificar / Crear

#### World Transmutation

**Core** — `src/main/java/com/skd/equivalentlegacy/world_transmutation/`:
- `WorldTransmutationManager.java` — Sistema central
  - Método: `canTransmute(Block from, Block to) → boolean`
  - Método: `getTransmutationCost(Block from, Block to) → long` (diferencia EMC)
  - Método: `transmute(ServerPlayer, BlockPos, Block target) → boolean`
    - Verificar si bloque es transmutable
    - Verificar si jugador tiene EMC suficiente
    - Aplicar cooldown
    - Consumir EMC del jugador
    - Cambiar bloque en el mundo
    - Spawn partículas/efectos
- `TransmutationConfig.java` — Configuración
  - Whitelist/Blacklist de bloques
  - Cooldown en ticks
  - Rango de transmutación (default: 4 blocks)
  - On/off para efectos

**Events** — `src/main/java/com/skd/equivalentlegacy/events/`:
- Extender `PlayerEvents.java`
  - Event listener para click derecho con Philosopher's Stone/Transmutation Stone
  - Detectar si es bloque en el mundo
  - Trigger `WorldTransmutationManager.transmute()`

**Entidades & Efectos** — `src/main/java/com/skd/equivalentlegacy/entity/`:
- Actualizar `EntityNovaPrimed.java` para explotar cuando se transmuta
- Particle effects (Nova Catalyst, Cataclysm, dust)

#### Rendering

**Custom Renderers** — `src/main/java/com/skd/equivalentlegacy/rendering/`:
- `PedestalRenderer.java`
  - Renderizar pedestal con rotación
  - Flotar items encima
  - Efectos de luz/glow
- `ChestRenderer.java`
  - Animación de apertura para Alchemical Chest
  - Brillo personalizado
- `TransmutationRenderingOverlay.java`
  - HUD en pantalla durante transmutación
  - Mostrar items transmutables cerca
  - Mostrar costo EMC
  - Preview visual

**Block/Entity Renderer Registration** — `src/main/java/com/skd/equivalentlegacy/`:
- Crear `EquivalentLegacyRenderers.java` si no existe
- Registrar custom renderers via event bus

#### APIs Públicas

**Public Interfaces** — `src/main/java/com/skd/equivalentlegacy/api/`:
- `IEMCProvider.java` — Interfaz para items que proveen EMC (external)
- `ITransmutationAllowed.java` — Bloques que pueden transmutarse (external)
- `IKnowledgeProvider.java` — Formalizar proveedor de conocimiento (external)
- `IEMCStorage.java` — Almacenamiento de EMC (external)
- `IEmcReceptor.java` — Receptor de EMC (external)

**Public Events** — `src/main/java/com/skd/equivalentlegacy/api/events/`:
- `EMCTransmutationEvent.java` — Antes/después de transmutación
- `EMCDiscoveryEvent.java` — Cuando se descubre nuevo item
- `WorldTransmutationEvent.java` — Cuando se transmuta bloque

#### Integraciones

**JEI Integration** — `src/main/java/com/skd/equivalentlegacy/integration/`:
- Crear `JEIPlugin.java` si no existe
  - Category de "Transmutation Recipes"
  - Mostrar costo EMC por receta
  - Mostrar qué items se pueden hacer desde item actual

**WTHIT/Jade Integration** — `src/main/java/com/skd/equivalentlegacy/integration/`:
- `WthitPlugin.java` — Mostrar EMC value en hover
- Mostrar si bloque es transmutable

**EMI Integration** (opcional) — Similar a JEI

#### Commands

**Extend ModCommands** — `src/main/java/com/skd/equivalentlegacy/command/`:
- Actualizar `ModCommands.java`
  - `/equivalent_legacy emc <item>` — Ver EMC value
  - `/equivalent_legacy transmute <item> <cantidad>` — Transmutación directa (OP)
  - `/equivalent_legacy reload` — Reload config
  - `/equivalent_legacy knowledge <player>` — Ver conocimiento

#### Config Expandida

**Update Config Files** — `src/main/java/com/skd/equivalentlegacy/config/`:
- Extender `ServerConfig.java` con world_transmutation options
- Extender `ClientConfig.java` con rendering options
- Validation de valores

### Constraints

- **No importar de equivox o ProjectE** — solo usar como referencia conceptual
- Rendering debe ser compatible con Intel/AMD/Nvidia GPUs
- APIs deben ser estables (no cambiar entre betas)
- Comandos OP deben verificar permisos (level 4)
- World transmutation debe ser sincronizable (server ↔ client)

### Definition of Done (Fase 2)

- [ ] `WorldTransmutationManager` compilado y funcional
- [ ] Event listeners registrados correctamente
- [ ] Transmutación de bloques testeable en juego
- [ ] Efectos visuales (partículas) visibles
- [ ] Renderers compilados y registrados
- [ ] Pedestales renderizan con items flotantes
- [ ] Chest abierto/cerrado renderiza correctamente
- [ ] Overlay muestra en pantalla durante transmutación
- [ ] APIs públicas compiladas
- [ ] JEI plugin detecta y muestra recetas
- [ ] WTHIT plugin muestra EMC values
- [ ] Comandos funcionan y respetan permisos
- [ ] Config carga sin errores
- [ ] Build gradle completa sin warnings
- [ ] Commit message formato: `feat: add phase 2 world_transmutation/rendering/apis (v0.0.0-beta.3)`
- [ ] Graphify actualizado (`graphify update . --force`)

---

## Detalles Técnicos

### Patrón de Herencia para Items
```java
// Base tool
public class PETool extends DiggerItem {
    public PETool(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }
}

// Specific tool with AOE
public class PEHammer extends PETool {
    @Override
    public boolean onBlockStartBreak(ItemStack itemStack, BlockPos pos, Player player) {
        // AOE logic: break 3x3 en lugar de 1x1
        return super.onBlockStartBreak(itemStack, pos, player);
    }
}
```

### Patrón de Herencia para Armor
```java
public class DarkMatterArmor extends ArmorItem {
    public DarkMatterArmor(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }
    
    // Implementar abilities especiales si es needed
}
```

### Patrón de Bloque con BlockEntity
```java
public class Pedestal extends BaseEntityBlock {
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }
}
```

### Registro de Items/Bloques
```java
// En EquivalentLegacyItems.java
public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
public static final DeferredHolder<Item, PhilosophersStone> PHILOSOPHERS_STONE = 
    ITEMS.register("philosophers_stone", () -> new PhilosophersStone(...));
```

### EMC Registration Pattern
```java
// En EquivalentLegacy.java → initEmcValues()
private static void initEmcValues() {
    var fixedValues = new FixedValues();
    
    var nssPhilosopher = NSSItem.createItem(PHILOSOPHERS_STONE.get());
    fixedValues.addSetValueBefore(nssPhilosopher, 24_576L);
    
    EMCHelper.registerFixedValues(fixedValues);
}
```

---

## Orden de Ejecución Sugerido

### Fase 1 (Sequence)
1. Crear todas las clases de items (Stones, Rings, Tools) — ~6 files
2. Crear todas las clases de armor — ~8 files
3. Crear todas las clases de bloques y block entities — ~9 files
4. Actualizar registries (EquivalentLegacyItems, Blocks, BlockEntities) — ~3 files
5. Actualizar EMC values en EquivalentLegacy.java — 1 file
6. Generar recetas y assets — data generation
7. Build y test

### Fase 2 (Sequence)
1. Crear WorldTransmutationManager y config — 2 files
2. Registrar event listeners en PlayerEvents — 1 file
3. Crear rendering classes (Pedestal, Chest, Overlay) — 3 files
4. Registrar renderers — 1 file
5. Crear API interfaces y eventos — 5 files
6. Crear integration plugins (JEI, WTHIT) — 2 files
7. Extender ModCommands — 1 file
8. Extender configs — 2 files
9. Build, test, Graphify update

---

## Notas Importantes

- **Equivox reference**: 691 clases compiladas, pero current tiene solo 99 .java. No necesitamos copiar TODO, solo traducir lo funcional a NeoForge 26.2.
- **ProjectE JAR incorrecto**: ProjectE-1.21.1-PE1.1.0.jar es de MC 1.21.1 + solo APIs. Ignorar completamente.
- **Arquitectura plana**: El proyecto no usa capas complejas. Mantener estructura simple.
- **Testing**: Verificar en cliente que items/bloques aparezcan y sean funcionales. No hay test framework formal.
- **Commits**: Hacer commits lógicos, no por file. Ej: "feat: add philosopher's stone variants" (multiple files).

---

## Success Criteria (End State)

✅ Equivalent Legacy beta.2:
- Todos los items, tools, armor implementados
- Todos los bloques base implementados
- EMC values asignados
- Build clean, sin errores

✅ Equivalent Legacy beta.3:
- World transmutation funcional (cambiar bloques en el mundo)
- Rendering personalizado para pedestales y chests
- APIs públicas disponibles
- JEI integration muestra recetas
- Build clean, sin errores
- Feature parity ~70-80% con Equivox

---

**Plan written**: 2026-08-06  
**Status**: Ready for review
