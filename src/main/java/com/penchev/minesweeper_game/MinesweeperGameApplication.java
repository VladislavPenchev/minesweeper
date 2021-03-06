package com.penchev.minesweeper_game;

import com.penchev.minesweeper_game.core.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MinesweeperGameApplication implements CommandLineRunner {

    @Autowired
    private Engine gameEngine;

    @Autowired
    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperGameApplication.class, args);
    }

    @Override
    public void run(String... args) {
        gameEngine.start();
        System.exit(SpringApplication.exit(context));
    }
}
