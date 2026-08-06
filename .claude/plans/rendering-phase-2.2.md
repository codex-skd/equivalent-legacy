# Plan: Fase 2.2 - Rendering Mejorado (Equivalent Legacy)

**Objetivo**: Implementar custom renderers para PedestalBlockEntity (items flotantes con rotación), ChestBlockEntity (animación de apertura), y TransmutationRenderingOverlay (HUD de opciones).

**Versión**: 1.2.0-beta.5

---

## 1. PedestalRenderer (Completo)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/rendering/PedestalRenderer.java`

**Responsabilidades**:
- Renderizar ItemStack flotante sobre pedestales (base, DM, RM)
- Rotación suave Y-axis: 360° cada 160 ticks
- Levitación: 0.5 bloques arriba del centro del bloque
- Brillo opcional para items especiales (Philosopher's Stone)
- Usar `net.minecraft.client.renderer.blockentity.BlockEntityRenderer<PedestalBlockEntity>`

**API de NeoForge 26.2**:
```java
public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    @Override
    public void render(PedestalBlockEntity entity, float partialTick, PoseStack poseStack, 
                      MultiBufferSource buffer, int packedLight, int packedOverlay)
}
```

**Lógica**:
1. Si `entity.getItem().isEmpty()` → no renderizar
2. Calcular posición: `blockCenter + offset(0.5 + bobbing)`
3. Calcular rotación: `angle = (tickCounter * 2.25) % 360` (360° cada 160 ticks)
4. `poseStack.pushPose()` → trasladar + rotar → `ItemRenderer.renderGuiItem()`
5. Si item es Philosopher's Stone → aplicar glow/brillo

**Métodos Helper en PedestalBlockEntity**:
- `public ItemStack getItem()` — ya existe
- `public Vec3 getItemRenderPos(float partialTick)` — NEW: calcula pos con bobbing
- `public float getItemRenderRotation(float partialTick)` — NEW: calcula ángulo rotación

---

## 2. ChestRenderer (Simplificado)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/rendering/ChestRenderer.java`

**Responsabilidades**:
- Animar tapa de Alchemical Chest
- Detectar estado abierto/cerrado
- Rotación: 0° (cerrado) → 90° (abierto) en 10 ticks smooth

**Lógica**:
1. Detectar si AlchemicalChestBlockEntity está abierto (flag o parámetro)
2. Si abierto: angle = min(90, ticksOpening * 9)
3. Si cerrado: angle = max(0, ticksClosing * 9)
4. Renderizar usando modelo del chest + rotación alrededor del eje X

**Nota**: Usar vanilla chest model como base, solo animar la rotación

---

## 3. TransmutationRenderingOverlay (Minimalist)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/rendering/TransmutationRenderingOverlay.java`

**Responsabilidades**:
- Renderizar HUD overlay cuando jugador tiene Philosopher's Stone/Transmutation Stone
- Mostrar bloques transmutables cercanos (rango: 4 bloques)
- Mostrar: bloque origen → bloque destino + costo EMC
- Colores: verde (ok), rojo (insuficiente EMC), gris (no transmutable)

**Lógica**:
1. Event listener para `ScreenEvent.Init.Post` (client-side)
2. Si jugador NO tiene stone en mano → no renderizar
3. Raycast 4 bloques alrededor
4. Si hay bloques transmutables → calcular costo EMC
5. Renderizar en esquina superior derecha (offset: 10px desde bordes)
6. Formato: `[Stone Icon] → [Destino Icon] | Cost: 1024 EMC`

**Tamaño**: 64x64 px, semi-transparent (alpha: 0.75)

---

## 4. ClientEvents (Nueva clase)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/events/ClientEvents.java`

**Propósito**: Event listeners client-side

```java
@Mod.EventBusSubscriber(modid = EquivalentLegacy.MODID, value = Dist.CLIENT)
public final class ClientEvents {
    
    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Registrar renderers
        EquivalentLegacyRenderers.registerBlockEntityRenderers();
    }
    
    @SubscribeEvent
    static void onRenderGuiEvent(ScreenEvent.Init.Post event) {
        // Renderizar overlay (delegado a TransmutationRenderingOverlay)
    }
}
```

---

## 5. EquivalentLegacyRenderers (Dispatcher)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/rendering/EquivalentLegacyRenderers.java`

**Propósito**: Central registration point

```java
@Mod.EventBusSubscriber(modid = EquivalentLegacy.MODID, 
                       bus = Mod.EventBusSubscriber.Bus.MOD, 
                       value = Dist.CLIENT)
public final class EquivalentLegacyRenderers {
    
    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(EquivalentLegacyBlockEntities.PEDESTAL.get(), 
                                        PedestalRenderer::new);
            BlockEntityRenderers.register(EquivalentLegacyBlockEntities.DM_PEDESTAL.get(), 
                                        PedestalRenderer::new);
            BlockEntityRenderers.register(EquivalentLegacyBlockEntities.RM_PEDESTAL.get(), 
                                        PedestalRenderer::new);
            BlockEntityRenderers.register(EquivalentLegacyBlockEntities.ALCHEMICAL_CHEST.get(), 
                                        ChestRenderer::new);
        });
    }
}
```

---

## 6. Modificaciones a Existing Classes

### EquivalentLegacyClient.java
- Verificar que ClientEvents está siendo registrado
- Anotación: `@Mod.EventBusSubscriber(value = Dist.CLIENT)` ya debería estar

### PedestalBlockEntity.java
Agregar métodos helper:
```java
public Vec3 getItemRenderPos(float partialTick) {
    int ticks = this.getLevel().getGameTime();
    double bob = 0.06 * Math.sin(ticks * 0.05);
    return Vec3.atCenterOf(this.getBlockPos()).add(0, 0.5 + bob, 0);
}

public float getItemRenderRotation(float partialTick) {
    int ticks = this.getLevel().getGameTime();
    return ((ticks + partialTick) * 2.25F) % 360F;
}
```

### DMPedestalBlockEntity.java, RMPedestalBlockEntity.java
- Heredan de PedestalBlockEntity → sin cambios necesarios

### AlchemicalChestBlockEntity.java
- Agregar flag `isOpen` o getter para estado
- `public boolean isOpen()` — detectar si jugador interactuó recientemente

---

## Restricciones Técnicas

### NeoForge 26.2.0.37-beta
- `BlockEntityRenderer<T>` interface con método `render(...)`
- `PoseStack` y `MultiBufferSource` para rendering
- `ItemRenderer.renderGuiItem()` para items en HUD
- `ScreenEvent.Init.Post` para overlays

### Performance
- **No raycasts cada frame**: cachear bloques cercanos con timeout (20 ticks)
- **Cálculos overlay limitados**: 1 vez por tick (no cada frame)
- **PoseStack correctamente stackeado**: siempre pushPose/popPose en pares

### Lado Cliente
- Todos los renderers anotados con `@Dist.CLIENT`
- No ejecutar código servidor en ClientEvents
- `Minecraft.getInstance()` para acceso a cliente

---

## Definition of Done

- [ ] `PedestalRenderer` renderiza items flotantes sobre pedestales
- [ ] Items rotan suavemente (360° cada 160 ticks)
- [ ] Items tienen efecto de levitación (bobbing)
- [ ] Brillo aplicado a Philosopher's Stone
- [ ] `ChestRenderer` renderiza tapa de chest
- [ ] Tapa se abre/cierra suavemente (10 ticks)
- [ ] `TransmutationRenderingOverlay` aparece cuando jugador tiene stone
- [ ] Overlay muestra bloque actual → destino + costo EMC
- [ ] Colores correctos (verde/rojo/gris)
- [ ] `EquivalentLegacyRenderers` registra todos los renderers correctamente
- [ ] `ClientEvents` ejecuta sin errores
- [ ] Métodos helper en PedestalBlockEntity compilados
- [ ] Sin crashes de rendering
- [ ] Sin memory leaks (PoseStack correctamente stackeado)
- [ ] Performance aceptable (FPS no baja)
- [ ] Compilación clean (0 warnings)
- [ ] Commit message: `feat: add custom renderers (Phase 2.2)`

---

## Notas de Implementación

1. **PedestalRenderer es prioritario**: Es lo más visible (items flotantes)
2. **ChestRenderer es simple**: Solo rotación de tapa, sin físicas complejas
3. **Overlay es bonus**: Nice-to-have, puede ser minimalist
4. **No JEI integration**: Será Fase 2.4
5. **Usar constantes**: Offsets, rotaciones, tiempos deben ser configurables
6. **Caching importante**: Raycasts deben cachearse 20 ticks para performance
