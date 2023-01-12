package com.example.checkers.game;

public class Variant {

    private final Board board;
    private final boolean menCaptureKing;
    private final boolean kingMovesManySquares;
    private final boolean mustCaptureMaxPieces;
    private final boolean manCaptureBackwards;
    private final boolean leftDownCornerBlack;

    public enum Winner {
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

    public boolean doMove(int x1, int y1, int x2, int y2, Color color, Type type){

        //general validation
        //-----------------------------------------------------------------------
        if (x1 < 0 || x1 > board.getSize() || y1 < 0 || y1 > board.getSize() ||
            x2 < 0 || x2 > board.getSize() || y2 < 0 || y2 > board.getSize() ) {
            //throw exception
            System.out.println("Out of board!");
            return false;
        }

        if (board.getPiece(x1, y1) == null) {
            //throw exception
            System.out.println("No piece at: (" + x1 +"," + y1 + ")!" );
            return false;
        }

        if (board.getPiece(x1, y1).getColor() != color || board.getPiece(x1, y1).getType() != type) {
            //throw color exception
            System.out.println("Not your color!");
            return false;
        }
        //-----------------------------------------------------------------------

        //variants of game rules
        //-----------------------------------------------------------------------
        if (menCaptureKing && kingMovesManySquares && mustCaptureMaxPieces && manCaptureBackwards) {

            if (board.getPiece(x1, y1).getType() == Type.MAN) {

                if (board.getPiece(x1 - 1, y1 + 1) != null && board.getPiece(x1 - 2, y1 + 2) == null ||
                        board.getPiece(x1 + 1, y1 + 1) != null && board.getPiece(x1 + 2, y1 + 2) == null ||
                        board.getPiece(x1 - 1, y1 - 1) != null && board.getPiece(x1 - 2, y1 - 2) == null ||
                        board.getPiece(x1 + 1, y1 - 1) != null && board.getPiece(x1 + 2, y1 - 2) == null) {

                    if (board.getPiece(x1 - 1, y1 - 1).getColor() == color && board.getPiece(x1 + 1, y1 - 1).getColor() == color) {

                        if (x2 != x1 + 2 && x2 != x1 - 2 || y2 != y1 + 2) {
                            //throw exception
                            System.out.println("Wrong movement!");
                            return false;
                        }

                        board.removePiece(x1, y1);
                        board.addPiece(x2, y2, color, type);
                        if (x2 > x1) {
                            board.removePiece(x2 - 1, y2 - 1);
                        } else {
                            board.removePiece(x2 + 1, y2 - 1);
                        }

                    } else {

                    }
                }

                if (x2 != x1 + 1 && x2 != x1 - 1 || y2 != y1 + 1 && y2 != y1 -1) {
                    //throw exception
                    System.out.println("Wrong movement!");
                    return false;
                }
                //simple movement
                if (board.getPiece(x2, y2) == null && (x2 == x1 + 1 || x2 == x1 - 1 && y2 == y1 + 1)) {
                    board.removePiece(x1, y1);
                    board.addPiece(x2, y2, color, type);
                    return true;
                }
            }
        }


        if (board.getPiece(x2, y2).getColor() == color) {
            // throw exception
            System.out.println("Can not capture your own piece!");
            return false;
        }

        //if passed:
        board.removePiece(x1, y1);
        board.addPiece(x2, y2, color, type);

        return true;
    }

    void checkForKings() {

    }

    void checkForCapture() {

    }

    public Winner checkForWinningConditions() {
        return Winner.NONE;
    }

    public String getBoardState() {
        return board.getBoardState();
    }

    public  boolean getLeftDownCornerBlack() {
        return leftDownCornerBlack;
    }

    public int getBoardSize() {
        return board.getSize();
    }
}
