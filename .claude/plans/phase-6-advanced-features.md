# Phase 6 — Advanced Features & Polish

## Objetivo
Expandir Equivalent Legacy con características avanzadas que hacen el sistema EMC más potente y manejable:
- Network sync entre múltiples Alchemical Chests
- Auto-condensing (items → EMC conversion)
- Sorting UI y filtros
- Mejoras visuales y pulido general

## Scope Completo

### Option A: Alchemical Chest Network (Recomendado para Phase 6)
**Priority**: Alta — completa el sistema de almacenamiento

1. **Chest Linking**
   - Detectar chests adyacentes automáticamente
   - Red Matter Pedestal con modo "Link" para conectar chests
   - Visual indicator (particles/glow cuando están linkados)
   - Max 8-16 chests por red (configurable)

2. **Network EMC Pool**
   - Chests conectados comparten EMC disponible
   - Un "master" chest (first clicked) muestra estado de red
   - Automatización: Collectors → Network Pool → Condensers
   - Sincronización instantánea (no delays)

3. **Network UI**
   - Mostrar conexión en Alchemical Chest GUI
   - "Network Status" botón → lista de conectados
   - EMC total disponible en red
   - Individual chest capacity vs network total

### Option B: Auto-Condensing (Phase 6 o Phase 7)
**Priority**: Media — mejora workflow pero no crítico

1. **Auto-Transmute Items → EMC**
   - Dark/Red Matter Pedestal con "Condense" mode
   - Items en chest → automáticamente convertidos a EMC
   - Configurable: toggle on/off, speed, item whitelist
   - Usa EmcValues para costear items

2. **Automatic Item Feed**
   - Pedestal extrae items de conectados (hopper-like)
   - Convierte a EMC via EMCHelper
   - Agrega EMC a network pool
   - Visual feedback: particles cuando condensa

### Option C: Storage UI Enhancements (Phase 6 o 7)
**Priority**: Media — comodidad

1. **Sort/Filter Buttons**
   - "Sort" → organiza items por tipo/cantidad
   - "Filter" → whitelist/blacklist items
   - Persiste configuración per-chest

2. **Quick Search**
   - Search box en GUI
   - Highlightea matching items
   - Keyboard shortcut (Ctrl+F)

### Option D: General Polish (Phase 6)
**Priority**: Baja — mejoras menores

1. **Textures & Models**
   - Mejorar texturas de furnaces/chests
   - Animación de tapa más suave en chests
   - Particle effects cuando fabrican/condesan

2. **Performance**
   - Optimize EMC calculations
   - Cache recipe lookups
   - Reduce network packets

3. **Config/Tunables**
   - Max EMC per chest (configurable)
   - Transmutation speed (client preference)
   - Mob farm detection range
   - Network sync latency

## Recomendación para Phase 6

**Implementar Option A (Alchemical Chest Network)** porque:
1. Completa el sistema de almacenamiento
2. Habilita automation workflows (Collectors → Network → Condensers)
3. Es feature natural después de Phase 5.2
4. No requires breaking changes

**Defer Options B-D a Phase 7** porque:
- Auto-condensing es nice-to-have (manual transmute already works)
- UI enhancements son polish
- Performance optimizations pueden venir después

## Phase 6.1 — Alchemical Chest Network

### New Classes
1. **ChestNetworkManager** (singleton)
   - Manages all connected chest networks
   - Track networks by ID
   - Handle linking/unlinking
   - Broadcast EMC changes

2. **ChestNetwork** (inner class or separate)
   - List of connected chest BlockPos
   - Total EMC pool (sum of all chests)
   - Master chest designation
   - Sync clients when EMC changes

3. **ChestLinkingHandler** (event listener)
   - PlayerInteractBlockEvent with Red Matter Pedestal
   - Mode: "Link" → select 2 chests to connect
   - Mode: "Unlink" → select chest to remove from network
   - Persist links in NBT

### Modified Classes
- **AlchemicalChestBlockEntity**
  - Add network ID field
  - Query network pool for EMC instead of local
  - Support for unloading/reloading networks
  - onLoad/onUnload to register/deregister with manager

- **AlchemicalChestScreen**
  - Show network status if linked
  - Display total network EMC
  - Button to view connected chests
  - Visual indicator (colored border if linked)

### Data Files
- Pedestal mode tag/key for "linking" functionality
- Network persistence file (per-dimension)

### Network Packets
- SyncNetworkEmc (broadcast EMC changes)
- UpdateNetworkList (update UI when chests connect/disconnect)

## Criteria de Éxito Phase 6.1

- [ ] Compila sin errores
- [ ] Alchemical Chests can be linked via Red Matter Pedestal
- [ ] Linking creates visible network (particles/glow)
- [ ] EMC syncs between linked chests in real-time
- [ ] Unlink works properly (removes from network)
- [ ] Network UI shows connected chests
- [ ] EMC total correct across network
- [ ] Collectors/Condensers work with network EMC
- [ ] Networks persist on chunk unload/reload
- [ ] Max chest limit enforced (8-16 chests)
- [ ] Build SUCCESS
- [ ] Commit con phase completada

## Alternativa si Phase 6.1 es demasiado:

**Phase 6 Lite** — Solo polish y optimización:
- Config file for tuning (max EMC, speeds, etc)
- Performance optimizations (recipe caching, etc)
- Visual improvements (better textures, particles)
- Bug fixes from Phase 5

Esto daría v1.3.0-beta.5 como stepping stone antes de Phase 7 stabilization.

## Estimado
- Phase 6.1 (Network): OpenCode 1-2 sesiones + 1 sesión de fixes = v1.3.0-beta.5 + beta.6
- Phase 6 Lite: OpenCode 1 sesión = v1.3.0-beta.5
- Phase 7 (Testing): Manual testing + UI polish = v1.3.0-RELEASE

## Post-Phase 6
Phase 7: Comprehensive testing, bug fixes, final stabilization → v1.3.0-RELEASE ready for production.
