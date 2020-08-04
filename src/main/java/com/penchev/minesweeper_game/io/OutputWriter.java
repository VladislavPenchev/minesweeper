package com.penchev.minesweeper_game.io;

import com.penchev.minesweeper_game.domain.entities.Board;

public interface OutputWriter {
    void writeLine(String line);

    void printBoard(Board board);
}
