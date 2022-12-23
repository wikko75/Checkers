package com.example.checkers;

public class Piece {

    protected enum Color {
        WHITE,
        BLACK
    }

    protected enum Type {
        MAN,
        KING
    }

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
