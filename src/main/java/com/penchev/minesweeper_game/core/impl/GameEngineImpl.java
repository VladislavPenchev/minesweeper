package com.penchev.minesweeper_game.core.impl;

import com.penchev.minesweeper_game.core.Engine;
import com.penchev.minesweeper_game.domain.entities.Board;
import com.penchev.minesweeper_game.io.impl.ConsoleReader;
import com.penchev.minesweeper_game.io.impl.ConsoleWriter;
import com.penchev.minesweeper_game.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameEngineImpl implements Engine {

    private static final String GAME_DESCRIPTION = "Enter the Difficulty Level\nPress 0 for BEGINNER (9 * 9 Cells and 10 Mines)\n" +
            "Press 1 for INTERMEDIATE (16 * 16 Cells and 40 Mines)\nPress 2 for ADVANCED (24 * 24 Cells and 99 Mines)\n";

    private static final String GAME_ENTER_MOVE = "Enter your move, (row, column)\n->";

    @Autowired
    private ConsoleReader consoleReader;

    @Autowired
    private ConsoleWriter consoleWriter;

    @Autowired
    private BoardService boardService;

    private boolean isStarted;

    public GameEngineImpl() {
        this.isStarted = true;
    }

    @Override
    public void start() {
        Board board;
        consoleWriter.writeLine(GAME_DESCRIPTION);
        int difficultyLevel = 0;
        try {
            difficultyLevel = Integer.parseInt(consoleReader.readLine());
        } catch (IOException e) {
            consoleWriter.writeLine(String.format("An error has occurred while reading input - %s", e.getMessage()));
        }
        board = boardService.createBoard(difficultyLevel);
        consoleWriter.printBoard(board);

        while (isStarted) {
            try {
                consoleWriter.writeLine(GAME_ENTER_MOVE);
                List<Integer> positions = Arrays.stream(consoleReader.readLine().split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toUnmodifiableList());

                int a = 6;

                if(true) {
                    //TODO: not mine
//                    boardService.
                } else {
                    isStarted = false;
                }
            } catch (IOException e) {
                consoleWriter.writeLine(String.format("An error has occurred while reading input - %s", e.getMessage()));
            }
        }
    }
}
