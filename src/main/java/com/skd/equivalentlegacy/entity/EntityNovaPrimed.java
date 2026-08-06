package com.skd.equivalentlegacy.entity;

import com.skd.equivalentlegacy.EquivalentLegacyEntities;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class EntityNovaPrimed extends Entity {
    public static final short DEFAULT_FUSE_TIME = 80;

    private static final EntityDataAccessor<Integer> DATA_FUSE_ID =
            SynchedEntityData.defineId(EntityNovaPrimed.class, EntityDataSerializers.INT);

    protected float blastRadius = 4.0F;
    protected boolean fireOnExplosion = false;
    protected Level.ExplosionInteraction explosionInteraction = Level.ExplosionInteraction.TNT;

    public EntityNovaPrimed(EntityType<? extends EntityNovaPrimed> type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }

    public EntityNovaPrimed(Level level, double x, double y, double z) {
        this(EquivalentLegacyEntities.NOVA.get(), level);
        this.setPos(x, y, z);
        this.setDeltaMovement(0.0, 0.2, 0.0);
        this.setFuse(DEFAULT_FUSE_TIME);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        entityData.define(DATA_FUSE_ID, (int) DEFAULT_FUSE_TIME);
    }

    public void setFuse(int fuse) {
        this.entityData.set(DATA_FUSE_ID, fuse);
    }

    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void tick() {
        this.applyGravity();
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }

        int fuse = this.getFuse() - 1;
        this.setFuse(fuse);
        if (fuse <= 0) {
            this.discard();
            if (!this.level().isClientSide()) {
                this.explode();
            }
        }
    }

    protected void explode() {
        if (this.level() instanceof ServerLevel level) {
            this.level().explode(
                    this,
                    Explosion.getDefaultDamageSource(this.level(), this),
                    null,
                    this.getX(),
                    this.getY() + 0.0625,
                    this.getZ(),
                    this.blastRadius,
                    this.fireOnExplosion,
                    this.explosionInteraction
            );
        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (this.isRemoved()) return false;
        if (source.is(net.minecraft.tags.DamageTypeTags.IS_PROJECTILE)) {
            this.explode();
            this.discard();
            return true;
        }
        return false;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        output.putShort("fuse", (short) this.getFuse());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        this.setFuse(input.getShortOr("fuse", DEFAULT_FUSE_TIME));
    }
}