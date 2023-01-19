package com.example.checkers.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class GameModeSelectionTest {
    @Test
    public void selectingItalianTest() throws IOException, InterruptedException {
        Communicator communicator = Mockito.mock(Communicator.class);
        when(communicator.getClientInput(1)).thenReturn("italian");

        GameModeSelection gameModeSelection = new GameModeSelection(communicator);
        Thread gameModeSelectionThread = new Thread(gameModeSelection);
        gameModeSelectionThread.start();
        gameModeSelectionThread.join();

        Assertions.assertEquals(GameMode.ITALIAN, gameModeSelection.getSelection());
    }

    @Test
    public void selectingGermanTest() throws IOException, InterruptedException {
        Communicator communicator = Mockito.mock(Communicator.class);
        when(communicator.getClientInput(1)).thenReturn("german");

        GameModeSelection gameModeSelection = new GameModeSelection(communicator);
        Thread gameModeSelectionThread = new Thread(gameModeSelection);
        gameModeSelectionThread.start();
        gameModeSelectionThread.join();

        Assertions.assertEquals(GameMode.GERMAN, gameModeSelection.getSelection());
    }

    @Test
    public void selectingPolishTest() throws IOException, InterruptedException {
        Communicator communicator = Mockito.mock(Communicator.class);
        when(communicator.getClientInput(1)).thenReturn("polish");

        GameModeSelection gameModeSelection = new GameModeSelection(communicator);
        Thread gameModeSelectionThread = new Thread(gameModeSelection);
        gameModeSelectionThread.start();
        gameModeSelectionThread.join();

        Assertions.assertEquals(GameMode.POLISH, gameModeSelection.getSelection());
    }

    @Test
    public void selectingExitTest() throws IOException, InterruptedException {
        Communicator communicator = Mockito.mock(Communicator.class);
        when(communicator.getClientInput(1)).thenReturn("exit");

        GameModeSelection gameModeSelection = new GameModeSelection(communicator);
        Thread gameModeSelectionThread = new Thread(gameModeSelection);
        gameModeSelectionThread.start();
        gameModeSelectionThread.join();

        Assertions.assertEquals(GameMode.EXIT, gameModeSelection.getSelection());
    }

    @Test
    public void incorrectSelectionTest() throws IOException, InterruptedException {
        Communicator communicator = Mockito.mock(Communicator.class);
        when(communicator.getClientInput(1)).thenReturn("error");

        GameModeSelection gameModeSelection = new GameModeSelection(communicator);
        Thread gameModeSelectionThread = new Thread(gameModeSelection);
        gameModeSelectionThread.start();
        gameModeSelectionThread.join();

        Assertions.assertEquals(GameMode.NOT_SELECTED, gameModeSelection.getSelection());
    }

    @Test
    public void exceptionTest() throws IOException, InterruptedException {
        Communicator communicator = Mockito.mock(Communicator.class);
        when(communicator.getClientInput(1)).thenThrow(new IOException());

        GameModeSelection gameModeSelection = new GameModeSelection(communicator);
        Thread gameModeSelectionThread = new Thread(gameModeSelection);
        gameModeSelectionThread.start();
        gameModeSelectionThread.join();

        Assertions.assertEquals(GameMode.NOT_SELECTED, gameModeSelection.getSelection());
    }
}
