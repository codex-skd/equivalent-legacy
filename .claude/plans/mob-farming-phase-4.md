# Plan: Fase 4 - Mob Farming System (Equivalent Legacy)

**Objetivo**: Implementar sistema de granja de mobs usando pedestales para controlar spawners y recolectar experiencia/drops automáticamente.

**Versión**: 1.3.0-beta.1

---

## Resumen

Fase 4 agrega capacidad de automatizar granjas de mobs mediante:

1. **Spawner Control**: Usar Dark/Red Matter Pedestales para controlar spawners cercanos (on/off, tipo de mob, velocidad)
2. **Experience Collection**: Pedestales equipados con Mind Stone recolectan XP de mobs cercanos automáticamente
3. **Item Collection**: Pedestales con Black Hole Band recolectan drops de mobs
4. **Mob Detection**: Sistema para detectar spawners y mobs cercanos
5. **Configuration UI**: Interfaz para configurar comportamiento de spawner (delay, count, type)

---

## 1. Crear MobFarmingManager (nueva clase)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/mob_farming/MobFarmingManager.java`

**Propósito**: Gestionar spawners activos y comportamiento de granjas

**Estructura base**:
```java
public class MobFarmingManager {
    private static final Map<BlockPos, SpawnerState> activeSpawners = new HashMap<>();
    private static final int DETECTION_RANGE = 16; // bloques
    
    // Configuración de spawner
    public static class SpawnerState {
        public BlockPos pos;
        public BlockEntity blockEntity; // CreatureSpawner
        public boolean enabled = true;
        public int delay = 20; // ticks entre spawns
        public int spawnCount = 4; // mobs por spawn
        public int maxNearby = 6;
        public String mobType; // minecraft:zombie, etc.
    }
    
    // Métodos públicos
    public static void registerSpawner(BlockPos pos, CreatureSpawner spawner) {
        // Detectar spawner cerca de pedestal y registrar
    }
    
    public static void unregisterSpawner(BlockPos pos) {
        // Desactivar control del spawner
    }
    
    public static SpawnerState getSpawnerState(BlockPos pos) {
        return activeSpawners.get(pos);
    }
    
    public static void updateSpawnerConfig(BlockPos pos, int delay, int count, String mobType) {
        // Actualizar configuración del spawner
    }
    
    public static void tick() {
        // Llamado cada tick: actualizar spawners, recolectar XP, etc.
    }
}
```

**Notas**:
- No modifica el spawner vanilla directamente — usa eventos
- Usa BlockEntity.setChanged() para marcar cambios
- Sincroniza estado vía packet al cliente (opcional visual)

---

## 2. Crear MobFarmingTickHandler (event listener)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/events/MobFarmingTickHandler.java`

**Propósito**: Handle ticks del servidor y actualizar granjas

**Estructura base**:
```java
@EventBusSubscriber(modid = MODID, bus = Bus.FORGE)
public class MobFarmingTickHandler {
    
    @SubscribeEvent
    static void onServerTick(ServerTickEvent event) {
        if (event.phase != Phase.END) return;
        
        // Actualizar MobFarmingManager
        MobFarmingManager.tick();
        
        // Para cada pedestal con mob farming setup:
        // - Detectar mobs cercanos
        // - Recolectar drops si hay Black Hole Band
        // - Recolectar XP si hay Mind Stone
        // - Controlar spawner si está registrado
    }
    
    @SubscribeEvent
    static void onMobSpawn(MobSpawnEvent.FinalSpawn event) {
        // Detectar si spawner está cerca de pedestal controlado
        // Aplicar modificaciones (delay, count, type)
    }
    
    @SubscribeEvent
    static void onExperiencePickup(PlayerXpEvent.PickupXp event) {
        // Si Mind Stone en pedestal cercano:
        // - Cancelar pickup normal
        // - Transferir XP a Mind Stone almacenamiento
        // - Dar feedback visual al jugador
    }
}
```

---

## 3. Actualizar PedestalBlockEntity para Mob Farming

**Archivo**: `src/main/java/com/skd/equivalentlegacy/block/entity/PedestalBlockEntity.java`

**Cambios**:
- Agregar `isMobFarmingSetup()` → verifica si tiene Mind Stone o Black Hole Band
- Agregar `getMobsNearby()` → detecta mobs en rango (16 bloques)
- Agregar `collectNearbyXp()` → recolecta XP de mobs (si Mind Stone equipado)
- Agregar `collectNearbyDrops()` → recolecta drops (si Black Hole Band equipado)
- Agregar `registerControlledSpawner(BlockPos)` → vincula spawner a pedestal
- Agregar `updateSpawnerBehavior()` → modifica delay/count/type del spawner

**Propiedades de almacenamiento**:
- `linkedSpawnerPos` — BlockPos del spawner controlado (NBT)
- `collectedXp` — XP acumulada (para Mind Stone)
- `lastMobDetection` — timestamp de última detección (caché)

---

## 4. Crear SpawnerControlItem (item para configurar spawners)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/item/SpawnerControlItem.java`

**Propósito**: Item que permite configurar spawners desde distancia

**Propiedades**:
- Extender de `Item`
- Right-click en spawner → abre config screen
- Right-click en pedestal (con spawner vinculado) → abre config GUI
- Shift+click → desvincula spawner

**Métodos**:
```java
public InteractionResult use(Level level, Player player, InteractionHand hand) {
    // Implementar búsqueda de spawner en rango (16 bloques)
    // Abrir config screen si encuentra uno
}

public InteractionResultHolder<ItemStack> useOn(...) {
    // Right-click en bloque: si es spawner o pedestal, abrir GUI
}
```

---

## 5. Crear SpawnerConfigScreen (GUI de configuración)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/gui/SpawnerConfigScreen.java`

**Propósito**: UI para configurar spawner (delay, count, type)

**Estructura base**:
```java
public class SpawnerConfigScreen extends Screen {
    private int delay = 20;        // Ticks entre spawns
    private int spawnCount = 4;    // Mobs por spawn
    private int maxNearby = 6;     // Mobs cercanos máx
    private String mobType = "minecraft:zombie";
    
    // Widgets
    private EditBox delayInput;
    private EditBox countInput;
    private EditBox maxNearbyInput;
    private DropdownWidget mobTypeDropdown; // Listar mobs conocidos
    
    // Botones
    private Button applyButton;
    private Button cancelButton;
    
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Renderizar labels + inputs + buttons
        // Mostrar preview de mob seleccionado
    }
    
    private void applyConfig() {
        // Enviar packet al servidor con nueva config
        // Cerrar screen
    }
}
```

**Características**:
- Sliders para delay (20-200 ticks)
- Input numérico para spawn count (1-8)
- Dropdown para tipo de mob (zombie, skeleton, creeper, etc.)
- Preview visual del mob seleccionado
- "Apply" y "Cancel" buttons

---

## 6. Crear packets para sincronización cliente-servidor

**Archivos**:
- `src/main/java/com/skd/equivalentlegacy/network/payload/SpawnerConfigPayload.java`
- `src/main/java/com/skd/equivalentlegacy/network/payload/MobFarmingStatePayload.java`

**Propósito**: Sincronizar config de spawner y estado de granja

**SpawnerConfigPayload**:
```java
public record SpawnerConfigPayload(
    BlockPos spawnerPos,
    int delay,
    int spawnCount,
    int maxNearby,
    String mobType
) implements CustomPacketPayload {
    // Implementar serialización/deserialización
    // Manejar en servidor: actualizar MobFarmingManager
}
```

**MobFarmingStatePayload**:
```java
public record MobFarmingStatePayload(
    BlockPos pedestalPos,
    boolean active,
    int xpStored,
    int dropsCollected
) implements CustomPacketPayload {
    // Enviar del servidor al cliente para UI
    // Mostrar feedback visual en pedestal
}
```

---

## 7. Integración con bloques existentes

**Cambios en bloques**:
- **Mind Stone en Pedestal**: Automáticamente recolecta XP de mobs cercanos (16 bloques)
- **Black Hole Band en Pedestal**: Automáticamente recolecta drops de mobs
- **Dark/Red Matter Pedestal**: Puede controlar spawners cercanos

**Cambios en items**:
- Crear `SpawnerControlWand` (item crafteable) para configurar spawners
- Registrar en `ModItems`

---

## 8. Configuración y limites

**Restricciones técnicas**:
- Rango de detección: 16 bloques (configurable vía config file)
- Delay mínimo: 20 ticks (1 segundo)
- Spawn count máximo: 8 mobs por spawn
- Max nearby: 6-32 mobs
- XP recolectado: 100% de los mobs cercanos
- Drops recolectados: 100% si hay espacio en pedestal/inventario

**Performance**:
- Caché de spawners activos (evitar búsquedas cada tick)
- Solo comprobar rango si pedestal está loaded
- Limitar detección de mobs a chunks cargados

---

## 9. Localization (nuevas claves)

**Archivos**: 18 lang files

**Claves a agregar**:
- `gui.equivalent_legacy.spawner_config` — título config screen
- `gui.equivalent_legacy.spawner_delay` — label para delay
- `gui.equivalent_legacy.spawner_count` — label para spawn count
- `gui.equivalent_legacy.spawner_max_nearby` — label para max nearby
- `gui.equivalent_legacy.spawner_type` — label para tipo de mob
- `gui.equivalent_legacy.spawner_apply` — botón aplicar
- `gui.equivalent_legacy.spawner_cancel` — botón cancelar
- `item.equivalent_legacy.spawner_control_wand` — nombre del item
- `tooltip.equivalent_legacy.mob_farming.active` — tooltip: granja activa
- `tooltip.equivalent_legacy.mob_farming.xp_collected` — XP recolectada
- `chat.equivalent_legacy.spawner_linked` — mensaje: spawner vinculado
- `chat.equivalent_legacy.spawner_unlinked` — mensaje: spawner desvinculado

---

## 10. Definition of Done

- [ ] `MobFarmingManager` creado y funcional
- [ ] `MobFarmingTickHandler` registrado e inyectando eventos
- [ ] `PedestalBlockEntity` actualizado con métodos de mob farming
- [ ] `SpawnerControlItem` crafteable y funcional
- [ ] `SpawnerConfigScreen` renderiza e interactúa sin crashes
- [ ] Packets sincronizados correctamente (cliente-servidor)
- [ ] Mind Stone en pedestal recolecta XP automáticamente
- [ ] Black Hole Band en pedestal recolecta drops automáticamente
- [ ] Spawner control wand vincula/desvincula spawners
- [ ] Config screen aplica cambios correctamente
- [ ] Mobs respawean con delay/count modificados
- [ ] Localization completa (18 idiomas)
- [ ] Sin crashes o lag con múltiples spawners activos
- [ ] Compilación clean
- [ ] Commit: `feat: add mob farming system (Phase 4, v1.3.0-beta.1)`

---

## 11. Fases posteriores

- **Release 1.3.0**: Feature-complete cuando Fases 1-4 estén done
- **Fase 5** (futuro): Advanced features (breeding automation, selective mob farming, etc.)

---

## 12. Restricciones y consideraciones

### Minecraft Mechanics
- Spawners requieren oscuridad (light level < 8)
- Mobs respawnean cada 20-39.95 ticks (configurable)
- Max 6-8 mobs por spawner normalmente
- XP drop: 5-16 puntos por mob

### NeoForge 26.2
- Usar `ServerTickEvent` en `Bus.FORGE`
- Usar `MobSpawnEvent.FinalSpawn` para interceptar spawns
- BlockEntity#setChanged() para marcar cambios
- Packets vía `CustomPacketPayload`

### Performance
- No hacer raycasts cada tick
- Caché de spawners y mobs
- Limitar búsquedas a chunks cargados
- Usar events en lugar de polling

### Compatibility
- No modificar spawner NBT directamente
- Respetar otros mods que controlen spawners
- Fallback si spawner no es vanilla

---

## 13. Archivos que se crearán/modificarán

**Crear (nuevos)**:
- `src/main/java/com/skd/equivalentlegacy/mob_farming/MobFarmingManager.java`
- `src/main/java/com/skd/equivalentlegacy/events/MobFarmingTickHandler.java`
- `src/main/java/com/skd/equivalentlegacy/item/SpawnerControlItem.java`
- `src/main/java/com/skd/equivalentlegacy/gui/SpawnerConfigScreen.java`
- `src/main/java/com/skd/equivalentlegacy/network/payload/SpawnerConfigPayload.java`
- `src/main/java/com/skd/equivalentlegacy/network/payload/MobFarmingStatePayload.java`

**Modificar (existentes)**:
- `src/main/java/com/skd/equivalentlegacy/block/entity/PedestalBlockEntity.java` — agregar métodos de mob farming
- `src/main/java/com/skd/equivalentlegacy/item/ModItems.java` — registrar SpawnerControlItem
- `src/main/java/com/skd/equivalentlegacy/gui/ModMenuTypes.java` — si es necesario para config packet
- 18 archivos `lang/*.json` — agregar claves de localization

---

## 14. Notas de implementación

- MobFarmingManager usa mapa estático (no persistir en NBT por ahora)
- Spawners se registran automáticamente cuando pedestal está activo
- XP storage: usar Data Attachment como PlayerEMCData (futuro)
- No crear nuevos items/bloques — usar existentes (Mind Stone, Black Hole Band, pedestales)
- SpawnerControlWand es crafteable con oscura + rojo matter + soul stone

---

## 15. Alternativas y trade-offs

**Option A (Implementado)**: Pedestal automático detecta spawners cercanos
- ✅ Simpler para usuario (plug & play)
- ❌ Puede comportarse inesperadamente
- ⚠️ Requiere caché inteligente

**Option B (Alternativa): Manual linking solo**
- ✅ Más control/predictible
- ❌ Tedioso configurar
- ❌ Requiere item especial

→ Elegimos **Option A** con opción manual via SpawnerControlWand.
