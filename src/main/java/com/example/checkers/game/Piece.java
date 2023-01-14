package com.example.checkers.game;
/**
 * Class managing the piece of the game*/
public class Piece {

    /**Color of the piece @see ENUM Color*/
    private final Color color;

    /**Type of the piece @see ENUM Type*/
    private final Type type;

    /**
     * Constructor - defines the color and the type of the piece instance
     * @param color piece's color
     * @param type  piece's type*/
    Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Gets the color of the piece
     * @return piece's color (WHITE / BLACK)*/
    Color getColor() {
        return color;
    }

    /**
     * Gets the type of the piece
     * @return  piece's type (MAN / KING)*/
    Type getType() {
        return type;
    }

}
