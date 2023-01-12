package com.example.checkers.client;

import org.json.JSONObject;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client extends Frame implements ActionListener, Runnable {
    Label msg;
    Label output;
    Button send;

    TextField input;
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    /**
     * Used to properly display the board
     * If =1 then numeration is normal
     * If =2 then numeration is reversed
     */
    private int player;
    Client() {
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        msg = new Label("Waiting for server...");
        input = new TextField(20);
        output = new Label();
        output.setBackground(Color.white);
        send = new Button("Send");
        send.addActionListener(this);
        setLayout(new GridLayout(4, 1));
        add(msg);
        add(input);
        add(send);
        add(output);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == send) {
            send();
        }
    }

    private void send(){
        out.println("{\"message\":\""+input.getText()+"\"}");
        msg.setText("Waiting for server response...");
        send.setEnabled(false);
        input.setText("");
        input.requestFocus();
    }

    private void receive(){
        try {
            String serverMessage = in.readLine();
            JSONObject json = new JSONObject(serverMessage);

            switch(json.getString("instruction")) {
                case "init" -> {
                    send.setEnabled(false);
                    player = json.getInt("value");
                }
                case "select_game_mode" -> {
                    if (json.getInt("player")==player) {
                        send.setEnabled(true);
                        msg.setText("Select the game mode");
                    } else {
                        send.setEnabled(false);
                        msg.setText("Wait for the other player to select the game mode");
                    }
                }
                case "move" -> {
                    if(json.getInt("player")==player) {
                        send.setEnabled(true);
                        msg.setText(json.getString("message"));
                    } else {
                        send.setEnabled(false);
                        msg.setText("Wait for opponent's move");
                    }
                }
                case "create_board" -> {
                    send.setEnabled(false);
                }
                case "draw_board" -> {
                    // TODO: reverse board for player 2
                    output.setText(json.getString("board_state"));
                }
                case "end_game" -> {
                    send.setEnabled(player == 1);
                    msg.setText(json.getString("winner"));
                }
                case "terminate" -> {
                    send.setEnabled(false);
                    msg.setText("Shutting down...");
                    try{ Thread.sleep(1000); }
                    catch(InterruptedException ex) {}
                    System.exit(0);
                }
            }
        }
        catch (IOException e) {
            System.out.println("Read failed"); System.exit(1);}
    }

    public void listenSocket() {
        try {
            socket = new Socket("localhost", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }


    private void receiveInitFromServer() {
        try {
            player = Integer.parseInt(in.readLine());
            msg.setText("Waiting for server signal");
            send.setEnabled(false);
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Client frame = new Client();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
        frame.listenSocket();
        //frame.receiveInitFromServer();
        frame.startThread();
    }

    private void startThread() {
        Thread playerThread = new Thread(this);
        playerThread.start();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                receive();
            }
        }
    }
}
