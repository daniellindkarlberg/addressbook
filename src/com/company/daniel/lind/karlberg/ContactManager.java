package com.company.daniel.lind.karlberg;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by daniellindkarlberg on 2016-11-30.
 */
public class ContactManager {
    private static final Logger log = Logger.getLogger(ContactManager.class.getName());

    private AddressBook addressBook;
    private FileHandler fileHandler;
    private ServerConnection serverConnection;
    private List<Contact> tempList;

    public ContactManager() {
        this.addressBook = new AddressBook();
        this.fileHandler = new FileHandler();
        this.tempList = new ArrayList<>();
        this.serverConnection = new ServerConnection();
        fileCheck();
        initAutoSaveContactsToFile();
        loadRemoteContacts();

    }


    public void addNewContact(String firstName, String lastName, String eMail) {
        Contact contact = new Contact(UUID.randomUUID().toString(), firstName, lastName, eMail);
        addressBook.addContact(contact);
        tempList.add(contact);
        System.out.println("New contact added");
        log.info("Contact added");
    }


    public void deleteContact(String id) {
log.info("Attempt to delete contact");
        for (int i = 0; i < tempList.size(); i++) {

            if (tempList.get(i).getContactId().contains(id)) {

                if (isLocalContact(id)) {
                    tempList.remove(i);
                    return;
                }
                if (!isLocalContact(id)) {
                    log.info("Cant delete remote contact");
                    System.out.println("Cant delete remote contact");
                    return;
                }
            }
        }
        log.info("No contact with matching id");
        System.out.println("No contact with this id");
    }


    private boolean isLocalContact(String id) {
        boolean local = false;
        log.info("Checking if contact is local");
        for (int i = 0; i < addressBook.getContacts().size(); i++) {

            if (addressBook.getContacts().get(i).getContactId().contains(id)) {
                addressBook.deleteContact(i);
                local = true;
            }
        }
        if (local) {
            return true;
        } else return false;
    }


    public void listContacts() {

        if (tempList.isEmpty()) {
            log.info("No contacts yet");
            System.out.println("No contacts yet");

        } else {
            log.info("Listing contacts");
            listIterator();

        }
    }

    public void searchContacts(String searchString) {
        sortContacts();
        boolean found = false;
        log.info("Searching contacts");
        if (tempList.isEmpty()) {
            log.info("No contacts yet");
            System.out.println("No contacts yet");
            return;
        }
        for (Contact contact : tempList) {
            if (contact.getFirstName().toLowerCase().startsWith(searchString.toLowerCase())
                    || contact.getLastName().toLowerCase().startsWith(searchString.toLowerCase())) {

                System.out.printf("Id: %s\nFirst Name: %s\nLast Name: %s\nEmail: %s\n\n",
                        contact.getContactId(),
                        contact.getFirstName(),
                        contact.getLastName(),
                        contact.geteMail());
                found = true;
            }
        }
        if (!found) {
            log.info("No match found");
            System.out.println("No match found");
        }
    }

    public void saveContactsToFile() {
        log.info("Attempt to serialize contacts");
        fileHandler.serialize(addressBook.getContacts());
    }

    private void fileCheck() {
        log.info("Checking if file exists");
        File f = new File("contacts.ser");
        if (f.exists() && f.isFile()) {
            loadContactsFromFile();
        } else {
            log.info("No file, create a new contact list");
            addressBook.initContacts(new ArrayList<>());
        }
    }

    private void loadContactsFromFile() {

        Thread LoadFromFile = new Thread(() -> {

            log.info("Attempt to de-serialize contacts");
            addressBook.initContacts(fileHandler.deSerialize());
            tempList.addAll(addressBook.getContacts());
        }
        );
        LoadFromFile.setPriority(10);
        LoadFromFile.start();
    }


    private void loadRemoteContacts() {

        Thread server1 = new Thread(() -> {
            log.info("Setting address and port for server 1");
            serverConnection.serverCheck("localhost", 61616);
            tempList.addAll(serverConnection.getRemoteContacts());
            serverConnection.clearRemoteContacts();
            }
            );
            server1.setPriority(7);
            server1.start();

        Thread server2 = new Thread(() -> {
            log.info("Setting address and port for server 2");
            serverConnection.serverCheck("localhost", 1616);
            tempList.addAll(serverConnection.getRemoteContacts());
            serverConnection.clearRemoteContacts();
        }
        );
        server2.setPriority(3);
        server2.start();
    }


    private void initAutoSaveContactsToFile() {
        log.info("Auto-save initialized");
        Thread newThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, "Error putting thread to sleep", e);
                }
                saveContactsToFile();
            }
        }
        );
        newThread.start();
    }

    private void sortContacts() {

        log.info("Contacts sorted");
        Collections.sort(tempList);
    }

    private void listIterator() {
        sortContacts();
        for (Contact aTempList : tempList) {
            System.out.printf("Id: %s\nFirst Name: %s\nLast Name: %s\nEmail: %s\n\n",
                    aTempList.getContactId(),
                    aTempList.getFirstName(),
                    aTempList.getLastName(),
                    aTempList.geteMail());
        }
    }
}