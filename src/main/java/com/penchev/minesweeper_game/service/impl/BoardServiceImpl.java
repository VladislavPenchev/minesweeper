package com.penchev.minesweeper_game.service.impl;

import com.penchev.minesweeper_game.domain.entities.Board;
import com.penchev.minesweeper_game.domain.entities.Position;
import com.penchev.minesweeper_game.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class BoardServiceImpl implements BoardService {
    private static final int ROWS_COLS_FOR_BEGINNER = 9;
    private static final int ROWS_COLS_FOR_INTERMEDIATE = 16;
    private static final int ROWS_COLS_FOR_ADVANCED = 24;

    private static final int BEGINNER_MINES_COUNT = 10;
    private static final int INTERMEDIATE_MINES_COUNT = 40;
    private static final int ADVANCED_MINES_COUNT = 99;

    @Override
    public Board createBoard(int difficultyLevel) {
        Board board;
        switch (difficultyLevel) {
            case 0:
                board = new Board(ROWS_COLS_FOR_BEGINNER);
                fillBoardWithMines(board, BEGINNER_MINES_COUNT);
                return generateInitBoard(board);
            case 1:
                board = new Board(ROWS_COLS_FOR_INTERMEDIATE);
                fillBoardWithMines(board, INTERMEDIATE_MINES_COUNT);
                return generateInitBoard(board);
            case 2:
                board = new Board(ROWS_COLS_FOR_ADVANCED);
                fillBoardWithMines(board, ADVANCED_MINES_COUNT);
                return generateInitBoard(board);
            default:
                return null;

        }
    }

    private Board generateInitBoard(Board board) {
        for (int row = 0; row < board.getRowsSize(); row++) {
            for (int col = 0; col < board.getColsSize(); col++) {
                Position position = new Position(row, col);
                board.add(position, "-");
            }
        }
        return board;
    }

    private void fillBoardWithMines(Board board, int minesCount) {
        Set<Position> mines = new HashSet<>();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        while(mines.size() != minesCount) {
            int row = Math.abs(random.nextInt(board.getRowsSize()) % 100);
            int col = Math.abs(random.nextInt(board.getRowsSize()) % 100);
            Position position = new Position(row, col);
            mines.add(position);
        }

        board.setMines(mines);
    }

    /**
     * Foreach all neighbour cells and calculate sum of green cells. Ignore all
     * coordinates that are invalid.
     *
     * @param board   non empty grid
     * @param rowArg current coordinate of row
     * @param colArg current coordinate of col
     * @return {@code Integer} sum of green neighbours.
     */
    private int calculateMines(Board board, int rowArg, int colArg) {
        int sumOfGreen = 0;
        int initRow = rowArg - 1;
        int initCol = colArg - 1;

        sumOfGreen -= board.getMines().contains(new Position(initRow, initCol)) ? 1 : 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (initRow + row < 0 || initCol + col < 0
                        || initRow + row > board.getRowsSize() - 1 || initCol + col > board.getColsSize() - 1) {
                    continue;
                }
                sumOfGreen += board.getMines().contains(new Position(initRow + row, initCol + col)) ? 1 : 0;
            }
        }
        return sumOfGreen;
    }
}
