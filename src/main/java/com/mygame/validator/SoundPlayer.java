package com.mygame.validator;

/**
 * Sound as the game logic sees it (ABSTRACTION: an interface).
 * The game never needs to know about javax.sound; AudioManager is just one implementation.
 */
public interface SoundPlayer {
    /** Plays a sound effect at normal level. */
    void play(String name);

    /** Plays a sound effect scaled by {@code loudness} (0.0 - 1.0 of the master volume). */
    void play(String name, float loudness);

    void playMusic(String name);

    void stopMusic();

    float getVolume();

    void setVolume(float volume);
}
