package com.example.checkers.game;

/**
 * Class responsible for managing the game board*/
public class Board {

    /**
     * Describes how many squares the board has in both direction:
     * effectively the board's shape is size x size
     * It is set only once during creation of object
     */
    protected final int size;

    /**Pieces placed on the board and used throughout the game*/
    protected Piece[][] pieces;

    /**
     * Creates the game board, places pieces on their default positions
     * @param size board size  \link size \endlink
     * @param leftDownCornerBlack feature specifying board layout
     */
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

    /**
     * Gets the size of the game board
     * @return Board size*/
    public int getSize() {
        return size;
    }

    /**
     * Gets the piece from given board coordinates
     * @param x  coordinate
     * @param y  coordinate
     * @return piece object at (x,y) */
    public Piece getPiece(int x, int y) {
        return pieces[x][y];
    }

    /**
     * Places the piece on the board at given coordinates
     * @param x piece's coordinate
     * @param y piece's coordinate
     * @param color piece's color (WHITE / BLACK)
     * @param type piece's type (MAN / KING)*/
    public void addPiece(int x, int y, Color color, Type type) {
        pieces[x][y] = new Piece(color, type);
    }

    /**Removes the piece from the game board
     * @param x piece's coordinate
     * @param y piece's coordinate*/
    public void removePiece(int x, int y) {
        pieces[x][y] = null;
    }

    /**
     * Gets the board state in form of the String <br>
     * Example: <br>
     * "WM 00 WM 00 00 00 BM 00..." <br>
     * W - white piece <br>
     * B - black piece <br>
     * M - man <br>
     * K - king <br>
     * 00 - no piece at all <br>
     * @return String representation of the board state, column by column
     * */
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
