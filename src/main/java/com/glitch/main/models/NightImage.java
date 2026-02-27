package com.glitch.main.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="night_images")
public class NightImage {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(length=100, nullable=false)
    private String title;

    @Column(length=100, nullable=false)
    private String filename;

    @Column(length=500, nullable=false)
    private String alt_text;

    protected NightImage() {}

    public String getFilename() { return this.filename; }
    public String getTitle() { return this.title; }
    public String getAlt() { return this.alt_text; }
}
