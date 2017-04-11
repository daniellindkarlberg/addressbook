package com.company.daniel.lind.karlberg;

import java.util.logging.Logger;

/**
 * Created by daniellindkarlberg on 2016-11-30.
 */
public class UserInputHandler {

    private ContactManager contactManager;
    private static final Logger log = Logger.getLogger(ContactManager.class.getName());

    public UserInputHandler(ContactManager contactManager) {
        this.contactManager = contactManager;
    }

    public void handle(UserInput userInput) {

        try {
            switch (userInput.getCommand()) {

                case "add":
                    if (userInput.getParameterCount() > 3 || userInput.getParameterCount() < 3) {
                        log.fine("Invalid parameters from user" + userInput.getInputData());
                        System.out.println("Invalid parameters: " + userInput.getInputData() + " please enter firstname lastname email");

                        log.fine("User command: add");
                    } else {
                        contactManager.addNewContact(userInput.getParameters(0), userInput.getParameters(1), userInput.getParameters(2));
                    }
                    break;

                case "delete":
                    log.fine("User command: delete");
                    if (userInput.getParameterCount() > 1 || userInput.getParameterCount() < 1) {
                        log.fine("Invalid parameters from user" + userInput.getInputData());
                        System.out.println("Invalid parameters: " + userInput.getInputData() + " please enter a contact id");
                    } else {

                        contactManager.deleteContact(userInput.getParameters(0));
                    }
                    break;

                case "list":
                    log.fine("User command: list");
                    if (!(userInput.getParameterCount() > 0)) {
                        contactManager.listContacts();

                    } else {
                        log.fine("Invalid parameters from user" + userInput.getInputData());
                        System.out.println("Invalid parameters: " + userInput.getInputData() + " list should not contain any parameters");
                    }
                    break;

                case "search":
                    log.fine("User command: search" + ", searched for: " + userInput.getParameters(0));
                    contactManager.searchContacts(userInput.getParameters(0));

                    break;

                case "help":
                    log.fine("User command: help");
                    if (!(userInput.getParameterCount() > 0)) {
                        helpMenu();

                    } else {
                        log.fine("Invalid parameters from user" + userInput.getInputData());
                        System.out.println("Invalid parameters: " + userInput.getInputData() + " help should not contain any parameters");
                    }
                    break;

                case "quit":
                    log.fine("User command: quit");
                    if (!(userInput.getParameterCount() > 0)) {
                        contactManager.saveContactsToFile();
                        System.out.println("Contacts saved to file");
                        System.exit(0);
                    } else {
                        log.fine("Invalid parameters from user" + userInput.getInputData());
                        System.out.println("Invalid parameters: " + userInput.getInputData() + " quit should not contain any parameters ");
                    }
                    break;

                default:
                    log.fine("Invalid command" + userInput.getInputData());
                    System.out.println("Invalid command: " + userInput.getInputData() + " type help for instructions");
            }
        } catch (Exception e) {
            log.fine("Invalid parameters:" + userInput.getInputData());
            System.out.println("Invalid parameters: " + userInput.getInputData() + " this command needs parameters");
        }

    }

    private void helpMenu() {
        System.out.println();
        System.out.println("Type your command followed by space and parameter(s) and hit Enter");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Command   Input parameters");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("add       firstname lastname email \n" + "delete    Id (tip, list contacts and copy/paste the id)\n" +
                "search    first or last name( tip, you only need to enter the first letter(s)) \n" +
                "list      no parameters, lists existing contacts\n" + "quit      no parameters, exits application");
    }
}
