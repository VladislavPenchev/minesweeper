package com.penchev.minesweeper_game.core.impl;

import com.penchev.minesweeper_game.constants.SignConstants;
import com.penchev.minesweeper_game.core.Engine;
import com.penchev.minesweeper_game.domain.entities.Board;
import com.penchev.minesweeper_game.domain.entities.Position;
import com.penchev.minesweeper_game.io.impl.ConsoleReader;
import com.penchev.minesweeper_game.io.impl.ConsoleWriter;
import com.penchev.minesweeper_game.service.BoardService;
import com.penchev.minesweeper_game.util.SplitterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private Set<Position> usedPositions;

    public GameEngineImpl() {
        this.isStarted = true;
        usedPositions = new HashSet<>();
    }

    @Override
    public void start() {
        Board board;
        consoleWriter.writeLine(GAME_DESCRIPTION);
        int difficultyLevel = 0;
        try {
            difficultyLevel = Integer.parseInt(consoleReader.readLine());
            if(difficultyLevel < 0) {
                consoleWriter.writeLine("Difficulty level can not be less than zero!");
                return;
            }
        } catch (IOException e) {
            consoleWriter.writeLine(String.format("An error has occurred while reading input - %s", e.getMessage()));
        }

        board = boardService.createBoard(difficultyLevel);
        consoleWriter.printBoard(board, false);

        while (isStarted) {
            try {
                consoleWriter.writeLine(GAME_ENTER_MOVE);
                List<Integer> positions = SplitterUtil.rowsAndCols(consoleReader.readLine());

                if(positions.get(0) < 0 || positions.get(1) < 0){
                    consoleWriter.writeLine("Row or col can not be less than zero!");
                    return;
                }

                if (checkIsMine(board, positions) && checkFirstPositionIsMine(board)) {
                    boardService.editFirstPositionIfMine(board, positions);
                }

                if (!checkIsMine(board, positions)) {
                    List<Position> collectionOfPositions = new ArrayList<>();
                    collectionOfPositions.add(new Position(positions.get(0), positions.get(1)));
                    generateNextBoard(board, collectionOfPositions);
                    consoleWriter.printBoard(board, false);

                } else {
                    consoleWriter.printBoard(board, true);
                    isStarted = false;
                }
            } catch (IOException e) {
                consoleWriter.writeLine(String.format("An error has occurred while reading input - %s", e.getMessage()));
            }
        }
    }

    private boolean checkIsMine(Board board, List<Integer> positions) {
        return board.getMines().contains(new Position(positions.get(0), positions.get(1)));
    }

    private boolean checkFirstPositionIsMine(Board board) {
        for (int row = 0; row < board.getRowsSize(); row++) {
            for (int col = 0; col < board.getColsSize(); col++) {
                if (!board.getBoard()[row][col].equals(SignConstants.SIGN_INIT_FIELD)
                        || board.getBoard()[row][col].equals(SignConstants.SIGN_MINE_FIELD)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkIsOnlyMines(Board board) {
        for (int row = 0; row < board.getRowsSize(); row++) {
            for (int col = 0; col < board.getColsSize(); col++) {
                if (board.getBoard()[row][col].equals(SignConstants.SIGN_INIT_FIELD)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void generateNextBoard(Board board, List<Position> positions) {
        usedPositions.addAll(positions);
        for (Position currPosition : positions) {
            int countOfMines = boardService.calculateMines(board, currPosition.getX(), currPosition.getY());
            List<Position> emptyPositions = boardService.calculateEmptyFields(board, currPosition.getX(), currPosition.getY());
            emptyPositions.removeAll(usedPositions);
            if (countOfMines > 0) {
                //has mines
                board.getBoard()[currPosition.getX()][currPosition.getY()] = String.valueOf(countOfMines);
                continue;
            } else {
                //hasn't mines
                board.getBoard()[currPosition.getX()][currPosition.getY()] = SignConstants.SIGN_EMPTY_FIELD;
                generateNextBoard(board, emptyPositions);
            }
        }
    }
}
