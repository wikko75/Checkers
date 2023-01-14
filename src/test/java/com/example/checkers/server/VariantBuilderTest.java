package com.example.checkers.server;

import com.example.checkers.game.Variant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VariantBuilderTest {

    @Test
    public void testingVariantBuilder() {
        VariantBuilder polishVariantBuilder = new PolishVariantBuilder();
        VariantBuilder italianVariantBuilder = new ItalianVariantBuilder();
        VariantBuilder garmanVariantBuilder = new GermanVariantBuilder();
        VariantDirector variantDirector = new VariantDirector(polishVariantBuilder);

        String polishBoardState = "WM 00 WM 00 00 00 BM 00 BM 00";
        polishBoardState = polishBoardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        polishBoardState = polishBoardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        polishBoardState = polishBoardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        polishBoardState = polishBoardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        polishBoardState = polishBoardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        polishBoardState = polishBoardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        polishBoardState = polishBoardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM");
        polishBoardState = polishBoardState.concat(" WM 00 WM 00 00 00 BM 00 BM 00");
        polishBoardState = polishBoardState.concat(" 00 WM 00 WM 00 00 00 BM 00 BM ");

        String italianBoardState = "00 WM 00 00 00 BM 00 BM";
        italianBoardState = italianBoardState.concat(" WM 00 WM 00 00 00 BM 00");
        italianBoardState = italianBoardState.concat(" 00 WM 00 00 00 BM 00 BM");
        italianBoardState = italianBoardState.concat(" WM 00 WM 00 00 00 BM 00");
        italianBoardState = italianBoardState.concat(" 00 WM 00 00 00 BM 00 BM");
        italianBoardState = italianBoardState.concat(" WM 00 WM 00 00 00 BM 00");
        italianBoardState = italianBoardState.concat(" 00 WM 00 00 00 BM 00 BM");
        italianBoardState = italianBoardState.concat(" WM 00 WM 00 00 00 BM 00 ");

        Assertions.assertNotNull(variantDirector.getVariant());
        Assertions.assertEquals(variantDirector.getVariant().getBoardState(), polishBoardState);

        variantDirector.setVariantBuilder(italianVariantBuilder);

        Assertions.assertNotNull(variantDirector.getVariant());
        Assertions.assertEquals(variantDirector.getVariant().getBoardState(), italianBoardState);

        variantDirector.setVariantBuilder(garmanVariantBuilder);

        Assertions.assertNotNull(variantDirector.getVariant());
        Assertions.assertEquals(variantDirector.getVariant().getBoardState(), italianBoardState);

        variantDirector.setVariantBuilder(null);

        Assertions.assertNull(variantDirector.getVariant());
    }
}
