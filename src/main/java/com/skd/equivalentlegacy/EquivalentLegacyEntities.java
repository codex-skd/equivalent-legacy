package com.skd.equivalentlegacy;

import com.skd.equivalentlegacy.entity.EntityNovaCataclysmPrimed;
import com.skd.equivalentlegacy.entity.EntityNovaCatalystPrimed;
import com.skd.equivalentlegacy.entity.EntityNovaPrimed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EquivalentLegacyEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, EquivalentLegacy.MODID);

    private static ResourceKey<EntityType<?>> key(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, EquivalentLegacy.rl(name));
    }

    public static final DeferredHolder<EntityType<?>, EntityType<EntityNovaPrimed>> NOVA =
            ENTITIES.register("nova", () -> EntityType.Builder.<EntityNovaPrimed>of(EntityNovaPrimed::new, MobCategory.MISC)
                    .clientTrackingRange(10).updateInterval(20).fireImmune()
                    .build(key("nova")));

    public static final DeferredHolder<EntityType<?>, EntityType<EntityNovaCatalystPrimed>> NOVA_CATALYST =
            ENTITIES.register("nova_catalyst", () -> EntityType.Builder.<EntityNovaCatalystPrimed>of(EntityNovaCatalystPrimed::new, MobCategory.MISC)
                    .clientTrackingRange(10).updateInterval(20).fireImmune()
                    .build(key("nova_catalyst")));

    public static final DeferredHolder<EntityType<?>, EntityType<EntityNovaCataclysmPrimed>> NOVA_CATACLYSM =
            ENTITIES.register("nova_cataclysm", () -> EntityType.Builder.<EntityNovaCataclysmPrimed>of(EntityNovaCataclysmPrimed::new, MobCategory.MISC)
                    .clientTrackingRange(12).updateInterval(20).fireImmune()
                    .build(key("nova_cataclysm")));

    private EquivalentLegacyEntities() {}
}