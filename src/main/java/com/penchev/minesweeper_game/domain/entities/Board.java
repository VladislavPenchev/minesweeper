package com.penchev.minesweeper_game.domain.entities;

import lombok.Data;

import java.util.Set;

@Data
public class Board {
    private String[][] board;
    private Set<Position> mines;

    public Board(int rowsAndCols) {
        this.board = new String[rowsAndCols][rowsAndCols];
    }

    /**
     * Inserts the specified element at the specified position in this
     * grid.
     *
     * @param position coordinates at which the specified element is to be inserted
     * @param rowValue value of element to be inserted
     */
    public void add(Position position, String rowValue) {
        board[position.getX()][position.getY()] = rowValue;
    }

    /**
     * @return {@code Integer} size of row
     */
    public int getRowsSize() {
        return board.length;
    }

    /**
     * @return {@code Integer} size of col
     */
    public int getColsSize() {
        return board[0].length;
    }
}
