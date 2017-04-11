package com.company.daniel.lind.karlberg;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by daniellindkarlberg on 2016-11-30.
 */
public class ConsoleInput {
    private static final Logger log = Logger.getLogger(ConsoleInput.class.getName());
    UserInputHandler userInputHandler = new UserInputHandler(new ContactManager());
    UserInput userInput;

    public void startUp() {
        log.info("Console input startup");
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome!");
        System.out.println("type help for instructions on how to operate the address book");

        while (true) {

            String input = sc.nextLine();

            if ("quit".equals(input)) {
                sc.close();
                log.info("Scanner closed");
            }
            userInput = new UserInput(input.split(" "), input);
            userInputHandler.handle(userInput);
        }
    }

}

