package com.example.checkers.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void testingGetPiece() {
        Board board = new Board(8, true);
        Assertions.assertSame(board.getPiece(0, 0).getColor(), Color.WHITE);
        Assertions.assertNull(board.getPiece(1, 0));
    }

    @Test
    public void testing8x8Board() {
        String boardState = "";

        boardState = boardState.concat("WM 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM ");

        Board board = new Board(8, true);
        Assertions.assertEquals(board.getBoardState(), boardState);
    }

    @Test
    public void testing8x8Board_alternateCorner() {
        String boardState = "";

        boardState = boardState.concat("00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00 ");

        Board board = new Board(8, false);
        Assertions.assertEquals(board.getBoardState(), boardState);
    }

    @Test
    public void testing10x10Board() {
        String boardState = "";

        boardState = boardState.concat("WM 00 WM 00 00 00 BM 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM ");

        Board board = new Board(10, true);
        Assertions.assertEquals(board.getBoardState(), boardState);
    }

    @Test
    public void testing10x10Board_alternateCorner() {
        String boardState = "";

        boardState = boardState.concat("00 WM 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00 ");

        Board board = new Board(10, false);
        Assertions.assertEquals(board.getBoardState(), boardState);
    }

    @Test
    public void testingGetSize() {
        Board board8x8 = new Board(8, true);
        Board board10x10 = new Board(10, false);
        Assertions.assertEquals(board8x8.getSize(), 8);
        Assertions.assertEquals(board10x10.getSize(), 10);
    }

    @Test
    public void testingAddRemovePiece() {
        String boardState = "";

        boardState = boardState.concat("BK 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM");
        boardState = boardState.concat(" WM 00 WM 00 00 00 BM 00");
        boardState = boardState.concat(" 00 WM 00 00 00 BM 00 BM ");

        Board board = new Board(8, true);
        board.removePiece(0, 0);
        board.addPiece(0, 0, Color.BLACK, Type.KING);
        Assertions.assertEquals(board.getBoardState(), boardState);
    }
}