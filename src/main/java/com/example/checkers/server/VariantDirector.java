package com.example.checkers.server;

import com.example.checkers.game.Variant;

public class VariantDirector {

    private VariantBuilder variantBuilder;

    public VariantDirector(VariantBuilder variantBuilder) {
        this.variantBuilder = variantBuilder;
    }

    public void setVariantBuilder(VariantBuilder variantBuilder) {
        this.variantBuilder = variantBuilder;
    }

    public Variant getVariant() {
        if(variantBuilder!=null) {
            variantBuilder.makeVariant();
            return variantBuilder.getVariant();
        }
        return null;
    }
}
