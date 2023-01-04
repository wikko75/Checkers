package com.example.checkers.game;

public class Variant {

    private final Board board;
    private final boolean menCaptureKing;
    private final boolean kingMovesManySquares;
    private final boolean mustCaptureMaxPieces;
    private final boolean manCaptureBackwards;
    private final boolean leftDownCornerBlack;

    enum Winner {
        WHITE_WIN,
        BLACK_WIN,
        DRAW,
        NONE
    }


    public Variant(int boardSize, boolean menCaptureKing,
                   boolean kingMovesManySquares, boolean mustCaptureMaxPieces,
                   boolean manCaptureBackwards, boolean leftDownCornerBlack) {

        this.menCaptureKing = menCaptureKing;
        this.kingMovesManySquares = kingMovesManySquares;
        this.mustCaptureMaxPieces = mustCaptureMaxPieces;
        this.manCaptureBackwards = manCaptureBackwards;
        this.leftDownCornerBlack = leftDownCornerBlack;

        board = new Board(boardSize,leftDownCornerBlack);
    }

    public boolean doMove(int x1, int y1, int x2, int y2, Color color, Type type) {

        //some kind of validation below
        //
        //if passed:
        board.removePiece(x1, y1);
        board.addPiece(x2, y2, color, type);

        return true;
    }

    void checkForKings() {

    }

    void checkForCapture() {

    }

    Winner checkForWinningConditions() {
        return Winner.NONE;
    }

    public String getBoardState() {
        return board.getBoardState();
    }

    public  boolean getLeftDownCornerBlack() {
        return leftDownCornerBlack;
    }











}
