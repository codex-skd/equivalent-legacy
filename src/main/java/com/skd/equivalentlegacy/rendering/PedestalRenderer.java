package com.skd.equivalentlegacy.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.skd.equivalentlegacy.block.entity.PedestalBlockEntity;
import com.skd.equivalentlegacy.item.EquivalentLegacyItems;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public final class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity, PedestalRenderer.State> {
    public static final float CENTER_Y = 0.5F;
    public static final float LIFT = 0.5F;
    public static final float BOB_AMPLITUDE = 0.06F;
    public static final float DEGREES_PER_TICK = 2.25F;

    private final ItemModelResolver itemModelResolver;

    public PedestalRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(
            PedestalBlockEntity entity,
            State state,
            float partialTick,
            Vec3 cameraPosition,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress
    ) {
        BlockEntityRenderer.super.extractRenderState(entity, state, partialTick, cameraPosition, breakProgress);

        ItemStack displayed = entity.getDisplayedItem();
        if (displayed.isEmpty()) {
            state.item.clear();
            state.hasItem = false;
            state.specialGlow = false;
            return;
        }
        state.hasItem = true;
        long time = entity.getLevel() != null ? entity.getLevel().getGameTime() : 0L;
        state.bob = (float) (BOB_AMPLITUDE * Math.sin((time + partialTick) * 0.05));
        state.rotation = ((time + partialTick) * DEGREES_PER_TICK) % 360.0F;
        state.specialGlow = displayed.is(EquivalentLegacyItems.PHILOSOPHERS_STONE.asItem());
        int seed = (int) (entity.getBlockPos().asLong() + displayed.hashCode());
        this.itemModelResolver.updateForTopItem(
                state.item, displayed, ItemDisplayContext.GROUND, entity.getLevel(), null, seed
        );
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (!state.hasItem || state.item.isEmpty()) return;

        poseStack.pushPose();
        poseStack.translate(0.5F, CENTER_Y + LIFT + state.bob, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(state.rotation));
        int light = state.specialGlow ? LightCoordsUtil.FULL_BRIGHT : state.lightCoords;
        state.item.submit(poseStack, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    public static final class State extends BlockEntityRenderState {
        public final ItemStackRenderState item = new ItemStackRenderState();
        public boolean hasItem = false;
        public float rotation = 0.0F;
        public float bob = 0.0F;
        public boolean specialGlow = false;
    }
}