package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Map;

public class Processor {
    Map<Status, Set<WorkOrder>> orders = new HashMap<>();

    public Processor() {
        orders.putIfAbsent(Status.INITIAL, new HashSet<>());
        orders.putIfAbsent(Status.ASSIGNED, new HashSet<>());
        orders.putIfAbsent(Status.IN_PROGRESS, new HashSet<>());
        orders.putIfAbsent(Status.DONE, new HashSet<>());
    }

    public void processWorkOrders() throws FileNotFoundException {
        while (true) {
            // print out our map.
            System.out.println(orders);
            readIt();
            moveIt();

            sleepForFiveSeconds();
            System.out.println(orders);
        }
    }

    private void sleepForFiveSeconds() {
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void moveIt() {
        moveOrders(Status.IN_PROGRESS, Status.DONE);
        moveOrders(Status.ASSIGNED, Status.IN_PROGRESS);
        moveOrders(Status.INITIAL, Status.ASSIGNED);
    }

    private void moveOrders(Status initial, Status next) {
        // set of orders from the initial status
        Set<WorkOrder> initialSet = orders.get(initial);

        // set of orders from the next status
        Set<WorkOrder> nextSet    = orders.get(next);

        // for each order in the initial set,
        for (WorkOrder order : initialSet) {
            // update that order's status
            order.setStatus(next);

            // add that order to the next set.
            nextSet.add(order);
        }

        // replace the hashset in the initial status
        orders.put(initial, new HashSet<>());
    }

    private void readIt() throws FileNotFoundException {
        // read the json files into WorkOrders and put in map
        File currentDirectory = new File(".");
        File files[] = currentDirectory.listFiles();
        for (File f : files) {
            if (f.getName().endsWith(".json")) {
                // f is a reference to a json file

                // 1. use an object mapper to read in a WorkOrder
                ObjectMapper mapper = new ObjectMapper();

                // 1a. Read in the entire contents of the file
                Scanner fileScanner = new Scanner(f);

                // \\Z is END OF FILE DELIMETER
                fileScanner.useDelimiter("\\Z");

                // since we updated our delimeter to END OF FILE
                // when we call next, we get the _entire_ file.
                String fileContents = fileScanner.next();

                try {
                    WorkOrder wo = mapper.readValue(fileContents, WorkOrder.class);

                    // 2. put the work order in our orders map.
                    // get the set at the current status and
                    // add the work order to the set.
                    orders.get(wo.getStatus()).add(wo);

                    // 3. delete the file
                    f.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String args[]) throws FileNotFoundException {
        Processor processor = new Processor();
        processor.processWorkOrders();
    }
}
