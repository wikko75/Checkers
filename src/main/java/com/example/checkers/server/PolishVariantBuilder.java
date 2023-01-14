package com.example.checkers.server;

import com.example.checkers.game.Variant;

/**
 * Class taking part in the implementation of the Builder Design Pattern.<br><br>
 * Defines Polish version of abstract methods declared in the Variant Builder Interface:<br>
 * - makeVariant()<br>
 * - getVariant()*/
public class PolishVariantBuilder implements  VariantBuilder {

    private Variant variant;

    /**
     *Creates Polish Variant*/
    @Override
    public void makeVariant() {
        variant = new Variant(10, true, true, true, true, true);
    }

    /**
     * Gets Polish Variant
     * @return Variant object*/
    @Override
    public Variant getVariant() {
        return variant;
    }
}
