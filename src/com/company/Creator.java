package com.company;

import java.util.Scanner;

public class Creator {
    public void createWorkOrders() throws InterruptedException {
        // read input, create work orders and write as json files
        Scanner input = new Scanner(System.in);
        System.out.println("******* WELCOME TO THE WORK ORDER CREATOR *******");

        while(true) {
            System.out.println("WHO IS REQUESTING THIS WORK ORDER?");
            String requestor = input.nextLine();

            System.out.println("THANK YOU.");

            System.out.println("WHAT DO YOU WISH FOR?");
            String thing = input.nextLine();

            System.out.println("YOUR REQUEST HAS BEEN NOTED.");
            WorkOrder wo = new WorkOrder(thing, requestor);

            wo.persist();
        }
    }

    public static void main(String args[]) throws InterruptedException {
        Creator creator = new Creator();
        creator.createWorkOrders();
    }
}