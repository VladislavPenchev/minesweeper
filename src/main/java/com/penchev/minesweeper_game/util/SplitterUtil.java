package com.penchev.minesweeper_game.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SplitterUtil {

    /**
     * Split argument by regex ", "
     *
     * @param rowAndColArgs contains coordinates of row and col
     * @return {@code List<Integer>} first position - row, second position - col
     */
    public static List<Integer> rowsAndCols(String rowAndColArgs) {
        return Arrays.stream(rowAndColArgs.split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toUnmodifiableList());
    }
}
