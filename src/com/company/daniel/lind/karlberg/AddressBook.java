package com.company.daniel.lind.karlberg;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by daniellindkarlberg on 2016-11-30.
 */
public class AddressBook {
    private static final Logger log = Logger.getLogger(AddressBook.class.getName());

    private List<Contact> contacts = new ArrayList<>();


    public void initContacts(List<Contact> contacts) {
        log.info("Contact list initialized");
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        log.info("Contact list requested");
        return contacts;
    }


    public void addContact(Contact contact) {
        log.info("Contact added");
        contacts.add(contact);
    }


    public void deleteContact(int i) {
        log.info("Contact deleted");
        contacts.remove(i);
        System.out.println("Contact deleted");
    }


}