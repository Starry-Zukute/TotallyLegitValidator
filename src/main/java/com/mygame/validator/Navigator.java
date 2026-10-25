package com.mygame.validator;

/**
 * What a screen is allowed to ask the window to do (ABSTRACTION: an interface).
 * Screens depend on this, not on GameFrame, so they stay decoupled from the window class.
 */
public interface Navigator {
    void showMenu();

    void showSettings();

    void showTutorial();

    /** Resets the game state and shows the gameplay screen. */
    void startNewGame();
}
