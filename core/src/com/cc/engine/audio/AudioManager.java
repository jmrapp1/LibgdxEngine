package com.cc.engine.audio;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    private static AudioManager instance = new AudioManager();
    private final HashMap<String, Sound> sounds = new HashMap<String, Sound>();
    private final HashMap<String, Music> music = new HashMap<String, Music>();

    private AudioManager() {

    }

    public void loadSound(String id, String file) {
        sounds.put(id, Gdx.audio.newSound(Gdx.files.internal(file)));
    }

    public Sound getSound(String id) {
        return (Sound) sounds.get(id);
    }

    public void loadMusic(String id, String file) {
        music.put(id, Gdx.audio.newMusic(Gdx.files.internal(file)));
    }

    public Music getMusic(String id) {
        return music.get(id);
    }

    public void playSound(String id) {
        Sound sound = getSound(id);
        if (sound != null) {
            sound.play();
        }
    }

    public void playMusic(String id) {
        playMusic(id, 1f);
    }

    /**
     * @param id
     * @param volume Number between 0 and 1
     */
    public void playSound(String id, float volume) {
        Sound sound = getSound(id);
        if (sound != null) {
            long songId = sound.play();
            sound.setVolume(songId, volume);
        }
    }

    public void playMusic(String id, float volume) {
        Music music = getMusic(id);
        if (music != null) {
            music.setVolume(volume);
            if (music.isPlaying()) {
                music.stop();
            }
            music.play();
        }
    }

    public Sound loopSound(String id) {
        Sound sound = getSound(id);
        if (sound != null) {
            sound.loop();
        }
        return sound;
    }

    public Music loopMusic(String id) {
        return loopMusic(id, 1f);
    }

    /**
     * @param id
     * @param volume Number between 0 and 1
     * @return
     */
    public Sound loopSound(String id, float volume) {
        Sound sound = getSound(id);
        if (sound != null) {
            long songId = sound.loop();
            sound.setVolume(songId, volume);
        }
        return sound;
    }

    public Music loopMusic(String id, float volume) {
        Music music = getMusic(id);
        if (music != null) {
            music.setVolume(volume);
            if (!music.isPlaying())
            {
                music.play();
            }
            music.setLooping(true);
        }
        return music;
    }

    public static AudioManager getInstance() {
        return instance;
    }

}
