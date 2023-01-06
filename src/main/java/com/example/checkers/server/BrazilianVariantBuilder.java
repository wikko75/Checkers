package com.example.checkers.server;

import com.example.checkers.game.Variant;

public class BrazilianVariantBuilder implements VariantBuilder {

    private Variant variant;

    @Override
    public void makeVariant() {
        variant = new Variant(8, true, true, true, true, true);
    }

    @Override
    public Variant getVariant() {
        return variant;
    }
}
