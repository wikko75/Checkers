package com.example.checkers.server;

import com.example.checkers.game.Variant;

/**Interface taking part in the implementation of the Builder Design Pattern.<br><br>
 * Contains abstract methods: <br>
 * - makeVariant();<br>
 * - getVariant();<br>
 * implemented by specific VariantBuilders<br>*/
public interface VariantBuilder {

    /**
    *Abstract method for creating Variant object, further implemented by specific VariantBuilders*/
    void makeVariant();

    /**
    *Abstract method for getting Variant object, further implemented by specific VariantBuilders
     * @return  Variant object*/
    Variant getVariant();
}
