package com.example.checkers.server;

import com.example.checkers.game.Variant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VariantBuilderTest {

    @Test
    public void testingVariantBuilder() {
        VariantBuilder polishVariantBuilder = new PolishVariantBuilder();
        VariantBuilder brazilianVariantBuilder = new BrazilianVariantBuilder();
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

        String brazilianBoardState = "WM 00 WM 00 00 00 BM 00";
        brazilianBoardState = brazilianBoardState.concat(" 00 WM 00 00 00 BM 00 BM");
        brazilianBoardState = brazilianBoardState.concat(" WM 00 WM 00 00 00 BM 00");
        brazilianBoardState = brazilianBoardState.concat(" 00 WM 00 00 00 BM 00 BM");
        brazilianBoardState = brazilianBoardState.concat(" WM 00 WM 00 00 00 BM 00");
        brazilianBoardState = brazilianBoardState.concat(" 00 WM 00 00 00 BM 00 BM");
        brazilianBoardState = brazilianBoardState.concat(" WM 00 WM 00 00 00 BM 00");
        brazilianBoardState = brazilianBoardState.concat(" 00 WM 00 00 00 BM 00 BM ");

        Assertions.assertNotNull(variantDirector.getVariant());
        Assertions.assertEquals(variantDirector.getVariant().getBoardState(), polishBoardState);

        variantDirector.setVariantBuilder(brazilianVariantBuilder);

        Assertions.assertNotNull(variantDirector.getVariant());
        Assertions.assertEquals(variantDirector.getVariant().getBoardState(), brazilianBoardState);

        variantDirector.setVariantBuilder(null);

        Assertions.assertNull(variantDirector.getVariant());
    }
}
