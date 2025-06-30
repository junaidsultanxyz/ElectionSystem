package com.junaid.client.model;

// @author junaidxyz

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Election {
    private int id;
    private String name;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
    private ArrayList<Vote> votes;

    public Election(String name, LocalDateTime startingTime, LocalDateTime endingTime) {
        this.name = name;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    public Election(int id, String name, LocalDateTime startingTime, LocalDateTime endingTime) {
        this.id = id;
        this.name = name;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public LocalDateTime getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(LocalDateTime endingTime) {
        this.endingTime = endingTime;
    }

    public ArrayList<Vote> getVotes() {
        return votes;
    }
}
