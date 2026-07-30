package com.skd.equivalentlegacy.gui;

import com.skd.equivalentlegacy.emc.EMCHelper;
import com.skd.equivalentlegacy.emc.nss.NSSItem;
import com.skd.equivalentlegacy.network.payload.KnowledgeDataPayload;
import com.skd.equivalentlegacy.player.PlayerKnowledge;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.SimpleContainer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class TransmutationContainer extends AbstractContainerMenu {
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int PLAYER_INV_START = 2;
    private static final int PLAYER_INV_END = 28;
    private static final int HOTBAR_START = 29;
    private static final int HOTBAR_END = 37;

    private final Player player;
    private final PlayerKnowledge knowledge;
    private final SimpleContainer inputContainer = new SimpleContainer(1);
    private final SimpleContainer outputContainer = new SimpleContainer(1);
    private boolean dataSent = false;
    private int knowledgeHash = 0;

    public TransmutationContainer(int containerId, Inventory playerInventory) {
        super(ModMenuTypes.TRANSMUTATION.get(), containerId);
        this.player = playerInventory.player;
        this.knowledge = PlayerKnowledge.of(player);

        addSlot(new Slot(inputContainer, 0, 26, 22) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return !stack.isEmpty();
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        addSlot(new Slot(outputContainer, 0, 134, 22) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        26 + col * 18, 104 + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col,
                    26 + col * 18, 162));
        }
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        if (player instanceof ServerPlayer serverPlayer) {
            handleInputSlot();
            syncKnowledgeDataIfChanged(serverPlayer);
        }
    }

    private void handleInputSlot() {
        ItemStack input = inputContainer.getItem(0);
        if (!input.isEmpty()) {
            NSSItem nss = NSSItem.createItem(input);
            if (EMCHelper.hasEMC(nss)) {
                knowledge.learnItem(nss);
            }
            inputContainer.setItem(0, ItemStack.EMPTY);
        }
    }

    private int computeKnowledgeHash() {
        int hash = Long.hashCode(knowledge.getEmc());
        for (NSSItem item : knowledge.getKnownItems()) {
            hash = 31 * hash + item.getResourceLocation().toString().hashCode();
        }
        return hash;
    }

    private void syncKnowledgeDataIfChanged(ServerPlayer serverPlayer) {
        int newHash = computeKnowledgeHash();
        if (dataSent && newHash == knowledgeHash) return;
        knowledgeHash = newHash;
        dataSent = true;

        Map<String, Long> data = new HashMap<>();
        if (knowledge.hasFullKnowledge()) {
            for (var entry : EMCHelper.getEMCMap().entrySet()) {
                if (entry.getKey() instanceof NSSItem nss) {
                    data.put(nss.getResourceLocation().toString(), entry.getValue());
                }
            }
        } else {
            for (NSSItem item : knowledge.getKnownItems()) {
                data.put(item.getResourceLocation().toString(), EMCHelper.getEMC(item));
            }
        }
        PacketDistributor.sendToPlayer(serverPlayer, new KnowledgeDataPayload(data));
    }

    public void transmute(String itemId) {
        if (player.level().isClientSide()) return;
        NSSItem target = NSSItem.createItem(Identifier.parse(itemId));
        if (!knowledge.hasKnowledge(target)) return;
        long emcCost = EMCHelper.getEMC(target);
        if (emcCost <= 0) return;
        ItemStack output = outputContainer.getItem(0);
        if (!output.isEmpty()) return;
        if (!knowledge.subtractEmc(emcCost)) return;
        var itemHolder = BuiltInRegistries.ITEM.getValue(target.getResourceLocation());
        if (itemHolder == null) return;
        ItemStack result = new ItemStack(itemHolder, 1);
        outputContainer.setItem(0, result);
        if (player instanceof ServerPlayer serverPlayer) {
            knowledge.syncEmc(serverPlayer);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        Slot slot = slots.get(slotIndex);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        if (slotIndex == INPUT_SLOT || slotIndex == OUTPUT_SLOT) {
            if (!moveItemStackTo(stack, PLAYER_INV_START, HOTBAR_END + 1, true)) {
                return ItemStack.EMPTY;
            }
        } else if (slotIndex >= PLAYER_INV_START) {
            if (!moveItemStackTo(stack, INPUT_SLOT, INPUT_SLOT + 1, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public PlayerKnowledge getKnowledge() {
        return knowledge;
    }

    public Player getPlayer() {
        return player;
    }
}
