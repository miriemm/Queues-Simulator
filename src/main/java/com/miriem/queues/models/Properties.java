package com.miriem.assignment2.models;

public class Properties {

    private int noOfClients;
    private int noOfQueues;
    private int simulationTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minServiceTime;
    private int maxServiceTime;

    public Properties(
            int noOfClients,
            int noOfQueues,
            int simulationTime,
            int minArrivalTime,
            int maxArrivalTime,
            int minServiceTime,
            int maxServiceTime
    ) {
        this.noOfClients = noOfClients;
        this.noOfQueues = noOfQueues;
        this.simulationTime = simulationTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
    }

    public int getNoOfClients() {
        return noOfClients;
    }

    public int getNoOfQueues() {
        return noOfQueues;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public int getMinArrivalTime() {
        return minArrivalTime;
    }

    public int getMaxArrivalTime() {
        return maxArrivalTime;
    }

    public int getMinServiceTime() {
        return minServiceTime;
    }

    public int getMaxServiceTime() {
        return maxServiceTime;
    }
}
