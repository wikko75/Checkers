package com.example.checkers.server;

import com.example.checkers.game.Variant;

public interface VariantBuilder {

    void makeVariant();

    Variant getVariant();
}
