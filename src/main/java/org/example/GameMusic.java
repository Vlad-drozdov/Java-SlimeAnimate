package org.example;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.io.File;

public class GameMusic {
    private final AudioPlayerComponent playerComponent;
    private final MediaPlayer mediaPlayer;
    private final String musicPath;
    private boolean playing = false;

    public GameMusic() {
        File musicFile = new File("src/main/resources/music/mainTheme.mp3");
        if (!musicFile.exists()) {
            throw new RuntimeException("Файл не знайдено: " + musicFile.getAbsolutePath());
        }

        musicPath = musicFile.getAbsolutePath();

        playerComponent = new AudioPlayerComponent();
        mediaPlayer = playerComponent.mediaPlayer();
        mediaPlayer.controls().setRepeat(true);

//        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
//            @Override
//            public void finished(MediaPlayer mediaPlayer) {
//
//            }
//        });
    }

    public void play() {
        if (!playing) {
            mediaPlayer.media().play(musicPath);
            playing = true;
        }
    }

    public void stop() {
        if (playing) {
            mediaPlayer.controls().stop();
            playing = false;
        }
    }

    public void pause() {
        if (playing) {
            mediaPlayer.controls().pause();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.status().isPlaying();
    }
}
