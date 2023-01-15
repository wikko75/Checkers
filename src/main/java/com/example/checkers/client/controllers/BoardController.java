package com.example.checkers.client.controllers;

public abstract class BoardController extends Controller {
    private boolean leftDownCornerBlack;

    public void setLeftDownCornerBlack(boolean leftDownCornerBlack) {
        this.leftDownCornerBlack = leftDownCornerBlack;
    }
}
