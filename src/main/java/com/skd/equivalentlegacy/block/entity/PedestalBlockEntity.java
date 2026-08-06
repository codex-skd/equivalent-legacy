package com.skd.equivalentlegacy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class PedestalBlockEntity extends BlockEntity {
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private long emc;
    private int tickCounter;

    public PedestalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        this(EquivalentLegacyBlockEntities.PEDESTAL.get(), pos, state);
    }

    public ItemStack getDisplayedItem() {
        return items.get(0);
    }

    public boolean insertItem(ItemStack stack) {
        if (getDisplayedItem().isEmpty()) {
            items.set(0, stack.split(1));
            setChanged();
            sync();
            return true;
        }
        return false;
    }

    public InteractionResult toggleItem(Player player) {
        ItemStack displayed = getDisplayedItem();
        if (displayed.isEmpty()) {
            ItemStack held = player.getMainHandItem();
            if (!held.isEmpty()) {
                items.set(0, held.split(1));
                setChanged();
                sync();
                return InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        }
        Containers.dropItemStack(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.6, worldPosition.getZ() + 0.5, displayed.copy());
        items.set(0, ItemStack.EMPTY);
        setChanged();
        sync();
        return InteractionResult.CONSUME;
    }

    public void dropContents() {
        if (level == null || level.isClientSide()) return;
        Containers.dropContents(level, worldPosition, items);
        items.clear();
    }

    public long getEmc() {
        return emc;
    }

    public void addEmc(long amount) {
        this.emc += amount;
        setChanged();
    }

    public long takeEmc(long max) {
        long taken = Math.min(emc, max);
        this.emc -= taken;
        setChanged();
        return taken;
    }

    public int getTickCounter() {
        return tickCounter;
    }

    public static BlockEntityTicker<PedestalBlockEntity> ticker() {
        return (level, pos, state, be) -> be.serverTick();
    }

    protected void serverTick() {
        tickCounter++;
    }

    private void sync() {
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
    }

    public net.minecraft.world.phys.Vec3 getItemRenderPos() {
        return new net.minecraft.world.phys.Vec3(
                worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.75 + 0.06 * Math.sin(tickCounter * 0.05),
                worldPosition.getZ() + 0.5
        );
    }

    public float getItemRenderRotation() {
        return (tickCounter * 2.0F) % 360.0F;
    }

    public net.minecraft.world.phys.Vec3 getItemRenderPos(float partialTick) {
        long time = level != null ? level.getGameTime() : tickCounter;
        double bob = 0.06 * Math.sin((time + partialTick) * 0.05);
        return new net.minecraft.world.phys.Vec3(
                worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5 + bob,
                worldPosition.getZ() + 0.5
        );
    }

    public float getItemRenderRotation(float partialTick) {
        long time = level != null ? level.getGameTime() : tickCounter;
        return ((time + partialTick) * 2.25F) % 360.0F;
    }

    public boolean isMobFarmingSetup() {
        ItemStack item = getDisplayedItem();
        return !item.isEmpty() && (item.getItem().toString().contains("mind_stone") ||
                                    item.getItem().toString().contains("black_hole_band"));
    }

    public java.util.List<net.minecraft.world.entity.Mob> getMobsNearby() {
        if (level == null || level.isClientSide()) return java.util.Collections.emptyList();
        net.minecraft.world.phys.AABB range = new net.minecraft.world.phys.AABB(worldPosition).inflate(16);
        return level.getEntitiesOfClass(net.minecraft.world.entity.Mob.class, range);
    }

    public long collectNearbyXp() {
        if (level == null || level.isClientSide()) return 0;
        long totalXp = 0;
        net.minecraft.world.phys.AABB range = new net.minecraft.world.phys.AABB(worldPosition).inflate(16);
        var xpOrbs = level.getEntitiesOfClass(net.minecraft.world.entity.ExperienceOrb.class, range);
        for (var orb : xpOrbs) {
            totalXp += orb.getValue();
            orb.discard();
        }
        return totalXp;
    }

    public int collectNearbyDrops() {
        if (level == null || level.isClientSide()) return 0;
        int collected = 0;
        net.minecraft.world.phys.AABB range = new net.minecraft.world.phys.AABB(worldPosition).inflate(16);
        var items = level.getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, range);
        for (var item : items) {
            collected++;
            item.discard();
        }
        return collected;
    }

    public void registerControlledSpawner(BlockPos spawnerPos) {
        if (level == null) return;
        var spawner = level.getBlockEntity(spawnerPos);
        if (spawner != null) {
            com.skd.equivalentlegacy.mob_farming.MobFarmingManager.registerSpawner(spawnerPos, spawner);
        }
    }

    public void updateSpawnerBehavior(BlockPos spawnerPos, int delay, int count, int maxNearby) {
        com.skd.equivalentlegacy.mob_farming.MobFarmingManager.updateSpawnerConfig(spawnerPos, delay, count, maxNearby);
    }
}