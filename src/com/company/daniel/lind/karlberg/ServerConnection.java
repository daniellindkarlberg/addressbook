package com.company.daniel.lind.karlberg;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by daniellindkarlberg on 2016-12-01.
 */
public class ServerConnection {
    private static final Logger log = Logger.getLogger(ServerConnection.class.getName());

    private List<Contact> remoteContacts;

    public ServerConnection() {
        log.info("Remote contact list created ");
        this.remoteContacts = new ArrayList<>();
    }


    public List<Contact> getRemoteContacts() {
        log.info("Remote contact list requested");
        return remoteContacts;
    }

    public void clearRemoteContacts() {
        log.info("Clear remote contact list");
        this.remoteContacts.clear();
    }


    public synchronized void serverCheck(String host, int port) {
        log.info("Checking if requested server is available");
        try (Socket s = new Socket(host, port)) {
            connect("getall" + "\n" + "exit" + "\n", host, port);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Server unavailable", e);
            System.out.println("[notification] remote contacts on server: " + host + " port: " + port + " is unavailable");
        }
    }


    private void connect(String request, String host, int port) {
        log.info("Establishing connection with the server");

        try (Socket socket = new Socket(host, port)) {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(request);
            bw.flush();

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;
            String[] info;


            while ((line = br.readLine()) != null) {
                log.info("Receiving data from server");
                info = line.split(" ");
                remoteContacts.add((new Contact(info[0], info[1], info[2], info[3])));

            }
            System.out.println("ouside while");

        } catch (IOException e) {
            log.log(Level.SEVERE, "Error I/O", e);
        }

        log.info("Connection with server closed");
    }
}

