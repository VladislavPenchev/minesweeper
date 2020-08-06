package com.penchev.minesweeper_game.service.impl;

import com.penchev.minesweeper_game.constants.SignConstants;
import com.penchev.minesweeper_game.domain.entities.Board;
import com.penchev.minesweeper_game.domain.entities.Position;
import com.penchev.minesweeper_game.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoardServiceImpl implements BoardService {
    private static final int ROWS_COLS_FOR_BEGINNER = 3;  //9
    private static final int ROWS_COLS_FOR_INTERMEDIATE = 16;
    private static final int ROWS_COLS_FOR_ADVANCED = 24;

    private static final int BEGINNER_MINES_COUNT = 2; // 10
    private static final int INTERMEDIATE_MINES_COUNT = 40;
    private static final int ADVANCED_MINES_COUNT = 99;

    @Override
    public Board createBoard(int difficultyLevel) {
        Board board;
        switch (difficultyLevel) {
            case 0:
                board = new Board(ROWS_COLS_FOR_BEGINNER);
                board.setCountOfMines(BEGINNER_MINES_COUNT);
//                Position position1 = new Position(0, 2);
                Position position2 = new Position(1, 2);
                Position position3 = new Position(2, 2);
                Set<Position> positions = new HashSet<>();
//                positions.add(position1);
                positions.add(position2);
                positions.add(position3);
                board.setMines(positions);
//                fillBoardWithMines(board);
                return generateInitBoard(board);
            case 1:
                board = new Board(ROWS_COLS_FOR_INTERMEDIATE);
                board.setCountOfMines(INTERMEDIATE_MINES_COUNT);
                fillBoardWithMines(board);
                return generateInitBoard(board);
            case 2:
                board = new Board(ROWS_COLS_FOR_ADVANCED);
                board.setCountOfMines(ADVANCED_MINES_COUNT);
                fillBoardWithMines(board);
                return generateInitBoard(board);
            default:
                return null;

        }
    }

    @Override
    public void editFirstPositionIfMine(Board board, List<Integer> constraints) {
        board.getMines().remove(new Position(constraints.get(0), constraints.get(1)));

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        while (board.getMines().size() != board.getCountOfMines()) {
            int row = Math.abs(random.nextInt(board.getRowsSize()) % 100);
            int col = Math.abs(random.nextInt(board.getRowsSize()) % 100);
            Position position = new Position(row, col);
            board.getMines().add(position);
        }
    }

    /**
     * Foreach all neighbour cells and calculate sum of green cells. Ignore all
     * coordinates that are invalid.
     *
     * @param board  non empty grid
     * @param rowArg current coordinate of row
     * @param colArg current coordinate of col
     * @return {@code Integer} sum of green neighbours.
     */
    @Override
    public int calculateMines(Board board, int rowArg, int colArg) {
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

    @Override
    public List<Position> calculateEmptyFields(Board board, int rowArg, int colArg) {
        List<Position> positionsOfEmptyFields = new ArrayList<>();
        int initRow = rowArg - 1;
        int initCol = colArg - 1;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (initRow + row < 0 || initCol + col < 0
                        || initRow + row > board.getRowsSize() - 1 || initCol + col > board.getColsSize() - 1) {
                    continue;
                }
                Position currPosition = new Position(initRow + row, initCol + col);
                if (!board.getMines().contains(currPosition)) {
                    positionsOfEmptyFields.add(currPosition);
                }
            }
        }
        return positionsOfEmptyFields;
    }

    private Board generateInitBoard(Board board) {
        for (int row = 0; row < board.getRowsSize(); row++) {
            for (int col = 0; col < board.getColsSize(); col++) {
                Position position = new Position(row, col);
                board.add(position, SignConstants.SIGN_INIT_FIELD);
            }
        }
        return board;
    }

    private void fillBoardWithMines(Board board) {
        Set<Position> mines = new HashSet<>();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        while (mines.size() != board.getCountOfMines()) {
            int row = Math.abs(random.nextInt(board.getRowsSize()) % 100);
            int col = Math.abs(random.nextInt(board.getRowsSize()) % 100);
            Position position = new Position(row, col);
            mines.add(position);
        }

        board.setMines(mines);
    }
}
