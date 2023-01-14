package com.example.checkers.server;

import com.example.checkers.game.Variant;

/**
 * Class taking part in the implementation of the Builder Design Pattern.<br><br>
 * Defines German version of abstract methods declared in the Variant Builder Interface:<br>
 * - makeVariant()<br>
 * - getVariant()*/
public class GermanVariantBuilder implements VariantBuilder{

    private Variant variant;

    /**
    *Creates German Variant*/
    @Override
    public void makeVariant() {
        variant = new Variant(8, true, true, true, true, false);
    }

    /**
     * Gets German Variant
     * @return Variant object*/
    @Override
    public Variant getVariant() {
        return variant;
    }
}
