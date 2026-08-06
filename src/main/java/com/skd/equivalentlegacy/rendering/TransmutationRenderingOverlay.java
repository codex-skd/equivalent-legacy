package com.skd.equivalentlegacy.rendering;

import com.skd.equivalentlegacy.item.EquivalentLegacyItems;
import com.skd.equivalentlegacy.player.PlayerKnowledge;
import com.skd.equivalentlegacy.world_transmutation.TransmutationConfig;
import com.skd.equivalentlegacy.world_transmutation.WorldTransmutationManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * HUD overlay shown while the player holds a Philosopher's Stone / Transmutation Stone and points at a
 * block. Displays the transmutation target, the EMC cost, and a colored status (green = affordable,
 * red = insufficient EMC, grey = not transmutable). Backed by a NeoForge {@code GuiLayer} registered
 * in {@link EquivalentLegacyRenderers}; {@link #render} runs once per frame on the logical client.
 * <p>Performance: block lookups are cached for {@link #CACHE_TICKS} ticks and re-run only when the
 * targeted block changes, so raycast/EMC work does not happen every frame.</p>
 */
@OnlyIn(Dist.CLIENT)
public final class TransmutationRenderingOverlay {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int FADE_TICKS = 20;
    public static final int MARGIN = 10;
    public static final int CACHE_TICKS = 20;

    public static final int COLOR_OK = 0x40FF60;
    public static final int COLOR_LOW_EMC = 0xFF4040;
    public static final int COLOR_NONE = 0xA0A0A0;
    public static final int BG_ARGB = 0xBF101010;

    private static BlockPos lastPos = null;
    private static long lastCacheTick = -1L;
    private static Block lastTarget = null;
    private static long lastCost = 0L;
    private static long lastEmc = 0L;

    private TransmutationRenderingOverlay() {}

    public static float computeAlpha(int ticksRemaining) {
        if (ticksRemaining <= 0) return 0.0F;
        if (ticksRemaining >= FADE_TICKS) return 1.0F;
        return ticksRemaining / (float) FADE_TICKS;
    }

    public static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        Level level = mc.level;
        if (player == null || level == null) return;

        ItemStack stoneStack = heldStone(player);
        if (stoneStack.isEmpty()) return;

        BlockHitResult hit = targetedBlock();
        if (hit == null) return;
        BlockPos pos = hit.getBlockPos();

        long now = level.getGameTime();
        if (lastPos == null || !pos.equals(lastPos) || (now - lastCacheTick) >= CACHE_TICKS) {
            lastPos = pos;
            lastCacheTick = now;
            Block source = level.getBlockState(pos).getBlock();
            lastTarget = WorldTransmutationManager.getTransmutationTarget(source);
            lastCost = lastTarget != null ? WorldTransmutationManager.getTransmutationCost(source, lastTarget) : -1L;
            lastEmc = PlayerKnowledge.of(player).getEmc();
        }

        int x = graphics.guiWidth() - WIDTH - MARGIN;
        int y = MARGIN;
        Font font = mc.font;

        graphics.fill(x, y, x + WIDTH, y + HEIGHT, BG_ARGB);
        graphics.outline(x, y, x + WIDTH, y + HEIGHT, 0xFF404040);

        graphics.item(stoneStack, x + 4, y + 8);
        graphics.text(font, "->", x + 22, y + 12, COLOR_NONE);

        if (lastTarget == null) {
            graphics.text(font, "Not", x + 38, y + 4, COLOR_NONE);
            graphics.text(font, "transmut.", x + 38, y + 14, COLOR_NONE);
            graphics.text(font, "Not transmutable", x + 4, y + HEIGHT - 14, COLOR_NONE);
            return;
        }

        ItemStack targetStack = stackOf(lastTarget);
        if (!targetStack.isEmpty()) {
            graphics.item(targetStack, x + 38, y + 8);
        }

        int color = COLOR_OK;
        String costText;
        if (lastCost <= 0L) {
            costText = "Free";
        } else if (lastEmc >= lastCost) {
            costText = "Cost: " + lastCost + " EMC";
        } else {
            color = COLOR_LOW_EMC;
            costText = "Cost: " + lastCost + " EMC";
        }
        graphics.text(font, costText, x + 4, y + 30, color);
    }

    private static ItemStack heldStone(Player player) {
        ItemStack main = player.getMainHandItem();
        if (isStone(main)) return main;
        ItemStack off = player.getOffhandItem();
        if (isStone(off)) return off;
        return ItemStack.EMPTY;
    }

    private static boolean isStone(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.is(EquivalentLegacyItems.PHILOSOPHERS_STONE.get())
                || stack.is(EquivalentLegacyItems.TRANSMUTATION_STONE.get()));
    }

    private static BlockHitResult targetedBlock() {
        Minecraft mc = Minecraft.getInstance();
        int range = TransmutationConfig.getRange();
        HitResult hr = mc.hitResult;
        if (hr == null || hr.getType() != HitResult.Type.BLOCK || !(hr instanceof BlockHitResult bhr)) return null;
        if (mc.player == null) return null;
        BlockPos p = bhr.getBlockPos();
        if (mc.player.distanceToSqr(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5) > (double) range * range) return null;
        return bhr;
    }

    private static ItemStack stackOf(Block block) {
        if (block == null) return ItemStack.EMPTY;
        ItemStack stack = new ItemStack(block.asItem());
        return stack.getItem() == Items.AIR ? ItemStack.EMPTY : stack;
    }
}