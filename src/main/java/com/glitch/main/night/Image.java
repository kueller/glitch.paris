package com.glitch.main.night;

public class Image {
    private String filename;
    private String title;
    private String alt;

    public Image(String filename, String title, String alt) {
        this.filename = filename;
        this.title = title;
        this.alt = alt;
    }

    public String getFilename() { return this.filename; }
    public String getTitle() { return this.title; }
    public String getAlt() { return this.alt; }
}
