package com.example.checkers.server;

import com.example.checkers.game.Variant;

public class GermanVariantBuilder implements VariantBuilder{

    private Variant variant;

    @Override
    public void makeVariant() {
        variant = new Variant(8, true, true, true, true, false);
    }

    @Override
    public Variant getVariant() {
        return variant;
    }
}
