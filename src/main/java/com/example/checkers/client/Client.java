package com.example.checkers.client;

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
        msg = new Label("Status");
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
        out.println(input.getText());
        msg.setText("Waiting for server response...");
        send.setEnabled(false);
        input.setText("");
        input.requestFocus();
    }

    private void receive(){
        try {
            String status = in.readLine();
            String command = in.readLine();
            msg.setText(command);

            switch(status) {
                case "RECEIVE" -> send.setEnabled(false);
                case "SEND" -> send.setEnabled(true);
                case "TERMINATE" -> {
                    send.setEnabled(false);
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
        frame.receiveInitFromServer();
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
