package com.mygame.validator;

/**
 * A screen of the game. GameFrame calls these hooks when a screen appears or disappears.
 * Both hooks do nothing by default; a screen overrides only what it needs (POLYMORPHISM).
 */

public interface Screen {
    default void onShow() {
    }

    default void onHide() {
    }
}
