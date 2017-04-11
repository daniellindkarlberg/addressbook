package com.company.daniel.lind.karlberg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by daniellindkarlberg on 2016-11-30.
 */
public class UserInput {
    private static final Logger log = Logger.getLogger(UserInput.class.getName());
    private String command;
    private List<String> parameters;
    private String inputData;

    public UserInput(String[] inputDataSplit, String inputData) {
        this.inputData = inputData;
        command = inputDataSplit[0];
        parameters = new ArrayList<>();
        parameters.addAll(Arrays.asList(inputDataSplit).subList(1, inputDataSplit.length));
    }

    public String getCommand() {
log.info("Check user input command");
        return command;
    }

    public String getParameters(int i) {
log.info("Check user input parameters");
        return parameters.get(i);

    }

    public int getParameterCount() {
log.info("Check how many parameters");
        return parameters.size();
    }

    public String getInputData(){
log.info("Check user input");
        return "'" + inputData + "'";
    }
}

