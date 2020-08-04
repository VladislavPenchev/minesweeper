package com.penchev.minesweeper_game.service;

import com.penchev.minesweeper_game.domain.entities.Board;

public interface BoardService {

    Board createBoard(int difficultyLevel);
}
