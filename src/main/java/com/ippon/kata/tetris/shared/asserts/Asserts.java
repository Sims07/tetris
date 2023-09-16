package com.ippon.kata.tetris.shared.asserts;

public final class Asserts {

    private Asserts() {
    }

    public static Assert withContext(Class<?> assertedClass, String contextName) {
        return new Assert(assertedClass, contextName);
    }

    public static Assert withContext(Class<?> assertedClass) {
        return new Assert(assertedClass, null);
    }
}
