package com.penchev.minesweeper_game.domain.entities;

public interface Command {
    String execute(String[] args);
}
