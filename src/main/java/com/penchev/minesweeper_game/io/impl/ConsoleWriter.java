package com.penchev.minesweeper_game.io.impl;

import com.penchev.minesweeper_game.constants.SignConstants;
import com.penchev.minesweeper_game.domain.entities.Board;
import com.penchev.minesweeper_game.domain.entities.Position;
import com.penchev.minesweeper_game.io.OutputWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleWriter implements OutputWriter {

    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }

    @Override
    public void printBoard(Board board, boolean gameOver) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current Status of Board :")
                .append(System.lineSeparator());

        if(gameOver) {
            for(Position minePositions : board.getMines()){
                board.getBoard()[minePositions.getX()][minePositions.getY()] = SignConstants.SIGN_MINE_FIELD;
            }
        }

        for (int row = 0; row < board.getRowsSize(); row++) {
            for (int col = 0; col < board.getColsSize(); col++) {
                stringBuilder.append(String.format("%s ", board.getBoard()[row][col]));
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1)
                    .append(System.lineSeparator());
        }

        System.out.println(stringBuilder.toString());
    }
}
