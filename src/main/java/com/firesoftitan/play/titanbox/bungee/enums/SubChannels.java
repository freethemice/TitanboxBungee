package com.firesoftitan.play.titanbox.bungee.enums;

public enum SubChannels {
    CHAT("chat");
    private String name;

    SubChannels(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
