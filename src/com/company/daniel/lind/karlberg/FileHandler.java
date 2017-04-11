package com.company.daniel.lind.karlberg;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by daniellindkarlberg on 2016-11-30.
 */
public class FileHandler {
    private static final Logger log = Logger.getLogger(FileHandler.class.getName());

   private Object lock1 = new Object();

    public void serialize(List<Contact> contacts) {
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;
        try {
            synchronized (lock1) {
                fileOut = new
                        FileOutputStream("contacts.ser");
                out = new
                        ObjectOutputStream(fileOut);
                out.writeObject(contacts);
                log.info("Contacts being serialized");
                out.close();
                fileOut.close();
                log.info("Output streams closed");
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error serializing to file", e);
        } finally {
            try {
                out.close();
                fileOut.close();
            } catch (IOException e) {
                log.log(Level.SEVERE, "Error closing output streams", e);
            }
        }
    }

    public List<Contact> deSerialize() {
        List contacts = null;
        FileInputStream is = null;
        ObjectInputStream ois = null;
        try {

            is = new FileInputStream("contacts.ser");
            ois = new ObjectInputStream(is);
            contacts = (List) ois.readObject();
            log.info("Contacts being de-serialized");
            ois.close();
            is.close();

        } catch (IOException | ClassNotFoundException e) {
            log.log(Level.SEVERE, "Error de-serializing from file ", e);
        } finally {
            try {
                ois.close();
                is.close();
                log.info("Input streams closed");
            } catch (IOException e) {
                log.log(Level.SEVERE, "Error closing input streams ", e);
            }
        }

        return contacts;
    }
}

