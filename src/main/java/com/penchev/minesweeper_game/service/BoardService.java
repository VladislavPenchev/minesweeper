package com.penchev.minesweeper_game.service;

import com.penchev.minesweeper_game.domain.entities.Board;
import com.penchev.minesweeper_game.domain.entities.Position;

import java.util.List;

public interface BoardService {

    Board createBoard(int difficultyLevel);

    void editFirstPositionIfMine(Board board, List<Integer> constraints);

    int calculateMines(Board board, int rowArg, int colArg);

    List<Position> calculateEmptyFields(Board board, int rowArg, int colArg);
}
