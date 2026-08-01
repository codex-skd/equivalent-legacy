package com.skd.equivalentlegacy.block.entity;

import com.skd.equivalentlegacy.block.entity.MachineTiers.CondenserTier;
import com.skd.equivalentlegacy.emc.EMCHelper;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.gui.CondenserMenu;
import com.skd.equivalentlegacy.item.KleinStar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CondenserBlockEntity extends BaseMachineBlockEntity {
    public static final int TARGET_SLOT = 0;
    public static final int KLEIN_SLOT = 1;
    public static final int OUTPUT_SLOT = 2;

    private String targetId;
    private int chargeProgress;

    public CondenserBlockEntity(BlockPos pos, BlockState state) {
        super(EquivalentLegacyBlockEntities.CONDENSER.get(), pos, state, 3);
    }

    public String getTargetId() {
        return targetId;
    }

    public int getChargeProgress() {
        return chargeProgress;
    }

    public static BlockEntityTicker<CondenserBlockEntity> ticker() {
        return (level, pos, state, be) -> tick(be);
    }

    private static void tick(CondenserBlockEntity be) {
        Level level = be.getLevel();
        if (level == null || level.isClientSide()) return;
        be.tickServer(level);
    }

    private void tickServer(Level level) {
        CondenserTier tier = MachineTiers.CondenserTier.of(getBlockState().getBlock());
        learnTarget();
        drainKleinStar(tier);
        pullFromRelays(level, tier);
        produceTarget();
        setChanged();
    }

    private void learnTarget() {
        ItemStack targetStack = inventory.getItem(TARGET_SLOT);
        if (targetStack.isEmpty()) return;
        Identifier id = BuiltInRegistries.ITEM.getKey(targetStack.getItem());
        if (id != null) {
            targetId = id.toString();
        }
        inventory.setItem(TARGET_SLOT, ItemStack.EMPTY);
    }

    private void drainKleinStar(CondenserTier tier) {
        ItemStack stack = inventory.getItem(KLEIN_SLOT);
        if (stack.isEmpty() || !(stack.getItem() instanceof KleinStar star)) return;
        long space = tier.emcCapacity - (long) emc;
        if (space <= 0) return;
        long extract = Math.min(star.getStoredEmc(stack), Math.min(space, tier.transferRate));
        if (extract > 0) {
            star.extractEmc(stack, extract, false);
            emc += extract;
        }
    }

    private void pullFromRelays(Level level, CondenserTier tier) {
        for (Direction dir : Direction.values()) {
            BlockEntity be = level.getBlockEntity(worldPosition.relative(dir));
            if (be instanceof RelayBlockEntity relay) {
                long available = relay.getEmc();
                long space = tier.emcCapacity - (long) emc;
                if (available <= 0 || space <= 0) continue;
                long take = Math.min(available, Math.min(space, tier.transferRate));
                if (take > 0) {
                    emc += relay.takeEmc(take);
                }
            }
        }
    }

    private void produceTarget() {
        if (targetId == null || emc < 1) {
            chargeProgress = 0;
            return;
        }
        Identifier id = Identifier.parse(targetId);
        long cost = EMCHelper.getEMC(NSSItem.createItem(id));
        if (cost <= 0) {
            chargeProgress = 0;
            return;
        }
        ItemStack out = inventory.getItem(OUTPUT_SLOT);
        if (out.getCount() >= out.getMaxStackSize()) {
            chargeProgress = 100;
            return;
        }
        if (emc < cost) {
            chargeProgress = (int) (100 * emc / cost);
            return;
        }
        ItemStack result = new ItemStack(BuiltInRegistries.ITEM.getValue(id), 1);
        if (result.isEmpty()) return;
        if (out.isEmpty()) {
            inventory.setItem(OUTPUT_SLOT, result);
        } else if (ItemStack.isSameItemSameComponents(out, result)) {
            out.grow(1);
        } else {
            return;
        }
        emc -= cost;
        chargeProgress = 0;
    }

    @Override
    protected MenuProvider createMenuProvider() {
        return new SimpleMenuProvider((id, inv, p) -> new CondenserMenu(id, inv, worldPosition), getDisplayName());
    }

    @Override
    protected Component getDisplayName() {
        return Component.translatable("container.equivalent_legacy.condenser");
    }
}
