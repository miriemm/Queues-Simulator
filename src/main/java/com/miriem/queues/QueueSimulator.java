package com.miriem.assignment2;

import com.miriem.assignment2.models.Client;
import com.miriem.assignment2.models.Queue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueueSimulator {

    private List<Client> clients;
    private List<Queue> queues;
    private int simulationTime;
    private int passedTime = 0;
    // This is used for outputting text into the output file
    private FileWriter fileWriter;

    public QueueSimulator(List<Client> clients, int noOfQueues, int simulationTime, String fileName) {
        this.clients = clients;
        this.queues = new ArrayList<>();
        // add as many new queues as needed, described by the noOfQueues parameter
        for (int i = 0; i < noOfQueues; i++) {
            queues.add(new Queue(i));
        }
        this.simulationTime = simulationTime;
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // this method starts the entire simulation in which clients are distributed to the created queues, each time a client arrives
    // he will be directed to the queue with the shortest waiting time.
    public void start() {
        // This will run as long as long as there are clients waiting and the time passed since we started the simulation is less
        // than the maximum simulation time
        while (!clients.isEmpty() && passedTime < simulationTime) {
            printStatus();
            try {
                updateClients();
                // After we updated the status of the waiting clients, wait for a second and increment the passedTime counter
                Thread.sleep(1000);
                passedTime++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        getAverageWaitingTime();
    }

    public int getQueueWithMinimumWaitingTime() {
        int minWaitingTime = Integer.MAX_VALUE;
        int minWaitingTimeQueueIndex = -1;
        for (int i = 0; i < queues.size(); i++) {
            int queueWaitingTime = queues.get(i).getWaitingTime();
            if (queueWaitingTime < minWaitingTime) {
                minWaitingTime = queueWaitingTime;
                minWaitingTimeQueueIndex = i;
            }
        }
        return minWaitingTimeQueueIndex;
    }

    public void getAverageWaitingTime() {
        double totalWaitingTime = 0f;
        try {
            // Notify every queue that it is time to close once the clients have been all processed
            for (Queue queue : queues) {
                queue.close();
            }
            while (!areQueuesEmpty()) {
                // While there are still clients waiting to be served at one of the queues,
                // print the status every second and increment the passedTime counter
                printStatus();
                Thread.sleep(1000);
                passedTime++;
            }
            for (Queue queue : queues) {
                // Since every queue, once it has finished processing clients, gets suspended (put on pause), we need to
                // resume the queue in order to stop the execution of the thread.
                queue.resumeQueue();
                totalWaitingTime += queue.getAverageWaitingTime();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(totalWaitingTime / queues.size());
        try {
            fileWriter.write(String.valueOf(totalWaitingTime / queues.size()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateClients() {
        // Make a copy from the current list of clients in the simulator to avoid removing elements while reading the list
        List<Client> clientsCopy = new ArrayList<>(clients);
        for (Client client : clientsCopy) {
            //If we find a client that has arrived
            if (client.getArrivalTime() == 0) {
                // Find the queue with the minimum waiting time
                Queue queue = queues.get(getQueueWithMinimumWaitingTime());
                //Add the client to the queue with the min waiting time
                queue.addClient(client);
                // This will happen only once when the first client will be added to the queue, since after we start
                // the thread, it will only be suspended, not killed/stopped
                if (!queue.isAlive()) {
                    queue.start();
                    // if the thread has been paused previously, resume the thread since a client has been added and needs processing
                } else if (queue.isPaused()) {
                    queue.resumeQueue();
                }
                // Remove client from remaining clients
                clients.remove(client);
            } else {
                client.decrementArrivalTime();
            }
        }
    }

    public boolean areQueuesEmpty() {
        for (Queue queue : queues) {
            if (!queue.getClients().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void printStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Time " + passedTime + "\n");
        stringBuilder.append("Waiting Clients: ");
        stringBuilder.append(printClientList(clients) + "\n");
        for (Queue queue : queues) {
            stringBuilder.append("Queue " + queue.getQueueId() + " " + printClientList(queue.getClients()) + "\n");
        }
        stringBuilder.append("\n");
        // Print to console
        System.out.println(stringBuilder.toString());
        try {
            // Print to the output file
            fileWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String printClientList(List<Client> clients) {
        StringBuilder clientsString = new StringBuilder();
        for (Client client : clients) {
            clientsString.append(client.toString());
        }
        if (clients.isEmpty()) {
            clientsString.append("Closed");
        }
        return clientsString.toString();
    }
}
