package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.EquivalentLegacy;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, EquivalentLegacy.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<TransmutationContainer>> TRANSMUTATION =
            MENU_TYPES.register("transmutation",
                    () -> new MenuType<>(TransmutationContainer::new, FeatureFlagSet.of()));

    private ModMenuTypes() {}
}
