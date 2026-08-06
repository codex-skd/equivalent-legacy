package com.skd.equivalentlegacy.integration.jei;

import com.skd.equivalentlegacy.item.EquivalentLegacyItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * JEI recipe category rendering a transmutation as {@code [input block] → [output block]} with the EMC
 * cost drawn underneath. Uses the vanilla JEI slot/arrow drawables so no custom assets are required.
 */
@OnlyIn(Dist.CLIENT)
public class TransmutationRecipeCategory implements IRecipeCategory<TransmutationRecipeDisplay> {
    private static final int WIDTH = 116;
    private static final int HEIGHT = 44;
    private static final int SLOT_Y = 8;
    private static final int INPUT_X = 6;
    private static final int OUTPUT_X = 66;
    private static final int ARROW_X = 46;
    private static final int COST_Y = 32;
    private static final int COLOR_COST = 0xFF404040;
    private static final int COLOR_FREE = 0xFF2E7D32;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic slot;
    private final IDrawableStatic outputSlot;
    private final IDrawable arrow;

    public TransmutationRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(EquivalentLegacyItems.PHILOSOPHERS_STONE.get()));
        this.slot = guiHelper.getSlotDrawable();
        this.outputSlot = guiHelper.getOutputSlot();
        this.arrow = guiHelper.getRecipeArrow();
    }

    @Override
    public IRecipeType<TransmutationRecipeDisplay> getRecipeType() {
        return EquivalentLegacyJeiPlugin.TRANSMUTATION_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.equivalent_legacy.transmutation");
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TransmutationRecipeDisplay recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, INPUT_X, SLOT_Y).addItemStack(recipe.inputBlock());
        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_X, SLOT_Y).addItemStack(recipe.outputBlock());
    }

    @Override
    public void draw(TransmutationRecipeDisplay recipe, IRecipeSlotsView recipeSlotsView,
                     GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        slot.draw(guiGraphics, INPUT_X - 1, SLOT_Y - 1);
        outputSlot.draw(guiGraphics, OUTPUT_X - 1, SLOT_Y - 1);
        arrow.draw(guiGraphics, ARROW_X, SLOT_Y + 2);

        boolean free = recipe.emcCost() <= 0L;
        String costText = free
                ? Component.translatable("jei.equivalent_legacy.transmutation.free").getString()
                : Component.translatable("jei.equivalent_legacy.transmutation.cost", recipe.emcCost()).getString();
        guiGraphics.text(Minecraft.getInstance().font, costText, 8, COST_Y, free ? COLOR_FREE : COLOR_COST);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, TransmutationRecipeDisplay recipe,
                           IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 8 && mouseX <= WIDTH - 8 && mouseY >= COST_Y && mouseY <= COST_Y + 8) {
            tooltip.add(Component.translatable("jei.equivalent_legacy.transmutation.tooltip",
                    recipe.inputBlock().getHoverName(), recipe.outputBlock().getHoverName()));
        }
    }
}
