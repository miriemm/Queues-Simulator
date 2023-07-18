package com.miriem.assignment2.models;

public class Client {
    //attributes
    private int id;
    private int arrivalTime;
    private int serviceTime;
    private int waitingTime;

    //constructor
    //initializez val de la atribute
    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.waitingTime = 0;
    }
    //pt encaps
    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void decrementServiceTime() {
        serviceTime--;
    }

    public void decrementArrivalTime(){
        arrivalTime--;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void incrementWaitingTime() {
        waitingTime++;
    }

    //Custom implementation of the String representation of the object
    @Override
    public String toString() {
        return "(" + id + "," + arrivalTime + "," + serviceTime + "); ";
    }
}
