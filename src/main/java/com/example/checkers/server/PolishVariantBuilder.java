package com.example.checkers.server;

import com.example.checkers.game.Variant;

public class PolishVariantBuilder implements  VariantBuilder {

    private Variant variant;

    @Override
    public void makeVariant() {
        variant = new Variant(10, true, true, true, true, true);
    }

    @Override
    public Variant getVariant() {
        return variant;
    }
}
