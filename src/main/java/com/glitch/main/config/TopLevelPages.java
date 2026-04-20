package com.glitch.main.config;

public enum TopLevelPages {
    HOME("home", "/"),
    KNOWLEDGE("knowledge", "https://knowledge.glitch.paris"),
    NIGHT("night", "/night"),
    ETC("etc", "/etc");

    private String name;
    private String path;

    private TopLevelPages(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() { return this.name; }
    public String getPath() { return this.path; }
}
