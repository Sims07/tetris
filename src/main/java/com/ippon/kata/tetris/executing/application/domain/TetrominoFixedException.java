package com.ippon.kata.tetris.executing.application.domain;

public class TetrominoFixedException extends RuntimeException {

    private final Tetromino tetromino;
    private final Board board;
    private final boolean outOfScope;

    public TetrominoFixedException(Tetromino tetromino, Board board, boolean outOfScope) {
        this.tetromino = tetromino;
        this.board = board;
        this.outOfScope = outOfScope;
    }

    public Board board() {
        return board;
    }

    public Tetromino tetromino() {
        return tetromino;
    }

    public boolean outOfScope() {
        return outOfScope;
    }
}
