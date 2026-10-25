package com.mygame.validator;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

/**
 * One looping music track plus short sound effects, all scaled by a master volume.
 * Use AudioManager.getInstance() from anywhere (so generated designer handlers can use it too).
 * Every call fails soft: with no audio device the game simply runs silent.
 */
public final class AudioManager implements SoundPlayer {
    private record Sample(AudioFormat format, byte[] data) {}

    private static final AudioManager INSTANCE = new AudioManager(0.7f);

    /** All sound effect names (files in /assets/sfx). */
    private static final String[] SFX = {
        "choice_enter", "choice_exit", "choice_grab", "choice_ungrab", "choice_destroy",
        "description_sound", "level_next_select", "news_enter", "news_exit", "next_level",
        "next_level_intro", "next_level_outro", "reputation_bad", "reputation_good",
        "reputation_enter", "emoji_1", "emoji_2"
    };

    private final Map<String, Sample> sfx = new ConcurrentHashMap<>();
    private Clip music;
    private String musicName;
    private float volume;

    /** Shared instance, typed as the interface so callers depend on SoundPlayer, not on this class. */
    public static SoundPlayer getInstance() {
        return INSTANCE;
    }

    private AudioManager(float initialVolume) {
        this.volume = initialVolume;
        Thread loader = new Thread(() -> {
            for (String name : SFX) {
                preloadSfx(name);
            }
        }, "sfx-preload");
        loader.setDaemon(true);
        loader.start();
    }

    private void preloadSfx(String name) {
        try (InputStream raw = AudioManager.class.getResourceAsStream("/assets/sfx/" + name + ".wav")) {
            if (raw == null) {
                return;
            }
            try (AudioInputStream in = AudioSystem.getAudioInputStream(new BufferedInputStream(raw))) {
                sfx.put(name, new Sample(in.getFormat(), in.readAllBytes()));
            }
        } catch (Exception e) {
            System.err.println("Could not load sfx " + name + ": " + e);
        }
    }

    /** Plays a short sound effect (does nothing if it is not loaded yet or there is no audio device). */
    @Override
    public void play(String name) {
        play(name, 1f);
    }

    /** Overload: same, but quieter. {@code loudness} scales the master volume (0.0 - 1.0). */
    @Override
    public void play(String name, float loudness) {
        Sample s = sfx.get(name);
        if (s == null) {
            return;
        }
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(s.format(), s.data(), 0, s.data().length);
            applyGain(clip, volume * Math.max(0f, Math.min(1f, loudness)));
            clip.addLineListener(ev -> {
                if (ev.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.start();
        } catch (Exception ignored) {
            // no audio device / too many lines: stay silent
        }
    }

    /** Starts looping a music track ("menu" or "game"). Does nothing if it is already playing. */
    @Override
    public void playMusic(String name) {
        synchronized (this) {
            if (name.equals(musicName) && music != null && music.isRunning()) {
                return;
            }
        }
        stopMusic();
        Thread t = new Thread(() -> startMusic(name), "music-loader");
        t.setDaemon(true);
        t.start();
    }

    private synchronized void startMusic(String name) {
        try (InputStream raw = AudioManager.class.getResourceAsStream("/assets/music/" + name + ".wav")) {
            if (raw == null) {
                return;
            }
            AudioInputStream in = AudioSystem.getAudioInputStream(new BufferedInputStream(raw));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            applyGain(clip, volume * 0.6f);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            stopMusic();
            music = clip;
            musicName = name;
        } catch (Exception e) {
            System.err.println("Music unavailable: " + e);
        }
    }

    @Override
    public synchronized void stopMusic() {
        if (music != null) {
            music.stop();
            music.close();
            music = null;
            musicName = null;
        }
    }

    /** Master volume, 0.0 - 1.0. */
    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public synchronized void setVolume(float v) {
        volume = Math.max(0f, Math.min(1f, v));
        if (music != null) {
            applyGain(music, volume * 0.6f);
        }
    }

    private static void applyGain(Clip clip, float linear) {
        if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            return;
        }
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float db = linear <= 0.0001f ? gain.getMinimum() : (float) (20.0 * Math.log10(linear));
        gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), db)));
    }
}
