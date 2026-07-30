package com.skd.equivalentlegacy.emc.mapper;

public final class LongArithmetic implements IValueArithmetic<Long> {
    public static final LongArithmetic INSTANCE = new LongArithmetic();

    private static final long INFINITE = Long.MAX_VALUE;

    private LongArithmetic() {}

    @Override
    public Long add(Long a, Long b) {
        if (a == INFINITE || b == INFINITE) return INFINITE;
        try {
            return Math.addExact(a, b);
        } catch (ArithmeticException e) {
            return INFINITE;
        }
    }

    @Override
    public Long multiply(Long a, int b) {
        if (a == INFINITE) return INFINITE;
        try {
            return Math.multiplyExact(a, b);
        } catch (ArithmeticException e) {
            return INFINITE;
        }
    }

    @Override
    public Long getZero() {
        return 0L;
    }

    @Override
    public Long getOne() {
        return 1L;
    }

    @Override
    public Long getInfinite() {
        return INFINITE;
    }

    @Override
    public boolean isZero(Long value) {
        return value == 0L;
    }

    @Override
    public boolean isInfinite(Long value) {
        return value == INFINITE;
    }

    @Override
    public int compare(Long a, Long b) {
        return Long.compare(a, b);
    }

    @Override
    public Long divide(Long a, int b) {
        if (a == INFINITE) return INFINITE;
        return a / b;
    }

    @Override
    public Long min(Long a, Long b) {
        return Long.min(a, b);
    }
}
