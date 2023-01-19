package com.example.checkers.server;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CommunicatorTest {
    public Communicator setUp(TestClient firstClient, TestClient secondClient, int port) throws IOException, InterruptedException {
        TestServer testServer = new TestServer(port);
        Thread serverThread = new Thread(testServer);
        serverThread.start();

        Thread firstClientThread = new Thread(firstClient);
        firstClientThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread secondClientThread = new Thread(secondClient);
        secondClientThread.start();

        serverThread.join();
        return testServer.getCommunicator();
    }

    @Test
    public void initiateTest() throws IOException, InterruptedException {
        TestClient firstClient = new TestClient(4444);
        TestClient secondClient = new TestClient(4444);
        Communicator communicator = setUp(firstClient, secondClient, 4444);
        Assertions.assertNotNull(communicator);

        communicator.initiate();
        TimeUnit.SECONDS.sleep(1);
        JSONObject json1 = new JSONObject(firstClient.getLatestMessage());
        JSONObject json2 = new JSONObject(secondClient.getLatestMessage());

        Assertions.assertEquals("init", json1.getString("instruction"));
        Assertions.assertEquals(1, json1.getInt("value"));

        Assertions.assertEquals("init", json2.getString("instruction"));
        Assertions.assertEquals(2, json2.getInt("value"));
    }

    @Test
    public void terminateTest() throws IOException, InterruptedException {
        TestClient firstClient = new TestClient(4445);
        TestClient secondClient = new TestClient(4445);
        Communicator communicator = setUp(firstClient, secondClient, 4445);
        Assertions.assertNotNull(communicator);

        communicator.terminate();
        TimeUnit.SECONDS.sleep(1);
        String message1 = firstClient.getLatestMessage();
        String message2 = secondClient.getLatestMessage();
        Assertions.assertEquals(message1, message2);
        JSONObject json = new JSONObject(message1);

        Assertions.assertEquals("terminate", json.getString("instruction"));
    }

    @Test
    public void sendGameModeSelectionRequestTest() throws IOException, InterruptedException {
        TestClient firstClient = new TestClient(4446);
        TestClient secondClient = new TestClient(4446);
        Communicator communicator = setUp(firstClient, secondClient, 4446);
        Assertions.assertNotNull(communicator);

        communicator.sendGameModeSelectionRequest();
        TimeUnit.SECONDS.sleep(1);
        String message1 = firstClient.getLatestMessage();
        String message2 = secondClient.getLatestMessage();
        Assertions.assertEquals(message1, message2);
        JSONObject json = new JSONObject(message1);

        Assertions.assertEquals("select_game_mode", json.getString("instruction"));
        Assertions.assertEquals(1, json.getInt("player"));
    }

    @Test
    public void sendMoveRequestTest() throws IOException, InterruptedException {
        TestClient firstClient = new TestClient(4447);
        TestClient secondClient = new TestClient(4447);
        Communicator communicator = setUp(firstClient, secondClient, 4447);
        Assertions.assertNotNull(communicator);

        communicator.sendMoveRequest(1, "testMessage");
        TimeUnit.SECONDS.sleep(1);
        String message1 = firstClient.getLatestMessage();
        String message2 = secondClient.getLatestMessage();
        Assertions.assertEquals(message1, message2);
        JSONObject json = new JSONObject(message1);

        Assertions.assertEquals("move", json.getString("instruction"));
        Assertions.assertEquals(1, json.getInt("player"));
        Assertions.assertEquals("testMessage", json.getString("message"));
    }

    @Test
    public void createBoardTest() throws IOException, InterruptedException {
        TestClient firstClient = new TestClient(4448);
        TestClient secondClient = new TestClient(4448);
        Communicator communicator = setUp(firstClient, secondClient, 4448);
        Assertions.assertNotNull(communicator);

        communicator.createBoard(8, true);
        TimeUnit.SECONDS.sleep(1);
        String message1 = firstClient.getLatestMessage();
        String message2 = secondClient.getLatestMessage();
        Assertions.assertEquals(message1, message2);
        JSONObject json = new JSONObject(message1);

        Assertions.assertEquals("create_board", json.getString("instruction"));
        Assertions.assertTrue(json.getBoolean("black"));
    }

    @Test
    public void drawBoardTest() throws IOException, InterruptedException {
        TestClient firstClient = new TestClient(4449);
        TestClient secondClient = new TestClient(4449);
        Communicator communicator = setUp(firstClient, secondClient, 4449);
        Assertions.assertNotNull(communicator);

        communicator.drawBoard("WM 00 00 BK");
        TimeUnit.SECONDS.sleep(1);
        String message1 = firstClient.getLatestMessage();
        String message2 = secondClient.getLatestMessage();
        Assertions.assertEquals(message1, message2);
        JSONObject json = new JSONObject(message1);

        Assertions.assertEquals("draw_board", json.getString("instruction"));
        Assertions.assertEquals("WM 00 00 BK", json.getString("board_state"));
    }

    @Test
    public void endGameTest() throws IOException, InterruptedException {
        TestClient firstClient = new TestClient(4450);
        TestClient secondClient = new TestClient(4450);
        Communicator communicator = setUp(firstClient, secondClient, 4450);
        Assertions.assertNotNull(communicator);

        communicator.endGame("white");
        TimeUnit.SECONDS.sleep(1);
        String message1 = firstClient.getLatestMessage();
        String message2 = secondClient.getLatestMessage();
        Assertions.assertEquals(message1, message2);
        JSONObject json = new JSONObject(message1);

        Assertions.assertEquals("end_game", json.getString("instruction"));
        Assertions.assertEquals("white", json.getString("winner"));
    }

    @Test
    public void getClientInputTest() throws IOException, InterruptedException {
        TestClient firstClient = new TestClient(4451);
        TestClient secondClient = new TestClient(4451);
        Communicator communicator = setUp(firstClient, secondClient, 4451);
        Assertions.assertNotNull(communicator);

        class MessageClass implements Runnable {
            private volatile String message;
            private int client;
            @Override
            public void run() {
                try {
                    message = communicator.getClientInput(client);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            String getMessage() {
                return message;
            }
            void setClient(int client) {
                this.client = client;
            }
        }
        MessageClass messageClass = new MessageClass();
        Thread thread = new Thread(messageClass);
        messageClass.setClient(1);
        thread.start();
        firstClient.sendMessage("{\"message\":\"sampleText1\"}");
        thread.join();
        String messageFromClient1 = messageClass.getMessage();
        Assertions.assertEquals("sampleText1", messageFromClient1);

        thread = new Thread(messageClass);
        messageClass.setClient(2);
        thread.start();
        secondClient.sendMessage("{\"message\":\"sampleText2\"}");
        thread.join();
        String messageFromClient2 = messageClass.getMessage();
        Assertions.assertEquals("sampleText2", messageFromClient2);
    }
}
