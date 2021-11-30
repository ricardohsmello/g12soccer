package br.com.g12soccer.domain;

public class Player {
    private String id;
    private String name;

    public Player(String id, String name) {
        this.id = id;
        this.name= name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
