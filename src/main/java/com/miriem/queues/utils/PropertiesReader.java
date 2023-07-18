package com.miriem.assignment2.utils;

import com.miriem.assignment2.models.Properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PropertiesReader {

    /**
     * The static keyword is used in this context due to the fact that we do not need to keep an instance
     * of the class Properties Reader, hence we do not need to call new PropertiesReader() and then call the method
     */

    public static Properties readProperties(String configFileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFileName));
            int noOfClients = Integer.parseInt(reader.readLine());
            int noOfQueues = Integer.parseInt(reader.readLine());
            int simulationTime = Integer.parseInt(reader.readLine());
            String[] arrivalTimes = reader.readLine().split(",");
            int minArrivalTime = Integer.parseInt(arrivalTimes[0]);
            int maxArrivalTime = Integer.parseInt(arrivalTimes[1]);
            String[] waitingTimes = reader.readLine().split(",");
            int minWaitingTime = Integer.parseInt(waitingTimes[0]);
            int maxWaitingTime = Integer.parseInt(waitingTimes[1]);
            reader.close();
            return new Properties(
                 noOfClients,
                 noOfQueues,
                 simulationTime,
                 minArrivalTime,
                 maxArrivalTime,
                 minWaitingTime,
                 maxWaitingTime
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

