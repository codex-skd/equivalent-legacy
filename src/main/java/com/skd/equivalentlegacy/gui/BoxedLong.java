package com.skd.equivalentlegacy.gui;

import net.minecraft.world.inventory.DataSlot;

public class BoxedLong {
    private long value;
    public final DataSlot highSlot = DataSlot.standalone();
    public final DataSlot lowSlot = DataSlot.standalone();

    public BoxedLong() {
        this.value = 0L;
    }

    public long get() {
        return this.value;
    }

    public void set(long value) {
        this.value = value;
    }

    public void sync(boolean all) {
        int high = (int) (this.value >> 32);
        int low = (int) this.value;

        if (all) {
            highSlot.set(high);
            lowSlot.set(low);
        } else {
            if (highSlot.get() != high) highSlot.set(high);
            if (lowSlot.get() != low) lowSlot.set(low);
        }
    }

    public void update() {
        long high = (long) highSlot.get() << 32;
        long low = lowSlot.get() & 0xFFFFFFFFL;
        this.value = high | low;
    }
}
