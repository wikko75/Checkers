package com.example.checkers.server;

import com.example.checkers.game.Variant;

/**
 * Class taking part in the implementation of the Builder Design Pattern.<br><br>
 * Defines Italian version of abstract methods declared in the Variant Builder Interface:<br>
 * - makeVariant()<br>
 * - getVariant()*/
public class ItalianVariantBuilder implements VariantBuilder {

    private Variant variant;

    /**
     *Creates Italian Variant*/
    @Override
    public void makeVariant() {
        variant = new Variant(8, false, false, true, false, false);
    }

    /**
     * Gets Italian Variant
     * @return Variant object*/
    @Override
    public Variant getVariant() {
        return variant;
    }
}
