package com.example.checkers.game;

public class Piece {

    private final Color color;
    private final Type type;

    Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    Color getColor() {
        return color;
    }

    Type getType() {
        return type;
    }

}
