package com.example.checkers.game;

public class Board {

    /**
     * Describes how many squares the board has in both direction:
     * effectively the board's shape is size x size
     * It is set only once during creation of object
     */
    protected final int size;

    protected Piece[][] pieces;

    Board(int size, boolean leftDownCornerBlack) {
        this.size = size;
        pieces = new Piece[size][size];

        // parameters
        int rows = size == 8? 3: 4;
        int offset = leftDownCornerBlack? 0: 1;

        // placing white pieces
        for(int y=0; y<rows; y++) {
            for(int x=0; x<size; x+=2) {
                pieces[x+(y+offset)%2][y] = new Piece(Color.WHITE, Type.MAN);
            }
        }
        // placing black pieces
        for(int y=size-rows; y<size; y++) {
            for (int x = 0; x < size; x += 2) {
                pieces[x + (y + offset) % 2][y] = new Piece(Color.BLACK, Type.MAN);
            }
        }
    }

    public int getSize() {
        return size;
    }
    public Piece getPiece(int x, int y) {
        return pieces[x][y];
    }

    public void addPiece(int x, int y, Color color, Type type) {
        pieces[x][y] = new Piece(color, type);
    }

    public void removePiece(int x, int y) {
        pieces[x][y] = null;
    }

    public String getBoardState() {
        String boardState = "";
        for (int x=0; x<size; x++) {
            for (int y=0; y<size; y++) {
                if(getPiece(x, y)!=null) {
                    if(getPiece(x, y).getColor()==Color.WHITE) {
                        boardState = boardState.concat("W"); }
                    else {
                        boardState = boardState.concat("B"); }
                    if(getPiece(x, y).getType()==Type.MAN) {
                        boardState = boardState.concat("M "); }
                    else {
                        boardState = boardState.concat("K "); }
                } else {
                    boardState = boardState.concat("00 "); }
            }
        }
        return boardState;
    }
}
