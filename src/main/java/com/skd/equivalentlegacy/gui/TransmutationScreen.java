package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.emc.EMCHelper;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.network.payload.TransmuteRequestPayload;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransmutationScreen extends AbstractContainerScreen<TransmutationContainer> {
    private static final Identifier BG_TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");
    private static final Map<String, Long> cachedEmcData = new HashMap<>();

    private static final int COLS = 6;
    private static final int ITEM_SIZE = 18;
    private static final int LIST_LEFT = 8;
    private static final int LIST_TOP = 36;
    private static final int LIST_WIDTH = COLS * ITEM_SIZE;
    private static final int LIST_HEIGHT = 72;
    private static final int ROWS_VISIBLE = LIST_HEIGHT / ITEM_SIZE;

    private int scrollOffset = 0;
    private int maxScroll = 0;
    private List<ItemEntry> sortedItems = List.of();
    public record ItemEntry(String itemId, ItemStack stack, long emcValue) {}

    public TransmutationScreen(TransmutationContainer menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 202, 194);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public static void cacheKnowledgeData(Map<String, Long> data) {
        cachedEmcData.clear();
        cachedEmcData.putAll(data);
    }

    @Override
    protected void init() {
        super.init();
        rebuildItemList();
    }

    private void rebuildItemList() {
        sortedItems = new ArrayList<>();
        var knownItems = menu.getKnowledge().getKnownItems();
        for (NSSItem nss : knownItems) {
            String id = nss.getResourceLocation().toString();
            var item = BuiltInRegistries.ITEM.getValue(nss.getResourceLocation());
            if (item == null) continue;
            ItemStack stack = new ItemStack(item);
            long emc = cachedEmcData.getOrDefault(id, EMCHelper.getEMC(nss));
            sortedItems.add(new ItemEntry(id, stack, emc));
        }
        sortedItems.sort(Comparator.comparingLong(ItemEntry::emcValue).reversed()
                .thenComparing(e -> e.stack().getHoverName().getString()));
        int totalRows = (sortedItems.size() + COLS - 1) / COLS;
        maxScroll = Math.max(0, totalRows - ROWS_VISIBLE);
        if (scrollOffset > maxScroll) scrollOffset = maxScroll;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        int x = leftPos;
        int y = topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE, x, y, 0, 0,
                imageWidth, imageHeight, 256, 256);
        graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE,
                x + 25, y + 35, 7, 7, 18, 18, 256, 256);
        graphics.blit(RenderPipelines.GUI_TEXTURED, BG_TEXTURE,
                x + 133, y + 35, 7, 7, 18, 18, 256, 256);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        long emc = menu.getKnowledge().getEmc();

        graphics.text(font,
                Component.translatable("gui.equivalent_legacy.transmutation.emc", emc),
                8, 6, 0x404040);

        graphics.text(font, Component.translatable("gui.equivalent_legacy.transmutation.input"),
                8, 22, 0x5E5E5E);
        graphics.text(font, Component.translatable("gui.equivalent_legacy.transmutation.output"),
                116, 22, 0x5E5E5E);

        renderKnownItemsList(graphics, mouseX, mouseY);
    }

    private void renderKnownItemsList(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        int startIndex = scrollOffset * COLS;

        for (int i = 0; i < ROWS_VISIBLE * COLS && (startIndex + i) < sortedItems.size(); i++) {
            int idx = startIndex + i;
            var entry = sortedItems.get(idx);
            int col = i % COLS;
            int row = i / COLS;
            int itemX = LIST_LEFT + col * ITEM_SIZE;
            int itemY = LIST_TOP + row * ITEM_SIZE;

            boolean affordable = menu.getKnowledge().getEmc() >= entry.emcValue();
            if (!affordable) {
                graphics.fill(RenderPipelines.GUI, itemX, itemY, itemX + 16, itemY + 16, 0x80_000000);
            }

            int color = affordable ? 0x00FF00 : 0xFF5555;
            String costText = "EMC " + entry.emcValue();
            int textWidth = font.width(costText);
            int textX = itemX + (ITEM_SIZE - textWidth) / 2 - 1;
            int textY = itemY + 1;
            graphics.text(font, costText, textX, textY, color);

            graphics.fakeItem(entry.stack(), itemX, itemY + 8, itemX + (itemY + 8) * imageWidth);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDragging) {
        if (!isDragging && event.button() == 0) {
            double mouseX = event.x();
            double mouseY = event.y();
            int listX = leftPos + LIST_LEFT;
            int listY = topPos + LIST_TOP;
            int relX = (int) Math.round(mouseX) - listX;
            int relY = (int) Math.round(mouseY) - listY;

            if (relX >= 0 && relX < LIST_WIDTH && relY >= 0 && relY < LIST_HEIGHT) {
                int col = relX / ITEM_SIZE;
                int row = relY / ITEM_SIZE;
                int idx = (scrollOffset + row) * COLS + col;

                if (idx >= 0 && idx < sortedItems.size()) {
                    var entry = sortedItems.get(idx);
                    if (menu.getKnowledge().getEmc() >= entry.emcValue()) {
                        ClientPacketDistributor.sendToServer(new TransmuteRequestPayload(entry.itemId()));
                    }
                    return true;
                }
            }
        }
        return super.mouseClicked(event, isDragging);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY < 0 && scrollOffset < maxScroll) {
            scrollOffset++;
            rebuildItemList();
            return true;
        } else if (scrollY > 0 && scrollOffset > 0) {
            scrollOffset--;
            rebuildItemList();
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
}
