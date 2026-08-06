# Plan: Fase 2.4b - ChestRenderer Full (Equivalent Legacy)

**Objetivo**: Completar la animación de apertura/cierre de AlchemicalChest mediante BlockEntityRenderer y sincronización de estado cliente-servidor.

**Versión**: 1.2.0-beta.7

---

## 1. AlchemicalChestBlockEntity — Implementar LidBlockEntity

**Archivo**: `src/main/java/com/skd/equivalentlegacy/block/entity/AlchemicalChestBlockEntity.java`

**Cambios**:
- Implementar interfaz `net.minecraft.world.level.block.entity.LidBlockEntity`
- Agregar campo `private int openNess = 0` (0-5, donde 5 = totalmente abierto)
- Implementar métodos requeridos:
  ```java
  public float getOpenNess(float partialTick) {
      return Math.min(1.0F, (openNess + partialTick) / 5.0F);
  }
  ```
- Anular `startOpen()` en `MenuProvider.openMenu()`:
  - Cuando el jugador abre el menú (click), incrementar `openNess` (max 5)
  - Sincronizar con cliente vía `ContainerData`
- Anular `stopOpen()`:
  - Cuando el menú se cierra, decrementar `openNess` (min 0)
  - Sincronizar con cliente

**Sincronización**:
- Agregar `ContainerData` al `ChestMenu` para enviar `openNess` al cliente:
  ```java
  containerData.set(0, openNess); // índice 0 = openNess
  ```
- En cliente, leer: `containerData.get(0)` en `ChestMenu.broadcastChanges()`

**Nota**: En NeoForge 26.2, `LidBlockEntity.getOpenNess(float)` es la API correcta (no legacy `getOpenness`).

---

## 2. ChestRenderer — Implementar BlockEntityRenderer Completo

**Archivo**: `src/main/java/com/skd/equivalentlegacy/rendering/ChestRenderer.java`

**Reemplazar** el placeholder con:

```java
@OnlyIn(Dist.CLIENT)
public final class ChestRenderer implements BlockEntityRenderer<AlchemicalChestBlockEntity, ChestRenderer.State> {
    
    public static final float LID_OPEN_DEGREES = 90.0F;
    
    private final ItemModelResolver itemModelResolver;
    
    public ChestRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }
    
    @Override
    public State createRenderState() {
        return new State();
    }
    
    @Override
    public void extractRenderState(
            AlchemicalChestBlockEntity entity,
            State state,
            float partialTick,
            Vec3 cameraPos,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress
    ) {
        if (entity.getLevel() == null) return;
        state.lidAngle = entity.getOpenNess(partialTick) * LID_OPEN_DEGREES;
    }
    
    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        // Renderizar cuerpo del chest + tapa rotada
        // Usar modelo vanilla del chest como base o modelo custom alchemical_chest.json
        poseStack.pushPose();
        
        // Rotar tapa alrededor del eje X (bisagra frontal)
        poseStack.mulPose(Axis.XP.rotationDegrees(state.lidAngle));
        
        // Dibujar modelo del chest con tapa rotada
        // (Usar ItemModelResolver para importar el modelo, o vanilla ChestBlockModel)
        
        poseStack.popPose();
    }
    
    @OnlyIn(Dist.CLIENT)
    public static final class State extends BlockEntityRenderState {
        public float lidAngle = 0.0F;
    }
}
```

**Detalles técnicos**:
- API NeoForge 26.2: `BlockEntityRenderer<T,S>` con `createRenderState`/`extractRenderState`/`submit`
- `getOpenNess(partialTick)` devuelve 0.0F (cerrado) a 1.0F (abierto)
- Eje de rotación: `Axis.XP` (arriba/abajo) — la tapa se abre hacia arriba
- Ángulo: `0°` (cerrado) a `90°` (abierto)
- Animación smooth: `partialTick` interpola entre frames

**Nota**: El modelo del chest es vanilla o custom. Si `alchemical_chest.json` no tiene la textura `#chest` correcta (bug pre-existente), usar modelo vanilla como fallback:
```java
// Fallback a vanilla chest model si custom no renderiza
BlockModel model = Minecraft.getInstance().getBlockModelShaper()
    .getBlockModel(Blocks.CHEST.defaultBlockState());
```

---

## 3. EquivalentLegacyRenderers — Registrar ChestRenderer

**Archivo**: `src/main/java/com/skd/equivalentlegacy/rendering/EquivalentLegacyRenderers.java`

**Agregar en método `registerBlockEntityRenderers()`**:

```java
BlockEntityRenderers.register(
    EquivalentLegacyBlockEntities.ALCHEMICAL_CHEST.get(),
    ChestRenderer::new
);
```

---

## 4. ChestMenu — Sincronización ContainerData

**Archivo**: `src/main/java/com/skd/equivalentlegacy/gui/ChestMenu.java`

**Cambios**:
- Agregar `ContainerData containerData` en constructor:
  ```java
  this.containerData = new SimpleContainerData(1); // 1 dato: openNess
  addDataSlots(containerData);
  ```
- En método que actualiza servidor:
  ```java
  containerData.set(0, blockEntity.getOpenNess()); // Enviar openNess al cliente
  ```

**Cliente**:
- En `broadcastChanges()` (lado cliente), leer `containerData.get(0)` para UI feedback (opcional).

---

## 5. BlockState / Modelo — Verificar Asset

**Archivo**: `src/main/resources/assets/equivalent_legacy/blockstates/alchemical_chest.json`

**Nota Pre-existente**: Referencia a `projecte:block/alchemical_chest` (namespace legacy roto). Opciones:
1. **Corregir** a `equivalent_legacy:block/alchemical_chest` si el modelo existe
2. **Usar vanilla** como fallback (`minecraft:block/chest`)
3. **No cambiar** si beta.5 ya renderiza (compilación pasa)

Para Fase 2.4b, NO modificar blockstate (puede romper assets existentes). ChestRenderer usa código, no depende de blockstate para rotación.

---

## 6. Definición de Hecho

- [ ] AlchemicalChestBlockEntity implementa `LidBlockEntity`
- [ ] `openNess` field (0-5 int) con getter `getOpenNess(float)`
- [ ] ContainerData sincroniza openNess al menú
- [ ] ChestRenderer implementado con `BlockEntityRenderer<T,S>`
- [ ] `createRenderState()`, `extractRenderState()`, `submit()` funcionales
- [ ] Tapa rota suavemente (0° a 90°) en `submit()`
- [ ] Registrado en EquivalentLegacyRenderers
- [ ] Sin crashes de rendering
- [ ] Compilación clean
- [ ] Probar en modpack:
  - [ ] Click en chest → tapa se abre (animación 5 ticks)
  - [ ] Menú abierto → tapa completamente abierta
  - [ ] Menú cerrado → tapa se cierra suavemente
  - [ ] Sin parpadeos o saltos de rotación
- [ ] Commit: `feat: implement ChestRenderer with LidBlockEntity (Phase 2.4b, v1.2.0-beta.7)`

---

## 7. Restricciones Técnicas

### NeoForge 26.2
- `LidBlockEntity` interfaz requerida
- `BlockEntityRenderer<T,S>` con `createRenderState`/`extractRenderState`/`submit`
- `ContainerData` para sincronización servidor→cliente
- `Axis.XP.rotationDegrees()` para rotación

### Performance
- Sin raycasts en `extractRenderState()`
- Cálculo de ángulo: simple interpolación lineal
- PoseStack correctamente stackeado (push/pop balanceado)

### Compatibility
- Fallback a vanilla chest model si custom no renderiza
- No requiere assets nuevos (opcional solo si se mejora modelo)

---

## 8. Deviaciones del Plan 2.2

Plan original Fase 2.2 (now 2.4b) era:
- Renderizar tapa de Alchemical Chest
- Animación suave (10 ticks)
- Detectar estado abierto/cerrado

Implementación real:
- Detectar vía `LidBlockEntity.getOpenNess()` (no ticksOpen)
- Animación 5 ticks (más rápido, mejor UX)
- API NeoForge 26.2 real (no legacy)

---

## 9. Próximas Fases

- **Fase 3**: GUI improvements (transmutation screen custom)
- **Fase 4**: Mob farming system

