package com.example.checkers.server;

import com.example.checkers.game.Variant;

public class ItalianVariantBuilder implements VariantBuilder {

    private Variant variant;

    @Override
    public void makeVariant() {
        variant = new Variant(8, false, false, true, false, false);
    }

    @Override
    public Variant getVariant() {
        return variant;
    }
}
