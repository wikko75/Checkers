package com.example.checkers.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    public void testingPieceVariants() {
        Piece piece = new Piece(Color.WHITE, Type.MAN);
        Assertions.assertSame(piece.getColor(), Color.WHITE);
        Assertions.assertSame(piece.getType(), Type.MAN);

        piece = new Piece(Color.BLACK, Type.KING);
        Assertions.assertSame(piece.getColor(), Color.BLACK);
        Assertions.assertSame(piece.getType(), Type.KING);
    }
}