package com.skd.equivalentlegacy.impl;

import java.util.Objects;
import com.skd.equivalentlegacy.api.ItemInfo;
import com.skd.equivalentlegacy.api.proxy.IEMCProxy;
import com.skd.equivalentlegacy.emc.components.DataComponentManager;
import com.skd.equivalentlegacy.utils.EMCHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class EMCProxyImpl implements IEMCProxy {

	@Override
	@Range(from = 0, to = Long.MAX_VALUE)
	public long getValue(@NotNull ItemInfo info) {
		return DataComponentManager.getEmcValue(Objects.requireNonNull(info));
	}

	@Override
	@Range(from = 0, to = Long.MAX_VALUE)
	public long getSellValue(@NotNull ItemInfo info) {
		return EMCHelper.getEmcSellValue(getValue(info));
	}
}