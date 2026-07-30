package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.emc.EMCHelper;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.gui.TransmutationContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PhilosophersStone extends Item {
    public PhilosophersStone(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            player.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new TransmutationContainer(id, inv),
                    Component.translatable("container.equivalent_legacy.transmutation")
            ));
        }
        return InteractionResult.CONSUME;
    }

    public long getEmcValue(ItemStack stack) {
        return EMCHelper.getEMC(NSSItem.createItem(this));
    }
}
