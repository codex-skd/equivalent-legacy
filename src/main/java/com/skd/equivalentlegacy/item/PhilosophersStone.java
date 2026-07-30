package com.skd.equivalentlegacy.item;

import com.skd.equivalentlegacy.emc.EMCHelper;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PhilosophersStone extends Item {
    public PhilosophersStone(Properties properties) {
        super(properties);
    }

    public long getEmcValue(ItemStack stack) {
        return EMCHelper.getEMC(NSSItem.createItem(this));
    }
}
