package org.example;

import org.example.server.Server;

import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) {


       Server server=new Server(2020);


        try {
            server.startServer();
            server.acceptRequests();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Error ",e);
        }

    }



}