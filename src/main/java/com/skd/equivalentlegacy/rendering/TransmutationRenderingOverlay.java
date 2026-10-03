package com.skd.equivalentlegacy.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ReferenceMap;
import java.util.ArrayList;
import java.util.List;
import com.skd.equivalentlegacy.config.EquivalentLegacyConfig;
import com.skd.equivalentlegacy.gameObjs.items.PhilosophersStone;
import com.skd.equivalentlegacy.gameObjs.items.PhilosophersStone.PhilosophersStoneMode;
import com.skd.equivalentlegacy.gameObjs.registries.PEItems;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TransmutationRenderingOverlay implements LayeredDraw.Layer {

	private record OutlineTarget(BlockPos pos, VoxelShape shape) {
	}

	private final Minecraft mc = Minecraft.getInstance();
	@Nullable
	private Block transmutationResult;
	private long lastGameTime;
	@Nullable
	private List<OutlineTarget> outlineTargets = List.of();
	private float outlineAlpha;

	public TransmutationRenderingOverlay() {
		NeoForge.EVENT_BUS.addListener(this::onOverlay);
	}

	@Override
	public void render(@NotNull GuiGraphics graphics, @NotNull DeltaTracker delta) {
		if (!mc.options.hideGui && transmutationResult != null) {
			graphics.renderItem(new ItemStack(transmutationResult), 1, 1);
			long gameTime = mc.level == null ? 0 : mc.level.getGameTime();
			if (lastGameTime != gameTime) {
				transmutationResult = null;
				lastGameTime = gameTime;
			}
		}
	}

	private void onOverlay(RenderHighlightEvent.Block event) {
		Camera activeRenderInfo = event.getCamera();
		if (!(activeRenderInfo.getEntity() instanceof Player player)) {
			clearOverlay();
			return;
		}
		Level level = player.level();
		lastGameTime = level.getGameTime();
		ItemStack stack = player.getMainHandItem();
		if (stack.isEmpty()) {
			stack = player.getOffhandItem();
		}
		if (stack.isEmpty() || !stack.is(PEItems.PHILOSOPHERS_STONE)) {
			clearOverlay();
			return;
		}
		boolean isSneaking = player.isSecondaryUseActive();
		PhilosophersStone philoStone = (PhilosophersStone) stack.getItem();
		BlockHitResult rtr = philoStone.getHitBlock(level, player, isSneaking);
		if (rtr.getType() != HitResult.Type.BLOCK) {
			clearOverlay();
			return;
		}
		int charge = philoStone.getCharge(stack);
		PhilosophersStoneMode mode = philoStone.getMode(stack);
		Object2ReferenceMap<BlockPos, BlockState> changes = PhilosophersStone.getChanges(level, rtr.getBlockPos(), rtr.getDirection(), player.getDirection(),
				isSneaking, mode, charge);
		if (changes.isEmpty()) {
			clearOverlay();
			return;
		}
		transmutationResult = changes.values().iterator().next().getBlock();
		outlineAlpha = EquivalentLegacyConfig.client.pulsatingOverlay.get() ? getPulseProportion() * 0.60F : 0.35F;
		CollisionContext selectionContext = CollisionContext.of(player);
		Vec3 viewPosition = activeRenderInfo.getPosition();
		VertexConsumer builder = event.getMultiBufferSource().getBuffer(PERenderType.TRANSMUTATION_OVERLAY);
		PoseStack matrix = event.getPoseStack();
		int color = ((int) (outlineAlpha * 255) << 24) | 0xFFFFFF;
		for (BlockPos pos : changes.keySet()) {
			BlockState state = level.getBlockState(pos);
			if (!state.isAir()) {
				VoxelShape shape = state.getShape(level, pos, selectionContext);
				if (!shape.isEmpty()) {
					matrix.pushPose();
					matrix.translate(pos.getX() - viewPosition.x, pos.getY() - viewPosition.y, pos.getZ() - viewPosition.z);
					shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
						for (Direction value : Direction.values()) {
							LevelRenderer.renderFace(matrix, builder, value, (float) minX, (float) minY, (float) minZ, (float) maxX, (float) maxY, (float) maxZ, 1, 1, 1, outlineAlpha);
						}
					});
					matrix.popPose();
				}
			}
		}
	}

	private void clearOverlay() {
		transmutationResult = null;
		outlineTargets = List.of();
	}

	private float getPulseProportion() {
		return (float) (0.5F * Math.sin(System.currentTimeMillis() / 350.0) + 0.5F);
	}
}
