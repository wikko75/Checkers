package com.example.checkers.game;

import com.example.checkers.client.Main;

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

    public boolean doMove(int x1, int y1, int x2, int y2, Color color){
        Type type = board.getPiece(x1, y1).getType();

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


        if (board.getPiece(x1, y1).getColor() == Color.WHITE) {

            if (board.getPiece(x1, y1).getType() == Type.MAN) {

                int offsetX = Math.abs(x1 - x2);
                int offsetY = y2 - y1;

                if (offsetX == 1 && offsetY == 1) {
                    if (board.getPiece(x2, y2) == null) {
                        board.addPiece(x2,y2,color, board.getPiece(x1,y1).getType());
                        board.removePiece(x1,y1);
                        return true;
                    }
                }
                else {
                    if (offsetX == 2 && offsetY == 2) {
                        if (x2 > x1) {
                            if (board.getPiece(x2 - 1, y2 - 1) != null) {
                                board.addPiece(x2, y2, color, board.getPiece(x1, y1).getType());
                                board.removePiece(x2 - 1, y2 - 1);
                                board.removePiece(x1, y1);
                                return true;
                            }
                        } else if(x2 < x1){
                            if (board.getPiece(x2 + 1, y2 - 1) != null) {
                                board.addPiece(x2, y2, color, board.getPiece(x1, y1).getType());
                                board.removePiece(x2 + 1, y2 - 1);
                                board.removePiece(x1, y1);
                                return true;
                                }
                            }
                         }
                    }
                }
                else {
                    //king
            }
            } else if (board.getPiece(x1, y1).getColor() == Color.BLACK) {
                if(type == Type.MAN){
                    int offsetX = Math.abs(x1 - x2);
                    int offsetY = Math.abs(y2 - y1);

                    if (offsetX == 1 && offsetY == 1) {
                        if (board.getPiece(x2, y2) == null) {
                            board.addPiece(x2,y2,color, board.getPiece(x1,y1).getType());
                            board.removePiece(x1,y1);
                            return true;
                        }
                    } else {
                        if (offsetX == 2 && offsetY == 2) {
                            if (x2 > x1) {
                                if (board.getPiece(x2 - 1, y2 + 1) != null) {
                                    board.addPiece(x2, y2, color, board.getPiece(x1, y1).getType());
                                    board.removePiece(x2 - 1, y2 + 1);
                                    board.removePiece(x1, y1);
                                    return true;
                                }
                            } else if(x2 < x1){
                                if (board.getPiece(x2 + 1, y2 + 1) != null) {
                                    board.addPiece(x2, y2, color, board.getPiece(x1, y1).getType());
                                    board.removePiece(x2 + 1, y2 + 1);
                                    board.removePiece(x1, y1);
                                    return true;
                                }
                            }
                        }
                    }
                } else {
                    //king
                }

        }
        return false;
    }


    public void checkForKings() {
        int size = getBoardSize();
        // check for white kings
        for(int i=0; i<size; i++) {
            if(board.getPiece(i, size-1)!=null) {
                if (board.getPiece(i, size - 1).getColor() == Color.WHITE && board.getPiece(i, size - 1).getType() == Type.MAN) {
                    board.removePiece(i, size - 1);
                    board.addPiece(i, size - 1, Color.WHITE, Type.KING);
                    System.out.println("White king!");
                }
            }
        }
        // check for black kings
        for(int i=0; i<size; i++) {
            if(board.getPiece(i, 0)!=null) {
                if (board.getPiece(i, 0).getColor() == Color.BLACK && board.getPiece(i, 0).getType() == Type.MAN) {
                    board.removePiece(i, 0);
                    board.addPiece(i, 0, Color.BLACK, Type.KING);
                }
                System.out.println("Black king!");
            }
        }
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
