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
        int rows = size == 8? 3: 5;
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

    public Color getPieceColor(int x, int y) {
        if(pieces[x][y]!=null) { return pieces[x][y].getColor(); }
        else { return Color.EMPTY; }
    }

    public Type getPieceType(int x, int y) {
        return pieces[x][y].getType();
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
                switch (getPieceColor(x, y)) {
                    case WHITE -> {
                        boardState = boardState.concat("W");
                        if (getPieceType(x, y) == Type.KING) {
                            boardState = boardState.concat("K ");
                        } else {
                            boardState = boardState.concat("M ");
                        }
                    }
                    case BLACK -> {
                        boardState = boardState.concat("B");
                        if (getPieceType(x, y) == Type.KING) {
                            boardState = boardState.concat("K ");
                        } else {
                            boardState = boardState.concat("M ");
                        }
                    }
                    case EMPTY -> boardState = boardState.concat("00 ");
                }
            }
        }
        return boardState;
    }
}
