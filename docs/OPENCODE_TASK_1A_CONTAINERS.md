# OpenCode Task 1A: Contenedores & Slots Completos para Equivalent Legacy

**Proyecto**: Equivalent Legacy (Minecraft 26.2 / NeoForge 26.2.0.37-beta)  
**Objetivo**: Port de contenedores y slots desde Equivox 26.1.2 a Equivalent Legacy 26.2  
**Prioridad**: 🔴 CRÍTICA (bloqueador para v1.2.0-RELEASE)  
**Duración estimada**: 2 semanas  
**Modelo preferido**: deepseek-v4-flash (ó Nvidia si OpenCode Go se agota)

---

## 📋 RESUMEN

Equivalent Legacy tiene contenedores/menus **muy simplificados**. El puerto de Equivox requiere:

1. **Reescribir 7 contenedores existentes** con lógica completa (slots validadores, sincronización, etc.)
2. **Portar 15+ tipos de slots personalizados** con predicados
3. **Implementar sincronización de EMC/estado** usando DataSlot y longFields
4. **Agregar soporte para ghost slots** (display-only, no removibles)

**Referencia de código**: `temp/Equivox-main/Equivox-main/src/main/java/com/yaskulsky/equivox/gameObjs/container/`

---

## 🎯 CONTENEDORES QUE NECESITAN REESCRITURA

### Existentes en Equivalent Legacy (SIMPLIFICADOS):
```
✗ CollectorMenu → CollectorMK1Menu (+ MK2, MK3)
✗ CondenserMenu → CondenserMenu (solo MK1)
✗ RelayMenu → RelayMenu (solo MK1)
✗ MatterFurnaceMenu → MatterFurnaceMenu (DM, RM)
✗ BagMenu → BagMenu
✗ ChestMenu → ChestMenu
✗ TransmutationContainer → TransmutationContainer
```

**Tarea**: Reescribir cada uno con:
- ValidatedSlot (slots con predicados)
- SlotGhost (para locks, display-only)
- Sincronización completa de EMC/estado
- DataSlot + longFields para valores grandes

---

## 🔧 ARQUITECTURA REQUERIDA

### Base Class: `PEContainer` (NO EXISTE en EL, necesario crear)
**Ubicación**: `src/main/java/com/skd/equivalentlegacy/gui/PEContainer.java`

```java
public abstract class PEContainer extends AbstractContainerMenu {
    protected List<BoxedLong> longFields = new ArrayList<>();
    protected Player player;
    
    public PEContainer(MenuType<?> type, int windowId, Inventory playerInv) {
        super(type, windowId);
        this.player = playerInv.player;
    }
    
    protected void addPlayerInventory(int x, int y) {
        // Add 27 inventory slots + 9 hotbar
    }
    
    // Sync longFields (EMC values > Integer.MAX_VALUE)
    protected void broadcastPE(boolean all) {
        for (BoxedLong field : longFields) {
            field.sync(this, all);
        }
    }
    
    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        broadcastPE(false);
    }
}
```

### Utility Class: `BoxedLong` (NO EXISTE, necesario crear)
**Ubicación**: `src/main/java/com/skd/equivalentlegacy/gui/BoxedLong.java`

```java
public class BoxedLong {
    private long value;
    private int id = -1;
    
    public long get() { return value; }
    public void set(long v) { this.value = v; }
    
    // Sync via two int DataSlots (high/low)
    public void sync(AbstractContainerMenu container, boolean force) {
        // Split long into 2 ints, add DataSlots
    }
}
```

---

## 📦 SLOTS PERSONALIZADOS REQUERIDOS

### Tipos de Slots:

#### 1. **ValidatedSlot** — Permite inserción/extracción con validación
```
Ubicación: gui/slots/ValidatedSlot.java
Constructor: ValidatedSlot(IItemHandler handler, int slot, int x, int y, SlotPredicate predicate)
Features:
  - SlotPredicate valida qué items son permitidos
  - mayPlace() usa predicate
  - canTakeItem() siempre true
```

#### 2. **SlotGhost** — Display-only (no se puede remover, solo reemplazar)
```
Ubicación: gui/slots/SlotGhost.java
Inherits: ValidatedSlot, implements ISlotGhost
Features:
  - mayPlace() siempre false (no se puede insertar)
  - canTakeItem() siempre false (no se puede remover)
  - tryClear() para borrar con shift+click
```

#### 3. **ComponentSlotGhost** — Variante para componentes
```
Similar a SlotGhost pero para DataComponent stacks
```

#### 4. **SlotPredicates** — Utilidad con predicados reutilizables
```
Ubicación: gui/slots/SlotPredicates.java
Predicados:
  - COLLECTOR_INV: Acepta fuels + upgrade items
  - CONDENSER_LOCK: Acepta items para lock
  - ALWAYS_FALSE: Rechaza todo
  - [copiar de Equivox]
```

#### 5. **Transmutation Slots** — Para la tabla de transmutación
```
- SlotInput: Input (consumible)
- SlotOutput: Output (no removible hasta transmute)
- SlotLock: Lock (display-only)
- SlotConsume: Consume on transmute
- SlotUnlearn: Unlearn on click
```

#### 6. **Inventory Slots** — Para acceso a inventario del jugador
```
- MainInventorySlot: Hotbar/inventory
- InventoryContainerSlot: Copy-on-take
```

---

## 🔀 CONTENEDORES ESPECÍFICOS

### 1. **CollectorMK1Menu** (requiere reescritura completa)
**Fuente de referencia**: `Equivox/gameObjs/container/CollectorMK1Container.java`

**Cambios principales**:
```
- Cambiar CollectorMenu → CollectorMK1Menu (separar MK2, MK3)
- Agregar 8 slots de fuel/upgrades (actualmente solo 1)
- Agregar slot ghost para lock
- Usar ValidatedSlot con SlotPredicates.COLLECTOR_INV
- Sincronizar: emc, sunLevel, kleinChargeProgress, fuelProgress, kleinEmc
- Usar BoxedLong para emc + kleinEmc (valores > 2B)
```

**Estructura esperada**:
```
Slot 0: Klein Star (aux, ValidatedSlot)
Slots 1-8: Fuel upgrades (main, ValidatedSlot)
Slot 9: Upgrade result (aux, ValidatedSlot ALWAYS_FALSE)
Slot 10: Lock target (aux, SlotGhost)
Slots 11-36: Player inventory
Slots 37-45: Hotbar
```

### 2. **CondenserMenu** (reescritura)
**Fuente**: `Equivox/gameObjs/container/CondenserContainer.java`

**Cambios**:
- Input slot (validado)
- Output slot (display)
- Lock slot (ghost)
- Sincronizar: inputEmc, outputEmc, progress

### 3. **RelayMenu** (reescritura)
**Cambios**:
- Simplificado (slots mínimos)
- Display de estado (no slots)

### 4. **MatterFurnaceMenu** (reescritura)
**Fuente**: `Equivox/gameObjs/container/DMFurnaceContainer.java` + `RMFurnaceContainer.java`

**Cambios**:
- Input slot (items a smeltar)
- Output slot (resultados)
- Fuel slot
- Validación por tier (DM ≠ RM)

### 5. **TransmutationContainer** (reescritura + lógica compleja)
**Fuente**: `Equivox/gameObjs/container/TransmutationContainer.java`

**Cambios IMPORTANTES**:
- Input slot (validado)
- Output slot (locked until transmute)
- Consume slots (2-3)
- Lock slots (1-2, ghost)
- Unlearn slot (ghost)
- Sincronización de knowledge, locked conversions

---

## 🔄 SINCRONIZACIÓN DE DATOS

**Patrón requerido**:

```java
// En el Menu
private DataSlot sunData = DataSlot.standalone();
private BoxedLong emcData = new BoxedLong(); // Para valores > 2B

public CollectorMK1Menu(...) {
    // ...
    addDataSlot(sunData);
    emcData.registerSlots(this); // Crea 2 DataSlots internos
    longFields.add(emcData);
}

@Override
public void broadcastChanges() {
    super.broadcastChanges();
    if (!level.isClientSide()) {
        sunData.set(blockEntity.getSunLevel());
        emcData.set(blockEntity.getStoredEmc());
    }
}
```

---

## 📝 ARCHIVOS A PORTAR/CREAR

### Crear nuevos:
```
✨ gui/PEContainer.java — Base class
✨ gui/BoxedLong.java — Long sync utility
✨ gui/slots/ISlotGhost.java — Interface
✨ gui/slots/SlotGhost.java — Ghost slot implementation
✨ gui/slots/ComponentSlotGhost.java — For DataComponents
✨ gui/slots/SlotPredicates.java — Predicate utilities
✨ gui/slots/ValidatedSlot.java — Validated slot
✨ gui/slots/transmutation/*.java — Transmutation slots (5 tipos)
✨ gui/slots/inventory/*.java — Inventory slots (3 tipos)
```

### Reescribir existentes:
```
🔄 gui/CollectorMenu.java → gui/CollectorMK1Menu.java
🔄 gui/CondenserMenu.java (completa reescritura)
🔄 gui/RelayMenu.java (completa reescritura)
🔄 gui/MatterFurnaceMenu.java (completa reescritura)
🔄 gui/BagMenu.java (validación de slots)
🔄 gui/ChestMenu.java (validación de slots)
🔄 gui/TransmutationContainer.java (reescritura grande)
```

### Actualizar registración:
```
🔄 gui/ModMenuTypes.java — Agregar MK2, MK3 collectors/relays
```

---

## ✅ PRUEBAS REQUERIDAS

1. **Colector MK1**:
   - [ ] Slot klein funciona (puedo insertar/extraer)
   - [ ] Slots de fuel se validan
   - [ ] EMC sincroniza correctamente
   - [ ] Lock slot es display-only

2. **Condenser**:
   - [ ] Input acepta items
   - [ ] Output es display-only
   - [ ] Conversión muestra progreso

3. **Transmutation**:
   - [ ] Slots input/output funcionan
   - [ ] Lock slots son display-only
   - [ ] Unlearn slot funciona

4. **General**:
   - [ ] No crashes al abrir GUIs
   - [ ] Datos sincronizados (cliente ↔ servidor)
   - [ ] EMC > 2B sincroniza correctamente

---

## 📚 REFERENCIAS

**Código fuente Equivox**:
- `temp/Equivox-main/Equivox-main/src/main/java/com/yaskulsky/equivox/gameObjs/container/`

**Cambios de namespace**:
- `com.yaskulsky.equivox` → `com.skd.equivalentlegacy`
- `equivox` → `equivalent_legacy`

**Otros cambios NeoForge 26.2**:
- `AbstractContainerMenu` en lugar de `Container`
- `IItemHandler` (Forge API)
- `MenuType` en lugar de `ContainerType`

---

## 🚀 INICIO

Mensaje inicial para OpenCode:

> Port complete container/slot system from Equivox 26.1.2 to Equivalent Legacy 26.2. 
> Start with PEContainer base class, then ValidatedSlot + SlotGhost, then rewrite all 7 containers.
> Reference: temp/Equivox-main source code. Target NeoForge 26.2 API.
> Build must compile clean. No logic changes, just port + adapt to 26.2 API.

---

**Contacto durante desarrollo**: Mensaje si hay ambigüedad en Equivox vs EL API differences.
