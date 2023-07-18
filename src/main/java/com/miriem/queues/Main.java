package com.miriem.assignment2;

import com.miriem.assignment2.models.Client;
import com.miriem.assignment2.models.Properties;
import com.miriem.assignment2.utils.ClientGenerator;
import com.miriem.assignment2.utils.PropertiesReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Ensure that the execution parameters numbers is the correct one i.e. input file and output file
        if (args.length == 2) {
            String inTextFile = args[0];
            String outTextFile = args[1];
            Properties properties = PropertiesReader.readProperties(inTextFile);
            if (properties != null) {
                //If the properties were read correctly, we instantiate the client generator.
                // Since most of the properties used in the client Generator are contained in the properties class
                // we will pass the entire object to the constructor of the generator instead of just the needed
                // properties for generating said clients.
                ClientGenerator clientGenerator = new ClientGenerator(properties);
                List<Client> clients = clientGenerator.generateClients();
                // Once the clients are created, we can then create the queue simulator which will handle
                // the queue allocation and client distribution to the queues.
                QueueSimulator queueSimulator = new QueueSimulator(clients, properties.getNoOfQueues(), properties.getSimulationTime(), outTextFile);
                queueSimulator.start();
            }
        } else {
            System.out.println("Please input the input file path and the output file path.");
        }
    }
}
