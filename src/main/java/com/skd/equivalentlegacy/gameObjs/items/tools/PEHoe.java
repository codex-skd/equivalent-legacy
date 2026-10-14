package com.skd.equivalentlegacy.gameObjs.items.tools;

import java.util.List;
import com.skd.equivalentlegacy.api.capabilities.item.IItemCharge;
import com.skd.equivalentlegacy.gameObjs.IMatterType;
import com.skd.equivalentlegacy.gameObjs.registries.PEDataComponentTypes;
import com.skd.equivalentlegacy.utils.ToolHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PEHoe extends PETool implements IItemCharge {

	public PEHoe(IMatterType matterType, int numCharges, Properties props) {
		super(matterType, BlockTags.MINEABLE_WITH_HOE, numCharges, props.attributes(PETool.createAttributes(matterType, -matterType.getAttackDamageBonus(), matterType.getMatterTier())));
	}

	@NotNull
	@Override
	public InteractionResult useOn(@NotNull UseOnContext context) {
		return ToolHelper.tillAOE(context, context.getLevel().getBlockState(context.getClickedPos()), 0);
	}
}
