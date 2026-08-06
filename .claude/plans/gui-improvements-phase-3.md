# Plan: Fase 3 - GUI Improvements (Equivalent Legacy)

**Objetivo**: Implementar interfaz gráfica personalizada para transmutación usando Philosopher's Stone.

**Versión**: 1.2.0-beta.8

---

## Resumen

Actualmente no existe GUI para transmutación — solo la capacidad del backend de transmutación mundo está implementada. Fase 3 crea un **TransmutationScreen** que permite al jugador:

1. Right-click Philosopher's Stone para abrir el GUI
2. Ver lista de **items conocidos** (scrollable, ordenados por EMC)
3. **Input slot**: click en un item de la lista → intenta aprender (requiere tenerlo en inventory)
4. **Output slot**: muestra el resultado de transmutación del item seleccionado
5. **EMC display**: muestra EMC actual del jugador (desde Data Attachment)
6. **Cost indicator**: muestra el costo EMC de la transmutación seleccionada (color: verde si puede, rojo si no)

---

## 1. Crear TransmutationScreen (nueva clase)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/gui/TransmutationScreen.java`

**Propósito**: Implementar el GUI usando NeoForge 26.2 Screen API

**Estructura base**:
```java
@OnlyIn(Dist.CLIENT)
public class TransmutationScreen extends AbstractContainerScreen<TransmutationMenu> {
    
    // Campos
    private final TransmutationContainer container;  // data access
    private int scrollOffset = 0;
    private static final int ITEMS_PER_PAGE = 9;
    private static final int SLOTS_WIDTH = 18;
    private static final int SLOTS_HEIGHT = 18;
    
    public TransmutationScreen(TransmutationMenu menu, Inventory playerInventory, Component component) {
        super(menu, playerInventory, component);
        this.imageWidth = 176;
        this.imageHeight = 222;
    }
    
    @Override
    protected void init() {
        super.init();
        // Configurar posiciones de widgets
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBg(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
    
    @Override
    protected void renderBg(GuiGraphics guiGraphics) {
        // Dibujar fondo del GUI (color sólido o textura)
        guiGraphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFF8B8B8B);
        
        // Dibujar lista de items scrollable
        renderScrollableItemList(guiGraphics);
        
        // Dibujar input/output slots
        renderSlots(guiGraphics);
        
        // Dibujar EMC display
        renderEMCDisplay(guiGraphics);
    }
    
    private void renderScrollableItemList(GuiGraphics guiGraphics) {
        // Obtener lista de items transmutables desde WorldTransmutationManager
        // Mostrar ITEMS_PER_PAGE items comenzando desde scrollOffset
        // Click handling: select item → update output slot
    }
    
    private void renderSlots(GuiGraphics guiGraphics) {
        // Dibujar rectángulo input slot (seleccionado desde lista)
        // Dibujar rectángulo output slot (resultado de transmutación)
    }
    
    private void renderEMCDisplay(GuiGraphics guiGraphics) {
        // Obtener EMC del jugador desde PlayerEMCData (Data Attachment)
        // Dibujar texto: "EMC: <valor>" en posición inferior derecha
        // Si hay item seleccionado: mostrar costo y disponibilidad (verde/rojo)
    }
}
```

**Notas técnicas NeoForge 26.2**:
- Screen API: `AbstractContainerScreen<T>` es base estándar
- GuiGraphics: reemplaza PoseStack/MultiBufferSource
- Component para títulos (translatables)
- `@OnlyIn(Dist.CLIENT)` para clases client-side

---

## 2. Crear TransmutationMenu (si no existe)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/gui/TransmutationMenu.java` (probablemente ya existe)

**Propósito**: Contenedor (container) que comunica cliente-servidor

**Cambios mínimos** (si ya existe):
- Asegurar que hereda de `AbstractContainerMenu`
- Tener slots para input/output (2 slots mínimo)
- Sincronizar datos EMC del jugador via `ContainerData` o Data Attachment
- No agregar recetas — eso es backend (WorldTransmutationManager)

---

## 3. Crear/Actualizar TransmutationContainer

**Archivo**: `src/main/java/com/skd/equivalentlegacy/gui/TransmutationContainer.java`

**Propósito**: Inventario de 2 slots (input + output) — es ephemeral, no persiste

**Cambios**:
- Extender de `SimpleContainer` con 2 slots
- No guardar en NBT (es temporal)
- Usar solo para drag-drop del GUI

---

## 4. Crear/Actualizar PlayerEMCData (Data Attachment)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/data/PlayerEMCData.java`

**Propósito**: Almacenar EMC del jugador persistentemente

**Cambios mínimos** (si ya existe):
- Getter: `long getEMC()` o `long getEnergyLevel()`
- Setter: `void setEMC(long amount)`
- Sincronización: enviar al cliente via `ContainerData` en menu
- Validar que se carga/guarda correctamente en servidor

---

## 5. Registrar Screen en ModScreens (si es necesario)

**Archivo**: `src/main/java/com/skd/equivalentlegacy/gui/ModMenuTypes.java` (o similar)

**Cambios**:
- Verificar que existe `MenuType<TransmutationMenu>`
- Si no, crear:
  ```java
  public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MODID);
  public static final RegistryObject<MenuType<TransmutationMenu>> TRANSMUTATION = 
      MENUS.register("transmutation", () -> new MenuType<>(TransmutationMenu::new));
  ```
- Registrar `MENUS` en event bus (`FMLCommonSetupEvent`)

---

## 6. Right-click Philosopher's Stone para abrir GUI

**Archivo**: `src/main/java/com/skd/equivalentlegacy/item/PhilosophersStone.java`

**Cambios**:
- Extender de `Item`
- Sobrescribir `use(ItemStack, Player, InteractionHand)`:
  ```java
  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      if (!level.isClientSide && player.containerMenu == null) {
          player.openMenu(new SimpleMenuProvider(
              (id, inv, p) -> new TransmutationMenu(id, inv, p.blockPosition()),
              Component.literal("Transmutation")
          ));
      }
      return InteractionResultHolder.success(player.getItemInHand(hand));
  }
  ```
- Lado **cliente**: manejar en event handler o screen registry

---

## 7. Localization (lenguajes)

**Archivos**: `src/main/resources/assets/equivalent_legacy/lang/en_us.json` + 17 otros

**Claves a agregar**:
- `gui.equivalent_legacy.transmutation` — título del GUI
- `gui.equivalent_legacy.transmutation.input` — label input slot
- `gui.equivalent_legacy.transmutation.output` — label output slot
- `gui.equivalent_legacy.transmutation.emc` — label EMC actual
- `gui.equivalent_legacy.transmutation.cost` — label costo transmutación
- `gui.equivalent_legacy.transmutation.can_transmute` — "Can transmute"
- `gui.equivalent_legacy.transmutation.insufficient_emc` — "Insufficient EMC"

---

## 8. Event Handlers para abrir GUI en cliente

**Archivo**: `src/main/java/com/skd/equivalentlegacy/events/ClientEvents.java`

**Agregar handler**:
- `onScreenOpen(ScreenEvent.Init event)` — registrar screen
- O usar `MenuScreens.register()` en client setup:
  ```java
  MenuScreens.register(ModMenuTypes.TRANSMUTATION.get(), TransmutationScreen::new);
  ```

---

## 9. Restricciones Técnicas

### NeoForge 26.2
- `AbstractContainerScreen<T>` para GUIs con slots
- `GuiGraphics` para rendering (reemplaza legacy rendering)
- `ContainerData` o `Attachment` para sincronización servidor-cliente
- `MenuScreens.register()` en `FMLClientSetupEvent`

### UI/UX
- Background color: gris oscuro (`0xFF8B8B8B`) o textura personalizada
- Font: vanilla Minecraft (bold para títulos)
- Scroll: up/down arrow buttons o mouse wheel (TBD)
- Tooltips: mostrar en hover si hay espacio
- Sin crashes con inventory vacío

### Performance
- Cachear lista de items (no recalcular cada frame)
- Renderizar solo items visibles (9 por página)
- Scroll suave (sin lag con 90+ items)

### Compatibility
- Funciona en single-player y multiplayer (sincronización correcta)
- No interferir con otros GUIs (JEI, WTHIT, etc.)
- Mantener compatible con mods de inventory (Curios, etc.)

---

## 10. Definition of Done

- [ ] `TransmutationScreen` compilado y se abre sin crashes
- [ ] Lista scrollable de items conocidos funciona (9 por página)
- [ ] Click en item → selecciona y muestra en output slot
- [ ] Input slot muestra item seleccionado (si está en inventory)
- [ ] Output slot muestra resultado de transmutación
- [ ] EMC display muestra EMC actual del jugador
- [ ] Cost indicator muestra "green" si puede transmutir, "red" si no
- [ ] Right-click Philosopher's Stone abre el GUI
- [ ] GUI se cierra cuando el jugador presiona ESC
- [ ] Localization: todas las claves traducidas (18 idiomas)
- [ ] Sin errores de rendering
- [ ] Sin crashes en single-player ni multiplayer
- [ ] Compilación clean
- [ ] Commit: `feat: add custom transmutation GUI (Phase 3, v1.2.0-beta.8)`

---

## 11. Fases posteriores

- **Fase 4**: Mob farming system (spawner control, experience collection)
- **Fase 5**: Advanced transmutation (recipes custom, transmutation tiers)
- **Release 1.3.0**: Feature complete cuando todas las fases estén done

---

## 12. Notas de Implementación

- No crear assets nuevos (texturas de GUI) — usar colores sólidos y fuentes vanilla
- No cambiar WorldTransmutationManager — eso ya está done y funcionando
- PlayerEMCData probablemente ya existe (verificar si está implementada)
- El inventory del jugador se sincroniza automáticamente con el cliente
- No necesita almacenamiento persistente en disco (es ephemeral del GUI)

---

## 13. Archivos que se crearán/modificarán

**Crear (nuevos)**:
- `src/main/java/com/skd/equivalentlegacy/gui/TransmutationScreen.java`

**Modificar (existentes)**:
- `src/main/java/com/skd/equivalentlegacy/item/PhilosophersStone.java` — right-click handler
- `src/main/java/com/skd/equivalentlegacy/gui/TransmutationMenu.java` — si necesita cambios
- `src/main/java/com/skd/equivalentlegacy/gui/ModMenuTypes.java` — registrar menu
- `src/main/java/com/skd/equivalentlegacy/events/ClientEvents.java` — screen registration
- 18 archivos `lang/*.json` — agregar claves de GUI

**Verificar existencia**:
- `src/main/java/com/skd/equivalentlegacy/gui/TransmutationContainer.java`
- `src/main/java/com/skd/equivalentlegacy/data/PlayerEMCData.java`
