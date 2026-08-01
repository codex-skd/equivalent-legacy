package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.EquivalentLegacy;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, EquivalentLegacy.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<TransmutationContainer>> TRANSMUTATION =
            MENU_TYPES.register("transmutation",
                    () -> new MenuType<>(TransmutationContainer::new, FeatureFlagSet.of()));

    public static final DeferredHolder<MenuType<?>, MenuType<CollectorMenu>> COLLECTOR =
            MENU_TYPES.register("collector",
                    () -> new MenuType<>((IContainerFactory<CollectorMenu>) (id, inv, buf) ->
                            new CollectorMenu(id, inv, buf.readBlockPos()), FeatureFlagSet.of()));

    public static final DeferredHolder<MenuType<?>, MenuType<RelayMenu>> RELAY =
            MENU_TYPES.register("relay",
                    () -> new MenuType<>((IContainerFactory<RelayMenu>) (id, inv, buf) ->
                            new RelayMenu(id, inv, buf.readBlockPos()), FeatureFlagSet.of()));

    public static final DeferredHolder<MenuType<?>, MenuType<CondenserMenu>> CONDENSER =
            MENU_TYPES.register("condenser",
                    () -> new MenuType<>((IContainerFactory<CondenserMenu>) (id, inv, buf) ->
                            new CondenserMenu(id, inv, buf.readBlockPos()), FeatureFlagSet.of()));

    public static final DeferredHolder<MenuType<?>, MenuType<BagMenu>> BAG =
            MENU_TYPES.register("bag",
                    () -> new MenuType<>((IContainerFactory<BagMenu>) (id, inv, buf) ->
                            new BagMenu(id, inv, buf.readEnum(net.minecraft.world.InteractionHand.class)), FeatureFlagSet.of()));

    public static final DeferredHolder<MenuType<?>, MenuType<ChestMenu>> CHEST =
            MENU_TYPES.register("chest",
                    () -> new MenuType<>((IContainerFactory<ChestMenu>) (id, inv, buf) ->
                            new ChestMenu(id, inv, buf.readBlockPos()), FeatureFlagSet.of()));

    public static final DeferredHolder<MenuType<?>, MenuType<MatterFurnaceMenu>> FURNACE =
            MENU_TYPES.register("furnace",
                    () -> new MenuType<>((id, inv) -> new MatterFurnaceMenu(id, inv), FeatureFlagSet.of()));

    private ModMenuTypes() {}
}
