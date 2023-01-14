package com.example.checkers.server;

import com.example.checkers.game.Variant;

/**
 * Class taking part in the implementation of the Builder Design Pattern.<br><br>
 * Manages VariantBuilder*/
public class VariantDirector {

    private VariantBuilder variantBuilder;

    /**
     *Constructor
     * @param variantBuilder Variant Builder to be set*/
    public VariantDirector(VariantBuilder variantBuilder) {
        this.variantBuilder = variantBuilder;
    }

    /**
     * Sets Variant Builder
     * @param variantBuilder VariantBuilder to be set*/
    public void setVariantBuilder(VariantBuilder variantBuilder) {
        this.variantBuilder = variantBuilder;
    }

    /**
     *Gets Variant of specific type (earlier created by VariantBuilder)
     * @return Variant object*/
    public Variant getVariant() {
        if(variantBuilder!=null) {
            variantBuilder.makeVariant();
            return variantBuilder.getVariant();
        }
        return null;
    }
}
