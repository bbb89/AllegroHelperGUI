package com.lukasz;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Notification {
    private static Clip clip;
    private static String soundPath = "boing_x.wav";
    private static boolean openInBrowser = true;


    public static void notify(String urlString) {
        if (openInBrowser) {
            try {
                URL url = new URL(urlString);
                openWebpage(url);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            File file = new File(soundPath);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();

        }catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void openWebpage(String urlString) {
        try {
            urlString = ItemsParser.fixURL(urlString);
            URL url = new URL(urlString);
            openWebpage(url.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void setOpenInBrowser(boolean openInBrowser) {
        Notification.openInBrowser = openInBrowser;
    }
}
