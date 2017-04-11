package com.company.daniel.lind.karlberg;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by daniellindkarlberg on 2016-11-30.
 */
public class Contact implements Serializable, Comparable<Contact> {
    private static final Logger log = Logger.getLogger(Contact.class.getName());

    private String contactId;
    private String firstName;
    private String lastName;
    private String eMail;


    public Contact(String contactId, String firstName, String lastName, String eMail) {

        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
    }


    public String getContactId() {
        log.info("Contact id requested");
        return contactId;
    }

    public String getFirstName() {
        log.info("First name requested");
        return firstName;
    }

    public String getLastName() {
        log.info("last name requested");
        return lastName;
    }

    public String geteMail() {
        log.info("email requested");
        return eMail;
    }

    @Override
    public int compareTo(Contact o) {
        log.info("CompareTo method invoked ");
        return firstName.compareToIgnoreCase(o.getFirstName());
    }

    @Override
    public String toString() {
        log.info("Contacts toString invoked");
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eMail='" + eMail + '\'' +
                ", contactId='" + contactId + '\'' +
                '}';
    }
}