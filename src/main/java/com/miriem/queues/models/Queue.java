package com.miriem.assignment2.models;

import java.util.ArrayList;
import java.util.List;

public class Queue extends Thread {
    private List<Client> clients;

    private int id;
    // Flag representing if the simulation needs to stop once the clients have been processed or not
    private boolean simulationTerminated = false;
    private int clientsServed = 0;
    private int totalWaitingTime = 0;
    private final Object lock = this;
    private boolean isPaused = false;

    public Queue(int id) {
        this.clients = new ArrayList<>();
        this.id = id;
    }

    @Override
    public synchronized void run() {
        super.run();
        while (!clients.isEmpty() || !simulationTerminated) {
            try {
                Client client = clients.get(0);
                client.decrementServiceTime();
                if (client.getServiceTime() == 0) {
                    clients.remove(client);
                    totalWaitingTime += client.getWaitingTime();
                    clientsServed++;
                }
                updateWaitingTimes();
                sleep(1000);
                if (clients.isEmpty()) {
                    pauseThread();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public int getWaitingTime() {
        int waitingTime = 0;
        for (Client client : clients) {
            waitingTime += client.getServiceTime();
        }
        return waitingTime;
    }

    public void updateWaitingTimes() {
        for (Client client : clients) {
            client.incrementWaitingTime();
        }
    }

    public Double getAverageWaitingTime() {
        return (double) totalWaitingTime / clientsServed;
    }

    public void close() {
        simulationTerminated = true;
    }

    private void pauseThread() {
        isPaused = true;
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resumeQueue() {
        isPaused = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public List<Client> getClients() {
        return clients;
    }

    public int getQueueId() {
        return this.id + 1;
    }
}
