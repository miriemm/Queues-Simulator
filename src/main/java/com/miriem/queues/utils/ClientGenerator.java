package com.miriem.assignment2.utils;

import com.miriem.assignment2.models.Client;
import com.miriem.assignment2.models.Properties;

import java.util.ArrayList;
import java.util.List;

public class ClientGenerator {

    private Properties properties;

    public ClientGenerator(Properties properties) {
        this.properties = properties;
    }

    public List<Client> generateClients() {
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < properties.getNoOfClients(); i++) {
            int arrivalTime = (int) (Math.random() * ((properties.getMaxArrivalTime() - properties.getMinArrivalTime()) + 1)) + properties.getMinArrivalTime();
            int serviceTime = (int) (Math.random() * ((properties.getMaxServiceTime() - properties.getMinServiceTime()) + 1)) + properties.getMinServiceTime();
            // Once two random values for a client have been generated we can add a new client in the list
            clients.add(new Client(i, arrivalTime, serviceTime));
        }
        // return the list containing the generated clients
        return clients;
    }
}
